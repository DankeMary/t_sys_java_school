package net.tsystems.entitymapper;

import net.tsystems.entities.TrainDO;
import net.tsystems.serviceobject.TrainSO;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TrainEntityMapper {
    TrainSO trainToSO(TrainDO tDO);
    TrainDO trainToDO(TrainSO tSO);
    List<TrainSO> trainListToSOList(List<TrainDO> tListDO);
    List<TrainDO> trainListToDOList(List<TrainSO> tListSO);
}
