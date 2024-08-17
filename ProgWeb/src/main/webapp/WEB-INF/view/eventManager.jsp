<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Amministratore - Gestione Eventi</title>
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
                        <input class="form-check-input" type="checkbox" id="sortByClicks" name="sortByClicks" <c:if test="${param.sortByClicks != null}">checked</c:if>
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
            <th class="text-center">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="event" items="${events}">
            <tr id="event-row-${event.id}">
                <td class="text-center">${event.id}</td>
                <td class="text-center">${event.name}</td>
                <td class="text-center">${event.start}</td>
                <td class="text-center">${event.end}</td>
                <td class="text-center">${event.totalSeats}</td>
                <td class="text-center">${event.availableSeats}</td>
                <td class="text-center">${event.totalStanding}</td>
                <td class="text-center">${event.availableStanding}</td>
                <td class="text-center">${event.seatPrice}</td>
                <td class="text-center">${event.standingPrice}</td>
                <td class="text-center">${event.nClick}</td>
                <td class="text-center">
                    <button type="button" class="btn btn-danger btn-sm" onclick="deleteEvent(${event.id})">Delete</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    // Check if the notification should be shown
    <c:if test="${requestScope.newEventCreated == true}">
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
    </c:if>
</script>




<script>
    const categoriesData = [
        <c:forEach var="entry" items="${nclicks}" varStatus="status">
        {
            name: '${entry.key}',
            y: ${entry.value}
        }<c:if test="${!status.last}">,</c:if>
        </c:forEach>
    ];

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
