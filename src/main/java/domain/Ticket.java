package domain;

public class Ticket {
    private String idShow;
    private Integer ticketNumber;
    private String buyerName;

    public Ticket(String idShow, Integer ticketNumber, String buyerName) {
        this.idShow = idShow;
        this.ticketNumber = ticketNumber;
        this.buyerName = buyerName;
    }

    public String getIdShow() {
        return idShow;
    }

    public void setIdShow(String idShow) {
        this.idShow = idShow;
    }

    public Integer getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "idShow='" + idShow + '\'' +
                ", ticketNumber=" + ticketNumber +
                ", buyerName='" + buyerName + '\'' +
                '}';
    }
}
