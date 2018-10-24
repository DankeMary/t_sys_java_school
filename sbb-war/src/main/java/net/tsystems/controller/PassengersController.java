package net.tsystems.controller;

import net.tsystems.bean.PassengerBean;
import net.tsystems.beanmapper.PassengerBeanMapper;
import net.tsystems.beanmapper.PassengerBeanMapperImpl;
import net.tsystems.service.PassengerService;
import net.tsystems.validator.PassengerValidator;
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
    private PassengerValidator validator = new PassengerValidator();
    private PassengerBeanMapper mapper = new PassengerBeanMapperImpl();

    @Autowired
    public void setPassengerService(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @RequestMapping(value = "/passengers", method = RequestMethod.GET)
    public String passengers(Model model) {
        List<PassengerBean> psngrs = mapper.passengerListToBeanList(passengerService.getAll());
        model.addAttribute("passengers", psngrs);
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
        PassengerBean passenger = mapper.passengerToBean(passengerService.getPassenger(id));
        model.addAttribute("passenger", passenger);
        return "profile";
    }

    @RequestMapping(value = "/passengers", method = RequestMethod.POST)
    public String addPassenger(@ModelAttribute("passengerForm") @Validated PassengerBean passenger,
                               BindingResult result, Model model,
                               final RedirectAttributes redirectAttributes) {

        validator.validate(passenger, result);
        if (result.hasErrors()) {
            return "addPassenger";
        } else {
            passengerService.create(mapper.passengerToSO(passenger));
            return "redirect:/passengers";
        }
    }

    @RequestMapping(value = "/passengers/{id}/update", method = RequestMethod.GET)
    public String showUpdatePassengerForm(@PathVariable("id") int id, Model model) {
        PassengerBean passenger = mapper.passengerToBean(passengerService.getPassenger(id));
        model.addAttribute("passengerForm", passenger);
        return "editPassenger";
    }

    @RequestMapping(value = "/passengers/{id}", method = RequestMethod.POST)
    public String updatePassenger(@PathVariable("id") int id,
                                  @ModelAttribute("passengerForm") @Validated PassengerBean passenger,
                                   BindingResult result, Model model,
                                   final RedirectAttributes redirectAttributes) {

        validator.validate(passenger, result);
        if (result.hasErrors()) {
            return "editPassenger";
        } else {
            passengerService.update(mapper.passengerToSO(passenger));
            return "redirect:/passengers";
        }
    }

    @RequestMapping(value = "/passengers/{id}/delete")
    public String deletePassenger(@PathVariable("id") int id,
                                  final RedirectAttributes redirectAttributes) {
        passengerService.delete(id);
        return "redirect:/passengers";

    }
}
