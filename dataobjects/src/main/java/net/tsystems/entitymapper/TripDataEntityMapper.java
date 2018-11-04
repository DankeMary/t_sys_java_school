package net.tsystems.entitymapper;

import net.tsystems.entities.TripDataDO;
import net.tsystems.serviceobject.TripDataSO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TripDataEntityMapper {
    TripDataSO tripDataToSO(TripDataDO tdDO);
    TripDataDO tripDataToDO(TripDataSO tdSO);
    List<TripDataSO> tripDataListToSOList(List<TripDataDO> tdListDO);
    List<TripDataDO> tripDataListToDOList(List<TripDataSO> tdListSO);
}
