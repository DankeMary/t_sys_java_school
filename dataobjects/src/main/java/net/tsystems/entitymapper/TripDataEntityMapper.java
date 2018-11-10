package net.tsystems.entitymapper;

import net.tsystems.entities.TripDataDO;
import net.tsystems.serviceobject.TripDataSO;
import net.tsystems.utilmapper.LocalDateMapper;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper(uses= LocalDateMapper.class)
public interface TripDataEntityMapper {
    TripDataSO tripDataToSO(TripDataDO tdDO);
    TripDataDO tripDataToDO(TripDataSO tdSO);
    List<TripDataSO> tripDataListToSOList(List<TripDataDO> tdListDO);
    List<TripDataDO> tripDataListToDOList(List<TripDataSO> tdListSO);
}
