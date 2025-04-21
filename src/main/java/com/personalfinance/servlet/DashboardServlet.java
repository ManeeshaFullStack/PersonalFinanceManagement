package com.personalfinance.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.personalfinance.config.DatabaseConnection;
import com.personalfinance.utility.SessionUtils;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		  // Retrieve userId from session
		Integer userId = (Integer) SessionUtils.getAttribute(request, "UserId");

        List<String> incomeLabels = new ArrayList<>();
        List<Double> incomeData = new ArrayList<>();
       
        List<String> expenseLabels = new ArrayList<>();
        List<Double> expenseData = new ArrayList<>();

        String fromDate = request.getParameter("fromDate"); // format: yyyy-MM-dd
        String toDate = request.getParameter("toDate");

     // If fromDate or toDate is null or empty, set to current month's range
        if (fromDate == null || fromDate.isEmpty() || toDate == null || toDate.isEmpty()) {
            LocalDate today = LocalDate.now();
            LocalDate firstDayOfMonth = today.withDayOfMonth(1);
            LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());

            fromDate = firstDayOfMonth.toString(); // "yyyy-MM-dd"
            toDate = lastDayOfMonth.toString();
        }
        
        String incomeSql = "SELECT c.Name AS CategoryName, SUM(i.Amount) AS TotalIncome " +
                           "FROM Income i " +
                           "JOIN Categories c ON i.CategoryId = c.CategoryId " +
                           "WHERE c.Type = 'Income' AND i.UserId = ? " +
                           "AND i.ReceivedDate BETWEEN ? AND ? " +
                           "GROUP BY c.Name";

        String expenseSql = "SELECT c.Name AS CategoryName, SUM(e.Amount) AS TotalExpense " +
                            "FROM Expenses e " +
                            "JOIN Categories c ON e.CategoryId = c.CategoryId " +
                            "WHERE c.Type = 'Expense' AND e.UserId = ? " +
                            "AND e.ExpenseDate BETWEEN ? AND ? " +  // << Add this
                            "GROUP BY c.Name";

        try (Connection conn = DatabaseConnection.getConnection()) {

            // Retrieve income data
            try (PreparedStatement stmt = conn.prepareStatement(incomeSql)) {
                stmt.setInt(1, userId);
                stmt.setDate(2, java.sql.Date.valueOf(fromDate));
                stmt.setDate(3, java.sql.Date.valueOf(toDate));
                
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String category = rs.getString("CategoryName");
                    Double amount = rs.getDouble("TotalIncome");
                    incomeLabels.add(category);
                    incomeData.add(amount);
                }
            }

            // Retrieve expense data
            try (PreparedStatement stmt = conn.prepareStatement(expenseSql)) {
                stmt.setInt(1, userId);
                stmt.setDate(2, java.sql.Date.valueOf(fromDate));
                stmt.setDate(3, java.sql.Date.valueOf(toDate));
                
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String category = rs.getString("CategoryName");
                    Double amount = rs.getDouble("TotalExpense");
                    expenseLabels.add(category);
                    expenseData.add(amount);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        request.setAttribute("incomeLabels", incomeLabels);
        request.setAttribute("incomeData", incomeData);
        
        request.setAttribute("expenseLabels", expenseLabels);
        request.setAttribute("expenseData", expenseData);
        
        double totalIncome = 0;
        double totalExpenses = 0;

        // After populating incomeData and expenseData lists:
        for (double income : incomeData) {
            totalIncome += income;
        }

        for (double expense : expenseData) {
            totalExpenses += expense;
        }

        double totalBalance = totalIncome - totalExpenses;

        // Check Budget
        double totalBudget = 0.0;
        String budgetSql = "SELECT SUM(Amount) AS TotalBudget FROM Budgets WHERE UserId = ? AND StartDate <= ? AND EndDate >= ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(budgetSql)) {
            
            stmt.setInt(1, userId);
            stmt.setDate(2, java.sql.Date.valueOf(toDate));
            stmt.setDate(3, java.sql.Date.valueOf(fromDate));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalBudget = rs.getDouble("TotalBudget");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String budgetStatus = totalExpenses <= totalBudget ? "Within Budget" : "Over Budget";

     // --- Check Goal Status ---
        String goalStatus = "No Active Goal";
        double totalGoalAmount = 0.0;

        // Fetch total goal amount where target date is still valid
        String goalSql = "SELECT SUM(Amount) AS TotalGoalAmount FROM Goals WHERE UserId = ? AND TargetDate >= ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(goalSql)) {

            stmt.setInt(1, userId);
            stmt.setDate(2, java.sql.Date.valueOf(toDate));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalGoalAmount = rs.getDouble("TotalGoalAmount");

                if (totalGoalAmount > 0) {
                    if (totalBalance >= totalGoalAmount) {
                        goalStatus = "Goal Achieved";
                    } else {
                        goalStatus = "Goal In Progress";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set attributes to pass to JSP
        request.setAttribute("totalIncome", totalIncome);
        request.setAttribute("totalExpenses", totalExpenses);
        request.setAttribute("totalBalance", totalBalance);
        request.setAttribute("budgetStatus", budgetStatus);
        request.setAttribute("goalStatus", goalStatus);
        
        request.getRequestDispatcher("/pages/dashboard/dashboard.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
