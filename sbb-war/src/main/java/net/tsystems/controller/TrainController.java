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

        List<String> customErrors = new LinkedList<>();
        trainService.validate(train.getTrain(), true, result);
        routeService.validatePrimitive(train.getPrimitivePath(), customErrors);
        //todo customErrors list
        if (result.hasErrors() || !customErrors.isEmpty()) {
            model.addAttribute("pathErrors", customErrors);
            //stationsData = new HashMap<>();
            return "addTrain";
        } else {
            trainService.create(train.getTrain(), routeService.generatePathMapFromPrimitiveData(train.getPrimitivePath()));
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

        trainService.validate(train, false, result);
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
        List<JourneyBean> journeys = tripDataService.getFirstJourneysByTrain(id, true);
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
        if (result.hasErrors()) {
            return "addJourney";
        } else {
            journey.setTrip(tripService.getTripByTrainId(id));
            tripDataService.createAll(journey);
            //TODO !!!!
            return "redirect:/trains/{id}/journeys";
        }
    }

    @RequestMapping(value = "/trains/{train_id}/journeys/{journey_id}/delete")
    public String deleteJourney(@PathVariable("train_id") int trainId,
                                @PathVariable("journey_id") int journeyId,
                                Model model) {
        try {
            //TODO!
            /*SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date parsedDate = dateFormat.parse(departureDay);
            Timestamp timestampDate = new java.sql.Timestamp(parsedDate.getTime());

            tripDataService.removeJourney(trainId, timestampDate);*/
            tripDataService.removeJourney(trainId, journeyId);
        } catch (Exception e) {
            return "/trains/{train_id}/journeys";
        }
        //TODO check that no tickets were sold!

        return "/trains/{train_id}/journeys";
    }

    @RequestMapping(value = "/trains/find", method = RequestMethod.GET)
    public String journeys(@RequestParam(required = false, defaultValue = "") String fromDay,
                           @RequestParam(required = false, defaultValue = "") String fromTime,
                           @RequestParam(required = false, defaultValue = "") String toDay,
                           @RequestParam(required = false, defaultValue = "") String toTime,
                           @RequestParam(required = false, defaultValue = "") String fromStation,
                           @RequestParam(required = false, defaultValue = "") String toStation,
                           Model model) {
        //TODO !!!!
        model.addAttribute("fromDay", fromDay); //tripData
        model.addAttribute("fromTime", fromTime); //route
        model.addAttribute("toDay", toDay);  //tripData
        model.addAttribute("toTime", toTime);  //route
        model.addAttribute("fromStation", fromStation); //
        model.addAttribute("toStation", toStation);



        /*List<JourneyBean> journeys = tripDataService.getFirstJourneysByTrain(id);
        model.addAttribute("journeys", journeys);
        model.addAttribute("trainId", id);
        model.addAttribute("journeyForm", new JourneyBean());*/
        return "journeys";
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
}
