<%@ page import="web.example.progweb.model.entity.Ticket" %>
<%@ page import="java.util.List" %>
<%@ page import="web.example.progweb.model.entity.User" %>
<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ page import="web.example.progweb.model.EventModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EntriEasy</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/icon.svg">
    <style>
        /* Stili di base */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
            padding: 20px;
            margin: 20px;
        }

        h1, h2 {
            color: #333;
            text-align: center;
        }

        h1 {
            font-size: 2rem;
            margin-bottom: 10px;
        }

        h2 {
            font-size: 1.5rem;
            margin-bottom: 20px;
        }

        p {
            color: #555;
            line-height: 1.6;
        }

        .data-section {
            margin-bottom: 20px;
        }

        .data-section ul {
            list-style-type: none;
            padding: 0;
        }

        .data-section ul li {
            background-color: #f9f9f9;
            margin-bottom: 10px;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ddd;
            justify-content: space-between;
        }

        .data-section ul li span {
            font-weight: bold;
            color: #333;
        }

        /* Stile per il pulsante */
        .delete-button {
            background-color: #e74c3c;
            color: #fff;
            border: none;
            padding: 10px 20px;
            text-align: center;
            display: block;
            width: 100%;
            font-size: 1.2rem;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .delete-button:hover {
            background-color: #c0392b;
        }
    </style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="topNavUser.jsp"/>
<div class="container">
    <h1>Profilo Utente</h1>

    <%
        User currentUser = (User) request.getAttribute("currentUser");
        List<Ticket> userTickets = (List<Ticket>) request.getAttribute("userTickets");
        EventModel eventModel = (EventModel) request.getAttribute("eventModel");
    %>

    <!-- Sezione Dati Personali -->
    <div class="data-section">
        <h2>I tuoi dati</h2>
        <p><strong>Nome:</strong> <%= currentUser.getName() %> </p>
        <p><strong>Cognome:</strong> <%= currentUser.getName() %> </p>
        <p><strong>Data di nascita:</strong> <%= currentUser.getBirthDate() %> </p>
        <p><strong>Email:</strong> <%= currentUser.getEmail() %></p>
        <p><strong>Telefono:</strong> <%= currentUser.getPhone() %> </p>
        <p><strong>Numero di acquisti:</strong> <%= currentUser.getnPurchases() %> </p>
        <p><strong>Username:</strong> <%= currentUser.getUsername() %> </p>
    </div>
    <!-- Link di cancellazione iscrizione -->
    <form action="${pageContext.request.contextPath}/personalArea/deleteAccount" method="post">
        <button class="delete-button" type="submit">Cancella Iscrizione</button>
    </form>


    <!-- Sezione Acquisti -->
    <div class="data-section">
        <h2>Acquisti Effettuati</h2>
        <ul>
            <%
                // Controlla se ci sono acquisti
                if (userTickets.isEmpty()) {
            %>
            <p>Non sono stati effettuati acquisti.</p>
            <%
            } else {
                // Se ci sono acquisti, mostra i dettagli
                for (Ticket ticket : userTickets) {
                    Event event = eventModel.getEventById(ticket.idEvento());
            %>
            <li>
                <h3><%= event.getName() %></h3>
                <p><strong>Descrizione:</strong> <%= event.getDescrizione() %></p>
                <p><strong>Data di Inizio:</strong> <%= event.getStart() %></p>
                <p><strong>Data di Fine:</strong> <%= event.getEnd() %></p>
                <p><strong>Data di Acquisto:</strong> <%= ticket.data_acquisto() %></p>
                <p><strong>Posti in Poltrona acquistati:</strong> <%= ticket.quantita_poltrona() %></p>
                <p><strong>Posti in Piedi acquistati:</strong> <%= ticket.quantita_in_piedi() %></p>
                <p><strong>Prezzo Totale:</strong> <%= ticket.getPrice() %></p>
            </li>
            <%
                    }
                }
            %>
        </ul>
    </div>
</div>
<jsp:include page="returnTopPage.jsp"/>
<jsp:include page="footer.jsp"/>
</body>
</html>
