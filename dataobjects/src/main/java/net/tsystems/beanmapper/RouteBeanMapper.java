package net.tsystems.beanmapper;

import net.tsystems.bean.RouteBean;
import net.tsystems.serviceobject.RouteSO;
import net.tsystems.utilmapper.LocalTimeMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = LocalTimeMapper.class)
public interface RouteBeanMapper {
    RouteBean routeToBean(RouteSO rSO);
    RouteSO routeToSO(RouteBean rBean);
    List<RouteSO> routeListToSOList(List<RouteBean> rListBean);
    List<RouteBean> routeListToBeanList(List<RouteSO> rListSO);

}
