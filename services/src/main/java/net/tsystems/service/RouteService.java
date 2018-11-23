package net.tsystems.service;

import net.tsystems.bean.*;
import net.tsystems.beanmapper.RouteBeanMapper;
import net.tsystems.beanmapper.RouteBeanMapperImpl;
import net.tsystems.entities.RouteDO;
import net.tsystems.entitydao.RouteDAO;
import net.tsystems.entitymapper.RouteEntityMapper;
import net.tsystems.entitymapper.RouteEntityMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("routeService")
@Transactional
public class RouteService {

    private static final Logger LOG = LoggerFactory.getLogger(RouteService.class);

    private RouteDAO routeDao;
    private RouteEntityMapper routeEntityMapper = new RouteEntityMapperImpl();
    private RouteBeanMapper routeBeanMapper = new RouteBeanMapperImpl();

    private StationService stationService;
    private TripService tripService;

    public void create(RouteBean route) {
        try {
            routeDao.create(routeBeanToDO(route));
        } catch (Exception e) {
            LOG.error("Failed to create route");
            e.printStackTrace();
        }
    }

    public void update(RouteBean route) {
        try {
            routeDao.update(routeBeanToDO(route));
        } catch (Exception e) {
            LOG.error("Failed to update route");
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            routeDao.delete(routeDao.find(id));
        } catch (Exception e) {
            LOG.error(String.format("Failed to delete route by id=%s", id));
            e.printStackTrace();
        }
    }

    public List<RouteBean> getAll() {
        List<RouteBean> routes = new LinkedList<>();
        try {
            routes = routeDOListToBeanList(routeDao.findAll());
        } catch (Exception e) {
            LOG.error("Failed to get all routes");
            e.printStackTrace();
        }
        return routes;
    }

    public List<RouteBean> getRoutesByTrainId(int trainId) {
        List<RouteBean> routes = new LinkedList<>();
        try {
            routes = routeDOListToBeanList(routeDao.getRoutesByTrainId(trainId));
        } catch (Exception e) {
            LOG.error(String.format("Failed to get routes by train's id=%s", trainId));
            e.printStackTrace();
        }
        return routes;
    }

    public void createTrainPath(TrainBean train, Map<Integer, StationBeanExpanded> stationsData) {
        try {
            if (train != null) {
                TripBean trip = new TripBean();
                trip.setTrain(train);
                trip.setFrom(stationService.getStationByName(stationsData.get(1).getStation().getName()));
                trip.setTo(stationService.getStationByName(stationsData.get(stationsData.size()).getStation().getName()));
                Integer savedTripId = tripService.createReturnId(trip);

                TripBean savedTrip = tripService.getTripById(savedTripId);
                createRoutesFromList(stationsData, savedTrip);
            } else {
                LOG.error("Failed to create path for train since train = null");
            }
        } catch (Exception e) {
            LOG.error(String.format("Failed to create path for train with id=%s", train.getId()));
            e.printStackTrace();
        }
    }

    public List<RouteBean> getTrainPathByTrainId(int trainId) {
        List<RouteBean> orderedTrainRoutes = new LinkedList<RouteBean>();
        try {
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
            orderedTrainRoutes.add(lastRoute);

            for (int i = 0; i < trainRoutes.size() - 1; i++) {
                currRoute = stationsData.get(nextStationId);
                orderedTrainRoutes.add(0, currRoute);
                nextStationId = currRoute.getStation().getId();
            }
        } catch (Exception e) {
            LOG.error(String.format("Failed to get path for train with id=%s", trainId));
            e.printStackTrace();
        }
        return orderedTrainRoutes;
    }

    //Validation Utils
    public void validatePrimitive(List<StationBeanExpanded> trainPathData, Map<String, String> errorsList) {
        //TODO check that all stations exist
        //TODO How does it act if I add a bunch of empty inputs?
        int cnt = 0;
        if (trainPathData != null)
            for (StationBeanExpanded stExpBean : trainPathData) {
                if ((stExpBean.getStation() != null &&
                        !stExpBean.getStation().getName().isEmpty()) ||
                        stExpBean.getArrTime() != null ||
                        stExpBean.getDepTime() != null
                )
                    cnt++;
            }
        if (cnt < 2) {
            errorsList.put("shortPath", "Train path hast to have at least 2 stations with data");
        } else {
            //all stations exist
            if (!stationService.allStationsExist(trainPathData))
                errorsList.put("invalidStations", "Not all of the given stations exist");
            else if (!areUniqueStations(trainPathData)) {
                errorsList.put("wrongPath", "Path has to have unique stations");
            }

            for (StationBeanExpanded stExpBean : trainPathData) {
                if (stExpBean.getStation().getName().equals("") ||
                        stExpBean.getArrTime() == null ||
                        stExpBean.getDepTime() == null) {
                    errorsList.put("dataMissing", "Some path data is missing (station name or timing)");
                    break;
                }
            }
        }
    }

    public boolean areUniqueStations(List<StationBeanExpanded> trainPath) {
        Set<String> stations = new HashSet<>();
        trainPath.forEach(station -> stations.add(station.getStation().getName().trim()));
        return trainPath.size() == stations.size();
    }


    //Help Functions
    //TODO Is using orderIndex actually better? Ont he other hand we cannot guarantee the ordered list
    public Map<Integer, StationBeanExpanded> generatePathMap(List<StationBeanExpanded> trainPathData) {
        Map<Integer, StationBeanExpanded> result = new HashMap<>();

        for (StationBeanExpanded stExpBean : trainPathData) {
            //result.put(result.size() + 1, b);
            result.put(stExpBean.getOrderIndex() + 1, stExpBean);
        }

        return result;
    }

    private void createRoutesFromList(Map<Integer, StationBeanExpanded> routesData, TripBean trip) {
        try {
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
        } catch (Exception e) {
            LOG.error("Failed to create routes from list");
            e.printStackTrace();
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
