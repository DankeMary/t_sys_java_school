package net.tsystems.beanmapper;

import net.tsystems.bean.StationBean;
import net.tsystems.serviceobject.StationSO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface StationBeanMapper {
    StationBean stationToBean(StationSO stSO);
    StationSO stationToSO(StationBean stBean);
    List<StationSO> stationListToSOList(List<StationBean> stListBean);
    List<StationBean> stationListToBeanList(List<StationSO> stListSO);
}
