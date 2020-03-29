package repository;

import domain.Ticket;

public interface ITicketRepository {
    void save(Ticket ticket);
    Ticket findOne(String id,int number);
    void disconnect();
    void delete(String id,int number);
}
