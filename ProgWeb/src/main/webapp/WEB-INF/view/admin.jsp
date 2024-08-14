<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Amministatore</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/admin/gestioneUtenti">Visualizzazione Utenti</a>
    <a href="${pageContext.request.contextPath}/admin/gestioneEventi">Gestione Eventi</a>
    <div id="button_logout">
    <button type="submit" name="logout">Logout</button>
</div>
</body>
</html>