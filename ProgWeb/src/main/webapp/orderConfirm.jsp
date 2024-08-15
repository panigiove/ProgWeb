<%--
  Created by IntelliJ IDEA.
  User: Giovanni
  Date: 12/08/2024
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Ordine Confermato </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"> </script>
    <script>

        var conto_alla_rovescia = 5;

        function inizioCountdown () {
            var countdownElement = document.getElementById('conto_alla_rovescia');

            var interval = setInverval( function() {
                countdownElement.textContent = conto_alla_rovescia;
                conto_alla_rovescia --;

                if (conto_alla_rovescia < 0) {
                    clearInterval(interval);
                    window.location.href ="index.jsp";
                }
            }, 1000);
        }

        window.onload = inizioCountdown;
    </script>
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Ordine Confermato!</h2>
    <p class="text-center" > Grazie di averci scelti</p>
    <p class="text-center" mb-2> Ti auguriamo un buon evento </p>
    <p class="text-center" mb20> Ritornei nella pagina principale tra <</p>
</div>

</body>
</html>
