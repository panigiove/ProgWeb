<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Amministratore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <style>
        .container {
            margin-top: 20px;
        }
        .table th, .table td {
            text-align: center;
        }
        .btn-custom {
            margin: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="mb-4">Event Management</h1>

    <!-- Form for sorting events -->
    <form method="get" action="admin" class="mb-4">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" id="sortByClicks" name="sortByClicks" <%= request.getParameter("sortByClicks") != null ? "checked" : "" %> />
            <label class="form-check-label" for="sortByClicks">
                Sort by Clicks
            </label>
        </div>
        <button type="submit" class="btn btn-primary mt-2">Apply</button>
    </form>

    <!-- Button to add new event -->
    <a href="newEvent.jsp" class="btn btn-success mb-4">New Event</a>

    <!-- Display events in a table -->
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Start</th>
            <th>End</th>
            <th>Total Seats</th>
            <th>Available Seats</th>
            <th>Total Standing</th>
            <th>Available Standing</th>
            <th>Seat Price</th>
            <th>Standing Price</th>
            <th>Clicks</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Event> events = (List<Event>) request.getAttribute("events");
            for (Event event : events) {
        %>
        <tr>
            <td><%= event.getId() %></td>
            <td><%= event.getName() %></td>
            <td><%= event.getStart() %></td>
            <td><%= event.getEnd() %></td>
            <td><%= event.getTotalSeats() %></td>
            <td><%= event.getAvailableSeats() %></td>
            <td><%= event.getTotalStanding() %></td>
            <td><%= event.getAvailableStanding() %></td>
            <td><%= event.getSeatPrice() %></td>
            <td><%= event.getStandingPrice() %></td>
            <td><%= event.getnClick() %></td>
            <td>
                <!-- VA RIFATTO CON UNO SCRIPT -->
                <form method="post" action="deleteEvent" class="d-inline">
                    <input type="hidden" name="eventId" value="<%= event.getId() %>" />
                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
