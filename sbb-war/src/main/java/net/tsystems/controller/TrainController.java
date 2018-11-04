package net.tsystems.controller;

import net.tsystems.bean.*;
import net.tsystems.beanmapper.*;
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
    private TrainService trainService;
    private StationService stationService;
    private TrainPathService trainPathService;

    private TrainValidator validator;

    private List<String> stations = new ArrayList<String>();
    private Map<Integer, StationDataBean> stationsData = new HashMap<Integer, StationDataBean>();

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
        return "addTrain";
    }

    @RequestMapping(value = "/trains", method = RequestMethod.POST)
    public String addTrain(@ModelAttribute("trainForm") @Validated TrainBean train,
                           BindingResult result, Model model,
                           final RedirectAttributes redirectAttributes) {

        if (train.getNumber() != null && trainService.getTrainByNumber(train.getNumber().intValue()) != null)
            result.rejectValue("number", "NonUnique", "Train with such number already exists");
        else
            validator.validate(train, result);
        if (result.hasErrors()) {
            return "addTrain";
        } else {
            trainService.create(train);
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
        if (train.getNumber() != null && !trainService.isUniqueByNumber(train.getId(), train.getNumber().intValue()))
            result.rejectValue("number", "NonUnique", "Train with such number already exists");
        else
            //TODO: check if you can update capacity (no trips after right now planned)
            validator.validate(train, result);
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

    //TRAIN PATH RELATED
    @RequestMapping(value = "/newTrain", method = RequestMethod.GET)
    public String newTrain(Model model) {

        TrainPathBean tpb = routeService.getTrainPathByTrainId(11);
        model.addAttribute("dataCont", new TestTrainBean());
        return "newTrainPath";
    }

    @RequestMapping(value = "/newTrain", method = RequestMethod.POST)
    public String addTrainPath(@ModelAttribute("dataCont") @Validated TestTrainBean data,
                               BindingResult result, Model model,
                               final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "newTrainPath";
        } else {
            routeService.createTrainRoutes(data.getTrainNumber(), stationsData);
            return "redirect:/passengers";
        }
    }



    //Ajax related
    @RequestMapping(value = "/getStationsForTrain", method = RequestMethod.GET)
    public @ResponseBody
    List<StationBean> getStations(@RequestParam String stationName) {
        List<StationBean> stationBeans = stationService.getAll();
        List<StationBean> result = new ArrayList<StationBean>();

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
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
            Date parsedDate = dateFormat.parse(timeArr);
            Timestamp timestampArr = new java.sql.Timestamp(parsedDate.getTime());

            parsedDate = dateFormat.parse(timeDep);
            Timestamp timestampDep = new java.sql.Timestamp(parsedDate.getTime());

            StationDataBean b = new StationDataBean();
            b.setStationName(stationName);
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
    public void setTrainPathService(TrainPathService trainPathService) {
        this.trainPathService = trainPathService;
    }

    @Autowired
    public void setValidator(TrainValidator validator) {
        this.validator = validator;
    }
}
