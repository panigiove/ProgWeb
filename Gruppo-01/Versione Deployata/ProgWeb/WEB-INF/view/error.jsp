<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EntriEasy</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/icon.svg">
</head>
<body>
<div class="error-container">
    <h1>Error <%= request.getAttribute("errorCode") %> <%= request.getAttribute("errorString") %></h1>
    <p><%= request.getAttribute("message") %></p>
    <a href="javascript:history.back()">Go Back</a>
</div>
</body>
</html>
