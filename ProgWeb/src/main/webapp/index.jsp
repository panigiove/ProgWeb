<%@ page import="web.example.progweb.model.entity.Discount" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <title>EntriEasy - HOME</title>
    <style>
        /* Classe per l'animazione di scorrimento */
        .slide-in {
            position: relative;
            animation: slideIn 0.5s ease-out forwards;
        }

        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateX(100%);
            }
            to {
                opacity: 1;
                transform: translateX(0);
            }
        }
    </style>

    <script>
        function update() {
            var discounts = <%= new Gson().toJson((List<Discount>) request.getAttribute("discounts")) %>;

            var randomIndex = Math.floor(Math.random() * discounts.length);
            var discount = discounts[randomIndex];
            var titleElement = document.getElementById("discountTitle");s
            var descriptionElement = document.getElementById("discountDescription");

            titleElement.classList.remove("slide-in");
            descriptionElement.classList.remove("slide-in");

            void titleElement.offsetWidth;
            void descriptionElement.offsetWidth;

            titleElement.innerText = discount.title;
            descriptionElement.innerText = discount.description;

            titleElement.classList.add("slide-in");
            descriptionElement.classList.add("slide-in");
        }

        setInterval(update, 15000);

        window.onload = update;
    </script>
</head>
<body>

<jsp:include page="/WEB-INF/view/topNavUser.jsp" />

<div class="container text-center mt-5">
    <div class="card mx-auto" style="width: 50%;">
        <div class="card-body">
            <h5 id="discountTitle" class="card-title"></h5>
            <p id="discountDescription" class="card-text"></p>
        </div>
    </div>
</div>

<br/>
<jsp:include page="/WEB-INF/view/mostClickedCard.jsp"/><br>
<jsp:include page="/WEB-INF/view/eventsNavbar.jsp"/><br>
<jsp:include page="/WEB-INF/view/footer.jsp"/>

<div style="height: 150px"></div>
</body>
</html>
