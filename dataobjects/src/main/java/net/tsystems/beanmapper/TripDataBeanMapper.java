package net.tsystems.beanmapper;

import net.tsystems.bean.TripDataBean;
import net.tsystems.serviceobject.TripDataSO;
import net.tsystems.utilmapper.BooleanMapper;
import net.tsystems.utilmapper.LocalDateMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses= {LocalDateMapper.class, BooleanMapper.class})
public interface TripDataBeanMapper {
    TripDataBean tripDataToBean(TripDataSO tdSO);
    TripDataSO tripDataToSO(TripDataBean tdBean);
    List<TripDataSO> tripDataListToSOList(List<TripDataBean> tdListBean);
    List<TripDataBean> tripDataListToBeanList(List<TripDataSO> tdListSO);
}
