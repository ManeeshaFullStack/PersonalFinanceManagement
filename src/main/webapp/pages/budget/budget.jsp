<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinance.model.Budget" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Budget</title>
    <link href="pages/budget/budget.css" type="text/css" rel="stylesheet" />
  </head>
  <body>
  
	<jsp:include page="/pages/includes/header.jsp" />
	
	<div class="whole-container">
	    <div class="container">
	      <div>
	        <h1>Budget management</h1>
	        <form action="<%= request.getContextPath() %>/BudgetServlet" method="post">
	          <lable  class="label-name" for="budgetDescription"  >Budget Description </lable>
	          <input type="text" name="budgetDescription" required maxlength="250" placeholder="maximum 250 characters only" />
	
	          <lable class="label-name" for="budgetAmount">Budget Amount</lable>
	          <input type="number" placeholder="eg:20000" name="budgetAmount" required />
	
	          <label class="label-name" for="startingDate">Starting Date</label>
	          <input type="date" name="startingDate" required />
	
	          <label class="label-name" for="endingDate">Ending Date</label>
	          <input type="date" name="endingDate" required />
	
	          <button type="submit"  class="custom-button">Save</button>
	        </form>
	      </div>
	    </div>
	
	    <div class="summary-table">
	      <div>
	        <table>
	          <tr>
	            <th>Description</th>
	            <th>Amount</th>
	            <th>Starting Date</th>
	            <th>Ending Date</th>
	             <th>Delete</th>
	          </tr>
			      <%
			        List<Budget> budgetList = (List<Budget>) request.getAttribute("budgetList");
			        if (budgetList != null && !budgetList.isEmpty()) {
			          for (Budget budget : budgetList) {
			      %>
			      <tr>
			        <td><%= budget.getDescription() %></td>
			        <td><%= budget.getAmount() %></td>
			        <td><%= budget.getStartDate() %></td>
			        <td><%= budget.getEndDate() %></td>
			        <td>
				      <form action="<%= request.getContextPath() %>/BudgetServlet" method="post" onsubmit="return confirm('Are you sure you want to delete this record?');">
				        <input type="hidden" name="BudgetId" value="<%= budget.getBudgetId() %>" />
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
