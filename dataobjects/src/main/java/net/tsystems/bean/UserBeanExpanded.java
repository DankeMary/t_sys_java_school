package net.tsystems.bean;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public class UserBeanExpanded implements Serializable {
    @Valid
    private UserBean user;
    private List<TicketBean> tickets;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<TicketBean> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketBean> tickets) {
        this.tickets = tickets;
    }
}
