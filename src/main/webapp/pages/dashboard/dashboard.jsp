<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dashboard</title>
    <link href="<%= request.getContextPath() %>/pages/dashboard/dashboard.css" rel="stylesheet" type="text/css" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  </head>
  <body>

	<jsp:include page="/pages/includes/header.jsp" />

    <div class="dashboard-content">
    <div class="dash-board-headding">
	      <div style="float:left">
	      	<h4>Welcome to Your Financial Dashboard</h4>
	      </div>
     </div>

     <div class="whole-container">
	      <div class="charts-container">
	        <div class="chart-box">
	          <canvas id="incomeChart"></canvas>
	          <p>"Track your income, grow your wealth!"</p>
	          <a href="<%= request.getContextPath() %>/IncomeServlet">Go to Income Tracking</a>
	        </div>
	        <div class="chart-box">
	          <canvas id="expenseChart"></canvas>
	          <p>"Control expenses, secure your future!"</p>
	          <a href="<%= request.getContextPath() %>/ExpenseServlet">Go to Expense Tracking</a>
	        </div>
	        
	        <div class="chart-box">
	          <canvas id="budgetChart"></canvas>
	          <p>"Plan your budget, achieve your dreams!"</p>
	          <a href="../budget/budget.html">Go to Budget Management</a>
	        </div>
	        <div class="chart-box">
	          <canvas id="goalsChart"></canvas>
	          <p>"Set goals, accomplish milestones!"</p>
	          <a href="../goals/goals.html">Go to Goal Setting</a>
	        </div>
	      </div>
	      <div class="content-box">
		      	<form action="<%= request.getContextPath() %>/DashboardServlet" method="get">
			          <div>
			          	<label>Total Income: <span>₹ <%= request.getAttribute("totalIncome") %></span></label>
			          	<br />
			          </div>
			          <div>
			          	<label>Total Expenses: <span>₹ <%= request.getAttribute("totalExpenses") %></span></label>
			          	<br />
			          </div>
			          <div>
			          	<label>Total Balance: <span>₹ <%= request.getAttribute("totalBalance") %></span></label>
			          	<br />
			          </div>
			          
			          <div>
			          	<label>Budget Status: <span><%= request.getAttribute("budgetStatus") %></span></label>
			          	<br />
			          </div>
			          
			          <div>
			          	<label>Goal Status: <span><%= request.getAttribute("goalStatus") %></span></label>
			          	<br />
			          </div>
			          
					  <div>
			          	<label>From:</label><input id="fromDate" name="fromDate"  type="date" />
			          </div>
			          <div>
			          	<label>To:</label><input id="toDate" name="toDate" type="date" />
			          </div>
			          <div>
			          	<button type="submit" class="menu-button">Search</button>
			          	<button type="submit" class="menu-button" onclick="return loadDefault()">Reset</button>
			          </div>
		          </form>
	        </div>
        </div>
    </div>

    <!---------------------------------------------------->
    <!------------------FOOTER TO ACTION SECTION------------------->
    <!---------------------------------------------------->

    <!-- <section class="Footer"> -->
    <footer class="section-footer">
      <div>
		<a href="<%= request.getContextPath() %>/DashboardServlet">
      		<img class="header-logo" src="<%= request.getContextPath() %>/img/logo 5.3.png" />
      	</a>

        <p class="Copyright">
          Copyright &copy; 2025 by FinTrack, Inc. All rights reserved.
        </p>
      </div>

      <div>
        <p class="footer-headding">contact us</p>
        <p class="contact-info">
          623 Harrison St., 2nd Floor, San Francisco, CA 94107
        </p>
        <p>
          <a href="tel:415-201-6370" class="number">415-201-6370</a><br />
          <a href="mailto:hello@Homlyfood.com" class="email"
            >hello@FinTrack.com</a
          >
        </p>
      </div>

      <nav>
        <p class="footer-headding">Account</p>
        <ul class="footer-acconts">
          <li>
            <a href="../signup/signup.html" class="account-links"
              >Create account</a
            >
          </li>
          <li>
            <a href="../login/login.html" class="account-links">Sign in</a>
          </li>
          <li><a href="#" class="account-links">iOS app</a></li>
          <li><a href="#" class="account-links">Android app</a></li>
        </ul>
      </nav>

      <nav>
        <p class="footer-headding">Company</p>
        <ul class="footer-acconts">
          <li>
            <a
              href="../aboutUs/aboutUs.html"
              target="_self"
              class="account-links"
              >About us</a
            >
          </li>
          <li>
            <a
              href="../successStories/successStories.html"
              target="_self"
              class="account-links"
              >Success Stories</a
            >
          </li>
          <li>
            <a
              href="../financialAdvices/financialAdvices.html"
              target="_self"
              class="account-links"
              >Financial advices</a
            >
          </li>
          <li>
            <a href="../notifications/notifications.html" class="account-links"
              >Alerts and Notifications</a
            >
          </li>
        </ul>
      </nav>

      <nav>
        <p class="footer-headding">Resources</p>
        <ul class="footer-acconts">
          <li>
            <a
              href="../ImportanceFinance/ImportanceFinance.html"
              target="_self"
              class="account-links"
              >Importance finance</a
            >
          </li>
          <li>
            <a
              href="../helpCenter/helpCenter.html"
              target="_self"
              class="account-links"
              >Help center</a
            >
          </li>
          <li>
            <a
              href="../security-privacy/security-privacy.html"
              target="_self"
              class="account-links"
              >Privacy and terms</a
            >
          </li>
        </ul>
      </nav>
    </footer>
    <!-- </section> -->
    <div class="dev-credits">
      <p>
        My other work
        <a href="https://maneeshafullstack.github.io/Homlyfood" target="_blank"
          >Homlyfood</a
        >
      </p>
      Designed &amp; Developed by
      <a href="javascript:void(0)">Maneesha Sangam</a><br />
    </div>

