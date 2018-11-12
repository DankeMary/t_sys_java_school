package net.tsystems.bean;

public class SearchTicketForm {
    private TripDataBean fromTDBean;
    private TripDataBean toTDBean;
    //private List<TripDataBean> ticketPath;
    private int ticketsQty;

    public SearchTicketForm() {
    }

    public SearchTicketForm(TripDataBean fromTDBean, TripDataBean toTDBean, int ticketsQty) {
        this.fromTDBean = fromTDBean;
        this.toTDBean = toTDBean;
        this.ticketsQty = ticketsQty;
    }

    public TripDataBean getFromTDBean() {
        return fromTDBean;
    }

    public void setFromTDBean(TripDataBean fromTDBean) {
        this.fromTDBean = fromTDBean;
    }

    public TripDataBean getToTDBean() {
        return toTDBean;
    }

    public void setToTDBean(TripDataBean toTDBean) {
        this.toTDBean = toTDBean;
    }

    public int getTicketsQty() {
        return ticketsQty;
    }

    public void setTicketsQty(int ticketsQty) {
        this.ticketsQty = ticketsQty;
    }
}
