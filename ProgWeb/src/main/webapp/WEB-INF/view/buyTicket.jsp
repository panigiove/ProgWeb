<%@ page import="web.example.progweb.model.entity.Event" %>
<%@ page import="web.example.progweb.model.entity.Discount" %>
<%@ page import="java.util.List" %>
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

        .small-title {
            font-size: 1.25rem;
        }

        .description {
            font-size: 0.875rem;
            color: #555;
        }

        .discount-checkboxes {
            margin-top: 1rem;
        }

        .no-select {
            user-select: none; /* Disable text selection */
        }
    </style>
</head>

<body>

<%
    Event event = (Event) request.getAttribute("event");
    List<Discount> discounts = (List<Discount>) request.getAttribute("discounts");
%>

<jsp:include page="topNavUser.jsp"/>

<div class="centered-container">
    <div class="container text-center">
        <div class="card mb-4" style="max-width: 500px;">
            <div class="card-body text-center">
                <h2 class="card-title small-title"><%= event.getName() %></h2>
                <p class="description mb-3"><%= event.getDescrizione() %></p>

                <div class="mb-3 row align-items-center">
                    <label for="seat-count" class="col-form-label col-auto no-select"><strong>Posti in poltrona: €<%= event.getSeatPrice() %></strong></label>
                    <div class="col">
                        <div class="input-group custom-input-group">
                            <span class="availability-label">Disponibili:</span>
                            <input type="text" id="seat-ava-count" class="form-control custom-input" value="<%= event.getAvailableSeats() %>" readonly/>
                            <input type="number" id="seat-count" class="form-control custom-input" value="0" readonly>
                            <button class="btn btn-outline-secondary btn-sm custom-btn" type="button" onclick="updateQuantity('seat', -1)">-</button>
                            <button class="btn btn-outline-secondary btn-sm custom-btn" type="button" onclick="updateQuantity('seat', 1)">+</button>
                        </div>
                    </div>
                </div>
                <div class="mb-3 row align-items-center">
                    <label for="standing-count" class="col-form-label col-auto no-select"><strong>Posti in piedi: €<%= event.getStandingPrice() %></strong></label>
                    <div class="col">
                        <div class="input-group custom-input-group">
                            <span class="availability-label">Disponibili:</span>
                            <input type="text" id="standing-ava-count" class="form-control custom-input" value="<%= event.getAvailableStanding() %>" readonly/>
                            <input type="number" id="standing-count" class="form-control custom-input" value="0" readonly>
                            <button class="btn btn-outline-secondary btn-sm custom-btn" type="button" onclick="updateQuantity('standing', -1)">-</button>
                            <button class="btn btn-outline-secondary btn-sm custom-btn" type="button" onclick="updateQuantity('standing', 1)">+</button>
                        </div>
                    </div>
                </div>

                <div class="discount-checkboxes">
                    <% if (discounts == null || discounts.isEmpty()) { %>
                    <div class="alert alert-info no-copy" role="alert"> Non sono presenti sconti per questo evento </div>
                    <% } else { %>
                    <p><strong>Scegli uno sconto:</strong></p>
                    <% for (int i = 0; i < discounts.size(); i++) { %>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="discount" id="discount<%= i %>" value="<%= discounts.get(i).getDiscount() %>" data-id="<%= discounts.get(i).getId_discount() %>" <% if (discounts.size() == 1) { %> checked <% } %>>
                        <label class="form-check-label no-select" for="discount<%= i %>">
                            <strong><%= discounts.get(i).getDiscount() %>%</strong> - <%= discounts.get(i).getFormattedExpirationDate() %>
                        </label>
                    </div>
                    <% } %>
                    <% } %>
                </div>

                <p class="mb-5 text-center" style="font-size: 1.5rem;"><strong>Prezzo Totale:</strong> <span id="total-price">0.00</span> €</p>
            </div>
        </div>

        <form action="buyTicket" method="POST">
            <input type="hidden" name="username" value="<%= request.getSession(false).getAttribute("username")%>">
            <input type="hidden" name="event" value="<%= event.getId() %>">
            <input type="hidden" name="nSeats" id="hidden-seat-count" value="0">
            <input type="hidden" name="nStands" id="hidden-standing-count" value="0">
            <input type="hidden" name="idDiscount" id="hidden-discount" value="">
            <button type="submit" class="btn btn-success w-100 mt-3">Procedi con il pagamento</button>
        </form>

    </div>
</div>

<jsp:include page="footer.jsp"/>

<script>
    let totalPrice = 0;
    let discount = 0;
    let idDiscount = -1;
    let nSeats = 0;
    let nStands = 0
    let seatsPrice = <%= event.getSeatPrice() %>;
    let standPrice = <%= event.getStandingPrice() %>;
    let avaibleSeats = <%= event.getAvailableSeats() %>;
    let availableStanding = <%= event.getAvailableStanding() %>;

    function update() {
        const seatDisplay = document.getElementById('seat-count');
        const standsDisplay = document.getElementById('standing-count');
        const totalPriceDisplay = document.getElementById('total-price');

        const hiddenSeats = document.getElementById('hidden-seat-count');
        const hiddenStands = document.getElementById('hidden-standing-count');
        const hiddenDiscount = document.getElementById('hidden-discount')

        totalPrice = nStands * standPrice + nSeats * seatsPrice;
        if (totalPrice<=(discount / 100) * totalPrice){
            totalPrice = 0;
        }else{
            totalPrice -= (discount / 100) * totalPrice;
        }

        seatDisplay.value = nSeats;
        standsDisplay.value = nStands;
        totalPriceDisplay.innerHTML = totalPrice.toFixed(2);

        hiddenSeats.value = nSeats;
        hiddenStands.value = nStands;
        hiddenDiscount.value = idDiscount;
    }

    function updateQuantity(type, change) {
        if (type === 'seat'){
            nSeats += change;
            if (nSeats > avaibleSeats) nSeats = avaibleSeats;
            if (nSeats < 0) nSeats = 0;
        }else{
            nStands += change;
            if (nStands > availableStanding) nStands = availableStanding;
            if (nStands < 0) nStands = 0;
        }
        update();
    }

    document.querySelectorAll('input[name="discount"]').forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            let selectedDiscount = null;
            let selectedId = null;
            document.querySelectorAll('input[name="discount"]:checked').forEach(cb => {
                selectedDiscount = cb.value;
                selectedId = cb.getAttribute('data-id');
            });
            discount = selectedDiscount;
            idDiscount = selectedId;
            update();
        });
    });

    document.querySelector('form').addEventListener('submit', function(event) {
        if (nSeats === 0 && nStands === 0) {
            alert('Devi selezionare almeno un posto in poltrona o in piedi per procedere.');
            event.preventDefault();
        }
    })
</script>
</body>
</html>
