package business;

import domain.Show;
import domain.Ticket;
import repository.IShowRepository;
import repository.ITicketRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TicketingService {
    private ITicketRepository ticketRepository;
    private IShowRepository showRepository;

    public TicketingService(ITicketRepository ticketRepository, IShowRepository showRepository) {
        this.ticketRepository = ticketRepository;
        this.showRepository = showRepository;
    }

    public List<Show> getShows(LocalDate date){
        List<Show> shows = showRepository.findArtist(date);
        showRepository.disconnect();
        return shows;
    }

    public void buyTickets(String idShow, Integer quantity,String buyer){
        for(int i=0;i<quantity;i++) {
            Show s = showRepository.findOne(idShow);
            int number = s.getTotalTickets() - s.getRemainingTickets() + 1;
            ticketRepository.save(new Ticket(idShow, number, buyer));
            s.setRemainingTickets(s.getRemainingTickets()-1);
            showRepository.update(s);
        }
        showRepository.disconnect();
        ticketRepository.disconnect();
    }

    public List<Show> getAllShows() {
        List<Show> shows = showRepository.findAll();
        showRepository.disconnect();
        return shows;
    }
}
