<%--
  Created by IntelliJ IDEA.
  User: Giovanni
  Date: 08/08/2024
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
        .form-group input {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
        }
        .form-group input[type="submit"],
        .form-group input[type="reset"] {
            width: auto;
            background-color: #007BFF;
            color: white;
            border: none;
            cursor: pointer;
            padding: 10px 20px;
        }
        .form-group input[type="reset"] {
            background-color: #dc3545;
        }
        .form-group input[type="submit"]:hover,
        .form-group input[type="reset"]:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Sign Up</h2>
    <form id="signupForm">
        <div class="form-group">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" required>
        </div>
        <div class="form-group">
            <label for="cognome">Cognome:</label>
            <input type="text" id="cognome" name="cognome" required>
        </div>
        <div class="form-group">
            <label for="data_nascita">Data di Nascita (GG/MM/AAAA):</label>
            <input type="text" id="data_nascita" name="data_nascita" pattern="\d{2}/\d{2}/\d{4}" required>
        </div>
        <div class="form-group">
            <label for="email">Indirizzo Email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="telefono">Numero di Telefono (10 cifre):</label>
            <input type="text" id="telefono" name="telefono" pattern="\d{10}" required>
        </div>
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="confirm_password">Conferma Password:</label>
            <input type="password" id="confirm_password" name="confirm_password" required>
        </div>
        <div class="form-group">
            <button type="submit">Invia</button>
            <button type="reset" value="Reset">Reset</button>
        </div>
    </form>
</div>

<script>
    // JavaScript per inviare i dati tramite fetch
    document.getElementById('signupForm').addEventListener('submit', function(event) {
        event.preventDefault();

        let password = document.getElementById('password').value;
        let confirmPassword = document.getElementById('confirm_password').value;

        if (password !== confirmPassword) {
            alert("Le password non coincidono. Riprova.");
            return;
        }

        let formData = new FormData(this);

        fetch('http://localhost:8080/ProgWeb_war_exploded/SignupServlet', {
            method: 'POST',
            body: new URLSearchParams(formData)
        })
            .then(response => response.text())
            .then(data => {
                console.log('Risposta dal server:', data);
                window.location.href="goodLogin.jsp";
            })
            .catch(error => {
                console.error('Errore:', error);
            });
    });
</script>

</body>
</html>
