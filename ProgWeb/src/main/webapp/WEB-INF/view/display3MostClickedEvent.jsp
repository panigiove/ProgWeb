<%@ page import="java.util.List" %>
<%@ page import="web.example.progweb.model.entity.Event" %>

<div class="container">
    <div class="row">
        <%
            // Recupera la lista degli eventi più cliccati passata come attributo della richiesta
            List<Event> mostClicked = (List<Event>) request.getAttribute("3mostClieckedEvent");
            for (Event event : mostClicked) {
        %>
        <div class="col-lg-4 col-md-6 mb-4"> <!-- Adjust column sizing and margin-bottom -->
            <div class="card" style="width: 100%;">
                <div class="card-body">
                    <h5 class="card-title"><%= event.getName() %></h5>
                    <p class="card-text"><%= event.getDescrizione() %></p>
                    <p><strong>Location:</strong> <%= event.getNomeLocation() %></p>
                    <p><strong>Start:</strong> <%= Event.formatData(event.getStart()) %></p>
                    <p><strong>End:</strong> <%= Event.formatData(event.getEnd()) %></p>
                    <p><strong>Available Tickets:</strong> <%= (event.getAvailableSeats() + event.getAvailableStanding()) == 0 ? "<span class='text-danger'>Esaurito</span>" : event.getAvailableSeats() + event.getAvailableStanding() %></p>

                    <a href="eventInformation.jsp?id=<%= event.getId() %>" class="btn btn-primary">Details</a>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>
