package net.tsystems.service;

import net.tsystems.bean.*;
import net.tsystems.beanmapper.TripDataBeanMapper;
import net.tsystems.beanmapper.TripDataBeanMapperImpl;
import net.tsystems.entities.TripDataDO;
import net.tsystems.entitydao.TripDataDAO;
import net.tsystems.entitymapper.*;
import net.tsystems.utilmapper.LocalDateMapper;
import net.tsystems.utilmapper.LocalDateMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service("journeyService")
@Transactional
public class TripDataService {
    //TODO: Use calendar

    @Autowired
    private TripDataDAO tripDataDAO;
    private TripDataEntityMapper tripDataEntityMapper = new TripDataEntityMapperImpl();
    private TripDataBeanMapper tripDataBeanMapper = new TripDataBeanMapperImpl();

    private RouteService routeService;

    private LocalDateMapper dateMapper = new LocalDateMapperImpl();

    //TODO create, update, delete
    public void create(TripDataBean tripDataBean){
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
            tripDataBean.setDate(currDate);
            tripDataBean.setTripDeparture(tripDepDay);
            tripDataBean.setRoute(stData);
            tripDataBean.setSeatsLeft(train.getCapacity().intValue());

            tripDataBean.setIsCancelled(false);
            tripDataBean.setIsLate(false);
            create(tripDataBean);
            prevStData = stData;
        }
    }

    public List<JourneyBean> getFirstJourneysByTrain(int id, boolean afterNow){
        List <TripDataBean> tripDataBeans;

        if (afterNow)
            tripDataBeans = tripDataDOListToBeanList(tripDataDAO.findFirstAfterNowByTrain(id));
        else
            tripDataBeans = tripDataDOListToBeanList(tripDataDAO.findFirstByTrain(id));

        List <JourneyBean> journeys = new LinkedList<JourneyBean>();
        if (tripDataBeans != null)
            for (TripDataBean tdb : tripDataBeans) {
                JourneyBean jb = new JourneyBean();
                jb.setJourneyId(tdb.getId());
                jb.setTrip(tdb.getRoute().getTrip());
                jb.setDepartureDay(tdb.getDate());
                journeys.add(jb);
            }
        return journeys;
    }

    //TODO doesn't work?
    //public void removeJourney(int trainId, Date departureDay) {
    public void removeJourney(int trainId, int journeyId) {
        //TODO check that journey with such Id exists
        //TODO how to pass the date?
        TripDataBean first = tripDataDOToBean(tripDataDAO.find(journeyId));
        List<TripDataBean> journeyParts = tripDataDOListToBeanList(tripDataDAO.findByTrainIdAndTripDepartureDay(trainId, dateMapper.asSqlDate(first.getDate())));

        if (journeyParts != null)
            for (TripDataBean tdBean : journeyParts)
                tripDataDAO.delete(tripDataBeanToDO(tdBean));
    }

    public List<ScheduleBean> getScheduleForStation(String stationName, int maxResults) {
        List<TripDataBean> tripData = tripDataDOListToBeanList(tripDataDAO.getScheduleForStation(stationName.trim(), 10));

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

    public List<TripDataBean> getDataForSection(LocalDate fromDay,
                                                LocalTime fromTime,
                                                LocalDate toDay,
                                                LocalTime toTime,
                                                String fromStation,
                                                String toStation) {
        List <TripDataBean> result = tripDataDOListToBeanList(tripDataDAO.getDataForSection(dateMapper.asSqlDate(fromDay),
                                        Time.valueOf(fromTime),
                                        dateMapper.asSqlDate(toDay),
                                        Time.valueOf(toTime),
                                        fromStation,
                                        toStation));

        return result;
    }
    //Mappers
    private TripDataDO tripDataBeanToDO (TripDataBean tdBean) {
        return tripDataEntityMapper.tripDataToDO(tripDataBeanMapper.tripDataToSO(tdBean));
    }

    private TripDataBean tripDataDOToBean (TripDataDO tdDO) {
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
}
