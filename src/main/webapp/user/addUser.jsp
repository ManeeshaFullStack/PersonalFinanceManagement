<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add User</title>
</head>
<body>
    <h2>Add New User</h2>
   <form action="<%= request.getContextPath() %>/UserServlet" method="post">
        <label>Username:</label>
        <input type="text" name="username" required /><br><br>
        
        <label>Password:</label>
        <input type="password" name="password" required /><br><br>

        <label>Email:</label>
        <input type="email" name="email" required /><br><br>

        <button type="submit">Add User</button>
    </form>
    <br>
    <a href="<%= request.getContextPath() %>/UserServlet">Back to Users</a>
</body>
</html>
