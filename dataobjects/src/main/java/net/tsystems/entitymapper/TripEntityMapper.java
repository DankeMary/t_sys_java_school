package net.tsystems.entitymapper;

import net.tsystems.CycleAvoidingMappingContext;
import net.tsystems.entities.TripDO;
import net.tsystems.serviceobject.TripSO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TripEntityMapper {
    TripSO tripToSO(TripDO tripDO, @Context CycleAvoidingMappingContext context);
    TripDO tripToDO(TripSO tripSO, @Context CycleAvoidingMappingContext context);
    List<TripSO> tripListToSOList(List<TripDO> tripListDO, @Context CycleAvoidingMappingContext context);
    List<TripDO> tripListToDOList(List<TripSO> tripListSO, @Context CycleAvoidingMappingContext context);
}
