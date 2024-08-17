<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Object username = session.getAttribute("username");
    Object isAdmin = session.getAttribute("isAdmin");
%>

<nav class="navbar navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <img src="${pageContext.request.contextPath}/images/icon.jpg" alt="Icon" width="30" height="30">
            EntriEasy
        </a>

        <div class="d-flex">
            <% if (username == null || isAdmin == null) { %>
            <a class="nav-link me-3 text-primary" href="${pageContext.request.contextPath}/signup">Sign up</a>
            <a class="nav-link text-secondary" href="${pageContext.request.contextPath}/login">Login</a>
            <% } else { %>
            <a class="nav-link me-3" href="${pageContext.request.contextPath}/personalArea">Area Personale</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
            <% } %>
        </div>
    </div>
</nav>
