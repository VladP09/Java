package repository;

import models.User;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class MainRepository {
    private static final String DB_USER = "vlad";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            UserRepositoryJDBCImpl userRepositoryJDBC = new UserRepositoryJDBCImpl(connection, statement);

            List<User> allUser = userRepositoryJDBC.findAll();
            for (User user : allUser){
                System.out.println(user);
            }
            Scanner scanner = new Scanner(System.in);
            String firstName = scanner.nextLine();
            String lastName = scanner.nextLine();
            int age = scanner.nextInt();

            String sqlInsertUser = "insert into driver(first_name, last_name, age) " +
                    "values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            System.out.println(sqlInsertUser);

            int affectedRows = preparedStatement.executeUpdate();

            System.out.println("Было добавлено " + affectedRows + " строк");

            System.out.println("Введите email пользователя, которого хотите проверить на наличие в базе данных: ");
            String email = scanner.nextLine();
            System.out.println(userRepositoryJDBC.findByLogin(email));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

