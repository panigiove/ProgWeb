<%@ page import="web.example.progweb.model.EventModel" %>
<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>

<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">I Nostri Eventi</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:forEach var="category" items="${categories}">
                    <li class="nav-item">
                        <form method="post" action="${pageContext.request.contextPath}/index/updateCards">
                            <input type="submit" value="${category.name}" class="nav-link active"/>
                            <input type="hidden" name="categoryId" value="${category.id}"/>
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</nav>

<div class="container text-center">
    <div class="row">
        <%
            if(request.getAttribute("events") != null){
                List<Event> events = (List<Event>) request.getAttribute("events");
                for (Event event : events) {
        %>
        <div class="col">
            <div class="card" style="width: 18rem;">
                <img src="..." class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title"><%= event.getName() %></h5>
                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                    <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>
    </div>
</div>