<%
    List<String> incomeLabels = (List<String>) request.getAttribute("incomeLabels");
    List<Double> incomeData = (List<Double>) request.getAttribute("incomeData");
    
    List<String> expenseLabels = (List<String>) request.getAttribute("expenseLabels");
    List<Double> expenseData = (List<Double>) request.getAttribute("expenseData");       
%>

<script>
    // Serialize categories
    const incomeLabels = [
        <% for (int i = 0; i < incomeLabels.size(); i++) { %>
            "<%= incomeLabels.get(i).replace("\"", "\\\"") %>"<%= (i < incomeLabels.size() - 1) ? "," : "" %>
        <% } %>
    ];
    
    const expenseLabels = [
        <% for (int i = 0; i < expenseLabels.size(); i++) { %>
            "<%= expenseLabels.get(i).replace("\"", "\\\"") %>"<%= (i < expenseLabels.size() - 1) ? "," : "" %>
        <% } %>
    ];

    // Serialize incomeData
    const incomeData = [
        <% for (int i = 0; i < incomeData.size(); i++) { %>
            <%= incomeData.get(i) %><%= (i < incomeData.size() - 1) ? "," : "" %>
        <% } %>
    ];
    
    // Serialize incomeData
    const expenseData = [
        <% for (int i = 0; i < expenseData.size(); i++) { %>
            <%= expenseData.get(i) %><%= (i < expenseData.size() - 1) ? "," : "" %>
        <% } %>
    ];
    
    function generateColors(count) {
    	  const colors = [];
    	  for (let i = 0; i < count; i++) {
    	    // Generate a random color in hexadecimal format
    	    colors.push('#' + Math.floor(Math.random() * 16777215).toString(16));
    	  }
    	  return colors;
    	}

</script>


    <script>
      document.addEventListener("DOMContentLoaded", function () {
        const menuButton = document.querySelector(".menu-button");
        const menuDropdown = document.querySelector(".menu-dropdown");

        menuButton.addEventListener("click", function (event) {
          event.stopPropagation();
          menuDropdown.classList.toggle("show");
        });

        document.addEventListener("click", function () {
          menuDropdown.classList.remove("show");
        });

        // Example chart data
        const exampleData = {
          income: [40, 30, 30],
          expenses: [50, 20, 30],
          budget: [60, 25, 15],
          goals: [70, 15, 15],
        };

        function createChart(canvasId, data, labels, backgroundColors) {
          new Chart(document.getElementById(canvasId), {
            type: "pie",
            data: {
              labels: labels,
              datasets: [
                {
                  data: data,
                  backgroundColor: backgroundColors,
                },
              ],
            },
            options: {
              responsive: true,
            },
          });
        }

        // Generate colors based on the number of categories
        const incomeLabelsBackgroundColors = generateColors(incomeLabels.length);
        const expenseLabelsBackgroundColors = generateColors(expenseLabels.length);
     
        createChart(
          "incomeChart",
          incomeData,
          incomeLabels,
          incomeLabelsBackgroundColors
        );
        createChart(
          "expenseChart",
          expenseData,
          expenseLabels,
          expenseLabelsBackgroundColors
        );
        createChart(
          "budgetChart",
          exampleData.budget,
          ["Essentials", "Savings", "Leisure"],
          ["#9C27B0", "#009688", "#FFC107"]
        );
        createChart(
          "goalsChart",
          exampleData.goals,
          ["Short-term", "Mid-term", "Long-term"],
          ["#673AB7", "#3F51B5", "#8BC34A"]
        );
      });
    </script>
    
   
    <script>
	    window.addEventListener('DOMContentLoaded', () => {
	    	loadDefault();
	    });
	    
	    function loadDefault(){
		      const today = new Date();
		      const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);
		      const lastDay = new Date(today.getFullYear(), today.getMonth() + 1, 0);
		
		      function formatDate (date) {
		    	  const day = date.getDate().toString().padStart(2, '0');
		    	  const month = (date.getMonth() + 1).toString().padStart(2, '0');
		    	  const year = date.getFullYear();
		    	  
		    	  var formatDate = year +"-" + month + "-" + day;
		    	  
		        return formatDate;
		      };
		
		      document.getElementById("fromDate").value = formatDate(firstDay);
		      document.getElementById("toDate").value = formatDate(lastDay);
	    }
  </script>
  
  

	<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script  nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
  </body>
</html>
