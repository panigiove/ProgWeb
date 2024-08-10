package web.example.progweb.model;

import web.example.progweb.model.abstractClass.AbstractModel;
import web.example.progweb.model.entity.Discount;
import web.example.progweb.model.entity.Ticket;
import web.example.progweb.model.entity.User;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
CREATE TABLE PRENOTAZIONE_BIGLIETTI (
    id_prenotazione INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    id_evento INT NOT NULL,
    id_utente INT NOT NULL,
    id_sconto INT,
    quantita INT DEFAULT 1,
    tipologia VARCHAR(10) CHECK (tipologia IN ('poltrona', 'in piedi')) NOT NULL,
    data_acquisto TIMESTAMP NOT NULL,
    prezzo DECIMAL (10, 2) NOT NULL,
    FOREIGN KEY (id_evento) REFERENCES EVENTI(id_evento) ON DELETE CASCADE,
    FOREIGN KEY (id_utente) REFERENCES UTENTI(id_utente) ON DELETE CASCADE,
    FOREIGN KEY (id_sconto) REFERENCES SCONTI_EVENTO(id_sconto) ON DELETE CASCADE
);

CREATE TABLE SCONTI_EVENTO (
    id_sconto INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    id_evento INT,
    data_scadenza DATE NOT NULL,
    sconto DECIMAL(5, 2) NOT NULL,
    FOREIGN KEY (id_evento) REFERENCES EVENTI(id_evento)
);
 */

public class TicketModel extends AbstractModel {
    private final PreparedStatement createTicketPreparedStatement;
    private final PreparedStatement getTicketPreparedStatement;
    private final PreparedStatement getAllUserTicketPreparedStatement;
    private final PreparedStatement createDiscountPreparedStatement;
    private final PreparedStatement getDiscountPreparedStatement;
    private final PreparedStatement deleteTicketPreparedStatement;
    private final PreparedStatement deleteDiscountPreparedStatement;
    private final PreparedStatement getValidDiscountPreparedStatement;
    private final PreparedStatement getValidDiscountIdPreparedStatement;
    private EventModel eventModel;
    private UserModel userModel;

