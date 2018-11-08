package net.tsystems.service;

import net.tsystems.bean.*;
import net.tsystems.beanmapper.*;
import net.tsystems.entities.RouteDO;
import net.tsystems.entitydao.RouteDAO;
import net.tsystems.entitymapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public List<RouteBean> getRoutesByTrainId(int trainId) {
        return routeDOListToBeanList(routeDao.getRoutesByTrainId(trainId));
    }

    public void createTrainPath(TrainBean train, Map<Integer, StationBeanExpanded> stationsData) {
        TripBean trip = new TripBean();
        trip.setTrain(train);
        trip.setFrom(stationService.getStationByName(stationsData.get(1).getStation().getName()));
        trip.setTo(stationService.getStationByName(stationsData.get(stationsData.size()).getStation().getName()));
        Integer savedTripId = tripService.createReturnId(trip);

        TripBean savedTrip = tripService.getTripById(savedTripId);
        createRoutesFromList(stationsData, savedTrip);
    }

    public List<RouteBean> getTrainPathByTrainId(int trainId) {
        List<RouteBean> trainRoutes = getRoutesByTrainId(trainId);

        Map<Integer, RouteBean> stationsData = new HashMap<Integer, RouteBean>();
        RouteBean lastRoute = null;
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

    //Validation Utils
    public void validatePrimitive(List<PrimitiveRouteBean> primitiveData, List<String> errorsList) {
        //TODO check that all stations exist
        if (primitiveData == null || primitiveData.size() < 2) {
            errorsList.add("Train path hast to have at least 2 stations)");
        } else {
            if (!isValidPath(primitiveData)) {
                errorsList.add("Path has to have unique stations");
            }

            for (PrimitiveRouteBean prBean : primitiveData) {
                if (prBean.getStation().equals("") ||
                        prBean.getArrival().equals("") ||
                        prBean.getDeparture().equals("")) {
                    errorsList.add("Some path data is missing (station name or timing)");
                    break;
                }
            }
        }
    }

    public boolean isValidPath(List<PrimitiveRouteBean> primitivePath) {
        Set<String> stations = new HashSet<>();
        primitivePath.forEach(station -> stations.add(station.getStation().trim()));
        return primitivePath.size() == stations.size();
    }

    //Help Functions
    //TODO  REPLACE DATE CLASS EVERYWHERE AND CREATE DATE CAST METHOD
    public Map<Integer, StationBeanExpanded> generatePathMapFromPrimitiveData(List<PrimitiveRouteBean> primitivePath) {
        Map<Integer, StationBeanExpanded> result = new HashMap<>();

        try {
            for (PrimitiveRouteBean prBean : primitivePath) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                Date parsedDate = dateFormat.parse(prBean.getArrival());
                Timestamp timeArr = new java.sql.Timestamp(parsedDate.getTime());

                parsedDate = dateFormat.parse(prBean.getDeparture());
                Timestamp timeDep = new java.sql.Timestamp(parsedDate.getTime());

                StationBeanExpanded b = new StationBeanExpanded();
                b.setStation(new StationBean());
                b.getStation().setName(prBean.getStation());
                b.setArrTime(timeArr);
                b.setDepTime(timeDep);

                result.put(result.size() + 1, b);
            }

            return result;

        } catch (ParseException e) {
            //TODO !!!
        }
        return null;
    }

    private void createRoutesFromList(Map<Integer, StationBeanExpanded> routesData, TripBean trip) {
        StationBean nextStation = null;
        for (int i = routesData.size(); i > 0; i--) {
            RouteBean route = new RouteBean();
            StationBeanExpanded currRoutePoint = routesData.get(i);
            StationBean currStation = stationService.getStationByName(currRoutePoint.getStation().getName());
            route.setStation(currStation);
            route.setNextStation(nextStation);
            route.setArrival(currRoutePoint.getArrTime());
            route.setDeparture(currRoutePoint.getDepTime());
            route.setTrip(trip);
            routeDao.create(routeBeanToDO(route));
            nextStation = currStation;
        }
    }

    //Mappers
    public RouteDO routeBeanToDO(RouteBean route) {
        return routeEntityMapper.routeToDO(routeBeanMapper.routeToSO(route));
    }

    public RouteBean routeDOToBean(RouteDO rDO) {
        return routeBeanMapper.routeToBean(routeEntityMapper.routeToSO(rDO));
    }

    public List<RouteBean> routeDOListToBeanList(List<RouteDO> routes) {
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
