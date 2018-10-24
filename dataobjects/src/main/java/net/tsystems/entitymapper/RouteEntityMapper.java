package net.tsystems.entitymapper;

import net.tsystems.entities.RouteDO;
import net.tsystems.serviceobject.RouteSO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RouteEntityMapper {
    RouteSO routeToSO(RouteDO rDO);
    RouteDO routeToDO(RouteSO rSO);
    List<RouteSO> routeListToSOList(List<RouteDO> pListDO);
    List<RouteDO> routeListToDOList(List<RouteSO> pListSO);
}
