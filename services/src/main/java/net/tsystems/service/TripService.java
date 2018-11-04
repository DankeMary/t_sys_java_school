package net.tsystems.service;

import net.tsystems.bean.TripBean;
import net.tsystems.beanmapper.TripBeanMapper;
import net.tsystems.beanmapper.TripBeanMapperImpl;
import net.tsystems.entities.TripDO;
import net.tsystems.entitydao.TripDAO;
import net.tsystems.entitymapper.TripEntityMapper;
import net.tsystems.entitymapper.TripEntityMapperImpl;
import net.tsystems.serviceobject.TripSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("tripService")
@Transactional
public class TripService {
    @Autowired
    private TripDAO tripDao;
    private TripEntityMapper entityMapper = new TripEntityMapperImpl();
    private TripBeanMapper beanMapper = new TripBeanMapperImpl();

    public void create(TripBean trip){
        tripDao.create(tripBeanToDO(trip));
    }
    public Integer createReturnId(TripBean trip){
        return tripDao.createReturnId(tripBeanToDO(trip));
    }
    public void update(TripBean trip){
        tripDao.update(tripBeanToDO(trip));
    }
    public void delete(int id){
        tripDao.delete(tripDao.find(id));
    }
    public List<TripBean> getAll() {
        return tripDOListToBeanList(tripDao.findAll());
    }
    public TripBean getTripById(int id){
        return tripDOToBean(tripDao.find(id));
    }
    public TripBean getTripByTrainId(int id) {
        return tripDOToBean(tripDao.getByTrainId(id));
    }

    public TripBean tripDOToBean (TripDO trip) {
        return beanMapper.tripToBean(entityMapper.tripToSO(trip));
    }
    public TripDO tripBeanToDO(TripBean trip) {
        return entityMapper.tripToDO(beanMapper.tripToSO(trip));
    }
    public List<TripBean> tripDOListToBeanList (List<TripDO> trips) {
        return beanMapper.tripListToBeanList(entityMapper.tripListToSOList(trips));
    }
}
