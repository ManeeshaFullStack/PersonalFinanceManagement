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
import com.personalfinance.model.Expense;

/**
 * Servlet implementation class ExpenseServlet
 */
@WebServlet("/ExpenseServlet")
public class ExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpenseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Load income categories from DB
        List<String> categories = new ArrayList<>();

        String sql = "SELECT Name FROM Categories WHERE Type = 'Expense'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("Name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("expenseCategories", categories);
        
        // New code to fetch income records
        List<Expense> expenseList = new ArrayList<>();
        String sqlIncomeList = "SELECT e.Description, e.Amount, c.Name AS Category, e.ExpenseDate " +
                     "FROM Expenses e " +
                     "JOIN Categories c ON e.CategoryId = c.CategoryId " +
                     "WHERE e.UserId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlIncomeList)) {

            stmt.setInt(1, 1); // Replace with actual user ID from session
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	Expense expense = new Expense();
            	expense.setDescription(rs.getString("Description"));
            	expense.setAmount(rs.getDouble("Amount"));
            	expense.setCategory(rs.getString("Category"));
            	expense.setExpenseDate(rs.getDate("ExpenseDate"));
            	expenseList.add(expense);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        request.setAttribute("expenseList", expenseList);
        
        request.getRequestDispatcher("/pages/expenses/expenses.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle form submission
        String description = request.getParameter("description");
        String amount = request.getParameter("amount");
        String category = request.getParameter("category");
        String expenseDate = request.getParameter("expenseDate");

        String sql = "INSERT INTO Expenses (UserId, Amount, Description, ExpenseDate, CategoryId) " +
                     "VALUES (?, ?, ?, ?, (SELECT CategoryId FROM Categories WHERE Name = ? AND Type = 'Expense'))";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, 1); // replace with logged-in user's ID (e.g., from session)
            stmt.setDouble(2, Double.parseDouble(amount));
            stmt.setString(3, description);
            stmt.setDate(4, Date.valueOf(expenseDate));
            stmt.setString(5, category);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/ExpenseServlet"); // reload page
    }


}
