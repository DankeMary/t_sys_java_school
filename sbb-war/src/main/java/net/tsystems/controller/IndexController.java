package net.tsystems.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String newTrain(Model model) {
        return "redirect:/passengers";
    }

    @RequestMapping(value = "/future", method = RequestMethod.GET)
    public String future(Model model) {
        return "future";
    }
}
