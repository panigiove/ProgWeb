<%@ page import="web.example.progweb.model.EventModel" %>
<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Giovanni
  Date: 07/08/2024
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<% Integer id_location; %>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">I Nostri Eventi</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" id="Concerti" href="#">Concerti</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" id="Spettacoli Teatrali" href="#">Spettacoli Teatrali</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" id="Eventi Sportivi" href="#">Eventi Sportivi</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link active" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Visite Guidate
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" id="Mostre" href="#">Mostre</a></li>
                        <li><a class="dropdown-item" id="Musei" href="#">Musei</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container text-center">
    <div class="row">
        <%
            EventModel eventModel = new EventModel();
            List<Event> mostClicked = eventModel.get3MostClickedEvent();
            for (Event event : mostClicked) {
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
        %>
    </div>
</div>
