package net.tsystems.controller;

import net.tsystems.UtilsClass;
import net.tsystems.bean.*;
import net.tsystems.service.PassengerService;
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

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


@Controller
public class TripDataController {

    private static final int MAX_TICKETS_QTY = 10;

    private TripDataService tripDataService;
    private PassengerService passengerService;

    @RequestMapping(value = "/trains/find", method = RequestMethod.GET)
    public String findTickets(@RequestParam(required = false, defaultValue = "") String fromDay,
                              @RequestParam(required = false, defaultValue = "") String fromTime,
                              @RequestParam(required = false, defaultValue = "") String toDay,
                              @RequestParam(required = false, defaultValue = "") String toTime,
                              @RequestParam(required = false, defaultValue = "") String fromStation,
                              @RequestParam(required = false, defaultValue = "") String toStation,
                              @RequestParam(required = false, defaultValue = "") String page,
                              Model model) {

        //TODO check that if some fields are empty and some are not then still not valid
        if (!fromDay.trim().isEmpty() &&
                !fromTime.trim().isEmpty() &&
                !toDay.trim().isEmpty() &&
                !toTime.trim().isEmpty() &&
                !fromStation.trim().isEmpty() &&
                !toStation.trim().isEmpty()) {
            //search tickets
            int navPagesQty = tripDataService.countDataForSectionPages(fromDay, fromTime,
                    toDay, toTime,
                    fromStation, toStation,
                    UtilsClass.MAX_PAGE_RESULT);
            int pageInt = UtilsClass.parseIntForPage(page, 1, navPagesQty);

            model.addAttribute("ticketsAvailable", tripDataService.getDataForSection(fromDay, fromTime,
                    toDay, toTime,
                    fromStation, toStation,
                    pageInt, UtilsClass.MAX_PAGE_RESULT));

            model.addAttribute("navPagesQty", navPagesQty);
            model.addAttribute("currentPage", pageInt);

        } else if (!(fromDay.trim().isEmpty() &&
                fromTime.trim().isEmpty() &&
                toDay.trim().isEmpty() &&
                toTime.trim().isEmpty() &&
                fromStation.trim().isEmpty() &&
                toStation.trim().isEmpty())) {
            model.addAttribute("dataError", "All fields are mandatory");
        }
        model.addAttribute("fromDay", fromDay);
        model.addAttribute("fromTime", fromTime);
        model.addAttribute("toDay", toDay);
        model.addAttribute("toTime", toTime);
        model.addAttribute("fromStation", fromStation);
        model.addAttribute("toStation", toStation);
        model.addAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd-MM-yyy"));
        return "tickets";
    }

    @RequestMapping(value = "/buyTicket", method = RequestMethod.GET)
    public String showBuyTicketForm(@RequestParam(required = false, defaultValue = "") String fromJourney,
                                    @RequestParam(required = false, defaultValue = "") String toJourney,
                                    Model model) {
        if (fromJourney.trim().isEmpty() || toJourney.trim().isEmpty())
            return "redirect:/trains/find";

        //TODO Add Train Number
        Map<String, String> metaData = new HashMap<>();
        tripDataService.makeMetaDataForBuyingTickets(fromJourney, toJourney, metaData);
        model.addAttribute("trainNumber", metaData.get("trainNumber"));
        model.addAttribute("fromMetaInfo", metaData.get("fromMetaInfo"));
        model.addAttribute("toMetaInfo", metaData.get("toMetaInfo"));
        model.addAttribute("ticketPrice", metaData.get("ticketPrice"));

        model.addAttribute("ticketForm", new BuyTicketsForm());
        model.addAttribute("ticketsQty", 1);
        model.addAttribute("fromJourneyId", fromJourney);
        model.addAttribute("toJourneyId", toJourney);

        return "buyTicket";
    }

    @RequestMapping(value = "/buyTicket", method = RequestMethod.POST)
    public String buyTicket(@ModelAttribute("ticketForm") @Valid BuyTicketsForm ticketsData,
                            BindingResult result, Model model,
                            final RedirectAttributes redirectAttributes) {

        int psngrsQty = passengerService.countCompleteInfo(ticketsData.getPassengers());
        if (result.hasErrors() &&
                !passengerService.passengersHaveCompleteInfo(ticketsData.getPassengers()) ||
                (psngrsQty < 1) || (psngrsQty > MAX_TICKETS_QTY)) {
            model.addAttribute("ticketForm", ticketsData);

            Map<String, String> metaData = new HashMap<>();
            tripDataService.makeMetaDataForBuyingTickets(Integer.toString(ticketsData.getFromJourneyId()),
                    Integer.toString(ticketsData.getToJourneyId()),
                    metaData);
            model.addAttribute("trainNumber", metaData.get("trainNumber"));
            model.addAttribute("fromMetaInfo", metaData.get("fromMetaInfo"));
            model.addAttribute("toMetaInfo", metaData.get("toMetaInfo"));
            model.addAttribute("ticketPrice", metaData.get("ticketPrice"));

            model.addAttribute("ticketsQty", ticketsData.getPassengers().size());
            model.addAttribute("fromJourneyId", ticketsData.getFromJourneyId());
            model.addAttribute("toJourneyId", ticketsData.getToJourneyId());
            if (psngrsQty < 1)
                model.addAttribute("psngrInfo", "Enter info about at least 1 passenger");
            else if (result.hasErrors() && psngrsQty <= MAX_TICKETS_QTY)
                model.addAttribute("possibleErrors", passengerService.possibleValidationErrors());
            return "buyTicket";
        }

        if (!tripDataService.buyTickets(ticketsData)) {
            model.addAttribute("noTickets", "No enough tickets available for this trip");
            return "buyTicket";
        }

        return "redirect:/trains";
    }

    //@Autowired
    @Autowired
    public void setTripDataService(TripDataService tripDataService) {
        this.tripDataService = tripDataService;
    }

    @Autowired
    public void setPassengerService(PassengerService passengerService) {
        this.passengerService = passengerService;
    }
}
