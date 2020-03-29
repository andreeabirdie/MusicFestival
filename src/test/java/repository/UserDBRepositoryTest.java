package repository;

import domain.User;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

public class UserDBRepositoryTest {
    IUserRepository userDBRepository;

    private void connect(){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        userDBRepository = new UserDBRepository(properties);
    }
    @Test
    public void findOne() {
        connect();
        assertTrue(userDBRepository.findOne("andreeaBirdie","parola1"));
        assertFalse(userDBRepository.findOne("andreeaBirdie","parola2"));
        userDBRepository.disconnect();
    }
}