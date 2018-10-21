package net.tsystems.entitymapper;

import net.tsystems.entities.StationDO;
import net.tsystems.serviceobject.StationSO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface StationEntityMapper {
    StationSO stationToSO(StationDO stDO);
    StationDO stationToDO(StationSO stSO);
    List<StationSO> stationListToSOList(List<StationDO> stListDO);
    List<StationDO> stationrListToDOList(List<StationSO> stListSO);
}
