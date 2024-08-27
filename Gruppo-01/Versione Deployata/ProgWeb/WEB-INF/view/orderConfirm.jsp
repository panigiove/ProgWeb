<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ page import="web.example.progweb.model.entity.Discount" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EntriEasy</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/icon.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <style>
        .centered-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .card {
            max-width: 600px;
            margin: auto;
        }
        .order-summary {
            font-size: 1.2rem;
        }
        .order-summary strong {
            display: block;
        }
    </style>
</head>
<body>

<jsp:include page="topNavUser.jsp"/>

<div class="centered-container">
    <div class="container">
        <div class="card">
            <div class="card-body">
                <h2 class="card-title text-center">Conferma dell'Ordine</h2>

                <%
                    Object discountObj = request.getAttribute("discount");
                    Discount discount = null;
                    if (discountObj != null) {
                        discount = (Discount) discountObj;
                    }
                    Event event = (Event) request.getAttribute("events");
                    int nSeats = (Integer) request.getAttribute("nSeats");
                    int nStands = (Integer) request.getAttribute("nStands");
                    String username = (String) request.getAttribute("username");
                    String price = (String) request.getAttribute("price");
                    int nOfferedTickets = (Integer) request.getAttribute("nOfferedTickets");
                    String priceNoFree = (String) request.getAttribute("priceNoFree");
                %>

                <div class="order-summary">
                    <h4><strong>Dettagli Evento</strong></h4>
                    <p><strong>Nome Evento:</strong> <%= event.getName() %></p>
                    <p><strong>Descrizione:</strong> <%= event.getDescrizione() %></p>
                    <p><strong>Data e Ora:</strong> <%= Event.formatData(event.getStart()) %> - <%= Event.formatData(event.getEnd()) %></p>

                    <h4 class="mt-4"><strong>Riepilogo Ordine</strong></h4>
                    <p><strong>Posti a Sedere:</strong> <%= nSeats %> x €<%= event.getSeatPrice() %></p>
                    <p><strong>Posti in Piedi:</strong> <%= nStands %> x €<%= event.getStandingPrice() %></p>
                    <% if (discount != null) { %>
                    <p><strong>Sconto Applicato:</strong> <%= discount.getDiscount() %>% - Scadenza: <%= discount.getFormattedExpirationDate() %></p>
                    <% } else { %>
                    <p><strong>Nessuno Sconto Applicato</strong></p>
                    <% } %>

                    <% if (nOfferedTickets > 0) { %>
                    <p><strong style="color: darkgreen">HAI IN OFFERTA <%= nOfferedTickets %> BIGLIETTI. PREZZO ORIGINALE: €<%= priceNoFree %> </strong></p>
                    <% } %>

                    <p><strong>Totale da Pagare:</strong> €<%= price %></p>
                </div>

                <div class="d-flex justify-content-between">
                    <form action="${pageContext.request.contextPath}/event/buyTicketConferm" method="POST" class="w-100">
                        <input type="hidden" name="username" value="<%= username %>">
                        <input type="hidden" name="event" value="<%= event.getId() %>">
                        <input type="hidden" name="nSeats" value="<%= nSeats %>">
                        <input type="hidden" name="nStands" value="<%= nStands %>">
                        <input type="hidden" name="idDiscount" value="<%= discount != null ? discount.getId_discount() : "" %>">
                        <button type="submit" class="btn btn-success w-100 mt-3">Conferma e Procedi al Pagamento</button>
                    </form>
                    <button class="btn btn-secondary w-100 mt-3 " onclick="history.back()">Annulla</button>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
