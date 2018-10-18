package net.tsystems.entitymapper;

import net.tsystems.entities.RouteDO;
import net.tsystems.serviceobject.RouteSO;
import org.mapstruct.Mapper;

@Mapper
public interface RouteEntityMapper {
    RouteSO routeToSO(RouteDO rDO);
    RouteDO routeToDO(RouteSO rSO);
}
