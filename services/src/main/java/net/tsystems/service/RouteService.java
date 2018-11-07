package net.tsystems.service;

import net.tsystems.bean.*;
import net.tsystems.beanmapper.*;
import net.tsystems.entities.RouteDO;
import net.tsystems.entitydao.RouteDAO;
import net.tsystems.entitymapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("routeService")
@Transactional
public class RouteService {

    private RouteDAO routeDao;
    private RouteEntityMapper routeEntityMapper = new RouteEntityMapperImpl();
    private RouteBeanMapper routeBeanMapper = new RouteBeanMapperImpl();

    private StationService stationService;
    private TripService tripService;

    public void create(RouteBean psngr) {
        routeDao.create(routeBeanToDO(psngr));
    }

    public void update(RouteBean psngr) {
        routeDao.update(routeBeanToDO(psngr));
    }

    public void delete(int id) {
        routeDao.delete(routeDao.find(id));
    }

    public List<RouteBean> getAll() {
        return routeDOListToBeanList(routeDao.findAll());
    }

    public RouteBean getRoute(int id) {
        return routeDOToBean(routeDao.find(id));
    }
    public List<RouteBean> getRoutesByTrainId(int trainId) { return routeDOListToBeanList(routeDao.getRoutesByTrainId(trainId)); }

    public void createTrainRoutes(TrainBean train, Map<Integer, StationBeanExpanded> stationsData) {
        TripBean trip = new TripBean();
        trip.setTrain(train);
        trip.setFrom(stationService.getStationByName(stationsData.get(1).getStation().getName()));
        trip.setTo(stationService.getStationByName(stationsData.get(stationsData.size()).getStation().getName()));
        Integer savedTripId = tripService.createReturnId(trip);

        TripBean savedTrip = tripService.getTripById(savedTripId);
        StationBean nextStation = null;
        for (int i = stationsData.size(); i > 0; i--) {
            RouteBean route = new RouteBean();
            StationBeanExpanded currRoutePoint = stationsData.get(i);
            StationBean currStation = stationService.getStationByName(currRoutePoint.getStation().getName());
            route.setStation(currStation);
            route.setNextStation(nextStation);
            route.setArrival(currRoutePoint.getArrTime());
            route.setDeparture(currRoutePoint.getDepTime());
            route.setTrip(savedTrip);
            routeDao.create(routeBeanToDO(route));
            nextStation = currStation;
        }
    }

    public List<RouteBean> getTrainPathByTrainId(int trainId) {
        List<RouteBean> trainRoutes = getRoutesByTrainId(trainId);

        Map<Integer, RouteBean> stationsData = new HashMap<Integer, RouteBean>();
        RouteBean lastRoute = null;
        //TODO: DAO get lastRoute (==null) & getPath(!=null) ?
        //Key - next station's ID
        for (RouteBean rb : trainRoutes) {
            if (rb.getNextStation() == null)
                lastRoute = rb;
            else
                stationsData.put(rb.getNextStation().getId(), rb);
        }

        //Make the ordered path
        int nextStationId = lastRoute.getStation().getId();
        RouteBean currRoute;
        List<RouteBean> orderedTrainRoutes = new LinkedList<RouteBean>();
        orderedTrainRoutes.add(lastRoute);

        for (int i = 0; i < trainRoutes.size() - 1; i++) {
            currRoute = stationsData.get(nextStationId);
            orderedTrainRoutes.add(0, currRoute);
            nextStationId = currRoute.getStation().getId();
        }

        return orderedTrainRoutes;
    }

    //Mappers
    public RouteDO routeBeanToDO (RouteBean route) {
        return routeEntityMapper.routeToDO(routeBeanMapper.routeToSO(route));
    }
    public RouteBean routeDOToBean (RouteDO rDO) {
        return routeBeanMapper.routeToBean(routeEntityMapper.routeToSO(rDO));
    }

    public List<RouteBean> routeDOListToBeanList (List<RouteDO> routes) {
        return routeBeanMapper.routeListToBeanList(routeEntityMapper.routeListToSOList(routes));
    }

    //Autowired
    @Autowired
    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }
    @Autowired
    public void setRouteDao(RouteDAO routeDao) {
        this.routeDao = routeDao;
    }
    @Autowired
    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }
}
