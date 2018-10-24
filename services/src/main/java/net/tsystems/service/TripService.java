package net.tsystems.service;

import net.tsystems.CycleAvoidingMappingContext;
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
    private TripEntityMapper mapper = new TripEntityMapperImpl();

    public void create(TripSO trip){
        tripDao.create(mapper.tripToDO(trip, new CycleAvoidingMappingContext() ));
    }
    public Integer createReturnId(TripSO trip){
        return tripDao.createReturnId(mapper.tripToDO(trip, new CycleAvoidingMappingContext() ));
    }
    public void update(TripSO trip){
        tripDao.update(mapper.tripToDO(trip, new CycleAvoidingMappingContext() ));
    }
    public void delete(int id){
        tripDao.delete(tripDao.find(id));
    }

    public List<TripSO> getAll() {
        return mapper.tripListToSOList(tripDao.findAll(), new CycleAvoidingMappingContext() );
    }
    public TripSO getTripById(int id){
        return mapper.tripToSO(tripDao.find(id), new CycleAvoidingMappingContext() );
    }
}
