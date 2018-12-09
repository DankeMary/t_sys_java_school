package net.tsystems.service;

import net.tsystems.bean.TicketBean;
import net.tsystems.bean.TripDataBean;
import net.tsystems.bean.UserBean;
import net.tsystems.beanmapper.TicketBeanMapper;
import net.tsystems.beanmapper.TicketBeanMapperImpl;
import net.tsystems.entities.TicketDO;
import net.tsystems.entitydao.TicketDAO;
import net.tsystems.entitymapper.TicketEntityMapper;
import net.tsystems.entitymapper.TicketEntityMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service("ticketService")
@Transactional
public class TicketService {

    private static final Logger LOG = LoggerFactory.getLogger(TicketService.class);

    private TicketDAO ticketDao;
    private TicketEntityMapper entityMapper = new TicketEntityMapperImpl();
    private TicketBeanMapper beanMapper = new TicketBeanMapperImpl();

    private TripDataService tripDataService;
    private UserService userService;
    private PassengerService passengerService;

    public void create(TicketBean ticket) {
        try {
            ticketDao.create(ticketBeanToDO(ticket));
        } catch (Exception e) {
            LOG.error("Failed to create ticket", e);
        }
    }
    public void delete(int id) {
        try {
            ticketDao.delete(ticketDao.find(id));
        } catch (Exception e) {
            LOG.error(String.format("Failed to delete ticket by id=%s", id), e);
        }
    }

    public List<TicketBean> getTicketsForTrainSold(int trainId, int journeyID, int page, int maxResult) {
        TripDataBean tdBean = tripDataService.getById(journeyID);
        List<TicketBean> tickets = new LinkedList<>();
        try {
            tickets = ticketDOListToBeanList(ticketDao.getTicketsByTrainIdAndDate(trainId, tdBean.getTripDeparture(), page, maxResult));
        } catch (Exception e) {
            LOG.error(String.format("Failed to get tickets for train with id=%s on journey with id=%s", trainId, journeyID), e);
        }
        return tickets;
    }

    public List<TicketBean> getUserTicketsAfterNow(UserBean user, int page, int maxResult) {
        List<TicketBean> tickets = new LinkedList<>();
        try {
            tickets = ticketDOListToBeanList(ticketDao.getUserTicketsForAfterNow(userService.userBeanToDO(user), page, maxResult));
        } catch (Exception e) {
            LOG.error("Failed to get tickets for user", e);
        }
        return tickets;
    }

    @PreAuthorize("#username == authentication.principal.username")
    public void deleteTicket(String username, int ticketId) {
        try {
            TicketBean ticket = ticketDOtoBean(ticketDao.find(ticketId));
            delete(ticket.getId());
            passengerService.delete(ticket.getPassenger().getId());
            tripDataService.ticketWasErased(ticket);
        } catch (Exception e) {
            LOG.error(String.format("Failed to delete ticket by id=%s", ticketId), e);
        }
    }

    public int countTicketsForTrainSold(int trainId, int journeyId, int maxResult) {
        int cnt = 0;
        try {
        TripDataBean tdBean = tripDataService.getById(journeyId);
        cnt = ticketDao.countTicketsByTrainIdAndDatePages(trainId, tdBean.getTripDeparture(), maxResult);
        } catch (Exception e) {
            LOG.error(String.format("Failed to count tickets for : trainId=%s, journeyId=%s, ",
                    trainId, journeyId), e);
        }
        return cnt;
    }

    public int countUserTicketsAfterNow(String username, int maxResult) {
        return ticketDao.countUserTicketsForAfterNow(username, maxResult);
    }

    public boolean hasTicketsOnTrainSold(int trainId, LocalDate date) {
        return ticketDao.ticketsOnTrainSold(trainId, date);
    }

    public TicketBean ticketDOtoBean(TicketDO ticketDO) {
        return beanMapper.ticketToBean(entityMapper.ticketToSO(ticketDO));
    }

    public TicketDO ticketBeanToDO(TicketBean ticketBean) {
        return entityMapper.ticketToDO(beanMapper.ticketToSO(ticketBean));
    }

    public List<TicketBean> ticketDOListToBeanList(List<TicketDO> ticketDOList) {
        return beanMapper.ticketListToBeanList(entityMapper.ticketListToSOList(ticketDOList));
    }

    @Autowired
    public void setTicketDao(TicketDAO ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Autowired
    public void setTripDataService(TripDataService tripDataService) {
        this.tripDataService = tripDataService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setPassengerService(PassengerService passengerService) {
        this.passengerService = passengerService;
    }
}
