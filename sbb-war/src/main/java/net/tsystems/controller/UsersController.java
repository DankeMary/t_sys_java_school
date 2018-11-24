package net.tsystems.controller;

import net.tsystems.UtilsClass;
import net.tsystems.bean.UserBean;
import net.tsystems.bean.UserBeanExpanded;
import net.tsystems.service.TicketService;
import net.tsystems.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UsersController {

    @Autowired
    UserService userService;
    @Autowired
    TicketService ticketService;

    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;


    //TODO Not needed for now
    /*@RequestMapping(value = "/users", method = RequestMethod.GET)
    public String listUsers(Model model) {

        List<UserBean> users = userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("loggedinuser", getPrincipal());
        //TODO page
        return "users";
    }*/

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        UserBean user = new UserBean();
        model.addAttribute("userForm", user);

        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/passengers";
        }
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public String newUser(Model model) {
        UserBean user = new UserBean();
        model.addAttribute("userForm", user);
        model.addAttribute("edit", false);
        //model.addAttribute("loggedinuser", getPrincipal());
        return "signup";
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("userForm") @Validated UserBean user, BindingResult result,
                           Model model) {

        Map<String, String> errors = new HashMap<>();
        userService.validate(user, errors);
        if (result.hasErrors() || !errors.isEmpty()) {
            //TODO add properties from errors
            model.addAttribute("uniqueUsername", errors.get("uniqueUsername"));
            user.setPassword(null);
            return "signup";
        }

        /*if (!userService.isUniqueUsername(user.getUsername(), user.getId())) {
            FieldError ssoError = new FieldError("user", "username", "User with such username already exists");
            result.addError(ssoError);
            return "signup";
        }*/

        userService.create(user, "");

        //TODO Is it needed?
        //TODO You have been successfully signed up
        /*model.addAttribute("loggedinuser", getPrincipal());*/
        return "redirect:/login";
    }

    //TODO Is needed?
    @RequestMapping(value = {"/user/{id}/update"}, method = RequestMethod.GET)
    public String editUser(@PathVariable int id, Model model) {
        UserBean user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        //TODO make another page?
        return "signup";
    }

    //TODO Is needed?
    @RequestMapping(value = {"/user/{id}"}, method = RequestMethod.POST)
    public String updateUser(@Validated UserBean user, BindingResult result,
                             Model model, @PathVariable int id) {

        //TODO How to check that new password is fine?
        if (result.hasErrors()) {
            //TODO add properties from errors
            model.addAttribute("haha", "lala");
            return "userProfile";
        }

        userService.update(user);

        //TODO Is it needed?
        model.addAttribute("success", "User " + user.getUsername() + " updated successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        return "userProfile";
    }

    //TODO Delete yourself? Make logout?
    @RequestMapping(value = {"/user/{id}/delete"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable int id,
                             final RedirectAttributes redirectAttributes) {
        userService.delete(id);
        return "redirect:/users";
    }

    //This method handles Access-Denied redirect.
    @RequestMapping(value = "/access_denied", method = RequestMethod.GET)
    public String accessDeniedPage(Model model) {
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("message", "Access denied");
        return "error";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public String userProfile(@PathVariable String username,
                              @RequestParam(required = false, defaultValue = "") String page,
                              Model model) {
        int navPagesQty = ticketService.countUserTicketsAfterNow(username, UtilsClass.MAX_PAGE_RESULT);
        int pageInt = UtilsClass.parseIntForPage(page, 1, navPagesQty);

        UserBeanExpanded userProfile = userService.getUserProfile(username, pageInt, UtilsClass.MAX_PAGE_RESULT);

        model.addAttribute("userData", userProfile);
        model.addAttribute("navPagesQty", navPagesQty);
        model.addAttribute("currentPage", pageInt);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd-MM-yyy"));
        return "userProfile";
    }

    //This method returns the principal[user-name] of logged-in user.
    private String getPrincipal() {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    //This method returns true if users is already authenticated [logged-in], else false.
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
}
