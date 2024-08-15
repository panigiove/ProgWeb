<%@ page import="web.example.progweb.model.entity.Location" %>
<%@ page import="java.util.List" %>
<%@ page import="web.example.progweb.model.entity.Category" %>
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

    <label for="dataFineEvento">Data Fine Evento:</label>
    <input type="datetime-local" id="dataFineEvento" name="dataFineEvento" required><br><br>

    <label for="categoriaEvento">Categoria:</label>
    <select id="categoriaEvento" name="categoriaEvento" required>
        <%
            List<Category> categories = (List<Category>) request.getAttribute("categories");
            for (Category category : categories) {
                out.print("<option value=\"" + category.getId() + "\">" + category.getName() + "</option>");
            }
        %>
    </select><br><br>

    <label for="localitaEvento">Località:</label>
    <select id="localitaEvento" name="localitaEvento" required>
        <%
            List<Location> locations = (List<Location>) request.getAttribute("locations");
            for (Location location : locations) {
                out.print("<option value=\"" + location.getId() + "\">" + location.getName() + "</option>");
            }
        %>
    </select><br><br>

    <label for="descrizioneEvento">Descrizione:</label>
    <textarea id="descrizioneEvento" name="descrizioneEvento" rows="4" cols="50" required></textarea><br><br>

    <label for="prezzoPostoSeduto">Prezzo Posto Seduto:</label>
    <input type="number" id="prezzoPostoSeduto" name="prezzoPostoSeduto" step="0.01" min="0" required><br><br>

    <label for="prezzoPostoInPiedi">Prezzo Posto in Piedi:</label>
    <input type="number" id="prezzoPostoInPiedi" name="prezzoPostoInPiedi" step="0.01" min="0" required><br><br>

    <label for="totalePostiSeduti">Totale Posti a Sedere:</label>
    <input type="number" id="totalePostiSeduti" name="totalePostiSeduti" min="0" required><br><br>

    <label for="totalePostiInPiedi">Totale Posti in Piedi:</label>
    <input type="number" id="totalePostiInPiedi" name="totalePostiInPiedi" min="0" required><br><br>

    <input type="submit" value="Inserisci Evento">
</form>
</body>
</html>
