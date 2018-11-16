package net.tsystems.service;

import net.tsystems.bean.*;
import net.tsystems.beanmapper.TripDataBeanMapper;
import net.tsystems.beanmapper.TripDataBeanMapperImpl;
import net.tsystems.entities.TripDataDO;
import net.tsystems.entitydao.TripDataDAO;
import net.tsystems.entitymapper.*;
import net.tsystems.utilmapper.LocalDateMapper;
import net.tsystems.utilmapper.LocalDateMapperImpl;
import net.tsystems.utilmapper.LocalTimeMapper;
import net.tsystems.utilmapper.LocalTimeMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.krb5.internal.Ticket;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service("journeyService")
@Transactional
public class TripDataService {
    //TODO: Use calendar

    @Autowired
    private TripDataDAO tripDataDAO;
    private TripDataEntityMapper tripDataEntityMapper = new TripDataEntityMapperImpl();
    private TripDataBeanMapper tripDataBeanMapper = new TripDataBeanMapperImpl();

    private RouteService routeService;
    private PassengerService passengerService;
    private TicketService ticketService;
    private TripService tripService;

    private LocalDateMapper dateMapper = new LocalDateMapperImpl();
    private LocalTimeMapper timeMapper = new LocalTimeMapperImpl();

    //TODO create, update, delete
    public void create(TripDataBean tripDataBean) {
        tripDataDAO.create(tripDataBeanToDO(tripDataBean));
    }

    public void createAll(JourneyBean journey) {
        //TODO check that no journey on that day already

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
    }

    public TripDataBean getById(int journeyId) {
        return tripDataDOToBean(tripDataDAO.find(journeyId));
    }

    public List<JourneyBean> getFirstJourneysByTrainNotCancelled(int id, boolean afterNow) {
        List<TripDataBean> tripDataBeans;

        if (afterNow)
            tripDataBeans = tripDataDOListToBeanList(tripDataDAO.findFirstAfterNowByTrain(id));
        else
            tripDataBeans = tripDataDOListToBeanList(tripDataDAO.findFirstByTrain(id));

        List<JourneyBean> journeys = new LinkedList<JourneyBean>();
        if (tripDataBeans != null)
            for (TripDataBean tdb : tripDataBeans) {
                JourneyBean jb = new JourneyBean();
                jb.setJourneyId(tdb.getId());
                jb.setTrip(tdb.getRoute().getTrip());
                jb.setDepartureDay(tdb.getDate().toLocalDate());
                journeys.add(jb);
            }
        return journeys;
    }

    //TODO doesn't work?
    //public void cancelJourney(int trainId, Date departureDay) {
    public void cancelJourney(int trainId, int journeyId) {
        TripDataBean first = tripDataDOToBean(tripDataDAO.find(journeyId));
        //TODO: first.getDate().toLocalDate() - is it a better way?
        List<TripDataBean> journeyParts = tripDataDOListToBeanList(tripDataDAO.findByTrainIdAndTripDepartureDay(trainId, first.getTripDeparture()/*first.getDate().toLocalDate()*//*dateMapper.asSqlDate(first.getDate())*/));

        if (journeyParts != null)
            for (TripDataBean tdBean : journeyParts) {
                tdBean.setIsCancelled(true);
                tripDataDAO.update(tripDataBeanToDO(tdBean));
            }
    }

    public List<ScheduleBean> getScheduleForStation(String stationName, int maxResults) {
        List<TripDataBean> tripData = tripDataDOListToBeanList(tripDataDAO.getScheduleForStation(stationName.trim(), maxResults));

        List<ScheduleBean> schedule = new LinkedList<>();
        if (tripData != null) {

            for (TripDataBean tdBean : tripData) {
                ScheduleBean item = new ScheduleBean();
                item.setTripData(tdBean);
                item.setRoute(tdBean.getRoute());
                item.setTrip(tdBean.getRoute().getTrip());
                item.setTrain(item.getTrip().getTrain());
                schedule.add(item);
            }
        }

        return schedule;
    }

    public List<SearchTicketForm> getDataForSection(String fromDay, String fromTime,
                                                    String toDay, String toTime,
                                                    String fromStation, String toStation) {
        LocalDate fromDate = LocalDate.parse(fromDay.trim());
        LocalDate toDate = LocalDate.parse(toDay.trim());

        LocalTime fromLocalTime = LocalTime.parse(fromTime);
        LocalTime toLocalTime = LocalTime.parse(toTime);

        //TODO Check that stations exist

        //1. Find data which satisfies the conditions
        List<TripDataBean> result = tripDataDOListToBeanList(tripDataDAO.getDataForSection(
                timeMapper.asTimestamp(fromDate),
                timeMapper.asTime(fromLocalTime),
                timeMapper.asTimestamp(toDate),
                timeMapper.asTime(toLocalTime),
                fromStation,
                toStation));
        List<SearchTicketForm> availableTicketsData = new LinkedList<>();

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
                if (ticketsAvailable > 0)
                    availableTicketsData.add(new SearchTicketForm(fromTDBean, toTDBean, ticketsAvailable));
            }
        }

        return availableTicketsData;
    }

    public boolean buyTicket(PassengerBean passenger, int fromTripDataId, int toTripDataId) {
        //1. Find data which satisfies the conditions
        TripDataBean fromTD = tripDataDOToBean(tripDataDAO.find(fromTripDataId));

        List<TripDataBean> journeyTripData = tripDataDOListToBeanList(tripDataDAO.findByTripIdAndTripDepartureDay(fromTD.getRoute().getTrip().getId(),
                fromTD.getTripDeparture()));

        TripDataBean fromTDBean = journeyTripData
                .stream()
                .filter(i -> i.getId() == fromTripDataId)
                .findFirst()
                .orElse(null);
        int fromTDBeanIndex = journeyTripData.indexOf(fromTDBean);

        TripDataBean toTDBean = journeyTripData
                .stream()
                .filter(i -> i.getId() == toTripDataId)
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

        //TODO if ticketsAv >= tickets needed then ok else return false
        if (ticketsAvailable < 1)
            return false;

        for (TripDataBean tdBean : ticketRelatedTDBeans) {
            tdBean.setSeatsLeft(tdBean.getSeatsLeft() - 1);
            tripDataDAO.update(tripDataBeanToDO(tdBean));
        }

        //create passenger
        PassengerBean newPassenger = passengerService.createReturnObject(passenger);
        //create ticket
        TicketBean ticket = new TicketBean();
        ticket.setPassenger(newPassenger);
        ticket.setFrom(fromTDBean);
        ticket.setTo(toTDBean);
        ticketService.create(ticket);
        //TODO return ID passengerService.create(passenger);
        //TODO create Ticket
        return true;
    }

    //Validation Utils
    public void validateJourney(JourneyBean journey, Map<String, String> errors) {
        //check that such trip exists
        if (tripService.getTripById(journey.getTrip().getId()) == null)
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
        else if (ticketService.ticketsOnTrainSold(trainId, tdBean.getTripDeparture()))
            errors.put("ticketsSold", "Can't cancel - tickets have been already sold");
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
}
