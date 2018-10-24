package net.tsystems.controller;


import net.tsystems.bean.StationBean;
import net.tsystems.beanmapper.StationBeanMapper;
import net.tsystems.beanmapper.StationBeanMapperImpl;
import net.tsystems.service.StationService;
import net.tsystems.validator.StationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
GET /stations
GET /stations/add
POST /stations
GET /stations/{id}/update
POST /stations/{id}  (update)
POST /stations/{id}/delete
*/
@Controller
public class StationsController {
    private StationService stationService;
    private StationValidator validator = new StationValidator();
    private StationBeanMapper mapper = new StationBeanMapperImpl();

    @Autowired
    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    @RequestMapping(value = "/stations", method = RequestMethod.GET)
    public String stations(Model model) {
        List<StationBean> stations = mapper.stationListToBeanList(stationService.getAll());
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

        if (stationService.getStationByName(station.getName()) != null)
            result.rejectValue("name", "NonUnique", "Station with such name already exists");
        else
            validator.validate(station, result);
        if (result.hasErrors()) {
            return "addStation";
        } else {
            stationService.create(mapper.stationToSO(station));
            return "redirect:/stations";
        }
    }

    @RequestMapping(value = "/stations/{id}/update", method = RequestMethod.GET)
    public String showUpdateStationForm(@PathVariable("id") int id, Model model) {
        StationBean station = mapper.stationToBean(stationService.getStationById(id));
        model.addAttribute("stationForm", station);
        return "editStation";
    }

    @RequestMapping(value = "/stations/{id}", method = RequestMethod.POST)
    public String updateStation(@PathVariable("id") int id,
                                @ModelAttribute("stationForm") @Validated StationBean station,
                                BindingResult result, Model model,
                                final RedirectAttributes redirectAttributes) {
        if (!stationService.isUniqueByName(station.getId(), station.getName()))
            result.rejectValue("name", "NonUnique", "Station with such name already exists");
        else
            validator.validate(station, result);
        if (result.hasErrors()) {
            return "editStation";
        } else {
            stationService.update(mapper.stationToSO(station));
            return "redirect:/stations";
        }
    }

    @RequestMapping(value = "/stations/{id}/delete")//, method = RequestMethod.POST)
    public String deleteStation(@PathVariable("id") int id,
                                final RedirectAttributes redirectAttributes) {
        stationService.delete(id);
        return "redirect:/stations";
    }
}
