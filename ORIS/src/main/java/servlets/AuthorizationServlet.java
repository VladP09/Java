package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import repository.UserRepository;
import repository.UserRepositoryJDBCImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.UUID;

@WebServlet("/aut")
public class AuthorizationServlet extends HttpServlet {
    private static final String DB_USER = "vlad";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    private UserRepository usersRepository;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            usersRepository = new UserRepositoryJDBCImpl(connection, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        PrintWriter writer = response.getWriter();

        if (usersRepository.findUser(email, password)) {
            writer.println("ok");
        } else {
            writer.println("fail");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getCookies() == null) {
            request.getRequestDispatcher("/html/authorization.html").forward(request, response);
        } else {
            Cookie[] cookies = request.getCookies();
            String uuid = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    uuid = cookie.getValue();
                }
            }
            String userName = usersRepository.findIdByUUID(uuid);

            if (!userName.equals("null")) {
                request.getRequestDispatcher("/successfulRegistration.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }
    }
}
