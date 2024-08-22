<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EntriEasy</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/icon.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <title>Amministratore</title>
</head>
<body>
<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-md-6 text-center">
            <h1>Pannello Amministratore</h1>
            <div class="list-group mt-4">
                <a href="${pageContext.request.contextPath}/admin/gestioneUtenti" class="list-group-item list-group-item-action">Visualizzazione Utenti</a>
                <a href="${pageContext.request.contextPath}/admin/gestioneEventi" class="list-group-item list-group-item-action">Gestione Eventi</a>
                <a href="${pageContext.request.contextPath}/logout" class="list-group-item list-group-item-action text-danger">Logout</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
