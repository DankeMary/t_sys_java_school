package net.tsystems.beanmapper;


import net.tsystems.bean.PassengerBean;
import net.tsystems.serviceobject.PassengerSO;
import net.tsystems.utilmapper.BooleanMapper;
import net.tsystems.utilmapper.LocalDateMapper;
import net.tsystems.utilmapper.LocalTimeMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses= {LocalDateMapper.class, BooleanMapper.class, LocalTimeMapper.class})
public interface PassengerBeanMapper {
    PassengerBean passengerToBean(PassengerSO pSO);
    PassengerSO passengerToSO(PassengerBean pBean);
    List<PassengerSO> passengerListToSOList(List<PassengerBean> pListBean);
    List<PassengerBean> passengerListToBeanList(List<PassengerSO> pListSO);
}
