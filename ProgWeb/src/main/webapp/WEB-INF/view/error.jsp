<%--
  Created by IntelliJ IDEA.
  User: Giovanni
  Date: 09/08/2024
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error <%= request.getAttribute("errorCode") %> </title>
</head>
<body>
<div class="error-container">
    <h1>Error <%= request.getAttribute("errorCode") %></h1>
    <p><%= request.getAttribute("message") %></p>
    <a href="javascript:history.back()">Go Back</a>
</div>
</body>
</html>
