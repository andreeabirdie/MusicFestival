package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Show {
    private String id;
    private String artistName;
    private LocalDate date;
    private String venue;
    private Integer remainingTickets;
    private Integer totalTickets;

    public Show(String id, String artistName, LocalDate date, String venue, int remainingTickets, int totalTickets) {
        this.id = id;
        this.artistName = artistName;
        this.date = date;
        this.venue = venue;
        this.remainingTickets = remainingTickets;
        this.totalTickets = totalTickets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int remainingTickets) {
        this.remainingTickets = remainingTickets;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    @Override
    public String toString() {
        return "Show{" +
                "id='" + id + '\'' +
                ", artistName='" + artistName + '\'' +
                ", date=" + date +
                ", venue='" + venue + '\'' +
                ", remainingTickets=" + remainingTickets +
                ", totalTickets=" + totalTickets +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return Objects.equals(artistName, show.artistName) &&
                Objects.equals(date, show.date);
    }

}
