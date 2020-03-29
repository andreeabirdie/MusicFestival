package repository;

import domain.Show;
import domain.Ticket;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Properties;

public class TicketDBRepository implements ITicketRepository {
    private JdbcUtils dbUtils;
    private static Logger logger = LogManager.getLogger(IShowRepository.class.getName());

    public TicketDBRepository(Properties properties) {
        dbUtils = new JdbcUtils(properties);
    }

    public void disconnect(){
        dbUtils.closeConnection();
    }

    @Override
    public void delete(String id, int number) {
        logger.trace("entry delete "+id+" "+number);
        String sql = "DELETE FROM Tickets WHERE idShow =? and numberTicket = ?";
        try (PreparedStatement preStmt = dbUtils.getConnection().prepareStatement(sql)) {
            preStmt.setString(1, id);
            preStmt.setInt(2, number);
            preStmt.executeUpdate();
            logger.info("deleted ticket with id "+ id +" and number " + number);
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public void save(Ticket ticket) {
        logger.trace("entry save"+ticket);
        String sql = "INSERT INTO Tickets (idShow,numberTicket,buyerName) values (?,?,?)";
        try (PreparedStatement preStmt = dbUtils.getConnection().prepareStatement(sql)) {
            preStmt.setString(1, ticket.getIdShow());
            preStmt.setInt(2, ticket.getTicketNumber());
            preStmt.setString(3, ticket.getBuyerName());
            preStmt.executeUpdate();
            logger.info("save"+ticket);
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.trace("exit save"+ticket);
    }


    @Override
    public Ticket findOne(String id, int number) {
        logger.trace("entry findOne "+id+" "+number);
        try (PreparedStatement preStmt = dbUtils.getConnection().prepareStatement(
                "SELECT * FROM Tickets where idShow = ? and numberTicket = ?")) {
            preStmt.setString(1, id);
            preStmt.setInt(2, number);
            try (ResultSet rs = preStmt.executeQuery()) {
                if (!rs.next() ) {
                    return null;
                }
                logger.trace("exiting findOne "+id+" "+number);
                logger.info("findOne "+id+" "+number);
                return new Ticket(rs.getString("idShow"),
                        rs.getInt("numberTicket"), rs.getString("buyerName"));
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
            return null;
        }
    }
}


