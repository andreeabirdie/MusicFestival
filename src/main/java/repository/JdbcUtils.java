package repository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static Logger logger = LogManager.getLogger(IShowRepository.class.getName());
    private Properties jdbcProps;
    private  Connection instance=null;

    public JdbcUtils(Properties props){
        jdbcProps=props;
    }

    private Connection getNewConnection(){
        logger.trace("entry get new connect");
        String url=jdbcProps.getProperty("festival.dbTest.url");
        logger.info("trying to connect to database ... ");
        Connection con=null;
        try {
                con=DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error getting connection "+e);
        }
        return con;
    }

    public Connection getConnection(){
        logger.trace("entry connect");
        try {
            if (instance==null || instance.isClosed())
                instance=getNewConnection();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.trace("exit connect");
        return instance;
    }

    public void closeConnection(){
        try {
            if(instance!=null && !instance.isClosed())
                instance.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
