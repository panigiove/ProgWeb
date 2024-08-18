<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Boolean isLogged = (Boolean)session.getAttribute("isLogged");
    Boolean isAdmin = (Boolean)session.getAttribute("isAdmin");
%>

<style>
    .navbar{
        background-color: #A1C7A6 !important;
    }
</style>

<nav class="navbar navbar-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}">
            <img src="${pageContext.request.contextPath}/images/icon.svg" alt="Icon" width="30" height="30">
            EntriEasy
        </a>

        <div class="d-flex">
            <% if (isLogged == null || !isLogged || isAdmin) { %>
            <a class="nav-link me-3 text-primary" href="${pageContext.request.contextPath}/signup">Sign up</a>
            <a class="nav-link text-secondary" href="${pageContext.request.contextPath}/login">Login</a>
            <% } else { %>
            <a class="nav-link me-3" href="${pageContext.request.contextPath}/personalArea">Area Personale</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
            <% } %>
        </div>
    </div>
</nav>
