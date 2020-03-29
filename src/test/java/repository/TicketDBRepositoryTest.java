package repository;

import domain.Ticket;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

public class TicketDBRepositoryTest {
    ITicketRepository ticketRepository;

    private void connect(){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ticketRepository = new TicketDBRepository(properties);
    }

    @Test
    public void save() {
        connect();
        Ticket t=ticketRepository.findOne("1",3);
        assertNull(t);
        ticketRepository.save(new Ticket("1",3,"Oana Vrabie"));
        t=ticketRepository.findOne("1",3);
        assertEquals(t.getBuyerName(),"Oana Vrabie");
        ticketRepository.delete("1",3);
        ticketRepository.disconnect();
    }
}