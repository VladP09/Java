package repository;

import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryJDBCImpl implements UserRepository{

    private Connection connection;
    private Statement statement;
    private static final String SQL_SELECT_ALL_FROM_DRIVER = "select * from driver1";


    public UserRepositoryJDBCImpl(Connection connection, Statement statement){
        this.connection = connection;
        this.statement = statement;
    }


    @Override
    public void save(User entity) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sqlInsertUser = "insert into driver1(first_name, email, password)" +
                    " values ('" + entity.getName() + "', '"  + entity.getEmail() + "', '" + entity.getPassword() + "');";
        try {
            statement.executeUpdate(sqlInsertUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);
            while (resultSet.next()){
                User user =User.builder()
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .name(resultSet.getString("first_name"))
                        .build();
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean findUser(String username, String password) {
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);
            while (resultSet.next()){
                User user =User.builder()
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .build();
                if (user.getPassword().equals(password)){
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean findByLogin(String email) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);

            while (resultSet.next()) {
                User user = User.builder()
                        .email(resultSet.getString("email"))
                        .build();
                if (user.getEmail().equals(email)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
