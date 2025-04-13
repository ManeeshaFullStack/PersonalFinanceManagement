package com.personalfinance.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.personalfinance.config.DatabaseConnection;
import com.personalfinance.model.Income;
import com.personalfinance.utility.SessionUtils;

/**
 * Servlet implementation class IncomeServlet
 */
@WebServlet("/IncomeServlet")
public class IncomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IncomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Load income categories from DB
        List<String> categories = new ArrayList<>();

        String sql = "SELECT Name FROM Categories WHERE Type = 'Income'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("Name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("incomeCategories", categories);
        
        // New code to fetch income records
        List<Income> incomeList = new ArrayList<>();
        String sqlIncomeList = "SELECT i.Source, i.Amount, c.Name AS Category, i.ReceivedDate " +
                     "FROM Income i " +
                     "JOIN Categories c ON i.CategoryId = c.CategoryId " +
                     "WHERE i.UserId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlIncomeList)) {

        	Integer userId = (Integer) SessionUtils.getAttribute(request, "UserId");
        	
            stmt.setInt(1, userId); // Replace with actual user ID from session
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Income income = new Income();
                income.setSource(rs.getString("Source"));
                income.setAmount(rs.getDouble("Amount"));
                income.setCategory(rs.getString("Category"));
                income.setReceivedDate(rs.getDate("ReceivedDate"));
                incomeList.add(income);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        request.setAttribute("incomeList", incomeList);
        
        request.getRequestDispatcher("/pages/income/income.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle form submission
        String description = request.getParameter("description");
        String amount = request.getParameter("amount");
        String category = request.getParameter("category");
        String incomeDate = request.getParameter("incomeDate");

        String sql = "INSERT INTO Income (UserId, Amount, Source, ReceivedDate, CategoryId) " +
                     "VALUES (?, ?, ?, ?, (SELECT CategoryId FROM Categories WHERE Name = ? AND Type = 'Income'))";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

        	Integer userId = (Integer) SessionUtils.getAttribute(request, "UserId");
            stmt.setInt(1, userId); // replace with logged-in user's ID (e.g., from session)
            stmt.setDouble(2, Double.parseDouble(amount));
            stmt.setString(3, description);
            stmt.setDate(4, Date.valueOf(incomeDate));
            stmt.setString(5, category);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/IncomeServlet"); // reload page
    }

}
