package net.tsystems.entitymapper;

import net.tsystems.entities.TicketDO;
import net.tsystems.serviceobject.TicketSO;
import org.mapstruct.Mapper;

@Mapper
public interface TicketEntityMapper {
    TicketSO ticketToSO(TicketDO tDO);
    TicketDO ticketToDO(TicketSO tSO);
}
