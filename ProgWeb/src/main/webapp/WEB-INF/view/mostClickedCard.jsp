<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ page import="java.util.List" %>
<%@ page import="web.example.progweb.model.EventModel" %>
<%--
  Created by IntelliJ IDEA.
  User: Giovanni
  Date: 07/08/2024
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>

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

