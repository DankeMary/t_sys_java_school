package net.tsystems.controller;

import net.tsystems.util.GeneralUtils;
import net.tsystems.bean.*;
import net.tsystems.service.*;
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
import java.util.*;

/**
 * GET /trains
 * GET /trains/add
 * POST /trains
 * GET /trains/{id}/update
 * POST /trains/{id}  (update)
 * POST /trains/{id}/delete
 */
@Controller
public class TrainController {

    private TripService tripService;
    private RouteService routeService;
    private TripDataService tripDataService;
    private TrainService trainService;
    private StationService stationService;
    private TicketService ticketService;


    @RequestMapping(value = "/worker/trains", method = RequestMethod.GET)
    public String trains(@RequestParam(required = false, defaultValue = "") String page,
                         Model model) {

        int navPagesQty = trainService.countPages(GeneralUtils.MAX_PAGE_RESULT);
        int pageInt = GeneralUtils.parseIntForPage(page, 1, navPagesQty);

        List<TrainBean> trains = trainService.getAll(pageInt, GeneralUtils.MAX_PAGE_RESULT);

        model.addAttribute("trains", trains);
        model.addAttribute("navPagesQty", navPagesQty);
        model.addAttribute("currentPage", pageInt);
        model.addAttribute("loggedinuser", getPrincipal());
        return "trains";
    }

    @RequestMapping(value = "/worker/trains/add", method = RequestMethod.GET)
    public String showAddTrainForm(Model model) {
        CreateTrainForm train = new CreateTrainForm();
        model.addAttribute("trainForm", train);
        model.addAttribute("routesQty", 1);
        model.addAttribute("pathErrorMessage", "");
        model.addAttribute("loggedinuser", getPrincipal());
        return "addTrain";
    }

    @RequestMapping(value = "/worker/trains", method = RequestMethod.POST)
    public String addTrain(@ModelAttribute("trainForm") @Validated CreateTrainForm train,
                           BindingResult result, Model model,
                           final RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();
        trainService.validate(train.getTrain(), true, errors);
        routeService.validatePrimitive(train.getPrimitivePath(), errors);
        if (result.hasErrors() || !errors.isEmpty()) {
            model.addAttribute("trainForm", train);
            model.addAttribute("shortPath", errors.get("shortPath"));
            model.addAttribute("wrongPath", errors.get("wrongPath"));
            model.addAttribute("dataMissing", errors.get("dataMissing"));
            model.addAttribute("routesQty", train.getPrimitivePath().size());
            model.addAttribute("invalidStations", errors.get("invalidStations"));
            model.addAttribute("numberNonUnique", errors.get("numberNonUnique"));
            model.addAttribute("capacityCannotUpdate", errors.get("capacityCannotUpdate"));
            model.addAttribute("loggedinuser", getPrincipal());
            return "addTrain";
        } else {
            trainService.create(train.getTrain(), routeService.generatePathMap(train.getPrimitivePath()));
            return "redirect:/worker/trains";
        }
    }

    @RequestMapping(value = "/worker/trains/{id}/update", method = RequestMethod.GET)
    public String showUpdateTrainForm(@PathVariable("id") int id, Model model) {
        if (!trainService.canUpdate(id)) {
            TrainBeanExpanded trainBean = trainService.getTrainWithPath(id);
            model.addAttribute("trainData", trainBean);
            model.addAttribute("tripPlanned", "Couldn't update: trips are planned already!");
            model.addAttribute("loggedinuser", getPrincipal());
            return "trainDetails";
        }

        TrainBean train = trainService.getTrainById(id);
        model.addAttribute("trainForm", train);
        model.addAttribute("loggedinuser", getPrincipal());
        return "editTrain";
    }

    @RequestMapping(value = "/worker/trains/{id}", method = RequestMethod.POST)
    public String updateTrain(@PathVariable("id") int id,
                              @ModelAttribute("trainForm") @Validated TrainBean train,
                              BindingResult result, Model model,
                              final RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();
        trainService.validate(train, false, errors);
        if (result.hasErrors() || !errors.isEmpty()) {
            model.addAttribute("loggedinuser", getPrincipal());
            if (errors.get("tripPlanned") != null) {
                TrainBeanExpanded trainBean = trainService.getTrainWithPath(id);
                model.addAttribute("trainData", trainBean);
                model.addAttribute("tripPlanned", errors.get("tripPlanned"));
                return "trainDetails";
            }
            model.addAttribute("numberNonUnique", errors.get("numberNonUnique"));
            model.addAttribute("capacityCannotUpdate", errors.get("capacityCannotUpdate"));
            model.addAttribute("loggedinuser", getPrincipal());
            return "editTrain";
        } else {
            trainService.update(train);
            return "redirect:/worker/trains/" + id;
        }
    }

