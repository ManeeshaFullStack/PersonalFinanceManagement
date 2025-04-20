package com.personalfinance.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.personalfinance.config.DatabaseConnection;
import com.personalfinance.utility.SessionUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
        String password = request.getParameter("password");

        Integer userId = authenticateUser(username, password);
        if (userId != null) {
        	SessionUtils.setAttribute(request, "UserId", userId);
        	SessionUtils.setAttribute(request, "Username", getUserDisplayName(userId));
            response.sendRedirect(request.getContextPath() + "/DashboardServlet");
        } else {
            // Invalid login, show error on Login.jsp
        	HttpSession session = request.getSession();
        	session.setAttribute("errorMessage", "Invalid username or password.");
        	response.sendRedirect(request.getContextPath() + "/pages/login/login.jsp");
        }
	}
	
	
	private Integer authenticateUser(String username, String password) {
	    String sql = "SELECT UserId FROM AppUsers WHERE LOWER(Email) = LOWER(?) AND Password = ?";

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, username);
	        stmt.setString(2, password); // Ensure password format matches DB

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("UserId"); // Return the UserId if authentication is successful
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	private String getUserDisplayName(Integer userId) {
	    String sql = "SELECT Username FROM AppUsers WHERE UserId = ?";

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getString("Username"); // Return the UserId if authentication is successful
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
