package net.tsystems.entitymapper;

import net.tsystems.entities.TripDO;
import net.tsystems.serviceobject.TripSO;
import org.mapstruct.Mapper;

@Mapper
public interface TripEntityMapper {
    TripSO tripToSO(TripDO tDO);
    TripDO tripToDO(TripSO tSO);
}
