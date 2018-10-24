package net.tsystems.beanmapper;

import net.tsystems.bean.TrainBean;
import net.tsystems.serviceobject.TrainSO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TrainBeanMapper {
    TrainBean trainToBean(TrainSO tSO);
    TrainSO trainToSO(TrainBean tBean);
    List<TrainSO> trainListToSOList(List<TrainBean> tListBean);
    List<TrainBean> trainListToBeanList(List<TrainSO> tListSO);
}
