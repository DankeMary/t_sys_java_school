package net.tsystems.service;

import net.tsystems.bean.*;
import net.tsystems.beanmapper.TripDataBeanMapper;
import net.tsystems.beanmapper.TripDataBeanMapperImpl;
import net.tsystems.entities.TripDataDO;
import net.tsystems.entitydao.TripDataDAO;
import net.tsystems.entitymapper.TripDataEntityMapper;
import net.tsystems.entitymapper.TripDataEntityMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("journeyService")
@Transactional
public class TripDataService {

    private static final Logger LOG = LoggerFactory.getLogger(TripDataService.class);
    private final String DEFAULT_CURRENCY = "Fr.";

    @Autowired
    private TripDataDAO tripDataDAO;
    private TripDataEntityMapper tripDataEntityMapper = new TripDataEntityMapperImpl();
    private TripDataBeanMapper tripDataBeanMapper = new TripDataBeanMapperImpl();

    private RouteService routeService;
    private PassengerService passengerService;
    private TicketService ticketService;
    private TripService tripService;
    private UserService userService;

    public void create(TripDataBean tripDataBean) {
        try {
            tripDataDAO.create(tripDataBeanToDO(tripDataBean));
        } catch (Exception e) {
            LOG.error("Failed to ");
            e.printStackTrace();
        }
    }

    public void createAll(JourneyBean journey) {
        try {
            TripBean trip = journey.getTrip();

            List<RouteBean> trainPath = routeService.getTrainPathByTrainId(trip.getTrain().getId());
            LocalDate tripDepDay = journey.getDepartureDay();
            LocalDate currDate = journey.getDepartureDay();
            RouteBean prevStData = trainPath.get(0);
            TrainBean train = trip.getTrain();

            for (RouteBean stData : trainPath) {
                if (prevStData.getArrival().isAfter(stData.getArrival())) {
                    currDate = currDate.plusDays(1);
                }
                TripDataBean tripDataBean = new TripDataBean();
                tripDataBean.setDate(LocalDateTime.of(currDate, stData.getDeparture()));
                tripDataBean.setTripDeparture(tripDepDay);
                tripDataBean.setRoute(stData);
                tripDataBean.setSeatsLeft(train.getCapacity().intValue());

                tripDataBean.setIsCancelled(false);
                tripDataBean.setIsLate(false);
                create(tripDataBean);
                prevStData = stData;
            }
        } catch (Exception e) {
            LOG.error("Failed to create tripDatas of a journey");
            e.printStackTrace();
        }
    }

    public TripDataBean getById(int journeyId) {
        TripDataBean tripDataBean = null;
        try {
            tripDataBean = tripDataDOToBean(tripDataDAO.find(journeyId));
        } catch (Exception e) {
            LOG.error(String.format("Failed to find tripData by id=%s", journeyId));
            e.printStackTrace();
        }
        return tripDataBean;
    }

    public List<JourneyBean> getFirstJourneysByTrainNotCancelled(int id, boolean afterNow, int page, int maxResult) {
        //TODO For now only afterNow
        List<JourneyBean> journeys = new LinkedList<>();
        try {
            List<TripDataBean> tripDataBeans = tripDataDOListToBeanList(tripDataDAO.findFirstAfterNowByTrain(id, page, maxResult));

            /*if (afterNow)
                tripDataBeans = tripDataDOListToBeanList(tripDataDAO.findFirstAfterNowByTrain(id));
            else
                tripDataBeans = tripDataDOListToBeanList(tripDataDAO.findFirstByTrain(id));*/

            for (TripDataBean tdb : tripDataBeans) {
                JourneyBean jb = new JourneyBean();
                jb.setJourneyId(tdb.getId());
                jb.setTrip(tdb.getRoute().getTrip());
                jb.setDepartureDay(tdb.getDate().toLocalDate());
                journeys.add(jb);
            }
        } catch (Exception e) {
            LOG.error(String.format("Failed to get first tripDatas for train with id=%s", id));
            e.printStackTrace();
        }
        return journeys;
    }

    public List<TripDataBean> getTrainJourneyDetails(int trainId, int journeyId) {
        //TODO Logger
        TripDataBean first = tripDataDOToBean(tripDataDAO.find(journeyId));

        return tripDataDOListToBeanList(tripDataDAO.findByTrainIdAndTripDepartureDay(trainId, first.getTripDeparture()));
    }

