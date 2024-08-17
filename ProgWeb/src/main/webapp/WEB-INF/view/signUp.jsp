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
    <form id="signupForm" action="${pageContext.request.contextPath}/signup" method="post">
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
            <input type="text" id="data_nascita" name="data_nascita" required>
        </div>
        <div class="form-group">
            <label for="email">Indirizzo Email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="telefono">Numero di Telefono (10 cifre):</label>
            <input type="text" id="telefono" name="telefono" required>
        </div>
        <div class="form-group">
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
            %>
            <div style="color:red">
                <strong><%= errorMessage %></strong>
            </div>
            <%
                }
            %>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password (almeno 9 caratteri, di cui uno speciale e due cifre):</label>
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
    document.getElementById('signupForm').addEventListener('submit', function(event) {
        const phoneInput = document.getElementById('telefono').value;
        const emailInput = document.getElementById('email').value;
        const passwordInput = document.getElementById('password').value;
        const dateOfBirth = document.getElementById('data_nascita').value;

        // Validazione del numero di telefono
        const phoneRegex = /^\d{10}$/;
        if (!phoneRegex.test(phoneInput)) {
            alert("Il numero di telefono deve contenere esattamente 10 cifre.");
            event.preventDefault();
            return;
        }

        // Validazione dell'email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(emailInput)) {
            alert("Inserisci un indirizzo email valido.");
            event.preventDefault();
            return;
        }

        // Validazione della password
        const passwordRegex = /^(?=.*[!@#$%^&*])(?=.*\d{2,}).{9,}$/;
        if (!passwordRegex.test(passwordInput)) {
            alert("La password deve avere almeno 9 caratteri, inclusi almeno un carattere speciale e due cifre.");
            event.preventDefault();
            return;
        }

        // Validazione della data di nascita
        if (!validateDate(dateOfBirth)) {
            event.preventDefault();
            return;
        }
    });

    function validateDate(dateOfBirth){
        const dob = /^(\d{2})\/(\d{2})\/(\d{4})$/;
        const match = dateOfBirth.match(dob);

        if (!match) {
            alert("Il formato di data di nascita non è valido, usare: GG/MM/AAAA");
            return false;
        }

        const day = parseInt(match[1], 10);
        const month = parseInt(match[2], 10) - 1;
        const year = parseInt(match[3], 10);

        const birthDate = new Date(year, month, day);

        if (birthDate.getFullYear() !== year || birthDate.getMonth() !== month || birthDate.getDate() !== day) {
            alert("La data di nascita non è valida, assicurati di inserire giorno, mese e anno giusti!");
            return false;
        }

        const today = new Date();

        if (birthDate > today){
            alert("La data non è valida, è futura!");
            return false;
        }

        let age = today.getFullYear() - year;
        const m = today.getMonth() - month;

        if (m < 0 || (m === 0 && today.getDate() < day)) {
            age--;
        }

        if (age < 18) {
            alert("Non puoi iscriverti al sito, non sei maggiorenne!");
            return false;
        }

        return true;
    }
</script>

</body>
</html>
