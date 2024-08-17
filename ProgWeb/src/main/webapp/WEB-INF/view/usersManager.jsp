<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Amministratore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <style>
        .pastel-green {
            background-color: #d4edda;
            padding: 15px;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <h1 class="mb-4">visualizzazione Utenti</h1>

    <div class="pastel-green mb-4">
        <div class="row align-items-center">
            <div class="col-md-6">
                <form method="get" action="gestioneUtenti" class="mb-0">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="sortByPurchases" name="sortByPurchases" <%= request.getParameter("sortByPurchases") != null ? "checked" : "" %>
                               onchange="this.form.submit()" />
                        <label class="form-check-label" for="sortByPurchases" style="user-select: none;">
                            Sort by Purchases
                        </label>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <table class="table table-striped">
        <thead>
        <tr>
            <th class="text-center">ID</th>
            <th class="text-center">Name</th>
            <th class="text-center">Surname</th>
            <th class="text-center">Date of Birth</th>
            <th class="text-center">Email</th>
            <th class="text-center">Phone Number</th>
            <th class="text-center">N° Purchases</th>
            <th class="text-center">Username</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td class="text-center">${user.id}</td>
                <td class="text-center">${user.name}</td>
                <td class="text-center">${user.surname}</td>
                <td class="text-center">${user.birthDate}</td>
                <td class="text-center">${user.email}</td>
                <td class="text-center">${user.phone}</td>
                <td class="text-center">${user.nPurchases}</td>
                <td class="text-center">${user.username}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>
