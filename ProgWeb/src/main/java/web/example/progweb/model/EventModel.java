package web.example.progweb.model;


import web.example.progweb.model.abstractClass.AbstractModel;
import web.example.progweb.model.entity.Category;
import web.example.progweb.model.entity.Event;
import web.example.progweb.model.entity.Location;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * EventModel gestisce le operazioni relative agli eventi, categorie e località nel database
 *
 * Questa classe fornisce metodi per:
 * - Inserire nuovi eventi
 * - Cancellare eventi esistenti
 * - Aggiornare eventi (aumentando i clic, la disponibilità dei posti o diminuendola)
 * - Ottenere eventi in diversi modi (tutti, per categoria, ordinati per clic, i 3 eventi più cliccati)
 * - Recuperare tutte le categorie e tutte le località
 * - Ottenere il nome di una località dato il suo ID
 *  * - Ottenere le statistiche categoria
 *
 * Utilizza la classe Event, Location e Category per rappresentare i risultati delle query e gestire le informazioni.
 */
public class EventModel extends AbstractModel {
    private PreparedStatement getEventByCategoryPreparedStatement;
    private PreparedStatement getEventByIdPreparedStatement;
    private PreparedStatement insertEventPreparedStatement;
    private PreparedStatement deleteEventPreparedStatement;
    private PreparedStatement incrementClickPreparedStatement;
    private PreparedStatement checkIdPreparedStatement;
    private PreparedStatement deleteCategoryPreparedStatement;
    private PreparedStatement deleteLocationPreparedStatement;
    private PreparedStatement updateAvailabilityPreparedStatement;
    private PreparedStatement checkAvailabilityPreparedStatement;
    private PreparedStatement checkIdCategoryPreparedStatement;
    private PreparedStatement getLocationNamePreparedStatement;
    private PreparedStatement getCategoryTotalNclickPreparedStatement;
    private PreparedStatement getCategoryNamePreparedStatement;

    public EventModel() throws SQLException, ClassNotFoundException {
        super();
    }

    public EventModel (Connection connection) throws SQLException{
        super(connection);
    }

    protected void prepareStatements ()  throws SQLException {
        getEventByCategoryPreparedStatement = connection.prepareStatement("SELECT * FROM EVENTI WHERE id_categoria = ?");
        getEventByIdPreparedStatement = connection.prepareStatement("SELECT * FROM EVENTI WHERE id_evento = ?");
        insertEventPreparedStatement = connection.prepareStatement("INSERT INTO EVENTI (id_categoria, id_localita, nome, descrizione, inizio, fine, totale_poltrona, disponibilita_poltrona, totale_in_piedi, disponibilita_in_piedi, prezzi_poltrona, prezzi_in_piedi, image_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        deleteEventPreparedStatement = connection.prepareStatement("DELETE FROM EVENTI WHERE id_evento = ?");
        checkIdPreparedStatement = connection.prepareStatement("SELECT * FROM EVENTI WHERE id_evento = ?");
        checkIdCategoryPreparedStatement = connection.prepareStatement("SELECT * FROM CATEGORIA WHERE id_categoria = ?");
        getCategoryTotalNclickPreparedStatement = connection.prepareStatement("SELECT SUM(n_click) AS totale_click FROM EVENTI WHERE id_categoria = ?");
        deleteCategoryPreparedStatement = connection.prepareStatement("DELETE FROM CATEGORIA WHERE id_categoria = ?");
        deleteLocationPreparedStatement = connection.prepareStatement("DELETE FROM LOCALITA WHERE id_localita = ?");
        incrementClickPreparedStatement = connection.prepareStatement("UPDATE EVENTI SET n_click = n_click + 1 WHERE id_evento = ?");
        getLocationNamePreparedStatement = connection.prepareStatement("SELECT localita FROM LOCALITA WHERE id_localita = ?");
        checkAvailabilityPreparedStatement = connection.prepareStatement("SELECT disponibilita_in_piedi, disponibilita_poltrona FROM EVENTI WHERE id_evento = ?");
        updateAvailabilityPreparedStatement = connection.prepareStatement("UPDATE EVENTI SET disponibilita_poltrona = disponibilita_poltrona + ?, disponibilita_in_piedi = disponibilita_in_piedi + ? WHERE id_evento = ?");
        getCategoryNamePreparedStatement = connection.prepareStatement("SELECT categoria FROM CATEGORIA WHERE id_categoria = ?");
    }

    /**
     * Crea un evento
     * @param idCategoria
     * @param idLocalita
     * @param name
     * @param descrizione
     * @param start
     * @param end
     * @param totalSeats
     * @param availableSeats
     * @param totalStanding
     * @param availableStanding
     * @param seatPrice
     * @param standingPrice
     * @return Event appena creato, null se ci sono stati problemi
     * @throws SQLException
     */
    public Event insertEvent(int idCategoria, int idLocalita, String name, String descrizione, String start, String end, int totalSeats, int availableSeats, int totalStanding, int availableStanding, BigDecimal seatPrice, BigDecimal standingPrice, String image_name) throws SQLException {
        insertEventPreparedStatement.setInt(1, idCategoria);
        insertEventPreparedStatement.setInt(2, idLocalita);
        insertEventPreparedStatement.setString(3, name);
        insertEventPreparedStatement.setString(4, descrizione);
        insertEventPreparedStatement.setString(5, formatDateTime(start));
        insertEventPreparedStatement.setString(6, formatDateTime(end));
        insertEventPreparedStatement.setInt(7, totalSeats);
        insertEventPreparedStatement.setInt(8, availableSeats);
        insertEventPreparedStatement.setInt(9, totalStanding);
        insertEventPreparedStatement.setInt(10, availableStanding);
        insertEventPreparedStatement.setBigDecimal(11, seatPrice);
        insertEventPreparedStatement.setBigDecimal(12, standingPrice);
        insertEventPreparedStatement.setString(13, image_name);
        int affectedRows = insertEventPreparedStatement.executeUpdate();
        if (affectedRows > 0){
            try (ResultSet generatedKeys = insertEventPreparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return getEventById(generatedKeys.getInt(1));
                }
            }
        }
        return null;
    }