    //TODO doesn't work?
    //public void cancelJourney(int trainId, Date departureDay) {
    public void cancelJourney(int trainId, int journeyId) {
        try {
            TripDataBean first = tripDataDOToBean(tripDataDAO.find(journeyId));

            List<TripDataBean> journeyParts = tripDataDOListToBeanList(tripDataDAO.findByTrainIdAndTripDepartureDay(trainId, first.getTripDeparture()));

            for (TripDataBean tdBean : journeyParts) {
                tdBean.setIsCancelled(true);
                tripDataDAO.update(tripDataBeanToDO(tdBean));
            }
        } catch (Exception e) {
            LOG.error(String.format("Failed to cancel journey of a train with id=%s and tripData with id=%s", trainId, journeyId));
            e.printStackTrace();
        }
    }

    public List<ScheduleBean> getScheduleForStation(String stationName, int maxResults) {
        List<ScheduleBean> schedule = new LinkedList<>();
        try {
            List<TripDataBean> tripData = tripDataDOListToBeanList(tripDataDAO.getScheduleForStation(stationName.trim(), maxResults));

            for (TripDataBean tdBean : tripData) {
                ScheduleBean item = new ScheduleBean();
                item.setTripData(tdBean);
                item.setRoute(tdBean.getRoute());
                item.setTrip(tdBean.getRoute().getTrip());
                item.setTrain(item.getTrip().getTrain());
                schedule.add(item);
            }
        } catch (Exception e) {
            LOG.error(String.format("Failed to get schedule for station=%s", stationName));
            e.printStackTrace();
        }
        return schedule;
    }

