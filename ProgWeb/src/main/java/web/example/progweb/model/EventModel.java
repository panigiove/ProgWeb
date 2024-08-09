package web.example.progweb.model;


import web.example.progweb.model.abstractClass.AbstractModel;
import web.example.progweb.model.entity.Category;
import web.example.progweb.model.entity.Event;
import web.example.progweb.model.entity.Location;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
CREATE TABLE EVENTI (
    id_evento INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    id_categoria INT,
    id_localita INT,
    nome VARCHAR(255) NOT NULL,
    inizio TIMESTAMP NOT NULL,
    fine TIMESTAMP NOT NULL,
    totale_poltrona INT NOT NULL,
    disponibilita_poltrona INT NOT NULL,
    totale_in_piedi INT NOT NULL,
    disponibilita_in_piedi INT NOT NULL,
    prezzi_poltrona DECIMAL(10, 2) NOT NULL,
    prezzi_in_piedi DECIMAL(10, 2) NOT NULL,
    n_click INT DEFAULT 0,
    FOREIGN KEY (id_localita) REFERENCES LOCALITA(id_localita),
    FOREIGN KEY (id_categoria) REFERENCES CATEGORIA(id_categoria)
);
 */

public class EventModel extends AbstractModel {
    private final PreparedStatement getEventByCategoryPreparedStatement;
    private final PreparedStatement getEventByIdPreparedStatement;
    private final PreparedStatement insertEventPreparedStatement;
    private final PreparedStatement deleteEventPreparedStatement;
    private final PreparedStatement incrementClickPreparedStatement;
    private final PreparedStatement decrementPoltronePreparedStatement;
    private final PreparedStatement decrementInPiediPreparedStatement;
    private final PreparedStatement checkAvailabilityPreparedStatement;
    private final PreparedStatement getDiscountPreparedStatement;
    private final PreparedStatement getDiscountIdPreparedStatement;
    private final PreparedStatement checkIdPreparedStatement;
    private final PreparedStatement deleteCategoryPreparedStatement;
    private final PreparedStatement deleteLocationPreparedStatement;
    private final PreparedStatement incrementPoltronePreparedStatement;
    private final PreparedStatement incrementInPiediPreparedStatement;

