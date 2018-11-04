package net.tsystems.controller;

import net.tsystems.bean.*;
import net.tsystems.beanmapper.*;
import net.tsystems.service.JourneyService;
import net.tsystems.service.StationService;
import net.tsystems.service.TrainService;
import net.tsystems.service.TripService;
import net.tsystems.validator.TripValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * GET /trips
 * GET /trips/add
 * POST /trips
 * //GET /trips/{id}/update
 * //POST /trips/{id}  (update)
 * POST /trips/{id}/delete
 */
@Controller
public class TripController {
    private TrainService trainService;
    private TripService tripService;
    private StationService stationService;
    private JourneyService journeyService;

    //TODO private TripValidator validator = new TripValidator();

    private TripBeanMapper tripMapper = new TripBeanMapperImpl();

    private List<StationBean> stations;
    private List<TrainBean> trains;

    @Autowired
    public void setTrainService(TrainService trainService) {
        this.trainService = trainService;
    }

    @Autowired
    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    @Autowired
    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    @Autowired
    public void setJourneyService(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    @RequestMapping(value = "/trips", method = RequestMethod.GET)
    public String trips(Model model) {
        List<TripBean> trips = tripMapper.tripListToBeanList(tripService.getAll());
        model.addAttribute("trips", trips);
        return "trips";
    }

    @RequestMapping(value = "/trips/add", method = RequestMethod.GET)
    public String showAddTripForm(Model model) {
        TripBeanObjectless trip = new TripBeanObjectless();
        if (stations == null)
            stations = stationService.getAll();
        if (trains == null)
            trains = trainService.getAll();

        model.addAttribute("tripForm", trip);
        return "addTrip";
    }

    @RequestMapping(value = "/trips", method = RequestMethod.POST)
    public String addTrip(@ModelAttribute("tripForm") @Validated TripBeanObjectless trip,
                          BindingResult result, Model model,
                          final RedirectAttributes redirectAttributes) {

        //TODO: validation
        if (result.hasErrors()) {
            return "addTrip";
        } else {
            TripBean tripBean = new TripBean();
            tripBean.setTrain(trainService.getTrainByNumber(trip.getTrain()));
            tripBean.setFrom(stationService.getStationByName(trip.getFrom()));
            tripBean.setTo(stationService.getStationByName(trip.getTo()));
            tripService.create(tripMapper.tripToSO(tripBean));
            return "redirect:/trips";
        }
    }

    //TODO: update
    /*@RequestMapping(value = "/trips/{id}/update", method = RequestMethod.GET)
    public String showUpdateTripForm(@PathVariable("id") int id, Model model) {
        TripBean trip = tripMapper.tripToBean(tripService.getTripById(id));
        model.addAttribute("tripForm", trip);
        return "editTrip";
    }

    @RequestMapping(value = "/trips/{id}", method = RequestMethod.POST)
    public String updateTrip(@PathVariable("id") int id,
                              @ModelAttribute("tripForm") @Validated TripBean trip,
                              BindingResult result, Model model,
                              final RedirectAttributes redirectAttributes) {
        return"";
    }*/

    @RequestMapping(value = "/trips/{id}/delete")
    public String deleteTrip(@PathVariable("id") int id,
                             final RedirectAttributes redirectAttributes) {
        tripService.delete(id);
        return "redirect:/trips";
    }


    @RequestMapping(value = "/trips/{id}/newJourney", method = RequestMethod.GET)
    public String newJourney(@PathVariable("id") int id, Model model) {

        model.addAttribute("journey", new JourneyBean());
        model.addAttribute("tripId", id);
        return "newJourney";
    }

    @RequestMapping(value = "/trips/{id}", method = RequestMethod.POST)
    public String addNewJourney(@PathVariable("id") int id,
                               @ModelAttribute("journey") @Validated JourneyBean journey,
                               BindingResult result, Model model,
                               final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "newJourney";
        } else {
            journey.setTripId(id);
            journeyService.createAll(journey);
            //journeyService.createNew
            //routeService.createTrainRoutes(data.getTrainNumber(), stationsData);
            //return "redirect:/passengers";
            return "";
        }
    }



    //Ajax related
    @RequestMapping(value = "/getStations", method = RequestMethod.GET)
    public @ResponseBody
    List<StationBean> getStations(@RequestParam String stationName) {
        if (stations == null)
            stations = stationService.getAll();
        return simulateSearchResult(stationName);
    }

    private List<StationBean> simulateSearchResult(String stationName) {
        List<StationBean> result = new ArrayList<StationBean>();

        for (StationBean station : stations) {
            if (station.getName().toLowerCase().contains(stationName.toLowerCase())) {
                result.add(station);
            }
        }
        return result;
    }

    @RequestMapping(value = "/getTrains", method = RequestMethod.GET)
    public @ResponseBody
    List<TrainBean> getTrains(@RequestParam int trainNumber) {
        if (trains == null)
            trains = trainService.getAll();
        return simulateSearchResult(trainNumber);
    }

    private List<TrainBean> simulateSearchResult(int trainNumber) {
        List<TrainBean> result = new ArrayList<TrainBean>();

        for (TrainBean train : trains) {
            if (train.getNumber().toString().contains(Integer.toString(trainNumber))) {
                result.add(train);
            }
        }
        return result;
    }
}
