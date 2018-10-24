package net.tsystems.service;

import net.tsystems.entitydao.RouteDAO;
import net.tsystems.entitymapper.RouteEntityMapper;
import net.tsystems.entitymapper.RouteEntityMapperImpl;
import net.tsystems.serviceobject.RouteSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("routeService")
@Transactional
public class RouteService {
    @Autowired
    private RouteDAO routeDao;
    private RouteEntityMapper mapper = new RouteEntityMapperImpl();

    public void create(RouteSO psngr){
        routeDao.create(mapper.routeToDO(psngr));
    }
    public void update(RouteSO psngr){
        routeDao.update(mapper.routeToDO(psngr));
    }
    public void delete(int id){
        routeDao.delete(routeDao.find(id));
    }

    public List<RouteSO> getAll() {
        return mapper.routeListToSOList(routeDao.findAll());
    }
    public RouteSO getRoute(int id){
        return mapper.routeToSO(routeDao.find(id));
    }
}
