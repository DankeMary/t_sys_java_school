package net.tsystems.entitymapper;

import net.tsystems.CycleAvoidingMappingContext;
import net.tsystems.entities.TrainDO;
import net.tsystems.serviceobject.TrainSO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TrainEntityMapper {
    TrainSO trainToSO(TrainDO tDO, @Context CycleAvoidingMappingContext context);
    TrainDO trainToDO(TrainSO tSO, @Context CycleAvoidingMappingContext context);
    List<TrainSO> trainListToSOList(List<TrainDO> tListDO, @Context CycleAvoidingMappingContext context);
    List<TrainDO> trainListToDOList(List<TrainSO> tListSO, @Context CycleAvoidingMappingContext context);
}
