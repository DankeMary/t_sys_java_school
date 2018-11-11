package net.tsystems.entitymapper;

import net.tsystems.entities.RouteDO;
import net.tsystems.serviceobject.RouteSO;
import net.tsystems.utilmapper.LocalTimeMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = LocalTimeMapper.class)
public interface RouteEntityMapper {
    RouteSO routeToSO(RouteDO rDO);
    RouteDO routeToDO(RouteSO rSO);
    List<RouteSO> routeListToSOList(List<RouteDO> pListDO);
    List<RouteDO> routeListToDOList(List<RouteSO> pListSO);
}
