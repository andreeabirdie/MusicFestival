package repository;

import domain.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Properties;

public class UserDBRepository implements IUserRepository {
    private JdbcUtils dbUtils;
    private static Logger logger = LogManager.getLogger(IShowRepository.class.getName());

    public UserDBRepository(Properties properties) {
        dbUtils = new JdbcUtils(properties);
    }

    public void disconnect(){
        dbUtils.closeConnection();
    }

    @Override
    public boolean findOne(String username,String password) {
        logger.trace("entry findUser "+username + " "+password);
        try (PreparedStatement preStmt = dbUtils.getConnection().prepareStatement("SELECT 1 FROM Users where username = ? and password =?")) {
            preStmt.setString(1, username);
            preStmt.setString(2, password);
            try (ResultSet rs = preStmt.executeQuery()) {
                if (!rs.next()) {
                    return false;
                }
                logger.trace("exit findUser "+username+" "+password);
                logger.info("findUser "+username+" "+password);
                return true;
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
            return false;
        }
    }
}
