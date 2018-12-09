package net.tsystems.impl;

import net.tsystems.entities.TicketDO;
import net.tsystems.entities.UserDO;
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
                        "ti.from.route.trip.train.id = :trainId and " +
                        " ti.from.tripDeparture = \'" + date + "\'" +
                        " order by ti.passenger.lastName, ti.passenger.firstName");
        q.setParameter("trainId", trainId);
        //q.setParameter("date", java.sql.Date.valueOf(date));
        return findAll(q, page, maxResult);
    }

    @Override
    public int countTicketsByTrainIdAndDatePages(int trainId, LocalDate date, int maxResult) {
        return countPages(
                getEntityManager()
                        .createQuery("select count(*) from TicketDO ti " +
                                "where ti.from.route.trip.train.id = :trainId and " +
                                " ti.from.tripDeparture = \'" + date + "\'")
                        .setParameter("trainId", trainId),
                        //.setParameter("date", java.sql.Date.valueOf(date)),
                maxResult
        );
    }

    @Override
    public boolean ticketsOnTrainSold(int trainId) {
        Long qty = (Long) getEntityManager()
                .createQuery("select count(*) as n from TicketDO ti " +
                        "where ti.from.route.trip.train.id = :trainId")
                .setParameter("trainId", trainId)
                .uniqueResult();
        return qty != 0;
    }

    @Override
    public boolean ticketsOnTrainSold(int trainId, LocalDate date) {
        Long qty = (Long) getEntityManager()
                .createQuery("select count(*) as n from TicketDO ti " +
                        "where ti.from.route.trip.train.id = :trainId and " +
                        " ti.from.tripDeparture = \'" + date + "\' ")
                .setParameter("trainId", trainId)
                //.setParameter("date", java.sql.Date.valueOf(date))
                .uniqueResult();
        return qty != 0;
    }

    @Override
    public List<TicketDO> getUserTicketsForAfterNow(UserDO user, int page, int maxResult) {
        Query q = getEntityManager()
                .createQuery("from TicketDO ti where " +
                        " ti.boughtBy.username = :username and " +
                        " ti.from.date > now() " +
                        " order by ti.from.date, ti.passenger.lastName, ti.passenger.firstName")
                .setParameter("username", user.getUsername());
        return findAll(q, page, maxResult);
    }

    @Override
    public int countUserTicketsForAfterNow(String username, int maxResult) {
        return countPages(
                getEntityManager()
                        .createQuery("select count(*) from TicketDO ti where " +
                                "ti.boughtBy.username = :username "
                                + " and ti.from.date > now()")
                .setParameter("username", username),
                maxResult
        );
    }
}
