package net.tsystems.bean;

public class SearchTicketForm {
    private TripDataBean fromTDBean;
    private TripDataBean toTDBean;
    private int ticketsQty;

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
