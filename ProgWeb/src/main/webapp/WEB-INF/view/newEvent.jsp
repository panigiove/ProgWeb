<%--
  Created by IntelliJ IDEA.
  User: Giovanni
  Date: 14/08/2024
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Inserisci Nuovo Evento</title>
</head>
<body>
<h2>Inserisci Nuovo Evento</h2>
<form action="inserisciEvento" method="post">
    <label for="nomeEvento">Nome Evento:</label>
    <input type="text" id="nomeEvento" name="nomeEvento" required><br><br>

    <label for="dataInizioEvento">Data Inizio Evento:</label>
    <input type="datetime-local" id="dataInizioEvento" name="dataInizioEvento" required><br><br>

    <label for="dataFineEvento">Data Inizio Evento:</label>
    <input type="datetime-local" id="dataFineEvento" name="dataFineEvento" required><br><br>

    <!--<label for="dataFineEvento">Data Inizio Evento:</label>
    <input type="datetime-local" id="dataFineEvento" name="dataFineEvento" required><br><br>-->

    <label for="categoriaEvento">Categoria:</label>
    <select id="categoriaEvento" name="categoriaEvento" required>
        <option value="Musica">Musica</option>
        <option value="Teatro">Teatro</option>
        <option value="Sport">Sport</option>
        <option value="Altro">Altro</option>
    </select><br><br>



    <label for="descrizioneEvento">Descrizione:</label>
    <textarea id="descrizioneEvento" name="descrizioneEvento" rows="4" cols="50" required></textarea><br><br>

    <input type="submit" value="Inserisci Evento">
</form>
</body>
</html>