    @RequestMapping(value = "/worker/trains/{id}/delete")
    public String deleteTrain(@PathVariable("id") int id,
                              Model model,
                              final RedirectAttributes redirectAttributes) {
        Map<String, String> errors = new HashMap<>();
        trainService.validateDeletion(id, errors);
        if (!errors.isEmpty()) {
            TrainBeanExpanded trainBean = trainService.getTrainWithPath(id);
            model.addAttribute("tripPlanned", errors.get("tripPlanned"));
            model.addAttribute("trainData", trainBean);
            model.addAttribute("loggedinuser", getPrincipal());
            return "trainDetails";
        }
        trainService.delete(id);
        return "redirect:/worker/trains";
    }

    @RequestMapping(value = "/worker/trains/{id}", method = RequestMethod.GET)
    public String showTrainDetails(@PathVariable("id") int id, Model model) {
        TrainBeanExpanded trainBean = trainService.getTrainWithPath(id);
        model.addAttribute("trainData", trainBean);
        model.addAttribute("loggedinuser", getPrincipal());
        return "trainDetails";
    }

    @RequestMapping(value = "/worker/trains/{id}/journeys", method = RequestMethod.GET)
    public String journeys(@PathVariable("id") int id,
                           @RequestParam(required = false, defaultValue = "") String page,
                           Model model) {

        int navPagesQty = tripDataService.countFirstAfterNowByTrainPages(id, GeneralUtils.MAX_PAGE_RESULT);
        int pageInt = GeneralUtils.parseIntForPage(page, 1, navPagesQty);

        List<JourneyBean> journeys = tripDataService.getFirstJourneysByTrainNotCancelled(id, pageInt, GeneralUtils.MAX_PAGE_RESULT);

        model.addAttribute("journeys", journeys);
        model.addAttribute("trainId", id);
        model.addAttribute("journeyForm", new JourneyBean());
        model.addAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd-MM-yyy"));
        model.addAttribute("navPagesQty", navPagesQty);
        model.addAttribute("currentPage", pageInt);
        model.addAttribute("loggedinuser", getPrincipal());
        return "journeys";
    }

    @RequestMapping(value = "/worker/trains/{id}/journeys", method = RequestMethod.POST)
    public String addNewJourney(@PathVariable("id") int id,
                                @ModelAttribute("journey") @Validated JourneyBean journey,
                                BindingResult result, Model model,
                                final RedirectAttributes redirectAttributes) {
        Map<String, String> errors = new HashMap<>();
        journey.setTrip(tripService.getTripByTrainId(id));
        tripDataService.validateJourney(journey, errors);
        if (result.hasErrors() || !errors.isEmpty()) {
            model.addAttribute("invalidTrip", errors.get("invalidTrip"));
            model.addAttribute("depDayError", errors.get("depDayError"));
            model.addAttribute("journeyExists", errors.get("journeyExists"));

            int navPagesQty = tripDataService.countFirstAfterNowByTrainPages(id, GeneralUtils.MAX_PAGE_RESULT);
            int pageInt = 1;

            List<JourneyBean> journeys = tripDataService.getFirstJourneysByTrainNotCancelled(id, pageInt, GeneralUtils.MAX_PAGE_RESULT);

            model.addAttribute("journeys", journeys);
            model.addAttribute("trainId", id);
            model.addAttribute("journeyForm", journey);
            model.addAttribute("navPagesQty", navPagesQty);
            model.addAttribute("currentPage", pageInt);
            model.addAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd-MM-yyy"));
            model.addAttribute("loggedinuser", getPrincipal());
            return "journeys";
        } else {
            tripDataService.createJourney(journey);
            return "redirect:/worker/trains/{id}/journeys";
        }
    }

