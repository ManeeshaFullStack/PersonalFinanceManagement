<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<link href="pages/includes/header.css" type="text/css" rel="stylesheet" />
<div class="main-header">
  <div>
    <a href="<%= request.getContextPath() %>/DashboardServlet">
      <img class="header-logo" src="<%= request.getContextPath() %>/img/logo 5.3.png" />
    </a>
  </div>
  <div class="navigation-container">
   <div>
	    <nav class="main-navigations">
	      <a href="<%= request.getContextPath() %>/IncomeServlet">Income tracking</a>
	      <a href="<%= request.getContextPath() %>/ExpenseServlet">Expense tracking</a>
	      <a href="<%= request.getContextPath() %>/BudgetServlet">budget management</a>
	      <a href="#">goal setting</a>
	    </nav>
    </div>
    <div class="menu-container">
      <button class="menu-button">Welcome, ${sessionScope.Username}</button>
      <div class="menu-dropdown">
        <a href="../profile/profile.html">My Profile</a>
        <a href="../change-password/changePassword.html">Change Password</a>
        <a href="../notifications/notifications.html">Notifications</a>
        <a href="<%= request.getContextPath() %>/pages/signout/signout.jsp">Sign Out</a>
      </div>
    </div>
  </div>
</div>
