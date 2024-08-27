<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
.topnav {
background-color: #f8f9fa;
padding: 0.5rem 1rem;
}
.topnav .nav-link {
color: #000;
font-weight: 500;
}
.topnav .nav-link:hover {
color: #007bff;
}
.topnav .nav-link.logout-link {
color: #dc3545;
}
.topnav .nav-link.logout-link:hover {
color: #c82333;
}
.navbar-nav {
flex-direction: row;
}
.nav-item {
margin: 0 1rem;
}
</style>

<nav class="navbar navbar-light topnav">
    <div class="container-fluid">
        <div class="d-flex justify-content-center w-100">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin">Pannello</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/gestioneUtenti">Visualizza Utenti</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/gestioneEventi">Gestione Eventi</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link logout-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>