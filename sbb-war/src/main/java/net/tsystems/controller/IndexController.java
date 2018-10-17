package net.tsystems.controller;

import net.tsystems.service.PassengerService;
import net.tsystems.serviceobject.PassengerSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
public class IndexController {

    private PassengerService passengerService;

    @Autowired
    public void setPassengerService(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<PassengerSO> psngrs = passengerService.getAll();
        model.addAttribute("list", psngrs);
        return "index";
    }
}
