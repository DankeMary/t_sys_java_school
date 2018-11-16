package net.tsystems.entitydao;

import net.tsystems.AbstractDao;
import net.tsystems.entities.TicketDO;

import java.time.LocalDate;
import java.util.List;

public interface TicketDAO extends AbstractDao<TicketDO, Integer> {
    List<TicketDO> getTicketsByTrainIdAndDate(int trainId, LocalDate date);
    boolean ticketsOnTrainSold(int trainId, LocalDate date);
}