    public List<SearchTicketForm> getDataForSection(String fromDay, String fromTime,
                                                    String toDay, String toTime,
                                                    String fromStation, String toStation,
                                                    int page, int maxResult) {
        List<SearchTicketForm> availableTicketsData = new LinkedList<>();
        try {
            LocalDate fromDate = LocalDate.parse(fromDay.trim());
            LocalDate toDate = LocalDate.parse(toDay.trim());

            LocalTime fromLocalTime = LocalTime.parse(fromTime);
            LocalTime toLocalTime = LocalTime.parse(toTime);

            //1. Find data which satisfies the conditions
            List<TripDataBean> result = tripDataDOListToBeanList(tripDataDAO.getDataForSection(
                    fromDate,
                    fromLocalTime,
                    toDate,
                    toLocalTime,
                    fromStation,
                    toStation,
                    page, maxResult));

            for (TripDataBean tdBean : result) {
                //2. For each find all the related TripDatas (for now we've only found the 'from' ones)
                List<TripDataBean> journeyTripData = tripDataDOListToBeanList(tripDataDAO.findByTripIdAndTripDepartureDay(tdBean.getRoute().getTrip().getId(),
                        tdBean.getTripDeparture()));
                //3. Extract only the ones between needed stations
                TripDataBean fromTDBean = journeyTripData
                        .stream()
                        .filter(i -> i.getRoute().getStation().getName().equals(fromStation.trim()))
                        .findFirst()
                        .orElse(null);
                int fromTDBeanIndex = journeyTripData.indexOf(fromTDBean);

                TripDataBean toTDBean = journeyTripData
                        .stream()
                        .filter(i -> i.getRoute().getStation().getName().equals(toStation.trim()))
                        .findFirst()
                        .orElse(null);
                int toTDBeanIndex = journeyTripData.indexOf(toTDBean);

                if (fromTDBeanIndex < toTDBeanIndex) {
                    List<TripDataBean> ticketRelatedTDBeans = journeyTripData.subList(fromTDBeanIndex, toTDBeanIndex + 1);
                    //4. Get the min "seats" number - that's the number of tickets available
                    int ticketsAvailable = ticketRelatedTDBeans
                            .stream()
                            .min(Comparator.comparing(TripDataBean::getSeatsLeft))
                            .get()
                            .getSeatsLeft();
                    if (ticketsAvailable > 0) {
                        SearchTicketForm ticketForm = new SearchTicketForm();
                        ticketForm.setFromTDBean(fromTDBean);
                        ticketForm.setToTDBean(toTDBean);
                        ticketForm.setTicketsQty(ticketsAvailable);
                        availableTicketsData.add(ticketForm);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(String.format("Failed to get data about tickets for: fromDate=%s, toDate=%s, " +
                            "fromTime=%s, toTime=%s, " +
                            "fromStation=%s, toStation=%s",
                    fromDay, toDay,
                    fromTime, toTime,
                    fromStation, toStation));
            e.printStackTrace();
        }

        return availableTicketsData;
    }

    @PreAuthorize("#boughtByUsername == authentication.principal.username")
    public boolean buyTickets(BuyTicketsForm ticketsData, String boughtByUsername) {
        if (boughtByUsername == null || boughtByUsername.trim().isEmpty()) {
            LOG.error("Failed to buy tickets since user is not present");
        } else {
            try {
                //1. Find data which satisfies the conditions
                int fromJourneyId = ticketsData.getFromJourneyId();
                int toJourneyId = ticketsData.getToJourneyId();
                TripDataBean fromTD = tripDataDOToBean(tripDataDAO.find(fromJourneyId));

                List<TripDataBean> journeyTripData = tripDataDOListToBeanList(
                        tripDataDAO.findByTripIdAndTripDepartureDay(
                                fromTD.getRoute().getTrip().getId(),
                                fromTD.getTripDeparture()));

                TripDataBean fromTDBean = journeyTripData
                        .stream()
                        .filter(i -> i.getId() == fromJourneyId)
                        .findFirst()
                        .orElse(null);
                int fromTDBeanIndex = journeyTripData.indexOf(fromTDBean);

                TripDataBean toTDBean = journeyTripData
                        .stream()
                        .filter(i -> i.getId() == toJourneyId)
                        .findFirst()
                        .orElse(null);
                int toTDBeanIndex = journeyTripData.indexOf(toTDBean);

                List<TripDataBean> ticketRelatedTDBeans = journeyTripData.subList(fromTDBeanIndex, toTDBeanIndex + 1);
                //4. Get the min "seats" number - that's the number of tickets available
                int ticketsAvailable = ticketRelatedTDBeans
                        .stream()
                        .min(Comparator.comparing(TripDataBean::getSeatsLeft))
                        .get()
                        .getSeatsLeft();

                List<PassengerBean> passengersWithCompleteInfo =
                        passengerService.filterCompleteInfo(ticketsData.getPassengers());

                if (ticketsAvailable < passengersWithCompleteInfo.size())
                    return false;

                for (TripDataBean tdBean : ticketRelatedTDBeans) {
                    tdBean.setSeatsLeft(tdBean.getSeatsLeft() - passengersWithCompleteInfo.size());
                    tripDataDAO.update(tripDataBeanToDO(tdBean));
                }
                //TODO What if user with such name wasn't found?
                UserBean user = userService.getUser(boughtByUsername);

                for (PassengerBean p : passengersWithCompleteInfo) {
                    //create passenger
                    PassengerBean newPassenger = passengerService.createReturnObject(p);
                    //create ticket
                    TicketBean ticket = new TicketBean();
                    ticket.setPassenger(newPassenger);
                    ticket.setFrom(fromTDBean);
                    ticket.setTo(toTDBean);
                    ticket.setBoughtBy(user);
                    ticketService.create(ticket);
                }
            } catch (Exception e) {
                LOG.error("Failed to buy tickets");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public void ticketWasErased(TicketBean ticket) {
        try {
                /*//1. Find data which satisfies the conditions
                int fromJourneyId = ticketsData.getFromJourneyId();
                int toJourneyId = ticketsData.getToJourneyId();
                TripDataBean fromTD = tripDataDOToBean(tripDataDAO.find(fromJourneyId));*/

            int fromJourneyId = ticket.getFrom().getId();
            int toJourneyId = ticket.getTo().getId();

            List<TripDataBean> journeyTripData = tripDataDOListToBeanList(
                    tripDataDAO.findByTripIdAndTripDepartureDay(
                            ticket.getFrom().getRoute().getTrip().getId(),
                            ticket.getFrom().getTripDeparture()));

            TripDataBean fromTDBean = journeyTripData
                    .stream()
                    .filter(i -> i.getId() == fromJourneyId)
                    .findFirst()
                    .orElse(null);
            int fromTDBeanIndex = journeyTripData.indexOf(fromTDBean);

            TripDataBean toTDBean = journeyTripData
                    .stream()
                    .filter(i -> i.getId() == toJourneyId)
                    .findFirst()
                    .orElse(null);
            int toTDBeanIndex = journeyTripData.indexOf(toTDBean);

            List<TripDataBean> ticketRelatedTDBeans = journeyTripData.subList(fromTDBeanIndex, toTDBeanIndex + 1);
            //4. Get the min "seats" number - that's the number of tickets available
                /*int ticketsAvailable = ticketRelatedTDBeans
                        .stream()
                        .min(Comparator.comparing(TripDataBean::getSeatsLeft))
                        .get()
                        .getSeatsLeft();*/

                /*List<PassengerBean> passengersWithCompleteInfo =
                        passengerService.filterCompleteInfo(ticketsData.getPassengers());*/

                /*if (ticketsAvailable < passengersWithCompleteInfo.size())
                    return false;*/

            //TODO check that if we increase number of tickets won't be bigger than train capacity?
            for (TripDataBean tdBean : ticketRelatedTDBeans) {
                tdBean.setSeatsLeft(tdBean.getSeatsLeft() + 1);
                tripDataDAO.update(tripDataBeanToDO(tdBean));
            }
        } catch (Exception e) {
            LOG.error("Failed to buy tickets");
            e.printStackTrace();
        }
    }

    //Validation Utils
    public void validateJourney(JourneyBean journey, Map<String, String> errors) {
        //check that such trip exists
        if (journey.getTrip() == null || tripService.getTripById(journey.getTrip().getId()) == null)
            errors.put("invalidTrip", "No such trip found");
        //check that date is in future
        if (journey.getDepartureDay().isBefore(LocalDate.now()))
            errors.put("depDayError", "Departure day has to be in the future");
            //check that no journey on that day yet
        else if (journey.getTrip() != null && tripDataDAO.journeyOfTripOnDateExists(journey.getTrip().getId(), journey.getDepartureDay()))
            errors.put("journeyExists", "There's already trip on that day created");
    }

    public void validateCancellation(int trainId, int journeyId, Map<String, String> errors) {
        TripDataBean tdBean = getById(journeyId);
        if (tdBean == null)
            errors.put("invalidTrip", "No such trip found");
        else if (ticketService.hasTicketsOnTrainSold(trainId, tdBean.getTripDeparture()))
            errors.put("ticketsSold", "Can't cancel - tickets have been already sold");
    }

    //Help Functions
    public void makeMetaDataForBuyingTickets(String fromJourneyId, String toJourneyId, Map<String, String> data) {
        try {
            TripDataBean fromTdBean = tripDataDOToBean(tripDataDAO.find(Integer.parseInt(fromJourneyId)));
            TripDataBean toTdBean = tripDataDOToBean(tripDataDAO.find(Integer.parseInt(toJourneyId)));
            StringBuilder result = new StringBuilder();

            data.put("trainNumber", Long.toString(fromTdBean.getRoute().getTrip().getTrain().getNumber()));

            data.put("fromMetaInfo", fromTdBean.getRoute().getStation().getName() +
                    " (" + fromTdBean.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm")) + ")");

            data.put("toMetaInfo", toTdBean.getRoute().getStation().getName() +
                    " (" + toTdBean.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm")) + ")");

            data.put("ticketPrice", fromTdBean.getRoute().getTrip().getTrain().getPrice() + " " + DEFAULT_CURRENCY);
        } catch (Exception e) {
            LOG.error(String.format("Failed to make metadata for fromTripDataId=%s, toTripDataId=%s", fromJourneyId, toJourneyId));
            e.printStackTrace();
        }
    }

    public int countDataForSectionPages(String fromDay, String fromTime,
                                        String toDay, String toTime,
                                        String fromStation, String toStation,
                                        int maxResult) {
        LocalDate fromDate = LocalDate.parse(fromDay.trim());
        LocalDate toDate = LocalDate.parse(toDay.trim());

        LocalTime fromLocalTime = LocalTime.parse(fromTime);
        LocalTime toLocalTime = LocalTime.parse(toTime);

        return tripDataDAO.countDataForSectionPages(fromDate, fromLocalTime,
                toDate, toLocalTime,
                fromStation, toStation, maxResult);
    }

    public int countFirstAfterNowByTrainPages(int id, int maxResult) {
        return tripDataDAO.countFirstAfterNowByTrainPages(id, maxResult);
    }

    //Mappers
    private TripDataDO tripDataBeanToDO(TripDataBean tdBean) {
        return tripDataEntityMapper.tripDataToDO(tripDataBeanMapper.tripDataToSO(tdBean));
    }

    private TripDataBean tripDataDOToBean(TripDataDO tdDO) {
        return tripDataBeanMapper.tripDataToBean(tripDataEntityMapper.tripDataToSO(tdDO));
    }

    public List<TripDataBean> tripDataDOListToBeanList(List<TripDataDO> tripData) {
        return tripDataBeanMapper.tripDataListToBeanList(tripDataEntityMapper.tripDataListToSOList(tripData));
    }

    //Autowired
    @Autowired
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    @Autowired
    public void setPassengerService(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Autowired
    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
