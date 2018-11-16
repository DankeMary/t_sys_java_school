package net.tsystems.entitydao;

import net.tsystems.AbstractDao;
import net.tsystems.entities.StationDO;

import java.util.List;

public interface StationDAO extends AbstractDao<StationDO, Integer> {
    StationDO findByName(String name);
    boolean isUniqueByName(int id, String name);
    boolean allStationsExist(List<String> stationNames);
}
