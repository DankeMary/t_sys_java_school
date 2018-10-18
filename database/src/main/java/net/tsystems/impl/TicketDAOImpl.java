package net.tsystems.impl;

import net.tsystems.entities.TicketDO;
import net.tsystems.entitydao.TicketDAO;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDAOImpl extends AbstractDaoImpl<TicketDO, Integer> implements TicketDAO {
}
