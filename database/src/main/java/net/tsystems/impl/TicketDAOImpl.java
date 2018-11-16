package net.tsystems.impl;

import net.tsystems.entities.TicketDO;
import net.tsystems.entitydao.TicketDAO;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TicketDAOImpl extends AbstractDaoImpl<TicketDO, Integer> implements TicketDAO {
    @Override
    public List<TicketDO> getTicketsByTrainIdAndDate(int trainId, LocalDate date) {
        List<TicketDO> list = (List<TicketDO>) getEntityManager()
                .createQuery("from TicketDO ti where " +
                        "ti.from.route.trip.train.id=" + trainId
                        + " and ti.from.tripDeparture=\'" + date + "\'")
                .list();
        return list.size() == 0 ? null : list;
    }
}
