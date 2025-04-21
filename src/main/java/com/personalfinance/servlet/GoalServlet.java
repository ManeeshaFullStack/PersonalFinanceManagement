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
import com.personalfinance.model.Goal;
import com.personalfinance.utility.SessionUtils;

/**
 * Servlet implementation class GoalServlet
 */
@WebServlet("/GoalServlet")
public class GoalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Goal> goalList = new ArrayList<>();
        String sql = "SELECT GoalId, UserId, Amount, Description, TargetDate, Progress FROM Goals WHERE UserId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Integer userId = (Integer) SessionUtils.getAttribute(request, "UserId");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Goal goal = new Goal();
                goal.setGoalId(rs.getInt("GoalId"));
                goal.setUserId(rs.getInt("UserId"));
                goal.setAmount(rs.getDouble("Amount"));
                goal.setDescription(rs.getString("Description"));
                goal.setTargetDate(rs.getDate("TargetDate"));
                goal.setProgress(rs.getDouble("Progress"));
                goalList.add(goal);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("goalList", goalList);
        request.getRequestDispatcher("/pages/goals/goals.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "insert";

        try {
            switch (action) {
                case "insert":
                    addGoal(request, response);
                    break;
                case "delete":
                    deleteGoal(request, response);
                    break;
                default:
                    addGoal(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void addGoal(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String amountStr = request.getParameter("goalAmount");
        String description = request.getParameter("goalDescription");
        String targetDate = request.getParameter("targerDate");

        String sql = "INSERT INTO Goals (UserId, Amount, Description, TargetDate, Progress) VALUES (?, ?, ?, ?, 0)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Integer userId = (Integer) SessionUtils.getAttribute(request, "UserId");

            stmt.setInt(1, userId);
            stmt.setDouble(2, Double.parseDouble(amountStr));
            stmt.setString(3, description);
            stmt.setDate(4, Date.valueOf(targetDate));

            stmt.executeUpdate();
        }

        response.sendRedirect(request.getContextPath() + "/GoalServlet");
    }

    private void deleteGoal(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int goalId = Integer.parseInt(request.getParameter("GoalId"));

        String sql = "DELETE FROM Goals WHERE GoalId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, goalId);
            pstmt.executeUpdate();
        }

        response.sendRedirect(request.getContextPath() + "/GoalServlet");
    }

}
