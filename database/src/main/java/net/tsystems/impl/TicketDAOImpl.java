package net.tsystems.impl;

import net.tsystems.entities.TicketDO;
import net.tsystems.entitydao.TicketDAO;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TicketDAOImpl extends AbstractDaoImpl<TicketDO, Integer> implements TicketDAO {
    @Override
    public List<TicketDO> getTicketsByTrainIdAndDate(int trainId, LocalDate date, int page, int maxResult) {
        Query q = getEntityManager()
                .createQuery("from TicketDO ti where " +
                        "ti.from.route.trip.train.id=" + trainId
                        + " and ti.from.tripDeparture=\'" + date + "\'");


        return findAll(q, page, maxResult);
    }

    @Override
    public int countTicketsByTrainIdAndPages(int trainId, LocalDate date, int maxResult) {
        return countPages(
                getEntityManager()
                        .createQuery("select count(*) from TicketDO ti " +
                                "where ti.from.route.trip.train.id=" + trainId
                                + " and ti.from.tripDeparture=\'" + date + "\'"),
                maxResult
        );
    }

    @Override
    public boolean ticketsOnTrainSold(int trainId, LocalDate date) {
        Long qty = (Long) getEntityManager()
                .createQuery("select count(*) as n from TicketDO ti " +
                        "where ti.from.route.trip.train.id=" + trainId
                        + " and ti.from.tripDeparture=\'" + date + "\'").uniqueResult();
        return qty != 0;
    }
}
