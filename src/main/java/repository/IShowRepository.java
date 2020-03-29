package repository;

import domain.Show;

import java.time.LocalDate;
import java.util.List;

public interface IShowRepository {
    List<Show> findArtist(LocalDate date);
    List<Show> findAll();
    void update(Show s);
    Show findOne(String id);
    void disconnect();
}
