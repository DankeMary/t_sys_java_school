package net.tsystems.entitymapper;

import net.tsystems.CycleAvoidingMappingContext;
import net.tsystems.entities.TripDO;
import net.tsystems.serviceobject.TripSO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TripEntityMapper {
    TripSO tripToSO(TripDO tripDO);
    TripDO tripToDO(TripSO tripSO);
    List<TripSO> tripListToSOList(List<TripDO> tripListDO);
    List<TripDO> tripListToDOList(List<TripSO> tripListSO);
}
