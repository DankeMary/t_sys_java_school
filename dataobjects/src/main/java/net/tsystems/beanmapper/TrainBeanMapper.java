package net.tsystems.beanmapper;

import net.tsystems.bean.TrainBean;
import net.tsystems.serviceobject.TrainSO;
import net.tsystems.utilmapper.LocalTimeMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = LocalTimeMapper.class)
public interface TrainBeanMapper {
    TrainBean trainToBean(TrainSO tSO);
    TrainSO trainToSO(TrainBean tBean);
    List<TrainSO> trainListToSOList(List<TrainBean> tListBean);
    List<TrainBean> trainListToBeanList(List<TrainSO> tListSO);
}