    public void deleteEvent(int id) throws SQLException {
        deleteEventPreparedStatement.setInt(1, id);
        deleteEventPreparedStatement.executeUpdate();
    }

    /**
     * Incrementa i click di un evento
     * @param id
     * @throws SQLException
     */
    public void incrementClick(int id) throws SQLException {
        incrementClickPreparedStatement.setInt(1, id);
        incrementClickPreparedStatement.executeUpdate();
    }

    public boolean checkIdEvent(int id) throws SQLException {
        checkIdPreparedStatement.setInt(1, id);
        ResultSet resultSet = checkIdPreparedStatement.executeQuery();
        return resultSet.next();
    }

    public boolean checkCategoryId(int id) throws SQLException{
        checkIdCategoryPreparedStatement.setInt(1, id);
        ResultSet resultSet = checkIdCategoryPreparedStatement.executeQuery();
        return resultSet.next();
    }

    public void deleteLocation (int id) throws SQLException {
        deleteLocationPreparedStatement.setInt(1, id);
        deleteLocationPreparedStatement.executeUpdate();
    }

    public void deleteCategory (int id) throws SQLException {
        deleteCategoryPreparedStatement.setInt(1, id);
        deleteCategoryPreparedStatement.executeUpdate();
    }

    /**
     * Ottenere il numero totale di click di una categoria
     * @param idCategory
     * @return -1 se la query non trova nulla
     * @throws SQLException
     */
    public int getCategoryTotalNclick (int idCategory) throws SQLException{
        getCategoryTotalNclickPreparedStatement.setInt(1, idCategory);
        ResultSet rs =  getCategoryTotalNclickPreparedStatement.executeQuery();
        if (rs.next()){
            return rs.getInt("totale_click");
        }
        return -1;
    }


    /**
     * Ottenere tutti gli eventi
     * @return
     * @throws SQLException
     */
    public List<Event> getEvents() throws SQLException {
        String query = "SELECT * FROM EVENTI";
        List<Event> events = new ArrayList<>();
        try (ResultSet resultSet = unsafeExecuteQuery(query)) {
            while (resultSet.next()) {
                String nomeLocation = getLocationName(resultSet.getInt("id_localita")); // Ricerca nome della località perchè verrà mostrato insieme nell'anteprima dell'evento
                String nomeCategory = getCategoryName(resultSet.getInt("id_categoria"));
                Event event = new Event(
                        resultSet.getInt("id_evento"),
                        resultSet.getInt("id_categoria"),
                        resultSet.getInt("id_localita"),
                        nomeLocation,
                        nomeCategory,
                        resultSet.getString("nome"),
                        resultSet.getString("descrizione"),
                        resultSet.getString("inizio"),
                        resultSet.getString("fine"),
                        resultSet.getInt("totale_poltrona"),
                        resultSet.getInt("disponibilita_poltrona"),
                        resultSet.getInt("totale_in_piedi"),
                        resultSet.getInt("disponibilita_in_piedi"),
                        resultSet.getBigDecimal("prezzi_poltrona"),
                        resultSet.getBigDecimal("prezzi_in_piedi"),
                        resultSet.getInt("n_click"),
                        resultSet.getString("image_name")
                );
                events.add(event);
            }
        }
        return events;
    }

