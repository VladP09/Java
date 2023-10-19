package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.UUID;


@WebServlet("/reg")
public class RegistrationServlet extends HttpServlet {
    private static final String DB_USER = "vlad";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");



        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            String uuid = UUID.randomUUID().toString();
            Statement statement = connection.createStatement();
            String sqlInsertUser = "insert into driver1(first_name, email, password)" +
                    " values ('" + name + "', '"  + email + "', '" + password + "');";


            System.out.println(sqlInsertUser);

            int affectedRows = statement.executeUpdate(sqlInsertUser);

            PrintWriter printWriter = response.getWriter();

            if(affectedRows > 0 && !password.isEmpty() && !name.isEmpty() && !email.isEmpty()) {
                Cookie cookies = new Cookie("id", uuid);
                response.addCookie(cookies);

                String sqlInsertUserUUID = "insert into uuid(uuid)" +
                        " values ('" + uuid + "');";

                statement.executeUpdate(sqlInsertUserUUID);

                printWriter.println("Регистрация прошла успешно");
            } else {
                printWriter.println("Не удалось выполнить регистрацию");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/html/registration.html").forward(request, response);
    }

}
