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
            <button type="button" onclick="sendForm()">Invia</button>
            <input type="reset" value="Reset">
        </div>
    </form>
</div>

<script>
    function sendForm() {

    }
</script>

<!--<script>
    const form = document.getElementById('signupForm');
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirm_password');

    form.addEventListener('submit', function(event) {
        // Validazione della password
        const groupID = '123'; // Sostituisci con l'ID del tuo gruppo
        const requiredPassword = 'utente!' + groupID;
        const passwordValue = password.value;

        if (passwordValue !== confirmPassword.value) {
            alert('Le password non coincidono');
            event.preventDefault();
            return;
        }

        const passwordRegex = new RegExp(`^utente!${groupID}$`);

        if (!passwordRegex.test(passwordValue) || passwordValue.length < 9) {
            alert('La password deve essere lunga 9 caratteri, contenere "utente!" e l\'ID del gruppo, uno speciale e due cifre.');
            event.preventDefault();
            return;
        }
    });
</script>-->

</body>
</html>
