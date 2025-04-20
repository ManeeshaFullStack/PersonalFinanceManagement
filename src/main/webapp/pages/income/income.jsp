<%@ page import="java.util.List" %>
<%@ page import="com.personalfinance.model.Income" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Income</title>
    <link href="pages/income/income.css" type="text/css" rel="stylesheet" />
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
        </nav>
      </div>
    </div>
    <div class="whole-container">
	    <div class="container">
	      <div>
	        <h1>Income management</h1>
	         <form action="<%= request.getContextPath() %>/IncomeServlet" method="post">
	            <label for="description">Income Description</label>
	            <input type="text" name="description" required maxlength="100" placeholder="Maximum 100 characters only" />
	
	            <label for="amount">Amount</label>
	            <input type="number" name="amount" placeholder="eg:20000" required />
	
	            <label for="category">Category</label>
	            <select name="category" class="category" required>
	                <option value="">Select one</option>
	                <%
	                    List<String> categories = (List<String>) request.getAttribute("incomeCategories");
	                    for (String cat : categories) {
	                %>
	                    <option value="<%= cat %>"><%= cat %></option>
	                <%
	                    }
	                %>
	            </select>
	
	            <label for="incomeDate">Income Date</label>
	            <input type="date" name="incomeDate" required />
	
	            <button type="submit" class="custom-button">Save</button>
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
		        <th>Income Date</th>
		         <th>Delete</th>
		      </tr>
		      <%
		        List<Income> incomeList = (List<Income>) request.getAttribute("incomeList");
		        if (incomeList != null) {
		          for (Income income : incomeList) {
		      %>
		      <tr>
		        <td><%= income.getSource() %></td>
		        <td><%= income.getAmount() %></td>
		        <td><%= income.getCategory() %></td>
		        <td><%= income.getReceivedDate() %></td>
		        <td>
			      <form action="<%= request.getContextPath() %>/IncomeServlet" method="post" onsubmit="return confirm('Are you sure you want to delete this record?');">
			        <input type="hidden" name="IncomeId" value="<%= income.getIncomeId() %>" />
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
		        <td colspan="4">No income records found.</td>
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
