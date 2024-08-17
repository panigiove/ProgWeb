<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ page import="web.example.progweb.model.entity.Discount" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Compra un biglietto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <style>

        .centered-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .custom-input-group .custom-btn {
            width: 40px;
            height: 40px;
            font-weight: bold;
            background-color: #2E8B57;
            color: black;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 0;
            line-height: 1;
            border: 1px solid #333533;
        }

        .custom-input-group .custom-btn:hover {
            background-color: #A1C7A6;
        }

        .custom-input-group .custom-input {
            max-width: 60px;
            margin: 0px;
            text-align: center;
            height: 40px;
            text-font: 0.5rem;
            color: black;
            border: none;
            background-color: transparent;
            border-radius: 0;
        }

        .input-group.custom-input-group {
            display: flex;
            justify-content: right;
            align-items: normal;
        }

        .card {
            margin: auto;
        }
    </style>
</head>

<body>

<%
    Event event = (Event) request.getAttribute("event");
    List<Discount> discounts = (List<Discount>) request.getAttribute("discounts");
%>

<div class="centered-container">
    <div class="container text-center">
        <h1 class="mb-4 text-center">Compra biglietti</h1>
        <div class="card mb-4" style="max-width: 500px;">
            <div class="card-body text-center">
                <h2 class="card-title text-center">Evento: <%= event.getName() %></h2>
                <div class="mb-3 row align-items-center">
                    <label for="seat-count" class="col-form-label col-auto"><strong>Posti in poltrona</strong></label>
                    <div class ="col">
                        <div class="input-group custom-input-group">
                            <input type="number" id="seat-count" class="form-control custom-input" value="0" readonly>
                            <button class="btn btn-outline-secondary btn-sm custom-btn" type="button" onclick="updateQuantity('seat', -1)">-</button>
                            <button class="btn btn-outline-secondary btn-sm custom-btn" type="button" onclick="updateQuantity('seat', 1)">+</button>
                        </div>
                    </div>
                </div>
                <div class="mb-3 row align-items-center">
                    <label for="standing-count" class="col-form-label col-auto"><strong>Posti in piedi</strong></label>
                    <div class="col">
                        <div class="input-group custom-input-group">
                            <input type="number" id="standing-count" class="form-control custom-input" value="0" readonly>
                            <button class="btn btn-outline-secondary btn-sm custom-btn" type="button" onclick="updateQuantity('standing', -1)">-</button>
                            <button class="btn btn-outline-secondary btn-sm custom-btn" type="button" onclick="updateQuantity('standing', 1)">+</button>
                        </div>
                    </div>
                </div>

                <p class="mb-5 text-center" style="font-size: 1.5rem;"><strong>Prezzo Totale:</strong> <span id="total-price">0.00</span> €</p>
            </div>
        </div>

        <form action="resumeOrder.jsp" method="POST">
            <input type="hidden" name="event" value="<%= event %>">
            <input type="hidden" name="numero_di_posti_poltrona" id="hidden-seat-count" value="0">
            <input type="hidden" name="numero_di_posti_piedi" id="hidden-standing-count" value="0">
            <input type="hidden" name="totale" id="hidden-total-price" value="0.00">
            <button type="submit" class="btn btn-success w-100 mt-3">Procedi con il pagamento</button>
        </form>

    </div>
</div>

<script>
    function updateTotalPrice() {
        var seatCount = parseInt(document.getElementById('seat-count').value) || 0;
        var standingCount = parseInt(document.getElementById('standing-count').value) || 0;

        var seatPrice = 10;
        var standingPrice = 5;

        var totalPrice = (seatCount * seatPrice) + (standingCount * standingPrice);
        document.getElementById('total-price').innerText = totalPrice.toFixed(2);
        document.getElementById('hidden-total-price').value = totalPrice.toFixed(2);
    }

    function updateQuantity(type, change) {
        var quantityInput = document.getElementById(type + '-count');
        var hiddenInput = document.getElementById('hidden-' + type + '-count');
        var currentValue = parseInt(quantityInput.value) || 0;
        var newValue = currentValue + change;

        if (newValue >= 0) {
            quantityInput.value = newValue;
            hiddenInput.value = newValue;
            updateTotalPrice();
        }
    }

    updateTotalPrice();
</script>
</body>
</html>
