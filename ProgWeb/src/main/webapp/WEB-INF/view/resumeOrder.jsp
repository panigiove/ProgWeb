<%--
  Created by IntelliJ IDEA.
  User: Giovanni
  Date: 11/08/2024
  Time: 23:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>resume Order</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<div class ="container mt-5">
    <h2 class ="text-center mb-4"> Riepilogo Ordine</h2>

    <div class="card mx-auto" style="width: 50%;">
        <div class="card-body text-center">
            <h5 class="card-title mb-3">Evento: <%=request.getParameter("evento")%></h5>
            <p class="custom-margin-top m-b2"><strong>Numero di posti in poltrona </strong> <%=request.getParameter("numero_di_posti_poltrona")%></p>
            <p class ="m-b2"><strong>Numero di posti in piedi </strong> <%=request.getParameter("numero_di_posti_piedi")%> </p>
            <p class="m-b5 custom-font-size" style="font-size: 1.5rem;"><strong>Prezzo totale</strong> <%=request.getParameter("totale")%> €</p>

            <% String messaggioSconto = "Nessuno sconto applicato.";
                String scontoParam = request.getParameter("sconto");
                double sconto = 0.0;

                if (scontoParam != null && !scontoParam.isEmpty()) {
                    try {
                        sconto = Double.parseDouble(scontoParam);
                        if (sconto > 0) {
                            messaggioSconto = "Hai ottenuto uno sconto di " + sconto + " €";
                        }
                    } catch (NumberFormatException e) {
                        messaggioSconto = "Errore nel calcolo dello sconto.";
                    }
                }
            %>

            <p class="card-text text-success mb-2"><%= messaggioSconto %></p>

            <form action="orderConfirm.jsp" method="POST">
                <input type="hidden" name="evento" value="<%= request.getParameter("evento") %>">
                <input type="hidden" name="numero_di_posti_poltrona" value="<%= request.getParameter("numero_di_posti_poltrona") %>">
                <input type="hidden" name="numero_di_posti_piedi" value="<%= request.getParameter("numero_di_posti_piedi") %>">
                <input type="hidden" name="totale" value="<%= request.getParameter("totale") %>">
                <input type="hidden" name="sconto" value="<%= request.getParameter("sconto") %>">
                <button type="submit" class="btn btn-success btn-block mt-3">Conferma Acquisto</button>
            </form>

        </div>
    </div>
</div>

</body>
</html>