    @RequestMapping(value = "/worker/trains/{train_id}/journeys/{journey_id}/cancel", method = RequestMethod.GET)
    public String cancelJourney(@PathVariable("train_id") int trainId,
                                @PathVariable("journey_id") int journeyId,
                                Model model,
                                final RedirectAttributes redirectAttributes) {
        Map<String, String> errors = new HashMap<>();

        tripDataService.validateCancellation(trainId, journeyId, errors);
        if (!errors.isEmpty()) {
            model.addAttribute("invalidTrip", errors.get("invalidTrip"));
            model.addAttribute("ticketsSold", errors.get("ticketsSold"));
            int navPagesQty = tripDataService.countFirstAfterNowByTrainPages(trainId, GeneralUtils.MAX_PAGE_RESULT);
            int pageInt = 1;

            List<JourneyBean> journeys = tripDataService.getFirstJourneysByTrainNotCancelled(trainId, pageInt, GeneralUtils.MAX_PAGE_RESULT);

            model.addAttribute("journeys", journeys);
            model.addAttribute("trainId", trainId);
            model.addAttribute("journeyForm", new JourneyBean());
            model.addAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd-MM-yyy"));
            model.addAttribute("navPagesQty", navPagesQty);
            model.addAttribute("currentPage", pageInt);
            model.addAttribute("loggedinuser", getPrincipal());
            return "journeys";
        }
        else
            tripDataService.cancelJourney(trainId, journeyId);

        return "redirect:/worker/trains/" + trainId + "/journeys";
    }

    @RequestMapping(value = "/user/trains/{train_id}/journeys/{journey_id}", method = RequestMethod.GET)
    public String showJourneyDetails(@PathVariable("train_id") int trainId,
                                @PathVariable("journey_id") int journeyId,
                                Model model,
                                final RedirectAttributes redirectAttributes) {

        List<TripDataBean> journeyDetails = tripDataService.getTrainJourneyDetails(trainId, journeyId);
        TrainBean train = trainService.getTrainById(trainId);
        TripDataBean tdBean = tripDataService.getById(journeyId);

        model.addAttribute("tripDepDay", tdBean == null ? null : tdBean.getTripDeparture());
        model.addAttribute("train", train);
        model.addAttribute("journeyDetails", journeyDetails);
        model.addAttribute("trainId", trainId);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("localDateFormat", DateTimeFormatter.ofPattern("dd-MM-yyy"));
        return "journeyDetails";
    }

    @RequestMapping(value = "/worker/trains/{train_id}/journeys/{journey_id}/passengers", method = RequestMethod.GET)
    public String getRegisteredPassengers(@PathVariable("train_id") int trainId,
                                          @PathVariable("journey_id") int journeyId,
                                          @RequestParam(required = false, defaultValue = "") String page,
                                          Model model) {

        int navPagesQty = ticketService.countTicketsForTrainSold(trainId, journeyId, GeneralUtils.MAX_PAGE_RESULT);
        int pageInt = GeneralUtils.parseIntForPage(page, 1, navPagesQty);

        List<TicketBean> tickets = ticketService.getTicketsForTrainSold(trainId, journeyId, pageInt, GeneralUtils.MAX_PAGE_RESULT);

        model.addAttribute("tickets", tickets);

        model.addAttribute("localDateFormat", DateTimeFormatter.ofPattern("dd-MM-yyy"));
        model.addAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm"));

        model.addAttribute("departureDay", tripDataService.getById(journeyId).getTripDeparture());
        model.addAttribute("trainNumber", trainService.getTrainById(trainId).getNumber());

        model.addAttribute("trainId", trainId);
        model.addAttribute("journeyId", journeyId);

        model.addAttribute("navPagesQty", navPagesQty);
        model.addAttribute("currentPage", pageInt);
        model.addAttribute("loggedinuser", getPrincipal());
        return "showPassengers";
    }

    @RequestMapping(value = "/getStationsForTrain", method = RequestMethod.GET)
    public @ResponseBody
    List<StationBean> getStations(@RequestParam String stationName) {
        List<StationBean> stationBeans = stationService.getAll();
        List<StationBean> result = new ArrayList<>();

        for (StationBean station : stationBeans) {
            if (station.getName().toLowerCase().contains(stationName.toLowerCase())) {
                result.add(station);
            }
        }
        return result;
    }

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        }
        return userName;
    }


    @Autowired
    public void setTrainService(TrainService trainService) {
        this.trainService = trainService;
    }

    @Autowired
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    @Autowired
    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    @Autowired
    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    @Autowired
    public void setTripDataService(TripDataService tripDataService) {
        this.tripDataService = tripDataService;
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
}
