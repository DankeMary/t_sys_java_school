package net.tsystems.controller;


import net.tsystems.UtilsClass;
import net.tsystems.bean.StationBean;
import net.tsystems.beanmapper.StationBeanMapper;
import net.tsystems.beanmapper.StationBeanMapperImpl;
import net.tsystems.service.StationService;
import net.tsystems.service.TripDataService;
import net.tsystems.validator.StationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    @RequestMapping(value = "/stations", method = RequestMethod.GET)
    public String stations(Model model) {
        List<StationBean> stations = stationService.getAll();
        model.addAttribute("stations", stations);
        return "stations";
    }

    @RequestMapping(value = "/stations/add", method = RequestMethod.GET)
    public String showAddStationForm(Model model) {
        StationBean station = new StationBean();
        model.addAttribute("stationForm", station);
        return "addStation";
    }

    @RequestMapping(value = "/stations", method = RequestMethod.POST)
    public String addStation(@ModelAttribute("stationForm") @Validated StationBean station,
                             BindingResult result, Model model,
                             final RedirectAttributes redirectAttributes) {

        stationService.validate(station, true, result);
        if (result.hasErrors()) {
            return "addStation";
        } else {
            stationService.create(station);
            return "redirect:/stations";
        }
    }

    @RequestMapping(value = "/stations/{id}/update", method = RequestMethod.GET)
    public String showUpdateStationForm(@PathVariable("id") int id, Model model) {
        StationBean station = stationService.getStationById(id);
        model.addAttribute("stationForm", station);
        return "editStation";
    }

    @RequestMapping(value = "/stations/{id}", method = RequestMethod.POST)
    public String updateStation(@PathVariable("id") int id,
                                @ModelAttribute("stationForm") @Validated StationBean station,
                                BindingResult result, Model model,
                                final RedirectAttributes redirectAttributes) {

        stationService.validate(station, false, result);
        if (result.hasErrors()) {
            return "editStation";
        } else {
            stationService.update(station);
            return "redirect:/stations";
        }
    }

    @RequestMapping(value = "/stations/{id}/delete")
    public String deleteStation(@PathVariable("id") int id,
                                final RedirectAttributes redirectAttributes) {
        stationService.delete(id);
        return "redirect:/stations";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public String showStationSchedule(@RequestParam(name = "stationName", required = false, defaultValue = "") String stationName, Model model) {
        String trimmedStationName = stationName.trim();

        if (!trimmedStationName.equals(""))
            if (stationService.getStationByName(trimmedStationName) != null) {
                model.addAttribute("schedule", tripDataService.getScheduleForStation(trimmedStationName, UtilsClass.MAX_PAGE_RESULT));

            } else {
                model.addAttribute("noStationMessage", "No station with such name found");
            }
        else {
            model.addAttribute("noStationMessage", "");
            model.addAttribute("schedule", new LinkedList<>());
        }
        model.addAttribute("stationName", trimmedStationName);
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
}