    public TicketModel() throws SQLException, ClassNotFoundException {
        super();
        eventModel = new EventModel();
        userModel = new UserModel();
        createTicketPreparedStatement = connection.prepareStatement("INSERT INTO PRENOTAZIONE_BIGLIETTI (id_evento, id_utente, id_sconto, quantita, tipologia, data_acquisto , prezzo) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        getTicketPreparedStatement = connection.prepareStatement("SELECT * FROM PRENOTAZIONE_BIGLIETTI WHERE id_prenotazione = ?");
        getAllUserTicketPreparedStatement = connection.prepareStatement("SELECT * FROM PRENOTAZIONE_BIGLIETTI WHERE id_utente = ?");
        createDiscountPreparedStatement = connection.prepareStatement("INSERT INTO SCONTI_EVENTO (id_evento, data_scadenza, sconto) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        getDiscountPreparedStatement = connection.prepareStatement("SELECT * FROM SCONTI_EVENTO WHERE id_sconto = ?");
        deleteTicketPreparedStatement = connection.prepareStatement("DELETE FROM PRENOTAZIONE_BIGLIETTI WHERE id_prenotazione = ?");
        deleteDiscountPreparedStatement = connection.prepareStatement("DELETE FROM SCONTI_EVENTO WHERE id_sconto = ?");
        getValidDiscountPreparedStatement = connection.prepareStatement("SELECT * FROM SCONTI_EVENTO WHERE id_evento = ? AND data_scadenza >= CURRENT_DATE");
        getValidDiscountIdPreparedStatement = connection.prepareStatement("SELECT id_sconto FROM SCONTI_EVENTO WHERE id_evento = ? AND data_scadenza >= CURRENT_DATE");
    }

    /*
    typology: false -> poltrona, true -> in piedi
     */
    public Ticket buyTicket(int nTicket, int id_event, int id_user, boolean typology) throws SQLException {
        if (userModel.checkId(id_user) && eventModel.checkId(id_event) && nTicket > 0 && eventModel.decrementAvailability(id_event, typology, nTicket)) {
            int id_discount = getValidDiscountId(id_event);
            createTicketPreparedStatement.setInt(1, id_event);
            createTicketPreparedStatement.setInt(2, id_user);
            if (id_discount != 0) {
                createTicketPreparedStatement.setInt(3, id_discount);
            } else {
                createTicketPreparedStatement.setNull(3, java.sql.Types.INTEGER);
            }
            createTicketPreparedStatement.setInt(4, nTicket);
            createTicketPreparedStatement.setString(5, typology ? "in_piedi" : "poltrona");
            createTicketPreparedStatement.setBigDecimal(6, calcolatePrice(id_user, id_event, typology, nTicket));
            int affectedRows = createTicketPreparedStatement.executeUpdate();
            userModel.incrementPurchases(id_user);

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = createTicketPreparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return getTicket(generatedKeys.getInt(1));
                    }
                }
            }
        }
        return null;
    }

    public List<Ticket> getAllUserTicket (int idUser) throws SQLException {
        getAllUserTicketPreparedStatement.setInt(1, idUser);
        ResultSet rs = getAllUserTicketPreparedStatement.executeQuery();
        List<Ticket> tickets = new ArrayList<>();
        while (rs.next()){
            int idPrenotazione = rs.getInt("id_prenotazione");
            int idEvento = rs.getInt("id_evento");
            int idUtente = rs.getInt("id_utente");

            // Gestire id_sconto che potrebbe essere NULL
            Integer idSconto = rs.wasNull() ? null : rs.getInt("id_sconto");

            int quantita = rs.getInt("quantita");
            String tipologia = rs.getString("tipologia");
            String dataAcquisto = rs.getString("data_acquisto");
            BigDecimal prezzo = rs.getBigDecimal("prezzo");
            tickets.add(new Ticket(idPrenotazione, idEvento, idUtente, idSconto, quantita, tipologia, dataAcquisto, prezzo));
        }
        return tickets;
    }

    public Ticket getTicket(int id) throws SQLException {
        getTicketPreparedStatement.setInt(1, id);
        ResultSet rs = getTicketPreparedStatement.executeQuery();
        if (rs.next()){
            int idPrenotazione = rs.getInt("id_prenotazione");
            int idEvento = rs.getInt("id_evento");
            int idUtente = rs.getInt("id_utente");

            // Gestire id_sconto che potrebbe essere NULL
            int idSconto = rs.wasNull() ? -1 : rs.getInt("id_sconto");

            int quantita = rs.getInt("quantita");
            String tipologia = rs.getString("tipologia");
            String dataAcquisto = rs.getString("data_acquisto");
            BigDecimal prezzo = rs.getBigDecimal("prezzo");
            return new Ticket(idPrenotazione, idEvento, idUtente, idSconto, quantita, tipologia, dataAcquisto, prezzo);
        }
        return null;
    }

    public Discount getDiscount(int id) throws SQLException {
        getDiscountPreparedStatement.setInt(1, id);
        ResultSet rs = getDiscountPreparedStatement.executeQuery();
        if (rs.next()){
            int id_discount = rs.getInt("id_sconto");
            int id_event = rs.getInt("id_evento");
            String expiration_date = rs.getString("data_scadenza");
            double discount = rs.getDouble("sconto");
            //return new Discount(id_discount, id_event, expiration_date, discount);
        }
        return null;
    }

    public BigDecimal getValidDiscount(int idEvent) throws SQLException {
        getValidDiscountPreparedStatement.setInt(1, idEvent);
        ResultSet resultSet = getValidDiscountPreparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getBigDecimal("sconto");
        }
        return new BigDecimal(0);
    }

    public int getValidDiscountId(int idEvent) throws SQLException {
        getValidDiscountIdPreparedStatement.setInt(1, idEvent);
        ResultSet resultSet = getValidDiscountIdPreparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id_sconto");
        }
        return 0;
    }

    public List<Discount> getDiscounts () throws SQLException {
        String query = "SELECT * FROM SCONTI_EVENTO";
        ResultSet resultSet = unsafeExecuteQuery(query);
        List<Discount> discounts = new ArrayList<>();
        while (resultSet.next()) {
            Discount discount = new Discount(
                    resultSet.getInt("id_sconto"),
                    resultSet.getInt("id_evento"),
                    resultSet.getString("data_scadenza"),
                    resultSet.getBigDecimal("sconto")
            );
            discounts.add(discount);
        }
        return discounts;
    }



    public Discount createDiscount(int id_event, String expiration_date, double discount) throws SQLException {
        createDiscountPreparedStatement.setInt(1, id_event);
        createDiscountPreparedStatement.setString(2, expiration_date);
        createDiscountPreparedStatement.setDouble(3, discount);
        int affectedRows = createDiscountPreparedStatement.executeUpdate();
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = createDiscountPreparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return getDiscount(generatedKeys.getInt(1));
                }
            }
        }
        return null;
    }

    public void deleteDiscount(int id) throws SQLException {
        Discount discount = getDiscount(id);
        if (discount != null) {
            deleteDiscountPreparedStatement.setInt(1, id);
            deleteDiscountPreparedStatement.executeUpdate();
        }
    }

    public void deleteTicket(int id) throws SQLException {
        Ticket ticket = getTicket(id);
        if (ticket != null) {
            if (deleteTicketPreparedStatement.executeUpdate() > 0) {
                eventModel.incrementAvailability(ticket.idEvento(), ticket.getTipology().equals("in_piedi"), ticket.getQuantita());
            }
        }
    }

    private BigDecimal calcolatePrice(int id_user, int id_event, boolean typology, int nTicket) throws SQLException {
        BigDecimal price = new BigDecimal(0);
        if ((userModel.getPurchases(id_user) + 1)  % 5 != 0){
            price = eventModel.getPrice(id_event, typology).multiply(new BigDecimal(nTicket));
            BigDecimal discount = getValidDiscount(id_event).divide(new BigDecimal(100));
            price = price.subtract(price.multiply(discount));
        }
        return price;
    }
}
