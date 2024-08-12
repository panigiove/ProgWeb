<%--
  Created by IntelliJ IDEA.
  User: Giovanni
  Date: 12/08/2024
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Compra un biglietto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous">
    </script>
</head>

<body>
<div class ="container">
    <h1 class="mb-4">Compra biglietti</h1>
    <div class="card mb-4">
        <div class="card-body">
        <h2 class="card-title"> Evento: <%=request.getParameter("evento")%> </h2>
            <div class="mb-3">
                <label for="seat-count" class="form-label"><strong> Posti in poltrona </strong> </label>
                <div class="input-group>
                <button class="btn btn-outline-secondary" type="button" onclick="updateQuantity('seat', -1)">-</button>
                <input type="number" id="seat-count" class="form-control" value="<%= request.getParameter("numero_di_posti_poltrona") %>" readonly>
                <button class="btn btn-outline-secondary" type="button" onclick="updateQuantity('seat', 1)">+</button>
                </div>
            </div>
            <div class="mb-3">
                <label for="standing-count" class="form-label"><strong>Posti in piedi</strong></label>
                <div class="input-group">
                    <button class="btn btn-outline-secondary" type="button" onclick="updateQuantity('standing', -1)">-</button>
                    <input type="number" id="standing-count" class="form-control" value="<%= request.getParameter("numero_di_posti_piedi") %>" readonly>
                    <button class="btn btn-outline-secondary" type="button" onclick="updateQuantity('standing', 1)">+</button>
                </div>
            </div>
        <p class="mb-5 custom-font-size" style="font-size:1.5rem"><strong>Prezzo Totale</strong> </p>
        </div>
    </div>

    <form action="resumeOrder.jsp" method="POST">
        <input type="hidden" name="evento" value="<%= request.getParameter("evento") %>">
        <input type="hidden" name="numero_di_posti_poltrona" id="hidden-seat-count" value="<%= request.getParameter("numero_di_posti_poltrona") %>">
        <input type="hidden" name="numero_di_posti_piedi" id="hidden-standing-count" value="<%= request.getParameter("numero_di_piedi") %>">
        <input type="hidden" name="totale" value="<%= request.getParameter("totale") %>">
        <input type="hidden" name="totale" id="hidden-total-price" value="<%= request.getParameter("totale") %>">
        <button type="submit" class="btn btn-success btn-block mt-3">Acquista</button>
    </form>
</div>
<script>
    function updateQuantity(type, change) {
        var quantityInput = document.getElementById(type + '-count');
        var hiddenInput = document.getElementById('hidden-' + type + '-count');
        var currentValue = parseInt(quantityInput.value);
        var newValue = currentValue +change;

        if (newValue >= 0) {
            quantityInput.value = newValue;
            hiddenInput.value = newValue;
            updateTotalPrice();
        }
    }

    function updateTotalPrice() {
        var  seatCount = parseInt(document.getElementById('seat-count').value);
        var standingCount = parseInt(document.getElementById('standing-count').value);
        var seatPrice = 10;
        var standingPrice = 5; //da togliere poi
    }

</script>
</body>
</html>
