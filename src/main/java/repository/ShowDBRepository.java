package repository;

import domain.Show;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ShowDBRepository implements IShowRepository {
    private JdbcUtils dbUtils;
    private static Logger logger = LogManager.getLogger(IShowRepository.class.getName());

    public ShowDBRepository(Properties properties) {
        dbUtils = new JdbcUtils(properties);
    }

    public void disconnect(){
        dbUtils.closeConnection();
    }


    @Override
    public List<Show> findAll() {
        logger.trace("Entry findAll");
        try(Statement stmt = dbUtils.getConnection().createStatement()) {
            try(ResultSet rs = stmt.executeQuery("SELECT * FROM Shows")) {
                List<Show> result = new ArrayList<>();
                while (rs.next()) {
                    Show show = new Show(rs.getString("id"), rs.getString("artistName"),
                            rs.getDate("showDate").toLocalDate(), rs.getString("venue")
                            , rs.getInt("remainingTickets")
                            , rs.getInt("totalTickets"));
                    result.add(show);
                }
                logger.trace("Exiting find all");
                logger.info("find all");
                //delete close
                stmt.close();
                return result;
            }} catch (SQLException e) {
            e.printStackTrace();
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<Show> findArtist(LocalDate date) {
        logger.trace("Entry findArtist");
        try(PreparedStatement stmt = dbUtils.getConnection().prepareStatement
                ("SELECT * FROM Shows WHERE showDate=?")) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            try(ResultSet rs = stmt.executeQuery()) {
                List<Show> result = new ArrayList<>();
                while (rs.next()) {
                    Show show = new Show(rs.getString("id"), rs.getString("artistName"),
                            rs.getDate("showDate").toLocalDate(), rs.getString("venue")
                            , rs.getInt("remainingTickets")
                            , rs.getInt("totalTickets"));
                    result.add(show);
                }
                logger.trace("Exiting findArtist");
                logger.info("findArtist");
                //delete close
                stmt.close();
                return result;
            }} catch (SQLException e) {
            e.printStackTrace();
            logger.error(e);
            return null;
        }
    }

    @Override
    public void update(Show s) {
        logger.trace("entry update");
        try(PreparedStatement preStmt = dbUtils.getConnection().prepareStatement("update Shows set artistName = ?," +
                " showDate =?, venue = ?, remainingTickets =?,totalTickets =? where id = ?")) {
            preStmt.setString(1, s.getArtistName());
            preStmt.setDate(2, Date.valueOf(s.getDate()));
            preStmt.setString(3, s.getVenue());
            preStmt.setInt(4, s.getRemainingTickets());
            preStmt.setInt(5, s.getTotalTickets());
            preStmt.setString(6, s.getId());
            preStmt.executeUpdate();
            logger.trace("exiting update");
            logger.info("update");
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public Show findOne(String id) {
        logger.trace("entry findOne");
        try (PreparedStatement preStmt = dbUtils.getConnection().prepareStatement("SELECT * FROM Shows where id = ?")) {
            preStmt.setString(1, id);
            try (ResultSet rs = preStmt.executeQuery()) {
                if (!rs.next() ) {
                    return null;
                }
                logger.trace("exiting findOne");
                logger.info("findOne");
                return new Show(rs.getString("id"), rs.getString("artistName"),
                        rs.getDate("showDate").toLocalDate(), rs.getString("venue")
                        , rs.getInt("remainingTickets")
                        , rs.getInt("totalTickets"));
            }
        }
        catch (SQLException e) {
                logger.error(e);
                e.printStackTrace();
                return null;
            }
    }
}
