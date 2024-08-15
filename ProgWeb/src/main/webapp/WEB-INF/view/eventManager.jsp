<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
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
    <form method="get" action="gestioneEventi" class="mb-4">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" id="sortByClicks" name="sortByClicks" <c:if test="${param.sortByClicks != null}">checked</c:if> />
            <label class="form-check-label" for="sortByClicks">
                Sort by Clicks
            </label>
        </div>
        <button type="submit" class="btn btn-primary mt-2">Apply</button>
    </form>

    <a href="${pageContext.request.contextPath}/admin/newEvent">New Event</a>

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
        <c:forEach var="event" items="${events}">
            <tr id="event-row-${event.id}">
                <td>${event.id}</td>
                <td>${event.name}</td>
                <td>${event.start}</td>
                <td>${event.end}</td>
                <td>${event.totalSeats}</td>
                <td>${event.availableSeats}</td>
                <td>${event.totalStanding}</td>
                <td>${event.availableStanding}</td>
                <td>${event.seatPrice}</td>
                <td>${event.standingPrice}</td>
                <td>${event.nClick}</td>
                <td>
                    <!-- Button for deleting events -->
                    <button type="button" class="btn btn-danger btn-sm" onclick="deleteEvent(${event.id})">Delete</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    function deleteEvent(eventId) {
        if (confirm('Are you sure you want to delete this event?')) {
            const xhr = new XMLHttpRequest();
            xhr.open('POST', 'deleteEvent', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        const response = JSON.parse(xhr.responseText);
                        if (response.success === 'success') {
                            // Remove the row from the table
                            const row = document.getElementById('event-row-' + eventId);
                            row.parentNode.removeChild(row);
                        } else {
                            alert('Failed to delete event.');
                        }
                    } else {
                        alert('Error: ' + xhr.status);
                    }
                }
            };
            xhr.send('eventId=' + encodeURIComponent(eventId));
        }
    }
</script>

</body>
</html>
