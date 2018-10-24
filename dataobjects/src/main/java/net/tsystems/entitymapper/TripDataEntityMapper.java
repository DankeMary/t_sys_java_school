package net.tsystems.entitymapper;

import net.tsystems.entities.TripDataDO;
import net.tsystems.serviceobject.TripDataSO;
import org.mapstruct.Mapper;

@Mapper
public interface TripDataEntityMapper {
    TripDataSO tripDataToSO(TripDataDO tdDO);
    TripDataDO tripDataToDO(TripDataSO tdSO);
}
