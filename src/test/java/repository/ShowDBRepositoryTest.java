package repository;

import domain.Show;
import org.junit.Test;

import javax.swing.text.DateFormatter;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class ShowDBRepositoryTest {
    IShowRepository showDBRepository;


    private void connect(){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        showDBRepository = new ShowDBRepository(properties);
    }


    @Test
    public void findArtist() {
        connect();
        LocalDate dateTime = LocalDate.now();
        List<Show> shows = showDBRepository.findArtist(dateTime);
        assertEquals(shows.size(),2);
        showDBRepository.disconnect();
    }

    @Test
    public void findAll() {
        connect();
        List<Show> shows = showDBRepository.findAll();
        assertEquals(shows.size(),2);
        showDBRepository.disconnect();
    }

    @Test
    public void ajutor(){
        connect();
        Show show = new Show("1","Twenty One Pilots",LocalDate.now(),"Sala Polivalenta",1598,1600);
        showDBRepository.update(show);
        show = new Show("2","The Neighbourhood",LocalDate.now(),"Arena BT",1499,1500);
        showDBRepository.update(show);

    }

    @Test
    public void update() {
        connect();
        Show show1 = showDBRepository.findOne("1");
        Show show2 = new Show("1","Zhu",show1.getDate(),"Sala Polivalenta",1500,1600);
        showDBRepository.update(show2);
        Show show3 = showDBRepository.findOne("1");
        assertEquals(show3.getArtistName(),"Zhu");
        showDBRepository.update(show1);
        showDBRepository.disconnect();
    }

    @Test
    public void findOne() {
        connect();
        Show show = showDBRepository.findOne("1");
        assertEquals(show.getArtistName(),"Twenty One Pilots");
        assertEquals(show.getTotalTickets(),1600);
        showDBRepository.disconnect();
    }
}