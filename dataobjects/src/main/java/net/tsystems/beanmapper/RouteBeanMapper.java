package net.tsystems.beanmapper;

import net.tsystems.bean.RouteBean;
import net.tsystems.serviceobject.RouteSO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RouteBeanMapper {
    RouteBean routeToBean(RouteSO rSO);
    RouteSO routeToSO(RouteBean rBean);
    List<RouteSO> routeListToSOList(List<RouteBean> rListBean);
    List<RouteBean> routeListToBeanList(List<RouteSO> rListSO);

}
