<%@ page import="web.example.progweb.model.entity.Location" %>
<%@ page import="java.util.List" %>
<%@ page import="web.example.progweb.model.entity.Category" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>EntriEasy</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/icon.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <style>
        .form-container {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        .error-label {
            color: red;
        }

        .btn-genera {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="d-flex justify-content-center">
        <div class="form-container col-md-8 col-lg-6">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>Inserisci Nuovo Evento</h2>
                <button type="button" class="btn btn-secondary btn-genera" onclick="generaDati()">Genera</button>
            </div>
            <form id="eventoForm" action="inserisciEvento" method="post" enctype="multipart/form-data">
                <div class="mb-3">
                    <label for="nomeEvento" class="form-label">Nome Evento:</label>
                    <input type="text" id="nomeEvento" name="nomeEvento" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="dataInizioEvento" class="form-label">Data Inizio Evento:</label>
                    <input type="datetime-local" id="dataInizioEvento" name="dataInizioEvento" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="dataFineEvento" class="form-label">Data Fine Evento:</label>
                    <input type="datetime-local" id="dataFineEvento" name="dataFineEvento" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="categoriaEvento" class="form-label">Categoria:</label>
                    <select id="categoriaEvento" name="categoriaEvento" class="form-select" required>
                        <%
                            List<Category> categories = (List<Category>) request.getAttribute("categories");
                            for (Category category : categories) {
                                out.print("<option value=\"" + category.getId() + "\">" + category.getName() + "</option>");
                            }
                        %>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="localitaEvento" class="form-label">Località:</label>
                    <select id="localitaEvento" name="localitaEvento" class="form-select" required>
                        <%
                            List<Location> locations = (List<Location>) request.getAttribute("locations");
                            for (Location location : locations) {
                                out.print("<option value=\"" + location.getId() + "\">" + location.getName() + "</option>");
                            }
                        %>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="descrizioneEvento" class="form-label">Descrizione:</label>
                    <textarea id="descrizioneEvento" name="descrizioneEvento" class="form-control" rows="4" required></textarea>
                </div>

                <div class="mb-3">
                    <label for="prezzoPostoSeduto" class="form-label">Prezzo Posto Seduto (€):</label>
                    <input type="number" id="prezzoPostoSeduto" name="prezzoPostoSeduto" class="form-control" step="0.01" min="0" required>
                </div>

                <div class="mb-3">
                    <label for="prezzoPostoInPiedi" class="form-label">Prezzo Posto in Piedi (€):</label>
                    <input type="number" id="prezzoPostoInPiedi" name="prezzoPostoInPiedi" class="form-control" step="0.01" min="0" required>
                </div>

                <div class="mb-3">
                    <label for="totalePostiSeduti" class="form-label">Totale Posti a Sedere:</label>
                    <input type="number" id="totalePostiSeduti" name="totalePostiSeduti" class="form-control" min="0" required>
                </div>

                <div class="mb-3">
                    <label for="totalePostiInPiedi" class="form-label">Totale Posti in Piedi:</label>
                    <input type="number" id="totalePostiInPiedi" name="totalePostiInPiedi" class="form-control" min="0" required>
                </div>

                <div class="mb-3">
                    <label for="immagineEvento" class="form-label">Immagine Evento:</label>
                    <input type="file" id="immagineEvento" name="immagineEvento" accept="image/*" required>
                </div>

                <div class="text-center">
                    <input type="submit" value="Inserisci Evento" class="btn btn-primary">
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    document.getElementById('eventoForm').addEventListener('submit', function(event) {
        const dataInizio = new Date(document.getElementById('dataInizioEvento').value);
        const dataFine = new Date(document.getElementById('dataFineEvento').value);
        const oraAttuale = new Date();

        // Save label
        const labelDataInizio = document.querySelector('label[for="dataInizioEvento"]');
        const labelDataFine = document.querySelector('label[for="dataFineEvento"]');

        document.querySelectorAll('.form-label').forEach(label => label.classList.remove('error-label'));

        let errore = false;

        document.getElementById('dataInizioEvento').setAttribute('min', oraAttuale.toISOString().slice(0, 16));

        if (dataFine <= dataInizio) {
            labelDataFine.classList.add('error-label');
            alert("La data di fine evento deve essere successiva alla data di inizio evento.");
            errore = true;
        }

        if (dataInizio < oraAttuale) {
            labelDataInizio.classList.add('error-label');
            alert("La data di inizio evento non può essere nel passato.");
            errore = true;
        }

        if (errore) {
            event.preventDefault();
        }
    });

    function generaDati() {
        const oraAttuale = new Date();

        // Calcola la data e l'ora attuale più 5 ore
        const dataInizio = new Date(oraAttuale.getTime() + 5 * 60 * 60 * 1000); // 5 ore
        const dataFine = new Date(dataInizio.getTime() + 3600000); // 1 ora dopo l'inizio

        document.getElementById('nomeEvento').value = 'Evento di Prova';
        document.getElementById('dataInizioEvento').value = dataInizio.toISOString().slice(0, 16);
        document.getElementById('dataFineEvento').value = dataFine.toISOString().slice(0, 16);
        document.getElementById('categoriaEvento').value = document.querySelector('#categoriaEvento option:first-child').value; // Seleziona il primo
        document.getElementById('localitaEvento').value = document.querySelector('#localitaEvento option:first-child').value; // Seleziona il primo
        document.getElementById('descrizioneEvento').value = 'Descrizione di esempio per l\'evento.';
        document.getElementById('prezzoPostoSeduto').value = '10.00';
        document.getElementById('prezzoPostoInPiedi').value = '5.00';
        document.getElementById('totalePostiSeduti').value = '100';
        document.getElementById('totalePostiInPiedi').value = '200';
    }
</script>
</body>
</html>
