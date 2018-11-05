package net.tsystems.controller;

import net.tsystems.bean.*;
import net.tsystems.service.*;
import net.tsystems.validator.TrainValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
GET /trains
GET /trains/add
POST /trains
GET /trains/{id}/update
POST /trains/{id}  (update)
POST /trains/{id}/delete
*/
@Controller
public class TrainController {
    private RouteService routeService;
    private TripService tripService;
    private TripDataService tripDataService;
    private TrainService trainService;
    private StationService stationService;
    private TrainPathService trainPathService;

    private TrainValidator validator;

    private List<String> stations = new ArrayList<String>();
    private Map<Integer, StationBeanExpanded> stationsData = new HashMap<Integer, StationBeanExpanded>();

    @RequestMapping(value = "/trains", method = RequestMethod.GET)
    public String trains(Model model) {
        List<TrainBean> trains = trainService.getAll();
        model.addAttribute("trains", trains);
        return "trains";
    }

    @RequestMapping(value = "/trains/add", method = RequestMethod.GET)
    public String showAddTrainForm(Model model) {
        TrainBean train = new TrainBean();
        model.addAttribute("trainForm", train);
        model.addAttribute("pathErrorMessage", new String());
        return "addTrain";
    }

    @RequestMapping(value = "/trains", method = RequestMethod.POST)
    public String addTrain(@ModelAttribute("trainForm") @Validated TrainBean train,
                           BindingResult result, Model model,
                           final RedirectAttributes redirectAttributes) {
        String pathError = trainService.isValidPath(stationsData) ? "" : "Path has to have unique stations";
        validator.validate(train, result);
        trainService.isValid(train, false, result);

        if (result.hasErrors() || !pathError.isEmpty()) {
            model.addAttribute("pathErrorMessage", pathError);
            stationsData = new HashMap<>();
            return "addTrain";
        } else {
            trainService.create(train, stationsData);
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
        String pathError = trainService.isValidPath(stationsData) ? "" : "Path has to have unique stations";
        trainService.isValid(train, false, result);
        //TODO: check if you can update capacity (no trips after right now planned)
        validator.validate(train, result);
        if (result.hasErrors() || !pathError.isEmpty()) {
            model.addAttribute("pathErrorMessage", pathError);
            stationsData = new HashMap<>();
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
    public String showTrainPath(@PathVariable("id") int id, Model model) {
        TrainBeanExpanded trainBean = trainService.getTrainWithPath(id);
        model.addAttribute("trainData", trainBean);
        return "trainDetails";
    }

    @RequestMapping(value = "/trains/{id}/journeys", method = RequestMethod.GET)
    public String journeys(@PathVariable("id") int id, Model model) {
        List<JourneyBean> journeys = tripDataService.getFirstJourneysByTrain(id);
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
            //tripDataService.createNew
            //routeService.createTrainRoutes(data.getTrainNumber(), stationsData);
            //return "redirect:/passengers";
            return "redirect:/journeys";
        }
    }

    @RequestMapping(value = "/trains/find", method = RequestMethod.GET)
    public String journeys(@RequestParam(required = false, defaultValue = "") String fromDay,
                           @RequestParam(required = false, defaultValue = "") String fromTime,
                           @RequestParam(required = false, defaultValue = "") String toDay,
                           @RequestParam(required = false, defaultValue = "") String toTime,
                           @RequestParam(required = false, defaultValue = "") String fromStation,
                           @RequestParam(required = false, defaultValue = "") String toStation,
                           Model model) {
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

    @RequestMapping(value = "/addStationToList", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> addStationToList(String stationName, String timeArr, String timeDep) {
        stations.add(stationName);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date parsedDate = dateFormat.parse(timeArr);
            Timestamp timestampArr = new java.sql.Timestamp(parsedDate.getTime());

            parsedDate = dateFormat.parse(timeDep);
            Timestamp timestampDep = new java.sql.Timestamp(parsedDate.getTime());

            StationBeanExpanded b = new StationBeanExpanded();
            //TODO!!!!
            b.setStation(new StationBean());
            b.getStation().setName(stationName);
            b.setArrTime(timestampArr);
            b.setDepTime(timestampDep);

            stationsData.put(stationsData.size() + 1, b);
        } catch (ParseException e) {
        }
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @Autowired
    public void setTrainService(TrainService trainService) {
        this.trainService = trainService;
    }
    @Autowired
    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }
    @Autowired
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
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
    public void setTrainPathService(TrainPathService trainPathService) {
        this.trainPathService = trainPathService;
    }
    @Autowired
    public void setValidator(TrainValidator validator) {
        this.validator = validator;
    }
}
