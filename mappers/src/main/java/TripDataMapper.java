import dataobject.TripDataDO;
import org.mapstruct.Mapper;

@Mapper
public interface TripDataMapper {
    TripDataSO tripDataToSO(TripDataDO tdDO);
    TripDataDO tripDataToDO(TripDataSO tdSO);
}
