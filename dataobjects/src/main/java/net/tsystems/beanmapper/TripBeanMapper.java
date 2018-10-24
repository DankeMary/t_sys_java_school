package net.tsystems.beanmapper;

import net.tsystems.bean.TripBean;
import net.tsystems.serviceobject.TripSO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TripBeanMapper {
    TripBean tripToBean(TripSO tripSO);
    TripSO tripToSO(TripBean tripBean);
    List<TripSO> tripListToSOList(List<TripBean> tripListBean);
    List<TripBean> tripListToBeanList(List<TripSO> tripListSO);
}
