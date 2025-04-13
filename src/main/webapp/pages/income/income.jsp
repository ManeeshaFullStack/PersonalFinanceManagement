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
        <img class="header-logo" src="<%= request.getContextPath() %>/img/logo 5.3.png" />
      </div>
      <div>
        <nav class="main-navigations">
          <a href="<%= request.getContextPath() %>/IncomeServlet">Income tracking</a>
          <a href="<%= request.getContextPath() %>/ExpenseServlet">Expense tracking</a>
          <a href="../budget/budget.html">budget management </a>
          <a href="#">goal setting</a>
          <a href="#">financial reports</a>
          <a>|</a>
          <a href="../reports/reports.html">Welcome, ${sessionScope.Username}</a>
        </nav>
      </div>
    </div>
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
	        <th>Income Date</th>
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
  </body>
</html>
