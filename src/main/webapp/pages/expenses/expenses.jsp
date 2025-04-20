<%@ page import="java.util.List" %>
<%@ page import="com.personalfinance.model.Expense" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Expense</title>
    <link href="pages/expenses/expenses.css" type="text/css" rel="stylesheet" />
  </head>
  <body>
    <div class="main-header">
      <div>
		<a href="<%= request.getContextPath() %>/DashboardServlet">
      		<img class="header-logo" src="<%= request.getContextPath() %>/img/logo 5.3.png" />
      	</a>
      </div>
      <div>
        <nav class="main-navigations">
          <a href="<%= request.getContextPath() %>/IncomeServlet">Income tracking</a>
          <a href="<%= request.getContextPath() %>/ExpenseServlet">Expense tracking</a>
          <a href="../budget/budget.html">budget management </a>
          <a href="#">goal setting</a>
          <a href="#">financial reports</a>
        </nav>
      </div>
    </div>
    <div class="whole-container">
	    <div class="container">
	      <div>
	        <h1>Expense Management</h1>
	         <form action="<%= request.getContextPath() %>/ExpenseServlet" method="post">
	            <label for="description">Expense Description</label>
	            <input type="text" name="description" required maxlength="255" placeholder="Maximum 255 characters only" />
	
	            <label for="amount">Amount</label>
	            <input type="number" name="amount" placeholder="eg:20000" required />
	
	            <label for="category">Category</label>
	            <select name="category" class="category" required>
	                <option value="">Select one</option>
	                <%
	                    List<String> categories = (List<String>) request.getAttribute("expenseCategories");
	                    for (String cat : categories) {
	                %>
	                    <option value="<%= cat %>"><%= cat %></option>
	                <%
	                    }
	                %>
	            </select>
	
	            <label for="expenseDate">Expense Date</label>
	            <input type="date" name="expenseDate" required />
	
	            <button type="submit">SAVE</button>
	        </form>
	      </div>
	    </div>
	
		<div class="summary-table">
		  <div>
		    <table style="width: fit-content;">
		      <tr>
		        <th>Description</th>
		        <th>Amount</th>
		        <th>Category</th>
		        <th>Expense Date</th>
		        <th>Delete</th>
		      </tr>
		      <%
		        List<Expense> expenseList = (List<Expense>) request.getAttribute("expenseList");
		        if (expenseList != null) {
		          for (Expense expense : expenseList) {
		      %>
		      <tr>
		        <td><%= expense.getDescription() %></td>
		        <td><%= expense.getAmount() %></td>
		        <td><%= expense.getCategory() %></td>
		        <td><%= expense.getExpenseDate() %></td>
				<td>
			      <form action="<%= request.getContextPath() %>/ExpenseServlet" method="post" onsubmit="return confirm('Are you sure you want to delete this record?');">
			        <input type="hidden" name="ExpenseId" value="<%= expense.getExpenseId() %>" />
			         <input type="hidden" name="action" value="delete" />
			         <button style="margin-top:0px" class="delete-button" type="submit"><ion-icon name="trash-outline"></ion-icon></button>
			      </form>
    			</td>
		      </tr>
		      <%
		          }
		        } else {
		      %>
		      <tr>
		        <td colspan="4">No expense records found.</td>
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
