<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.AbstractMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>EntriEasy</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/icon.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <style>
        .pastel-green {
            background-color: #d4edda;
            padding: 15px;
            border-radius: 5px;
        }
        .notification {
            position: fixed;
            top: 10px;
            right: 10px;
            background-color: #28a745;
            color: #fff;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.2);
            z-index: 1050;
            opacity: 1;
            transition: opacity 0.5s ease-in-out;
        }

        .notification-progress {
            height: 5px;
            background-color: #007bff;
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            animation: progressBar 5s linear;
        }

        @keyframes progressBar {
            from { width: 100%; }
            to { width: 0%; }
        }
    </style>
</head>

<body>
<jsp:include page="topNavAdmin.jsp" />
<jsp:include page="returnTopPage.jsp"/>

<div class="container mt-4">
    <h1 class="mb-4">Gestione Eventi</h1>

    <div id="category-chart" class="mb-4" style="height: 400px;"></div>

    <div id="notification" class="notification" style="display: none;">
        <p>Evento Creato</p>
        <div class="notification-progress"></div>
    </div>

    <div class="pastel-green mb-0">
        <div class="row align-items-center">
            <div class="col-md-6">
                <form method="get" action="gestioneEventi" class="mb-0">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="sortByClicks" name="sortByClicks" <%= request.getParameter("sortByClicks") != null ? "checked" : "" %>
                               onchange="this.form.submit()" />
                        <label class="form-check-label" for="sortByClicks" style="user-select: none;">
                            Sort by Clicks
                        </label>
                    </div>
                </form>
            </div>
            <div class="col-md-6 text-end">
                <a href="${pageContext.request.contextPath}/admin/newEvent" class="btn btn-success">New Event</a>
            </div>
        </div>
    </div>

    <table class="table table-striped">
        <thead>
        <tr>
            <th class="text-center">ID</th>
            <th class="text-center">Name</th>
            <th class="text-center">Start</th>
            <th class="text-center">End</th>
            <th class="text-center">Total Seats</th>
            <th class="text-center">Available Seats</th>
            <th class="text-center">Total Standing</th>
            <th class="text-center">Available Standing</th>
            <th class="text-center">Seat Price</th>
            <th class="text-center">Standing Price</th>
            <th class="text-center">Clicks</th>
            <th class="text-center">Location</th>
            <th class="text-center">Category</th>
            <th class="text-center">Actions</th>
        </tr>
        </thead>

        <tbody>
        <%
            List<Event> events = (List<Event>) request.getAttribute("events");
            for(Event event : events) {
        %>
            <tr id="event-row-<%= event.getId()%>">
                <td class="text-center"><%= event.getId()%></td>
                <td class="text-center"><%= event.getName()%></td>
                <td class="text-center"><%= event.getStart()%></td>
                <td class="text-center"><%= event.getEnd()%></td>
                <td class="text-center"><%= event.getTotalSeats()%></td>
                <td class="text-center"><%= event.getAvailableSeats()%></td>
                <td class="text-center"><%= event.getTotalStanding()%></td>
                <td class="text-center"><%= event.getAvailableStanding()%></td>
                <td class="text-center"><%= event.getSeatPrice()%></td>
                <td class="text-center"><%= event.getStandingPrice()%></td>
                <td class="text-center"><%= event.getnClick()%></td>
                <td class="text-center"><%= event.getNomeLocation()%></td> <!-- New Column -->
                <td class="text-center"><%= event.getNomeCategory()%></td> <!-- New Column -->
                <td class="text-center">
                    <button type="button" class="btn btn-danger btn-sm" onclick="deleteEvent(${event.id})">Delete</button>
                </td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

<% if (Boolean.TRUE.equals(request.getAttribute("newEventCreated"))) { %>
<script>
    // Check if the notification should be shown
    document.addEventListener('DOMContentLoaded', function() {
        var notification = document.getElementById('notification');
        notification.style.display = 'block';
        setTimeout(function() {
            notification.style.opacity = 0;
            setTimeout(function() {
                notification.style.display = 'none';
            }, 500); // Match the fade-out time
        }, 5000); // Show for 5 seconds
    });
</script>
<%
    }
%>




<script>

    <%
    List<AbstractMap.SimpleEntry<String, Integer>> nclicks = (List<AbstractMap.SimpleEntry<String, Integer>>) request.getAttribute("nclicks");
    StringBuilder json = new StringBuilder("[");
    for (int i = 0; i < nclicks.size(); i++) {
        AbstractMap.SimpleEntry<String, Integer> entry = nclicks.get(i);
        json.append("{")
            .append("\"name\": \"").append(entry.getKey()).append("\", ")
            .append("\"y\": ").append(entry.getValue())
            .append("}");
        if (i < nclicks.size() - 1) {
            json.append(",");
        }
    }
    json.append("]");
%>
    const categoriesData = <%= json.toString() %>;

    Highcharts.chart('category-chart', {
        chart: {
            type: 'pie'
        },
        title: {
            text: 'Most Clicked Categories'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true
                }
            }
        },
        series: [{
            name: 'Clicks',
            data: categoriesData
        }]
    });

    function deleteEvent(eventId) {
        if (confirm('Conferma per procedere l\'eliminazione dell\'evento?')) {
            const xhr = new XMLHttpRequest();
            xhr.open('POST', 'deleteEvent', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        const response = JSON.parse(xhr.responseText);
                        if (response.success === 'success') {
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
