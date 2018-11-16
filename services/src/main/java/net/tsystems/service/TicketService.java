package net.tsystems.service;

import net.tsystems.bean.TicketBean;
import net.tsystems.bean.TripDataBean;
import net.tsystems.beanmapper.TicketBeanMapper;
import net.tsystems.beanmapper.TicketBeanMapperImpl;
import net.tsystems.entities.TicketDO;
import net.tsystems.entitydao.TicketDAO;
import net.tsystems.entitymapper.TicketEntityMapper;
import net.tsystems.entitymapper.TicketEntityMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service("ticketService")
@Transactional
public class TicketService {
    private TicketDAO ticketDao;
    private TicketEntityMapper entityMapper = new TicketEntityMapperImpl();
    private TicketBeanMapper beanMapper = new TicketBeanMapperImpl();

    private TripDataService tripDataService;

    public void create(TicketBean ticket) {
        ticketDao.create(ticketBeanToDO(ticket));
    }

    public List<TicketBean> getTicketsForTrain(int trainId, int journeyID) {
        TripDataBean tdBean = tripDataService.getById(journeyID);

        return ticketDOListToBeanList(ticketDao.getTicketsByTrainIdAndDate(trainId, tdBean.getTripDeparture()));
    }

    public boolean ticketsOnTrainSold(int trainId, LocalDate date) {
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
