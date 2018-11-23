package net.tsystems.entitydao;

import net.tsystems.AbstractDao;
import net.tsystems.entities.TicketDO;

import java.time.LocalDate;
import java.util.List;

public interface TicketDAO extends AbstractDao<TicketDO, Integer> {
    List<TicketDO> getTicketsByTrainIdAndDate(int trainId, LocalDate date, int page, int maxResult);
    int countTicketsByTrainIdAndDatePages(int trainId, LocalDate date, int maxResult);
    boolean ticketsOnTrainSold(int trainId, LocalDate date);
}
