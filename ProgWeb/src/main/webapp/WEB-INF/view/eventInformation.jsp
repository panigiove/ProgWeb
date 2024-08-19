<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ page import="web.example.progweb.model.entity.Discount" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EntriEasy</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/icon.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <style>
        .btn-success {
            background-color: #28a745;
            border-color: #28a745;
            color: #fff;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            padding: 10px 20px;
            border-radius: 4px;
            font-size: 16px;
        }

        .btn-success:hover {
            background-color: #218838;
            border-color: #1e7e34;
        }

        .btn-disabled {
            background-color: #6c757d;
            border-color: #6c757d;
            color: #fff;
            cursor: not-allowed;
            pointer-events: none;
        }

        .alert-danger {
            color: #dc3545;
        }

        .event-image {
            max-width: 100%;
            height: auto;
            max-height: 400px;
            object-fit: cover;
            border-radius: 8px;
        }
    </style>
</head>

<body>
<jsp:include page="topNavUser.jsp"/>
<%
    Event event = (Event) request.getAttribute("event");
    List<Discount> discounts = (List<Discount>) request.getAttribute("discounts");
%>

<div class="container mt-5 d-flex flex-column align-items-center">
    <div class="row">
        <div class="col-md-12">
            <h1 class="display-4 text-center"><%= event.getName() %></h1>
        </div>
    </div>

    <div class="row mt-4 w-100">
        <div class="col-md-12 d-flex justify-content-center">
            <img src="${pageContext.request.contextPath}/images/<%= event.getImageName() %>" alt="Immagine dell'evento" class="event-image">
        </div>
    </div>

    <% if (event.getAvailableSeats() == 0 && event.getAvailableStanding() == 0) { %>
    <div class="alert alert-danger w-75 text-center mt-2">
        <strong>Attenzione:</strong> Tutti i posti in piedi e seduti sono esauriti.
    </div>
    <% } else if (event.getAvailableSeats() == 0) { %>
    <div class="alert alert-danger w-75 text-center">
        <strong>Attenzione:</strong> I posti a sedere sono esauriti.
    </div>
    <% } else if (event.getAvailableStanding() == 0) { %>
    <div class="alert alert-danger w-75 text-center">
        <strong>Attenzione:</strong> I posti in piedi sono esauriti.
    </div>
    <% } %>

    <div class="row mt-4 w-100">
        <div class="col-md-12 d-flex justify-content-center">
            <div class="card w-75">
                <div class="card-body text-right">
                    <h5 class="card-title">Informazioni Generali</h5>
                    <p class="card-text">
                        <strong>Data e Ora di Inizio:</strong> <%=event.getStart() != null ? event.getStart() : "Data e ora di inizio non disponibili"%><br>
                        <strong>Data e Ora di Fine:</strong> <%= event.getEnd() != null ? event.getEnd() : "Data e ora di fine non disponibili"%><br>
                        <strong>Luogo:</strong> <%=event.getNomeLocation() != null ? event.getNomeLocation() : "Posizione non disponibile"%>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-4 w-100">
        <div class="col-md-12 d-flex justify-content-center">
            <div class="card w-75">
                <div class="card-body text-right">
                    <h5 class="card-title">Cosa offre l'Evento</h5>
                    <p class="card-text">
                        <%=event.getDescrizione() != null ? event.getDescrizione() : "Descrizione non disponibile"%>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-4 w-100">
        <div class="col-md-12 d-flex justify-content-center">
            <div class="card w-75">
                <div class="card-body text-right">
                    <h5 class="card-title">Biglietti</h5>
                    <p class="card-text">
                        <strong>Prezzi in Poltrona:</strong> <%=event.getSeatPrice() != null ? event.getSeatPrice()+" €" : "Non ancora disponibili"%><br>
                        <strong>Prezzi in Piedi:</strong> <%=event.getStandingPrice() != null ? event.getStandingPrice()+ " €" : "Non ancora disponibili"%>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-4 w-100">
        <div class="col-md-12 d-flex justify-content-center">
            <div class="card w-75">
                <div class="card-body">
                    <h5 class="card-title">Sconti Correlati</h5>
                    <% if (discounts == null || discounts.isEmpty()) { %>
                    <div class="alert alert-info no-copy" role="alert"> Non sono presenti sconti per questo evento </div>
                    <% } else { %>
                    <% for (Discount discount : discounts) { %>
                    <div class="mb-2">
                        <p>
                            <strong><%= discount.getDiscount() %>%</strong> - <%= discount.getFormattedExpirationDate() %>
                        </p>
                    </div>
                    <% } %>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-4 w-100">
        <div class="col-md-12 d-flex justify-content-center">
            <% if (event.getAvailableSeats() == 0 && event.getAvailableStanding() == 0) { %>
            <a class="btn btn-disabled w-75 mt-3 text-center">Acquista</a>
            <% } else { %>
            <a href="${pageContext.request.contextPath}/event/buyTicketForm?eventId=<%= event.getId() %>" class="btn btn-success w-75 mt-3 text-center">Acquista</a>
            <% } %>
        </div>
    </div>
</div>
<jsp:include page="returnTopPage.jsp"/>
<jsp:include page="footer.jsp"/>
</body>
</html>
