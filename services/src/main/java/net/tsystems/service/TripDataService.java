package net.tsystems.service;

import net.tsystems.bean.*;
import net.tsystems.beanmapper.*;
import net.tsystems.entities.RouteDO;
import net.tsystems.entities.TripDO;
import net.tsystems.entities.TripDataDO;
import net.tsystems.entitydao.RouteDAO;
import net.tsystems.entitydao.TrainDAO;
import net.tsystems.entitydao.TripDAO;
import net.tsystems.entitydao.TripDataDAO;
import net.tsystems.entitymapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service("journeyService")
@Transactional
public class TripDataService {
    //TODO: Use calendar
    @Autowired
    private RouteService routeService = new RouteService();

    @Autowired
    private TripDataDAO tripDataDAO;
    private TripDataEntityMapper tripDataEntityMapper = new TripDataEntityMapperImpl();
    private TripDataBeanMapper tripDataBeanMapper = new TripDataBeanMapperImpl();
    @Autowired
    private TrainDAO trainDao;
    private TrainEntityMapper trainEntityMapper = new TrainEntityMapperImpl();
    private TrainBeanMapper trainBeanMapper = new TrainBeanMapperImpl();
    @Autowired
    private RouteDAO routeDao;
    private RouteEntityMapper routeEntityMapper = new RouteEntityMapperImpl();
    private RouteBeanMapper routeBeanMapper = new RouteBeanMapperImpl();
    @Autowired
    private TripDAO tripDao;
    private TripEntityMapper tripEntityMapper = new TripEntityMapperImpl();
    private TripBeanMapper tripBeanMapper = new TripBeanMapperImpl();

    //TODO create, update, delete
    public void create(TripDataBean tripDataBean){
        tripDataDAO.create(tripDataEntityMapper.tripDataToDO(tripDataBeanMapper.tripDataToSO(tripDataBean)));
    }

    public void createAll(JourneyBean journey) {
        //TODO check that no journey on that day already
        Calendar cal = Calendar.getInstance();
        cal.setTime(journey.getDepartureDay());

        TripBean trip = journey.getTrip();

        List<RouteBean> trainPath = routeService.getTrainPathByTrainId(trip.getTrain().getId());

        Date currDate = journey.getDepartureDay();
        RouteBean prevStData = trainPath.get(0);
        TrainBean train = trip.getTrain();

        for (RouteBean stData : trainPath) {
            if (prevStData.getDeparture().after(stData.getArrival())) {
                cal.add(Calendar.DATE, 1);
                currDate = cal.getTime();
            }
            TripDataBean tripDataBean = new TripDataBean();
            tripDataBean.setDate(currDate);
            tripDataBean.setRoute(stData); //(routeDOToBean(routeDao.find(stData.getRouteId()))));
            tripDataBean.setSeatsLeft(train.getCapacity().intValue());

            tripDataBean.setIsCancelled((byte)0);
            tripDataBean.setIsLate((byte)0);
            create(tripDataBean);
            prevStData = stData;
        }
    }

    public List<JourneyBean> getFirstJourneysByTrain(int id){
        List <TripDataBean> tripDataBeans = tripDataDOListToBeanList(tripDataDAO.findFirstByTrain(id));
        List <JourneyBean> journeys = new LinkedList<JourneyBean>();
        if (tripDataBeans != null)
            for (TripDataBean tdb : tripDataBeans) {
                JourneyBean jb = new JourneyBean();
                jb.setTrip(tdb.getRoute().getTrip());
                jb.setDepartureDay(tdb.getDate());
                journeys.add(jb);
            }
        return journeys;
    }

    public List<JourneyBean> getFirstJourneysAfterNowByTrain(int id){
        List <TripDataBean> tripDataBeans = tripDataDOListToBeanList(tripDataDAO.findFirstAfterNowByTrain(id));
        List <JourneyBean> journeys = new LinkedList<JourneyBean>();
        if (tripDataBeans != null)
            for (TripDataBean tdb : tripDataBeans) {
                JourneyBean jb = new JourneyBean();
                jb.setTrip(tdb.getRoute().getTrip());
                jb.setDepartureDay(tdb.getDate());
                journeys.add(jb);
            }
        return journeys;
    }

    //Mappers
    private TripBean tripDOToBean(TripDO tripDO) {
        return tripBeanMapper.tripToBean(tripEntityMapper.tripToSO(tripDO));
    }
    public RouteBean routeDOToBean (RouteDO route) {
        return routeBeanMapper.routeToBean(routeEntityMapper.routeToSO(route));
    }

    public List<TripDataBean> tripDataDOListToBeanList(List<TripDataDO> tripData) {
        return tripDataBeanMapper.tripDataListToBeanList(tripDataEntityMapper.tripDataListToSOList(tripData));
    }
}
