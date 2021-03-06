package net.tsystems.entitymapper;

import net.tsystems.entities.PassengerDO;
import net.tsystems.serviceobject.PassengerSO;
import net.tsystems.utilmapper.LocalDateMapper;
import net.tsystems.utilmapper.LocalTimeMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses= {LocalDateMapper.class, LocalTimeMapper.class})
public interface PassengerEntityMapper {
    PassengerSO passengerToSO(PassengerDO pDO);
    PassengerDO passengerToDO(PassengerSO pSO);
    List<PassengerSO> passengerListToSOList(List<PassengerDO> pListDO);
    List<PassengerDO> passengerListToDOList(List<PassengerSO> pListSO);
}
