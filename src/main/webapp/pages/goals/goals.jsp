<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinance.model.Goal" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Financial Goals</title>
    <link href="pages/goals/goals.css" type="text/css" rel="stylesheet" />
  </head>
  <body>
  
    <jsp:include page="/pages/includes/header.jsp" />
    
    
    <div class="whole-container">
	    <div class="container">
	      <div>
	        <h1>Financial Goals</h1>
	        <form action="<%= request.getContextPath() %>/GoalServlet" method="post">
	          <lable class="label-name" for="goalDescription" >Goal Description</lable>
	
	          <input type="text" name="goalDescription" required maxlength="255" placeholder="maximum 255 characters only" />
	
	          <lable class="label-name" for="goalAmount">Goal Amount</lable>
	          
	          <input type="number"  name="goalAmount" placeholder="eg:20000" required />
	
	          <label class="label-name" for="targerDate" >Target Date</label>
	          <input type="date" name="targerDate" required />
	
	           <button type="submit"  class="custom-button">Save</button>
	        </form>
	      </div>
	    </div>
	
	    <div class="summary-table">
	      <div>
	        <table style="width: fit-content;">
	          <tr>
	            <th>Description</th>
	            <th>Amount</th>
	            <th>Target Date</th>
	            <th>Delete</th>
	          </tr>
	          <%
			        List<Goal> goalList = (List<Goal>) request.getAttribute("goalList");
			        if (goalList != null && !goalList.isEmpty()) {
			          for (Goal goal : goalList) {
			      %>
			      <tr>
			        <td><%= goal.getDescription() %></td>
			        <td><%= goal.getAmount() %></td>
			        <td><%= goal.getTargetDate() %></td>
			        <td>
				      <form action="<%= request.getContextPath() %>/GoalServlet" method="post" onsubmit="return confirm('Are you sure you want to delete this record?');">
				        <input type="hidden" name="GoalId" value="<%= goal.getGoalId() %>" />
				         <input type="hidden" name="action" value="delete" />
				         <button style="margin-top:0px" class="custom-button" type="submit"><ion-icon name="trash-outline"></ion-icon></button>
				      </form>
	    			</td>
			      </tr>
			      <%
			       }
			        } else {
			      %>
			      <tr>
			        <td colspan="5">No budget records found.</td>
			      </tr>
			      <%
			        }
			      %>
	        </table>
	      </div>
	    </div>
    </div>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script  nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
  </body>
</html>
