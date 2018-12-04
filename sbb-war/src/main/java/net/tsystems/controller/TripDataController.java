package net.tsystems.controller;

import net.tsystems.bean.BuyTicketsForm;
import net.tsystems.bean.UserBeanExpanded;
import net.tsystems.service.*;
import net.tsystems.util.UtilsClass;
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
import java.util.Map;


@Controller
public class TripDataController {

    private static final int MAX_TICKETS_QTY = 10;

    private TripDataService tripDataService;
    private PassengerService passengerService;
    private TrainService trainService;
    private TicketService ticketService;
    private UserService userService;

    @RequestMapping(value = "/trains/find", method = RequestMethod.GET)
    public String findTickets(@RequestParam(required = false, defaultValue = "") String fromDay,
                              @RequestParam(required = false, defaultValue = "") String fromTime,
                              @RequestParam(required = false, defaultValue = "") String toDay,
                              @RequestParam(required = false, defaultValue = "") String toTime,
                              @RequestParam(required = false, defaultValue = "") String fromStation,
                              @RequestParam(required = false, defaultValue = "") String toStation,
                              @RequestParam(required = false, defaultValue = "") String page,
                              Model model) {

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
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd-MM-yyy"));
        return "tickets";
    }

    @RequestMapping(value = "/user/buyTicket", method = RequestMethod.GET)
    public String showBuyTicketForm(@RequestParam(required = false, defaultValue = "") String fromJourney,
                                    @RequestParam(required = false, defaultValue = "") String toJourney,
                                    Model model) {
        if (fromJourney.trim().isEmpty() || toJourney.trim().isEmpty())
            return "redirect:/trains/find";

        Map<String, String> metaData = new HashMap<>();
        tripDataService.makeMetaDataForBuyingTickets(fromJourney, toJourney, metaData);
        model.addAttribute("trainNumber", metaData.get("trainNumber"));
        model.addAttribute("trainId", trainService.getTrainByNumber(Integer.valueOf(metaData.get("trainNumber"))).getId());
        model.addAttribute("fromMetaInfo", metaData.get("fromMetaInfo"));
        model.addAttribute("toMetaInfo", metaData.get("toMetaInfo"));
        model.addAttribute("ticketPrice", metaData.get("ticketPrice"));

        model.addAttribute("ticketForm", new BuyTicketsForm());
        model.addAttribute("ticketsQty", 1);
        model.addAttribute("fromJourneyId", fromJourney);
        model.addAttribute("toJourneyId", toJourney);
        model.addAttribute("loggedinuser", getPrincipal());

        return "buyTicket";
    }

    @RequestMapping(value = "/user/buyTicket", method = RequestMethod.POST)
    public String buyTicket(@ModelAttribute("ticketForm") @Validated BuyTicketsForm ticketsData,
                            BindingResult result, Model model,
                            final RedirectAttributes redirectAttributes) {

        int psngrsQty = passengerService.countCompleteInfo(ticketsData.getPassengers());
        Map<String, String> errors = new HashMap<>();
        passengerService.validateList(ticketsData.getPassengers(), errors);
        boolean passengersHaveCompleteAndValidInfo = passengerService.passengersHaveCompleteAndValidInfo(ticketsData.getPassengers());

        if (result.hasErrors() &&
                !passengersHaveCompleteAndValidInfo ||
                !errors.isEmpty() ||
                (psngrsQty < 1) || (psngrsQty > MAX_TICKETS_QTY)) {
            model.addAttribute("ticketForm", ticketsData);

            Map<String, String> metaData = new HashMap<>();
            tripDataService.makeMetaDataForBuyingTickets(Integer.toString(ticketsData.getFromJourneyId()),
                    Integer.toString(ticketsData.getToJourneyId()),
                    metaData);
            model.addAttribute("trainNumber", metaData.get("trainNumber"));
            model.addAttribute("trainId", trainService.getTrainByNumber(Integer.valueOf(metaData.get("trainNumber"))).getId());
            model.addAttribute("fromMetaInfo", metaData.get("fromMetaInfo"));
            model.addAttribute("toMetaInfo", metaData.get("toMetaInfo"));
            model.addAttribute("ticketPrice", metaData.get("ticketPrice"));

            model.addAttribute("ticketsQty", ticketsData.getPassengers().size());
            model.addAttribute("fromJourneyId", ticketsData.getFromJourneyId());
            model.addAttribute("toJourneyId", ticketsData.getToJourneyId());
            model.addAttribute("loggedinuser", getPrincipal());

            if (((result.hasErrors() && !passengersHaveCompleteAndValidInfo) || !errors.isEmpty()) && psngrsQty <= MAX_TICKETS_QTY)
                model.addAttribute("possibleErrors", passengerService.possibleValidationErrors());
            else if (psngrsQty < 1)
                model.addAttribute("psngrInfo", "Enter info about at least 1 passenger");

            return "buyTicket";
        }

        if (!tripDataService.buyTickets(ticketsData, getPrincipal())) {
            model.addAttribute("noTickets", "No enough tickets available for this trip");
            return "buyTicket";
        }

        return "redirect:/user/" + getPrincipal();
    }

    @RequestMapping(value = {"/user/{username}/tickets/{ticket_id}/delete"}, method = RequestMethod.GET)
    public String deleteTicket(@PathVariable("username") String username,
                               @PathVariable("ticket_id") int ticketId, Model model) {
        ticketService.deleteTicket(username, ticketId);

        int navPagesQty = ticketService.countUserTicketsAfterNow(username, UtilsClass.MAX_PAGE_RESULT);
        int pageInt = 1;

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

    //@Autowired
    @Autowired
    public void setTripDataService(TripDataService tripDataService) {
        this.tripDataService = tripDataService;
    }

    @Autowired
    public void setPassengerService(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @Autowired
    public void setTrainService(TrainService trainService) {
        this.trainService = trainService;
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
