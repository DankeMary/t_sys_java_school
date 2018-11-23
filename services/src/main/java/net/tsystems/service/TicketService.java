package net.tsystems.service;

import net.tsystems.bean.TicketBean;
import net.tsystems.bean.TripDataBean;
import net.tsystems.beanmapper.TicketBeanMapper;
import net.tsystems.beanmapper.TicketBeanMapperImpl;
import net.tsystems.entities.TicketDO;
import net.tsystems.entitydao.TicketDAO;
import net.tsystems.entitymapper.TicketEntityMapper;
import net.tsystems.entitymapper.TicketEntityMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void create(TicketBean ticket) {
        try {
            ticketDao.create(ticketBeanToDO(ticket));
        } catch (Exception e) {
            LOG.error("Failed to create ticket");
            e.printStackTrace();
        }
    }

    //TODO Delete ticket!!!
    public List<TicketBean> getTicketsForTrainSold(int trainId, int journeyID, int page, int maxResult) {
        TripDataBean tdBean = tripDataService.getById(journeyID);
        List<TicketBean> tickets = new LinkedList<>();
        try {
            tickets = ticketDOListToBeanList(ticketDao.getTicketsByTrainIdAndDate(trainId, tdBean.getTripDeparture(), page, maxResult));
        } catch (Exception e) {
            LOG.error(String.format("Failed to get tickets for train with id=%s on journey with id=%s", trainId, journeyID));
            e.printStackTrace();
        }
        return tickets;
    }

    //Help Functions
    public int countTicketsForTrainSold(int trainId, int journeyID, int maxResult) {
        TripDataBean tdBean = tripDataService.getById(journeyID);
        return ticketDao.countTicketsByTrainIdAndDatePages(trainId, tdBean.getTripDeparture(), maxResult);
    }

    public boolean hasTicketsOnTrainSold(int trainId, LocalDate date) {
        return ticketDao.ticketsOnTrainSold(trainId, date);
    }

    //Mappers
    public TicketBean ticketDOtoBean(TicketDO ticketDO) {
        return beanMapper.ticketToBean(entityMapper.ticketToSO(ticketDO));
    }

    public TicketDO ticketBeanToDO(TicketBean ticketBean) {
        return entityMapper.ticketToDO(beanMapper.ticketToSO(ticketBean));
    }

    public List<TicketBean> ticketDOListToBeanList(List<TicketDO> ticketDOList) {
        return beanMapper.ticketListToBeanList(entityMapper.ticketListToSOList(ticketDOList));
    }

    //Autowired
    @Autowired
    public void setTicketDao(TicketDAO ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Autowired
    public void setTripDataService(TripDataService tripDataService) {
        this.tripDataService = tripDataService;
    }
}
