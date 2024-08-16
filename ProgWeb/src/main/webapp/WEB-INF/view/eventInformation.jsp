<%--
  Created by IntelliJ IDEA.
  User: Giovanni
  Date: 16/08/2024
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Informazioni sull'Evento </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>

<div class="container mt-5">
    <div class="row">
        <div class="col-md-12 text-center">
            <h1 class="display-4"> <%=request.getParameter("evento")%> </h1>
        </div>
    </div>

    <div class="row mt-4">
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title"> informazioni generali </h5>
                    <p class="card-text">
                        <strong> Data: </strong> <%=request.getParameter("data_evento") != null ? request.getParameter("data-evento") : "Data non disponibile"%>
                        <strong> Orario di inizio: </strong> <%=request.getParameter("ora_iniziale") != null ? request.getParameter("ora_iniziale") : "Ora di inizio non disponibile"%>
                        <strong> Luogo: </strong> <%=request.getParameter("luogo_evento") != null ? request.getParameter("luogo_evento") : "Posizione non disponibile"%>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-4">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title"> Cosa offre l'Evento </h5>
                    <p class="card-text">
                        <%=request.getParameter("descrizione_evento") != null ? request.getParameter("descrizione_evento") : "descrizione non disponibile"%>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <div class="rex mt-4">
        <div class="col-md-12">
            <div class="card-body">
                <h5 class="card-title"> Biglietti</h5>
                <p class="card-text">
                    <strong> Prezzi in poltrona </strong> <%=request.getParameter("prezzo_poltrona") != null? request.getParameter("prezzo_poltrona") : "non ancora disponibili"%>
                    <strong> Prezzi in piedi </strong> <%=request.getParameter("prezzo_in_piedi") != null? request.getParameter("prezzo_in_piedi") : "non ancora disponibili"%>
                </p>
            </div>
        </div>
    </div>

    <form action="buyTicket" method="POST">
        <input type="hidden" name="evento" value="<%= request.getParameter("evento") %>">
        <input type="hidden" name="numero_di_posti_poltrona" id="hidden-seat-count" value="<%= request.getParameter("prezzo_poltrona") %>">
        <input type="hidden" name="numero_di_posti_piedi" id="hidden-standing-count" value="<%= request.getParameter("prezzo_in_piedi) %>">
        <button type="submit" class="btn btn-success btn-block mt-3">Acquista</button>
    </form>
</div>

</body>
</html>
