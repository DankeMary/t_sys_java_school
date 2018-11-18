package net.tsystems.controller;

import net.tsystems.bean.*;
import net.tsystems.service.TripDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;


@Controller
public class TripDataController {

    private TripDataService tripDataService;

    @RequestMapping(value = "/trains/find", method = RequestMethod.GET)
    public String findTickets(@RequestParam(required = false, defaultValue = "") String fromDay,
                              @RequestParam(required = false, defaultValue = "") String fromTime,
                              @RequestParam(required = false, defaultValue = "") String toDay,
                              @RequestParam(required = false, defaultValue = "") String toTime,
                              @RequestParam(required = false, defaultValue = "") String fromStation,
                              @RequestParam(required = false, defaultValue = "") String toStation,
                              Model model) {
        //TODO !!!!
        //TODO check that all are empty
        if (!fromDay.trim().isEmpty() &&
                !fromTime.trim().isEmpty() &&
                !toDay.trim().isEmpty() &&
                !toTime.trim().isEmpty() &&
                !fromStation.trim().isEmpty() &&
                !toStation.trim().isEmpty()) {
            //search tickets
            model.addAttribute("ticketsAvailable", tripDataService.getDataForSection(fromDay, fromTime,
                    toDay, toTime,
                    fromStation, toStation));
        } else if (!(fromDay.trim().isEmpty() &&
                fromTime.trim().isEmpty() &&
                toDay.trim().isEmpty() &&
                toTime.trim().isEmpty() &&
                fromStation.trim().isEmpty() &&
                toStation.trim().isEmpty())) {
            model.addAttribute("dataError", "All fields are mandatory");
        }
        model.addAttribute("fromDay", fromDay); //tripData
        model.addAttribute("fromTime", fromTime); //route
        model.addAttribute("toDay", toDay);  //tripData
        model.addAttribute("toTime", toTime);  //route
        model.addAttribute("fromStation", fromStation); //
        model.addAttribute("toStation", toStation);
        model.addAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return "tickets";
    }

    @RequestMapping(value = "/buyTicket", method = RequestMethod.GET)
    public String showBuyTicketForm(@RequestParam(required = false, defaultValue = "") String fromJourney,
                                    @RequestParam(required = false, defaultValue = "") String toJourney,
                                    Model model) {
        if (fromJourney.trim().isEmpty() || toJourney.trim().isEmpty())
            return "redirect:/trains/find";

        model.addAttribute("ticketForm", new BuyTicketsForm());
        model.addAttribute("fromJourneyId", fromJourney);
        model.addAttribute("toJourneyId", toJourney);

        return "buyTicket";
    }

    @RequestMapping(value = "/buyTicket", method = RequestMethod.POST)
    public String buyTicket(@ModelAttribute("ticketForm") @Validated BuyTicketsForm ticketsData,
                            BindingResult result, Model model,
                            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors())
            return "buyTickets";

        if (!tripDataService.buyTickets(ticketsData))
        {
            //TODO add attribute to the JSP
            model.addAttribute("notTickets", "No enough tickets available");
            return "reditect:/trains/find";
        }

        return "redirect:/trains";
    }
    //@Autowired
    @Autowired
    public void setTripDataService(TripDataService tripDataService) {
        this.tripDataService = tripDataService;
    }
}
