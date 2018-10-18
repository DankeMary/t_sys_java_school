package net.tsystems.service;

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
    @Autowired
    private StationDAO stationDao;
    private StationEntityMapper mapper = new StationEntityMapperImpl();

    public void create(StationSO station){
        stationDao.create(mapper.stationToDO(station));
    }
    public void update(StationSO station){
        stationDao.update(mapper.stationToDO(station));
    }
    public void delete(int id){
        stationDao.delete(stationDao.find(id));
    }

    public List<StationSO> getAll() {
        return mapper.passengerListToSOList(stationDao.findAll());
    }
    public StationSO getStationById(int id){
        return mapper.stationToSO(stationDao.find(id));
    }
    public StationSO getStationByName(String name){
        return mapper.stationToSO(stationDao.findByName(name));
    }
}
