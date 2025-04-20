package com.personalfinance.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.personalfinance.config.DatabaseConnection;
import com.personalfinance.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) action = "list";

        try {
            switch (action) {
                case "new":
                    request.getRequestDispatcher("addUser.jsp").forward(request, response);
                    break;
                case "insert":
                    addUser(request, response);
                    break;
                case "edit":
                    getUser(request, response);
                    break;
                case "update":
                    updateUser(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM AppUsers"; // Change table name if needed

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Create a User object and add it to the list
                users.add(new User(
                    rs.getInt("UserId"), 
                    rs.getString("Username"), 
                    rs.getString("Email")
                ));
            }
        }

        // Set the users list in request scope
        request.setAttribute("users", users);

        // Forward to index.jsp in the "user" folder
        request.getRequestDispatcher("/user/index.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            addUser(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String sql = "INSERT INTO AppUsers (Username, Email, Password) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();
        }

        // Redirect back to UserServlet to refresh the user list
        response.sendRedirect(request.getContextPath() + "/LoginServlet");
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        String sql = "SELECT * FROM AppUsers WHERE UserId = ?";
        String[] user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new String[]{
                    String.valueOf(rs.getInt("UserId")),
                    rs.getString("Username"),
                    rs.getString("Email")
                };
            }
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("editUser.jsp").forward(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        String email = request.getParameter("email");

        String sql = "UPDATE AppUsers SET Email = ? WHERE UserId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }

        response.sendRedirect("UserServlet");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));

        String sql = "DELETE FROM AppUsers WHERE UserId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        }

        response.sendRedirect("UserServlet");
    }
}
