package net.tsystems.beanmapper;

import net.tsystems.bean.TicketBean;
import net.tsystems.serviceobject.TicketSO;
import net.tsystems.utilmapper.BooleanMapper;
import net.tsystems.utilmapper.LocalDateMapper;
import net.tsystems.utilmapper.LocalTimeMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses= {LocalDateMapper.class, BooleanMapper.class, LocalTimeMapper.class})
public interface TicketBeanMapper {
    TicketBean ticketToBean(TicketSO tickSO);
    TicketSO ticketToSO(TicketBean tickBean);
    List<TicketSO> ticketListToSOList(List<TicketBean> tickListBean);
    List<TicketBean> ticketListToBeanList(List<TicketSO> tickListSO);
}
