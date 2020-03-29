package repository;

import domain.User;

import java.sql.SQLException;

public interface IUserRepository {
    boolean findOne(String username,String password);
    void disconnect();
}
