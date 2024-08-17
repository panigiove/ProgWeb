<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Informazioni sull'Evento</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>

<body>

<%
    Event event = (Event) request.getAttribute("event");
%>

<div class="container mt-5 d-flex flex-column align-items-center">
    <div class="row">
        <div class="col-md-12">
            <h1 class="display-4 text-center"><%=event.getName()%></h1>
        </div>
    </div>

    <div class="row mt-4 w-100">
        <div class="col-md-12 d-flex justify-content-center">
            <div class="card w-75">
                <div class="card-body text-right">
                    <h5 class="card-title">Informazioni Generali</h5>
                    <p class="card-text">
                        <strong>Data:</strong> <%=event.getStart() != null ? event.getStart() : "Data non disponibile"%><br>
                        <strong>Orario di Inizio:</strong> <%=request.getParameter("ora_iniziale") != null ? request.getParameter("ora_iniziale") : "Ora di inizio non disponibile"%><br>
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
                        <strong>Prezzi in Poltrona:</strong> <%=event.getSeatPrice() != null ? event.getSeatPrice() : "Non ancora disponibili"%><br>
                        <strong>Prezzi in Piedi:</strong> <%=event.getStandingPrice() != null ? event.getStandingPrice() : "Non ancora disponibili"%>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-4 w-100">
        <div class="col-md-12 d-flex justify-content-center">
            <form action="buyTicket.jsp" method="POST" class="w-75">
                <input type="hidden" name="evento" value="<%= request.getParameter("evento") %>">
                <input type="hidden" name="numero_di_posti_poltrona" id="hidden-seat-count" value="<%=request.getParameter("prezzo_poltrona") %>">
                <input type="hidden" name="numero_di_posti_piedi" id="hidden-standing-count" value="<%=request.getParameter("prezzo_in_piedi") %>">
                <button type="submit" class="btn btn-success w-100 mt-3">Acquista</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
