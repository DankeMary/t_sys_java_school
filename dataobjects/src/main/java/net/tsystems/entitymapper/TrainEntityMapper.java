package net.tsystems.entitymapper;

import net.tsystems.entities.TrainDO;
import net.tsystems.serviceobject.TrainSO;

import org.mapstruct.Mapper;

@Mapper
public interface TrainEntityMapper {
    TrainSO TrainToSO(TrainDO tDO);
    TrainDO TrainToDO(TrainSO tSO);
}
