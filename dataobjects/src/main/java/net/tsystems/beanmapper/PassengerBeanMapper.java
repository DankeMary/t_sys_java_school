package net.tsystems.beanmapper;


import net.tsystems.bean.PassengerBean;
import net.tsystems.serviceobject.PassengerSO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PassengerBeanMapper {
    PassengerBean passengerToBean(PassengerSO pSO);
    PassengerSO passengerToSO(PassengerBean pBean);
    List<PassengerSO> passengerListToSOList(List<PassengerBean> pListBean);
    List<PassengerBean> passengerListToBeanList(List<PassengerSO> pListSO);
}
