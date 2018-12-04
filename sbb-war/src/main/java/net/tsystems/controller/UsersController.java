package net.tsystems.controller;

import net.tsystems.bean.UserBean;
import net.tsystems.bean.UserBeanExpanded;
import net.tsystems.service.TicketService;
import net.tsystems.service.UserService;
import net.tsystems.util.UtilsClass;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UsersController {

    private UserService userService;
    private TicketService ticketService;

    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "registered", required = false) String registered,
                            Model model) {
        UserBean user = new UserBean();
        model.addAttribute("userForm", user);

        if (isCurrentAuthenticationAnonymous()) {
            if (registered != null && !registered.isEmpty())
                model.addAttribute("signup", "You have registered successfully");
            return "login";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public String newUser(Model model) {
        UserBean user = new UserBean();
        model.addAttribute("userForm", user);
        model.addAttribute("edit", false);
        return "signup";
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("userForm") @Validated UserBean user, BindingResult result,
                           Model model) {

        Map<String, String> errors = new HashMap<>();
        userService.validate(user, errors);
        if (result.hasErrors() || !errors.isEmpty()) {
            model.addAttribute("uniqueUsername", errors.get("uniqueUsername"));
            user.setPassword(null);
            return "signup";
        }

        userService.create(user, "");
        model.addAttribute("signup", "You have registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        return "redirect:/login?registered=ok";
    }

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

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        }
        return userName;
    }

    //This method returns true if users is already authenticated, else false.
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }

    //Autowired
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
}
