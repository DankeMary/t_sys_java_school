package net.tsystems.service;

import net.tsystems.CycleAvoidingMappingContext;
import net.tsystems.entitydao.TrainDAO;
import net.tsystems.entitymapper.TrainEntityMapper;
import net.tsystems.entitymapper.TrainEntityMapperImpl;
import net.tsystems.serviceobject.TrainSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("trainService")
@Transactional
public class TrainService {
    @Autowired
    private TrainDAO trainDao;
    private TrainEntityMapper mapper = new TrainEntityMapperImpl();

    public void create(TrainSO train){
        trainDao.create(mapper.trainToDO(train, new CycleAvoidingMappingContext() ));
    }
    public void update(TrainSO train){
        trainDao.update(mapper.trainToDO(train, new CycleAvoidingMappingContext() ));
    }
    public void delete(int id){
        trainDao.delete(trainDao.find(id));
    }

    public List<TrainSO> getAll() {
        return mapper.trainListToSOList(trainDao.findAll(), new CycleAvoidingMappingContext() );
    }
    public TrainSO getTrainById(int id){
        return mapper.trainToSO(trainDao.find(id), new CycleAvoidingMappingContext() );
    }
    public TrainSO getTrainByNumber(int number){
        return mapper.trainToSO(trainDao.findByNumber(number), new CycleAvoidingMappingContext() );
    }
    public boolean isUniqueByNumber(int id, int number) {
        return trainDao.isUniqueByNumber(id, number);
    }
}


