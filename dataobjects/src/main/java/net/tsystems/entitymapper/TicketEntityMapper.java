package net.tsystems.entitymapper;

import net.tsystems.entities.TicketDO;
import net.tsystems.serviceobject.TicketSO;
import net.tsystems.utilmapper.LocalDateMapper;
import net.tsystems.utilmapper.LocalTimeMapper;
import org.mapstruct.Mapper;

@Mapper(uses= {LocalDateMapper.class, LocalTimeMapper.class})
public interface TicketEntityMapper {
    TicketSO ticketToSO(TicketDO tDO);
    TicketDO ticketToDO(TicketSO tSO);
}
