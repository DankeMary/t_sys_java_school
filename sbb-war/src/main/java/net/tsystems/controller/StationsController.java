package net.tsystems.controller;


import net.tsystems.util.UtilsClass;
import net.tsystems.bean.StationBean;
import net.tsystems.service.StationService;
import net.tsystems.service.TripDataService;
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
import java.util.LinkedList;
import java.util.List;

/**
 * GET /stations
 * GET /stations/add
 * POST /stations
 * GET /stations/{id}/update
 * POST /stations/{id}  (update)
 * POST /stations/{id}/delete
 */
@Controller
public class StationsController {

    private StationService stationService;
    private TripDataService tripDataService;


    @RequestMapping(value = "/worker/stations", method = RequestMethod.GET)
    public String stations(@RequestParam(required = false, defaultValue = "") String page,
                           Model model) {
        int navPagesQty = stationService.countPages(UtilsClass.MAX_PAGE_RESULT);
        int pageInt = UtilsClass.parseIntForPage(page, 1, navPagesQty);

        List<StationBean> stations = stationService.getAll(pageInt, UtilsClass.MAX_PAGE_RESULT);
        model.addAttribute("stations", stations);
        model.addAttribute("navPagesQty", navPagesQty);
        model.addAttribute("currentPage", pageInt);
        model.addAttribute("loggedinuser", getPrincipal());
        return "stations";
    }

    @RequestMapping(value = "/worker/stations/add", method = RequestMethod.GET)
    public String showAddStationForm(Model model) {
        StationBean station = new StationBean();
        model.addAttribute("stationForm", station);
        model.addAttribute("loggedinuser", getPrincipal());
        return "addStation";
    }

    @RequestMapping(value = "/worker/stations", method = RequestMethod.POST)
    public String addStation(@ModelAttribute("stationForm") @Validated StationBean station,
                             BindingResult result, Model model,
                             final RedirectAttributes redirectAttributes) {

        stationService.validate(station, true, result);
        if (result.hasErrors()) {
            model.addAttribute("loggedinuser", getPrincipal());
            return "addStation";
        } else {
            stationService.create(station);
            return "redirect:/worker/stations";
        }
    }

    @RequestMapping(value = "/worker/stations/{id}/update", method = RequestMethod.GET)
    public String showUpdateStationForm(@PathVariable("id") int id, Model model) {
        StationBean station = stationService.getStationById(id);
        model.addAttribute("stationForm", station);
        model.addAttribute("loggedinuser", getPrincipal());
        return "editStation";
    }

    @RequestMapping(value = "/worker/stations/{id}", method = RequestMethod.POST)
    public String updateStation(@PathVariable("id") int id,
                                @ModelAttribute("stationForm") @Validated StationBean station,
                                BindingResult result, Model model,
                                final RedirectAttributes redirectAttributes) {

        stationService.validate(station, false, result);
        if (result.hasErrors()) {
            model.addAttribute("loggedinuser", getPrincipal());
            return "editStation";
        } else {
            stationService.update(station);
            return "redirect:/worker/stations";
        }
    }

    @RequestMapping(value = "/worker/stations/{id}/delete")
    public String deleteStation(@PathVariable("id") int id,
                                final RedirectAttributes redirectAttributes) {
        stationService.delete(id);
        return "redirect:/worker/stations";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public String showStationSchedule(@RequestParam(name = "stationName", required = false, defaultValue = "") String stationName, Model model) {
        String trimmedStationName = stationName.trim();

        if (!trimmedStationName.equals(""))
            if (stationService.getStationByName(trimmedStationName) != null) {
                model.addAttribute("schedule", tripDataService.getScheduleForStation(trimmedStationName, UtilsClass.MAX_PAGE_RESULT));
                model.addAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd-MM-yyy"));
            } else {
                model.addAttribute("noStationMessage", "No station with such name found");
            }
        else {
            model.addAttribute("noStationMessage", "");
            model.addAttribute("schedule", new LinkedList<>());
        }
        model.addAttribute("stationName", trimmedStationName);
        model.addAttribute("loggedinuser", getPrincipal());
        return "schedule";
    }

    @Autowired
    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    @Autowired
    public void setTripDataService(TripDataService tripDataService) {
        this.tripDataService = tripDataService;
    }

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        }
        //TODO Is this ok?
        /*else {
            userName = principal.toString();
        }*/
        return userName;
    }
}