    /**
     * Ottenere tutti gli eventi di una categoria
     * @param idCategoria
     * @return
     * @throws SQLException
     */
    public List<Event> getEventByCategory(int idCategoria) throws SQLException {
        List<Event> events = new ArrayList<>();
        getEventByCategoryPreparedStatement.setInt(1, idCategoria);
        try (ResultSet resultSet = getEventByCategoryPreparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String nomeLocation = getLocationName(resultSet.getInt("id_localita"));
                String nomeCategory = getCategoryName(resultSet.getInt("id_categoria"));
                Event event = new Event(
                        resultSet.getInt("id_evento"),
                        resultSet.getInt("id_categoria"),
                        resultSet.getInt("id_localita"),
                        nomeLocation,
                        nomeCategory,
                        resultSet.getString("nome"),
                        resultSet.getString("descrizione"),
                        resultSet.getString("inizio"),
                        resultSet.getString("fine"),
                        resultSet.getInt("totale_poltrona"),
                        resultSet.getInt("disponibilita_poltrona"),
                        resultSet.getInt("totale_in_piedi"),
                        resultSet.getInt("disponibilita_in_piedi"),
                        resultSet.getBigDecimal("prezzi_poltrona"),
                        resultSet.getBigDecimal("prezzi_in_piedi"),
                        resultSet.getInt("n_click"),
                        resultSet.getString("image_name")
                );
                events.add(event);
            }
        }
        return events;
    }

    /**
     * Ottenere un evento dato il suo ID
     * @param id
     * @return
     * @throws SQLException
     */
    public Event getEventById(int id) throws SQLException {
        getEventByIdPreparedStatement.setInt(1, id);
        try (ResultSet resultSet = getEventByIdPreparedStatement.executeQuery()) {
            if (resultSet.next()) {
                String nomeLocation = getLocationName(resultSet.getInt("id_localita"));
                String nomeCategory = getCategoryName(resultSet.getInt("id_categoria"));
                return new Event(
                        resultSet.getInt("id_evento"),
                        resultSet.getInt("id_categoria"),
                        resultSet.getInt("id_localita"),
                        nomeLocation,
                        nomeCategory,
                        resultSet.getString("nome"),
                        resultSet.getString("descrizione"),
                        resultSet.getString("inizio"),
                        resultSet.getString("fine"),
                        resultSet.getInt("totale_poltrona"),
                        resultSet.getInt("disponibilita_poltrona"),
                        resultSet.getInt("totale_in_piedi"),
                        resultSet.getInt("disponibilita_in_piedi"),
                        resultSet.getBigDecimal("prezzi_poltrona"),
                        resultSet.getBigDecimal("prezzi_in_piedi"),
                        resultSet.getInt("n_click"),
                        resultSet.getString("image_name")
                );
            }
        }
        return null;
    }

    public String getEventName (int id) throws SQLException {
        return getEventById(id).getName();
    }

    /**
     * ottenere i 3 eventi più cliccati
     * @return
     * @throws SQLException
     */
    public List<Event> get3MostClickedEvent() throws SQLException {
        String query = "SELECT * FROM EVENTI ORDER BY n_click DESC FETCH FIRST 3 ROWS ONLY";
        List<Event> events = new ArrayList<>();
        try (ResultSet resultSet = unsafeExecuteQuery(query)) {
            while (resultSet.next()) {
                String nomeLocation = getLocationName(resultSet.getInt("id_localita"));
                String nomeCategory = getCategoryName(resultSet.getInt("id_categoria"));
                Event event = new Event(
                        resultSet.getInt("id_evento"),
                        resultSet.getInt("id_categoria"),
                        resultSet.getInt("id_localita"),
                        nomeLocation,
                        nomeCategory,
                        resultSet.getString("nome"),
                        resultSet.getString("descrizione"),
                        resultSet.getString("inizio"),
                        resultSet.getString("fine"),
                        resultSet.getInt("totale_poltrona"),
                        resultSet.getInt("disponibilita_poltrona"),
                        resultSet.getInt("totale_in_piedi"),
                        resultSet.getInt("disponibilita_in_piedi"),
                        resultSet.getBigDecimal("prezzi_poltrona"),
                        resultSet.getBigDecimal("prezzi_in_piedi"),
                        resultSet.getInt("n_click"),
                        resultSet.getString("image_name")
                );
                events.add(event);
            }
        }
        return events;
    }

    /**
     * Ottenere tutti gli eventi ordinati per numero di click
     * @return
     * @throws SQLException
     */
    public List<Event> getEventsOrderedByClick() throws SQLException {
        String query = "SELECT * FROM EVENTI ORDER BY n_click DESC";
        List<Event> events = new ArrayList<>();
        try (ResultSet resultSet = unsafeExecuteQuery(query)) {
            while (resultSet.next()) {
                String nomeLocation = getLocationName(resultSet.getInt("id_localita"));
                String nomeCategory = getCategoryName(resultSet.getInt("id_categoria"));
                Event event = new Event(
                        resultSet.getInt("id_evento"),
                        resultSet.getInt("id_categoria"),
                        resultSet.getInt("id_localita"),
                        nomeLocation,
                        nomeCategory,
                        resultSet.getString("nome"),
                        resultSet.getString("descrizione"),
                        resultSet.getString("inizio"),
                        resultSet.getString("fine"),
                        resultSet.getInt("totale_poltrona"),
                        resultSet.getInt("disponibilita_poltrona"),
                        resultSet.getInt("totale_in_piedi"),
                        resultSet.getInt("disponibilita_in_piedi"),
                        resultSet.getBigDecimal("prezzi_poltrona"),
                        resultSet.getBigDecimal("prezzi_in_piedi"),
                        resultSet.getInt("n_click"),
                        resultSet.getString("image_name")
                );
                events.add(event);
            }
        }
        return events;
    }

    public List<Category> getAllCategory() throws SQLException {
        String query = "SELECT * FROM CATEGORIA";
        ResultSet resultSet = unsafeExecuteQuery(query);
        List<Category> categories = new ArrayList<>();
        while (resultSet.next()) {
            Category category = new Category(
                    resultSet.getInt("id_categoria"),
                    resultSet.getString("categoria")
            );
            categories.add(category);
        }
        return categories;
    }

    public List<Location> getAllLocation() throws SQLException {
        String query = "SELECT * FROM LOCALITA";
        ResultSet resultSet = unsafeExecuteQuery(query);
        List<Location> locations = new ArrayList<>();
        while (resultSet.next()) {
            Location location = new Location(
                    resultSet.getInt("id_localita"),
                    resultSet.getString("localita")
            );
            locations.add(location);
        }
        return locations;
    }

    public String getLocationName(int idLocation) throws SQLException {
        getLocationNamePreparedStatement.setInt(1, idLocation);

        try (ResultSet rs = getLocationNamePreparedStatement.executeQuery()) {
            if (rs.next()) {
                return rs.getString("localita");
            } else {
                return null;
            }
        }
    }

    public String getCategoryName(int idCategory) throws SQLException {
        getCategoryNamePreparedStatement.setInt(1, idCategory);
        try (ResultSet rs = getCategoryNamePreparedStatement.executeQuery()) {
            if (rs.next()) {
                return rs.getString("categoria");
            } else {
                return null;
            }
        }
    }

    /**
     * Decrementa la disponibilità
     * @param id_event
     * @param n_seats
     * @param n_stands
     * @return True se effettuato, False altrimenti
     * @throws SQLException
     */
    public boolean decrementAvailability(int id_event, int n_seats, int n_stands) throws SQLException {
        if (n_seats >= 0 && n_stands >= 0 && checkAvailability(id_event, n_seats, n_stands)) {
            n_seats *= -1;
            n_stands *= -1;
            updateAvailabilityPreparedStatement.setInt(1, n_seats);
            updateAvailabilityPreparedStatement.setInt(2, n_stands);
            updateAvailabilityPreparedStatement.setInt(3, id_event);
            int rowsAffected = updateAvailabilityPreparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
        return false;
    }

    /**
     * Aumento la disponibilità
     * @param id_event
     * @param n_seats
     * @param n_stands
     * @throws SQLException
     */
    public void incrementAvailability(int id_event, int n_seats, int n_stands) throws SQLException {
        if (n_seats >= 0 && n_stands >= 0){
            updateAvailabilityPreparedStatement.setInt(1,n_seats);
            updateAvailabilityPreparedStatement.setInt(2,n_stands);
            updateAvailabilityPreparedStatement.setInt(3, id_event);
            updateAvailabilityPreparedStatement.executeUpdate();
        }
    }

    public boolean checkAvailability(int id_event, int n_seats, int n_stands) throws SQLException {
        checkAvailabilityPreparedStatement.setInt(1, id_event);
        try (ResultSet resultSet = checkAvailabilityPreparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int availableSeats = resultSet.getInt("disponibilita_poltrona");
                int availableStanding = resultSet.getInt("disponibilita_in_piedi");
                return availableSeats >= n_seats && availableStanding >= n_stands;
            }
            return false;
        }
    }
}
