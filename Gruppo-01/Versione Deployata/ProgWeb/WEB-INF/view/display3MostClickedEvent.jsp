<%@ page import="java.util.List" %>
<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div class="row">
        <%
            List<Event> mostClicked = (List<Event>) request.getAttribute("3mostClieckedEvent");
            for (Event event : mostClicked) {
        %>
        <div class="col-lg-4 col-md-6 mb-4">
            <div class="card" style="width: 100%;">
                <div class="card-body">
                    <h5 class="card-title"><%= event.getName() %></h5>
                    <p class="card-text"><%= event.getDescrizione() %></p>
                    <p><strong>Luogo:</strong> <%= event.getNomeLocation() %></p>
                    <p><strong>Inizio:</strong> <%= Event.formatData(event.getStart()) %></p>
                    <p><strong>Fine:</strong> <%= Event.formatData(event.getEnd()) %></p>
                    <p><strong>Biglietti Disponibili:</strong> <%= (event.getAvailableSeats() + event.getAvailableStanding()) == 0 ? "<span class='text-danger'>Esaurito</span>" : event.getAvailableSeats() + event.getAvailableStanding() %></p>

                    <a href="${pageContext.request.contextPath}/event?eventId=<%= event.getId()%>" class="btn btn-primary">Dettagli</a>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>
