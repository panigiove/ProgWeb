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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <style>

        body {
            font-family: Arial, sans-serif;
            background-color: #A1C7A6;
            height: 100vh;
        }
        .container {
            justify-content: center;
            align-items: center;
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
<jsp:include page="topNavUser.jsp"/>
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

<jsp:include page="footer.jsp"/>

</body>
</html>
