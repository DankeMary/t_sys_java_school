package net.tsystems.entitymapper;

import net.tsystems.entities.TicketDO;
import net.tsystems.serviceobject.TicketSO;
import net.tsystems.utilmapper.LocalDateMapper;
import org.mapstruct.Mapper;

@Mapper(uses= LocalDateMapper.class)
public interface TicketEntityMapper {
    TicketSO ticketToSO(TicketDO tDO);
    TicketDO ticketToDO(TicketSO tSO);
}
