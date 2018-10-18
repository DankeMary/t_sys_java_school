package net.tsystems.entitymapper;

import net.tsystems.entities.StationDO;
import net.tsystems.serviceobject.StationSO;
import org.mapstruct.Mapper;

@Mapper
public interface StationEntityMapper {
    StationSO stationToSO(StationDO sDO);
    StationDO stationToDO(StationSO sSO);
}
