<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Object username = session.getAttribute("username");
    Object isAdmin = session.getAttribute("isAdmin");
%>

<nav class="navbar navbar-light bg-light">
    <div class="container-fluid">
        <!-- Spazio per l'icona e il nome del sito -->
        <a class="navbar-brand" href="#">
            <img src="${pageContext.request.contextPath}/images/icon.png" alt="Icona" width="30" height="30" class="d-inline-block align-text-top">
            EntriEasy
        </a>

        <!-- Collegamenti di navigazione allineati a destra -->
        <div class="d-flex">
            <% if (username == null || isAdmin == null) { %>
            <a class="nav-link me-3 text-primary" href="${pageContext.request.contextPath}/signup">Sign up</a>
            <a class="nav-link text-secondary" href="${pageContext.request.contextPath}/login">Login</a>
            <% } else { %>
            <a class="nav-link me-3" href="/personalArea">Area Personale</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
            <% } %>
        </div>
    </div>
</nav>
