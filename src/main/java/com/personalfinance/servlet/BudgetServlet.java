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
import com.personalfinance.model.Budget;
import com.personalfinance.model.Income;
import com.personalfinance.utility.SessionUtils;

/**
 * Servlet implementation class BudgetServlet
 */
@WebServlet("/BudgetServlet")
public class BudgetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BudgetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	List<Budget> budgetList = new ArrayList<>();
        String sqlBudgetList = "SELECT BudgetId, UserId, Amount, Description, StartDate, EndDate " +
                               "FROM Budgets WHERE UserId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBudgetList)) {

            Integer userId = (Integer) SessionUtils.getAttribute(request, "UserId");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Budget budget = new Budget();
                budget.setBudgetId(rs.getInt("BudgetId"));
                budget.setUserId(rs.getInt("UserId"));
                budget.setAmount(rs.getDouble("Amount"));
                budget.setDescription(rs.getString("Description"));
                budget.setStartDate(rs.getDate("StartDate"));
                budget.setEndDate(rs.getDate("EndDate"));
                budgetList.add(budget);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("budgetList", budgetList);
        request.getRequestDispatcher("/pages/budget/budget.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "insert";

        try {
            switch (action) {
                case "insert":
                    addBudget(request, response);
                    break;
                case "delete":
                    deleteBudget(request, response);
                    break;
                default:
                    addBudget(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void addBudget(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String amountStr = request.getParameter("budgetAmount");
        String description = request.getParameter("budgetDescription");
        String startDate = request.getParameter("startingDate");
        String endDate = request.getParameter("endingDate");

        String sql = "INSERT INTO Budgets (UserId, Amount, Description, StartDate, EndDate) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Integer userId = (Integer) SessionUtils.getAttribute(request, "UserId");
            stmt.setInt(1, userId);
            stmt.setDouble(2, Double.parseDouble(amountStr));
            stmt.setString(3, description);
            stmt.setDate(4, Date.valueOf(startDate));
            stmt.setDate(5, Date.valueOf(endDate));

            stmt.executeUpdate();
        }

        response.sendRedirect(request.getContextPath() + "/BudgetServlet");
    }

    private void deleteBudget(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int budgetId = Integer.parseInt(request.getParameter("BudgetId"));

        String sql = "DELETE FROM Budgets WHERE BudgetId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, budgetId);
            pstmt.executeUpdate();
        }

        response.sendRedirect(request.getContextPath() + "/BudgetServlet");
    }

}

