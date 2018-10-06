import dataobject.TripDataDO;
import org.mapstruct.Mapper;

@Mapper//(uses=TicketMapper.class)
public interface TripDataMapper {
    TripDataSO tripDataToSO(TripDataDO tdDO);
    TripDataDO tripDataToDO(TripDataSO tdSO);
}
