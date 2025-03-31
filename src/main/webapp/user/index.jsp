<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.personalfinance.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List</title>
</head>
<body>
    <h2>List of Users</h2>
    
    <table border="1">
        <tr>
            <th>User ID</th>
            <th>Name</th>
            <th>Email</th>
        </tr>

        <%
            List<User> users = (List<User>) request.getAttribute("users");
            if (users != null) {
                for (User user : users) {
        %>
            <tr>
                <td><%= user.getUserId() %></td>
                <td><%= user.getName() %></td>
                <td><%= user.getEmail() %></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="3">No users found.</td>
            </tr>
        <%
            }
        %>
    </table>
    
     <a href="user/addUser.jsp">Add User</a>
     
</body>
</html>
