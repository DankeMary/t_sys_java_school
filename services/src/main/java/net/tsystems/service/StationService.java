package net.tsystems.service;

import net.tsystems.bean.StationBean;
import net.tsystems.beanmapper.StationBeanMapper;
import net.tsystems.beanmapper.StationBeanMapperImpl;
import net.tsystems.entities.StationDO;
import net.tsystems.entitydao.StationDAO;
import net.tsystems.entitymapper.StationEntityMapper;
import net.tsystems.entitymapper.StationEntityMapperImpl;
import net.tsystems.serviceobject.StationSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("stationService")
@Transactional
public class StationService {

    private StationDAO stationDao;
    private StationEntityMapper entityMapper = new StationEntityMapperImpl();
    private StationBeanMapper beanMapper = new StationBeanMapperImpl();

    public void create(StationBean station){
        stationDao.create(stationBeanToDO(station));
    }
    public void update(StationBean station){
        stationDao.update(stationBeanToDO(station));
    }
    public void delete(int id){
        stationDao.delete(stationDao.find(id));
    }

    public List<StationBean> getAll() {
        return stationDOListToBeanList(stationDao.findAll());
    }
    public StationBean getStationById(int id){
        return stationDOToBean(stationDao.find(id));
    }
    public StationBean getStationByName(String name){
        return stationDOToBean(stationDao.findByName(name));
    }
    public boolean isUniqueByName(int id, String name) {
        return stationDao.isUniqueByName(id, name);
    }

    //Mappers
    private StationBean stationDOToBean (StationDO station) {
        return beanMapper.stationToBean(entityMapper.stationToSO(station));
    }
    private StationDO stationBeanToDO (StationBean station) {
        return entityMapper.stationToDO(beanMapper.stationToSO(station));
    }
    private List<StationBean> stationDOListToBeanList (List<StationDO> stations) {
        return beanMapper.stationListToBeanList(entityMapper.stationListToSOList(stations));
    }
    @Autowired
    public void setStationDao(StationDAO stationDao) {
        this.stationDao = stationDao;
    }
}
