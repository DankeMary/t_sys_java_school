package net.tsystems.controller;

import net.tsystems.bean.PassengerBean;
import net.tsystems.service.PassengerService;
import net.tsystems.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
GET  /passengers
GET  /passengers/add
GET  /passengers/{id}
POST /passengers
GET  /passengers/{id}/update
POST /passengers/{id}  (update)
POST /passengers/{id}/delete
*/
@Controller
public class PassengersController {
    private PassengerService passengerService;

    @RequestMapping(value = "/passengers", method = RequestMethod.GET)
    public String passengers(@RequestParam(required = false, defaultValue = "") String page,
                             Model model) {
        int navPagesQty = passengerService.countPages(GeneralUtils.MAX_PAGE_RESULT);
        int pageInt = GeneralUtils.parseIntForPage(page, 1, navPagesQty);

        List<PassengerBean> psngrs = passengerService.getAll(pageInt, GeneralUtils.MAX_PAGE_RESULT);

        model.addAttribute("passengers", psngrs);
        model.addAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd-MM-yyy"));
        model.addAttribute("navPagesQty", navPagesQty);
        model.addAttribute("currentPage", pageInt);
        return "passengers";
    }

    @RequestMapping(value = "/passengers/add", method = RequestMethod.GET)
    public String showAddPassengerForm(Model model) {
        PassengerBean passenger = new PassengerBean();
        model.addAttribute("passengerForm", passenger);
        return "addPassenger";
    }

    @RequestMapping(value = "/passengers/{id}", method = RequestMethod.GET)
    public String showUser(@PathVariable("id") int id, Model model) {
        PassengerBean passenger = passengerService.getPassenger(id);
        model.addAttribute("passenger", passenger);
        return "profile";
    }

    @RequestMapping(value = "/passengers", method = RequestMethod.POST)
    public String addPassenger(@ModelAttribute("passengerForm") @Validated PassengerBean passenger,
                               BindingResult result, Model model,
                               final RedirectAttributes redirectAttributes) {
        Map<String, String> errors = new HashMap<>();
        passengerService.validate(passenger, errors);
        if (result.hasErrors() || !errors.isEmpty()) {
            model.addAttribute("birthdayError", errors.get("birthdayError"));
            return "addPassenger";
        } else {
            passengerService.create(passenger);
            return "redirect:/passengers";
        }
    }

    @RequestMapping(value = "/passengers/{id}/update", method = RequestMethod.GET)
    public String showUpdatePassengerForm(@PathVariable("id") int id, Model model) {
        PassengerBean passenger = passengerService.getPassenger(id);
        model.addAttribute("passengerForm", passenger);
        return "editPassenger";
    }

    @RequestMapping(value = "/passengers/{id}", method = RequestMethod.POST)
    public String updatePassenger(@PathVariable("id") int id,
                                  @ModelAttribute("passengerForm") @Validated PassengerBean passenger,
                                   BindingResult result, Model model,
                                   final RedirectAttributes redirectAttributes) {
        Map<String, String> errors = new HashMap<>();
        passengerService.validate(passenger, errors);
        if (result.hasErrors() || !errors.isEmpty()) {
            model.addAttribute("birthdayError", errors.get("birthdayError"));
            return "editPassenger";
        } else {
            passengerService.update(passenger);
            return "redirect:/passengers";
        }
    }

    @RequestMapping(value = "/passengers/{id}/delete")
    public String deletePassenger(@PathVariable("id") int id,
                                  final RedirectAttributes redirectAttributes) {
        passengerService.delete(id);
        return "redirect:/passengers";
    }

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        }
        return userName;
    }

    //Autowired
    @Autowired
    public void setPassengerService(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

}