    public EventModel() throws SQLException, ClassNotFoundException {
        super();
        getEventByCategoryPreparedStatement = connection.prepareStatement("SELECT * FROM EVENTI WHERE id_categoria = ?");
        getEventByIdPreparedStatement = connection.prepareStatement("SELECT * FROM EVENTI WHERE id_evento = ?");
        insertEventPreparedStatement = connection.prepareStatement("INSERT INTO EVENTI (id_categoria, id_localita, nome, inizio, fine, totale_poltrona, disponibilita_poltrona, totale_in_piedi, disponibilita_in_piedi, prezzi_poltrona, prezzi_in_piedi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        deleteEventPreparedStatement = connection.prepareStatement("DELETE FROM EVENTI WHERE id_evento = ?");
        checkAvailabilityPreparedStatement = connection.prepareStatement("SELECT disponibilita_in_piedi FROM EVENTI WHERE id_evento = ?");
        incrementClickPreparedStatement = connection.prepareStatement("UPDATE EVENTI SET n_click = n_click + 1 WHERE id_evento = ?");
        decrementPoltronePreparedStatement = connection.prepareStatement("UPDATE EVENTI SET disponibilita_poltrona = disponibilita_poltrona - ? WHERE id_evento = ?");
        decrementInPiediPreparedStatement = connection.prepareStatement("UPDATE EVENTI SET disponibilita_in_piedi = disponibilita_in_piedi - ? WHERE id_evento = ?");
        getDiscountPreparedStatement = connection.prepareStatement("SELECT * FROM SCONTI_EVENTO WHERE id_evento = ? AND data_scadenza >= CURRENT_DATE");
        getDiscountIdPreparedStatement = connection.prepareStatement("SELECT id_sconto FROM SCONTI_EVENTO WHERE id_evento = ? AND data_scadenza >= CURRENT_DATE");
        checkIdPreparedStatement = connection.prepareStatement("SELECT * FROM EVENTI WHERE id_evento = ?");
        deleteCategoryPreparedStatement = connection.prepareStatement("DELETE FROM CATEGORIA WHERE id_categoria = ?");
        deleteLocationPreparedStatement = connection.prepareStatement("DELETE FROM LOCALITA WHERE id_localita = ?");
        incrementPoltronePreparedStatement = connection.prepareStatement("UPDATE EVENTI SET disponibilita_poltrona = disponibilita_poltrona + ? WHERE id_evento = ?");
        incrementInPiediPreparedStatement = connection.prepareStatement("UPDATE EVENTI SET disponibilita_in_piedi = disponibilita_in_piedi + ? WHERE id_evento = ?");
    }

    public Event insertEvent(int idCategoria, int idLocalita, String name, String start, String end, int totalSeats, int availableSeats, int totalStanding, int availableStanding, BigDecimal seatPrice, BigDecimal standingPrice) throws SQLException {
        insertEventPreparedStatement.setInt(1, idCategoria);
        insertEventPreparedStatement.setInt(2, idLocalita);
        insertEventPreparedStatement.setString(3, name);
        insertEventPreparedStatement.setString(4, formatDateTime(start));
        insertEventPreparedStatement.setString(5, formatDateTime(end));
        insertEventPreparedStatement.setInt(6, totalSeats);
        insertEventPreparedStatement.setInt(7, availableSeats);
        insertEventPreparedStatement.setInt(8, totalStanding);
        insertEventPreparedStatement.setInt(9, availableStanding);
        insertEventPreparedStatement.setBigDecimal(10, seatPrice);
        insertEventPreparedStatement.setBigDecimal(11, standingPrice);
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

    public void incrementClick(int id) throws SQLException {
        incrementClickPreparedStatement.setInt(1, id);
        incrementClickPreparedStatement.executeUpdate();
    }

    /*
    typology: false -> poltrona, true -> in piedi
     */
    public boolean decrementAvailability(int id, boolean type, int n) throws SQLException {
        if (checkAvailability(id, type, n)) {
            if (type) {
                decrementInPiediPreparedStatement.setInt(1, n);
                decrementInPiediPreparedStatement.setInt(2, id);
                decrementInPiediPreparedStatement.executeUpdate();
            } else {
                decrementPoltronePreparedStatement.setInt(1, n);
                decrementPoltronePreparedStatement.setInt(2, id);
                decrementPoltronePreparedStatement.executeUpdate();
            }
            return true;
        }
        return false;
    }

    public void incrementAvailability(int id, boolean type, int n) throws SQLException {
        if (type) {
            incrementInPiediPreparedStatement.setInt(1, n);
            incrementInPiediPreparedStatement.setInt(2, id);
            incrementInPiediPreparedStatement.executeUpdate();
        } else {
            incrementPoltronePreparedStatement.setInt(1, n);
            incrementPoltronePreparedStatement.setInt(2, id);
            incrementPoltronePreparedStatement.executeUpdate();
        }
    }

    public boolean checkId (int id) throws SQLException {
        checkIdPreparedStatement.setInt(1, id);
        ResultSet resultSet = checkIdPreparedStatement.executeQuery();
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

    public BigDecimal getPrice (int id, boolean type) throws SQLException {
        String query = "SELECT " + (type ? "prezzi_in_piedi" : "prezzi_poltrona") + " FROM EVENTI WHERE id_evento = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getBigDecimal(1);
    }

    public List<Event> getEventByCategory(int idCategoria) throws SQLException {
        getEventByCategoryPreparedStatement.setInt(1, idCategoria);
        ResultSet resultSet = getEventByCategoryPreparedStatement.executeQuery();
        List<Event> events = new ArrayList<>();
        while (resultSet.next()) {
            Event event = new Event(
                    resultSet.getInt("id_evento"),
                    resultSet.getInt("id_categoria"),
                    resultSet.getInt("id_localita"),
                    resultSet.getString("nome"),
                    resultSet.getString("inizio"),
                    resultSet.getString("fine"),
                    resultSet.getInt("totale_poltrona"),
                    resultSet.getInt("disponibilita_poltrona"),
                    resultSet.getInt("totale_in_piedi"),
                    resultSet.getInt("disponibilita_in_piedi"),
                    resultSet.getBigDecimal("prezzi_poltrona"),
                    resultSet.getBigDecimal("prezzi_in_piedi"),
                    resultSet.getInt("n_click")
            );
            events.add(event);
        }
        return events;
    }

    public Event getEventById (int id) throws SQLException {
        getEventByIdPreparedStatement.setInt(1, id);
        ResultSet resultSet = getEventByIdPreparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Event(
                    resultSet.getInt("id_evento"),
                    resultSet.getInt("id_categoria"),
                    resultSet.getInt("id_localita"),
                    resultSet.getString("nome"),
                    resultSet.getString("inizio"),
                    resultSet.getString("fine"),
                    resultSet.getInt("totale_poltrona"),
                    resultSet.getInt("disponibilita_poltrona"),
                    resultSet.getInt("totale_in_piedi"),
                    resultSet.getInt("disponibilita_in_piedi"),
                    resultSet.getBigDecimal("prezzi_poltrona"),
                    resultSet.getBigDecimal("prezzi_in_piedi"),
                    resultSet.getInt("n_click")
            );
        }
        return null;
    }

    public List<Event> get3MostClickedEvent() throws SQLException{
        String query = "SELECT * FROM EVENTI ORDER BY n_click DESC FETCH FIRST 3 ROWS ONLY";
        ResultSet resultSet = unsafeExecuteQuery(query);
        List<Event> events = new ArrayList<>();
        while (resultSet.next()) {
            Event event = new Event(
                    resultSet.getInt("id_evento"),
                    resultSet.getInt("id_categoria"),
                    resultSet.getInt("id_localita"),
                    resultSet.getString("nome"),
                    resultSet.getString("inizio"),
                    resultSet.getString("fine"),
                    resultSet.getInt("totale_poltrona"),
                    resultSet.getInt("disponibilita_poltrona"),
                    resultSet.getInt("totale_in_piedi"),
                    resultSet.getInt("disponibilita_in_piedi"),
                    resultSet.getBigDecimal("prezzi_poltrona"),
                    resultSet.getBigDecimal("prezzi_in_piedi"),
                    resultSet.getInt("n_click")
            );
            events.add(event);
        }
        return events;
    }

    public List<Event> getEventsOrderedByClick() throws SQLException {
        String query = "SELECT * FROM EVENTI ORDER BY n_click DESC";
        ResultSet resultSet = unsafeExecuteQuery(query);
        List<Event> events = new ArrayList<>();
        while (resultSet.next()) {
            Event event = new Event(
                    resultSet.getInt("id_evento"),
                    resultSet.getInt("id_categoria"),
                    resultSet.getInt("id_localita"),
                    resultSet.getString("nome"),
                    resultSet.getString("inizio"),
                    resultSet.getString("fine"),
                    resultSet.getInt("totale_poltrona"),
                    resultSet.getInt("disponibilita_poltrona"),
                    resultSet.getInt("totale_in_piedi"),
                    resultSet.getInt("disponibilita_in_piedi"),
                    resultSet.getBigDecimal("prezzi_poltrona"),
                    resultSet.getBigDecimal("prezzi_in_piedi"),
                    resultSet.getInt("n_click")
            );
            events.add(event);
        }
        return events;
    }

    private boolean checkAvailability(int id, boolean type, int n) throws SQLException {
        checkAvailabilityPreparedStatement.setInt(1, id);
        ResultSet resultSet = checkAvailabilityPreparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1) >= n;
        }
        return false;
    }

    public BigDecimal getDiscount(int idEvent) throws SQLException {
        getDiscountPreparedStatement.setInt(1, idEvent);
        ResultSet resultSet = getDiscountPreparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getBigDecimal("sconto");
        }
        return new BigDecimal(0);
    }

    public int getDiscountId(int idEvent) throws SQLException {
        getDiscountIdPreparedStatement.setInt(1, idEvent);
        ResultSet resultSet = getDiscountIdPreparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id_sconto");
        }
        return 0;
    }

    public List<Category> getAllCategory() throws SQLException {
        String query = "SELECT * FROM CATEGORIA";
        ResultSet resultSet = unsafeExecuteQuery(query);
        List<Category> categories = new ArrayList<>();
        while (resultSet.next()) {
            Category category = new Category(
                    resultSet.getInt("id_categoria"),
                    resultSet.getString("nome")
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
                    resultSet.getString("nome")
            );
            locations.add(location);
        }
        return locations;
    }


}
