<%--
  Created by IntelliJ IDEA.
  User: Giovanni
  Date: 08/08/2024
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            width: 300px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
        .form-group input {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
        }
        .form-group input[type="submit"] {
            width: 100%;
            background-color: #007BFF;
            color: white;
            border: none;
            cursor: pointer;
            padding: 10px;
            font-size: 16px;
        }
        .form-group input[type="submit"]:hover {
            opacity: 0.9;
        }
        .error-message {
            color: red;
            margin-bottom: 15px;
            font-weight: bold;
        }
        .signup-redirect {
            margin-top: 15px;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Login</h2>

    <% if (request.getAttribute("error") != null) { %>
    <div class="error-message">
        Credenziali non valide. Riprova.
    </div>
    <% } %>

    <form id="loginForm" action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <button type="submit" value="Login">Login</button>
        </div>
        <div class="signup-redirect">
            Non hai un account? <a href="${pageContext.request.contextPath}/signup">Iscriviti!</a>
        </div>
    </form>
</div>

</body>
</html>
