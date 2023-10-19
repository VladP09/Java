package repository;

import models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryJDBCImpl implements UserRepository {

    private Connection connection;
    private Statement statement;
    private static final String SQL_SELECT_ALL_FROM_DRIVER = "select * from driver1";


    public UserRepositoryJDBCImpl(Connection connection, Statement statement) {
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
                " values ('" + entity.getName() + "', '" + entity.getEmail() + "', '" + entity.getPassword() + "');";
        try {
            statement.executeUpdate(sqlInsertUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);
            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getInt("id"))
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
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);
            while (resultSet.next()) {
                User user = User.builder()
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .build();
                if (user.getPassword().equals(password)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByLogin(String email) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);

            while (resultSet.next()) {
                User user = User.builder()
                        .email(resultSet.getString("email"))
                        .build();
                if (user.getEmail().equals(email)) {
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    @Override
    public String findIdByUUID(String uuid) {
        try (Statement statement = connection.createStatement();
             Statement statement1 = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("select * from uuid");
            ResultSet resultSet1 = statement1.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);

            User UserCopy = null;

            while (resultSet.next()) {

                User user = User.builder()
                        .id(resultSet.getInt("id"))
                        .uuid(resultSet.getString("uuid"))
                        .build();
                if (user.getUuid().equals(uuid)) {
                    UserCopy = user;
                }
            }


            while (resultSet1.next()) {
                System.out.println(resultSet1.getString("id"));
                User user = User.builder()
                        .id(resultSet1.getInt("id"))
                        .name(resultSet1.getString("first_name"))
                        .build();
                if (user.getId().equals(UserCopy.getId())) {
                    return user.getName();
                }
            }
            return "null";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
