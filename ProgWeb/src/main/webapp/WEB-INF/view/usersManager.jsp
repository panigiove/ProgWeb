<%--
  Created by IntelliJ IDEA.
  User: Giovanni
  Date: 14/08/2024
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ page import="java.util.List" %>
<%@ page import="web.example.progweb.model.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Amministratore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <style>
        .container {
            margin-top: 20px;
        }
        .table th, .table td {
            text-align: center;
        }
        .btn-custom {
            margin: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="mb-4">Event Management</h1>

    <!-- Form for sorting events -->
    <form method="get" action="admin" class="mb-4">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" id="sortByClicks" name="sortByClicks" <%= request.getParameter("sortByClicks") != null ? "checked" : "" %> />
            <label class="form-check-label" for="sortByClicks">
                Sort by Clicks
            </label>
        </div>
        <button type="submit" class="btn btn-primary mt-2">Apply</button>
    </form>

    <!-- Display users in a table -->
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Date of Birth</th>
            <th>Email</th>
            <th>Phone Number</th>
            <th>N° Purchases</th>
            <th>Username</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<User> users = (List<User>) request.getAttribute("users");
            for (User user : users) {
        %>
        <tr>
            <td><%= user.getId() %></td>
            <td><%= user.getName() %></td>
            <td><%= user.getSurname() %></td>
            <td><%= user.getBirthDate() %></td>
            <td><%= user.getEmail() %></td>
            <td><%= user.getPhone() %></td>
            <td><%= user.getnPurchases() %></td>
            <td><%= user.getUsername() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
