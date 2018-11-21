package net.tsystems.controller;

import net.tsystems.bean.*;
import net.tsystems.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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


    @RequestMapping(value = "/trains", method = RequestMethod.GET)
    public String trains(Model model) {
        List<TrainBean> trains = trainService.getAll();
        model.addAttribute("trains", trains);
        return "trains";
    }

    @RequestMapping(value = "/trains/add", method = RequestMethod.GET)
    public String showAddTrainForm(Model model) {
        CreateTrainForm train = new CreateTrainForm();
        model.addAttribute("trainForm", train);
        model.addAttribute("pathErrorMessage", "");
        return "addTrain";
    }

    @RequestMapping(value = "/trains", method = RequestMethod.POST)
    public String addTrain(@ModelAttribute("trainForm") @Validated CreateTrainForm train,
                           BindingResult result, Model model,
                           final RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();
        trainService.validate(train.getTrain(), true, errors);
        routeService.validatePrimitive(train.getPrimitivePath(), errors);
        //routeService.validatePrimitive(null, errors);
        //todo customErrors list
        if (result.hasErrors() || !errors.isEmpty()) {
            model.addAttribute("shortPath", errors.get("shortPath"));
            model.addAttribute("wrongPath", errors.get("wrongPath"));
            model.addAttribute("dataMissing", errors.get("dataMissing"));
            model.addAttribute("invalidStations", errors.get("invalidStations"));
            model.addAttribute("numberNonUnique", errors.get("numberNonUnique"));
            model.addAttribute("capacityCannotUpdate", errors.get("capacityCannotUpdate"));

            return "addTrain";
        } else {
            trainService.create(train.getTrain(), routeService.generatePathMap(train.getPrimitivePath()));
            return "redirect:/trains";
        }
    }

    @RequestMapping(value = "/trains/{id}/update", method = RequestMethod.GET)
    public String showUpdateTrainForm(@PathVariable("id") int id, Model model) {
        TrainBean train = trainService.getTrainById(id);
        model.addAttribute("trainForm", train);
        return "editTrain";
    }

    @RequestMapping(value = "/trains/{id}", method = RequestMethod.POST)
    public String updateTrain(@PathVariable("id") int id,
                              @ModelAttribute("trainForm") @Validated TrainBean train,
                              BindingResult result, Model model,
                              final RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();
        trainService.validate(train, false, errors);
        if (result.hasErrors()) {
            return "editTrain";
        } else {
            trainService.update(train);
            return "redirect:/trains";
        }
    }

    @RequestMapping(value = "/trains/{id}/delete")
    public String deleteTrain(@PathVariable("id") int id,
                              final RedirectAttributes redirectAttributes) {
        trainService.delete(id);
        return "redirect:/trains";
    }

    @RequestMapping(value = "/trains/{id}", method = RequestMethod.GET)
    public String showTrainDetails(@PathVariable("id") int id, Model model) {
        TrainBeanExpanded trainBean = trainService.getTrainWithPath(id);
        model.addAttribute("trainData", trainBean);
        return "trainDetails";
    }

    @RequestMapping(value = "/trains/{id}/journeys", method = RequestMethod.GET)
    public String journeys(@PathVariable("id") int id, Model model) {
        //TODO Order by date
        List<JourneyBean> journeys = tripDataService.getFirstJourneysByTrainNotCancelled(id, true);
        model.addAttribute("journeys", journeys);
        model.addAttribute("trainId", id);
        model.addAttribute("journeyForm", new JourneyBean());
        return "journeys";
    }

    @RequestMapping(value = "/trains/{id}/journeys/newJourney", method = RequestMethod.GET)
    public String newJourney(@PathVariable("id") int id, Model model) {

        model.addAttribute("journey", new JourneyBean());
        model.addAttribute("trainId", id);
        return "addJourney";
    }

    @RequestMapping(value = "/trains/{id}/journeys", method = RequestMethod.POST)
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

            List<JourneyBean> journeys = tripDataService.getFirstJourneysByTrainNotCancelled(id, true);
            model.addAttribute("journeys", journeys);
            model.addAttribute("trainId", id);
            model.addAttribute("journeyForm", journey);
            return "journeys";
        } else {
            tripDataService.createAll(journey);
            //TODO !!!!
            return "redirect:/trains/{id}/journeys";
        }
    }

    @RequestMapping(value = "/trains/{train_id}/journeys/{journey_id}/cancel", method = RequestMethod.GET)
    public String cancelJourney(@PathVariable("train_id") int trainId,
                                @PathVariable("journey_id") int journeyId,
                                Model model,
                                final RedirectAttributes redirectAttributes) {
        Map<String, String> errors = new HashMap<>();

        tripDataService.validateCancellation(trainId, journeyId, errors);
        if (!errors.isEmpty()) {
            model.addAttribute("invalidTrip", errors.get("invalidTrip"));
            model.addAttribute("ticketsSold", errors.get("ticketsSold"));
        }
        else tripDataService.cancelJourney(trainId, journeyId);
        //return "redirect:/trains/{train_id}/journeys";
        //TODO repetitive code from GET journeys - is that ok?
        List<JourneyBean> journeys = tripDataService.getFirstJourneysByTrainNotCancelled(trainId, true);
        model.addAttribute("journeys", journeys);
        model.addAttribute("trainId", trainId);
        model.addAttribute("journeyForm", new JourneyBean());
        return "journeys";
    }

    @RequestMapping(value = "/trains/{train_id}/journeys/{journey_id}/passengers", method = RequestMethod.GET)
    public String getRegisteredPassengers(@PathVariable("train_id") int trainId,
                                          @PathVariable("journey_id") int journeyId,
                                          Model model) {

        List<TicketBean> tickets = ticketService.getTicketsForTrain(trainId, journeyId);
        model.addAttribute("tickets", tickets);
        model.addAttribute("localDateFormat", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        model.addAttribute("localDateTimeFormat", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        //TODO What if there are no with such IDs
        model.addAttribute("departureDay", tripDataService.getById(journeyId).getTripDeparture());
        model.addAttribute("trainNumber", trainService.getTrainById(trainId).getNumber());

        return "showPassengers";
    }

    //Ajax related
    @RequestMapping(value = "/getStationsForTrain", method = RequestMethod.GET)
    public @ResponseBody
    List<StationBean> getStations(@RequestParam String stationName) {
        List<StationBean> stationBeans = stationService.getAll();
        List<StationBean> result = new ArrayList<StationBean>();

        //TODO Java 8 Streams?
        for (StationBean station : stationBeans) {
            if (station.getName().toLowerCase().contains(stationName.toLowerCase())) {
                result.add(station);
            }
        }
        return result;
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
