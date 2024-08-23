package web.example.progweb.model;

import web.example.progweb.model.abstractClass.AbstractModel;
import web.example.progweb.model.entity.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * TicketModel gestisce le operazioni relative ai biglietti nel database
 *
 * Questa classe fornisce metodi per:
 * - Inserire nuovi biglietti, effettuando i controlli sugli id e aggiornando le statistiche di utenti e disponibilità di un evento
 * - Ottenere un biglietto dato ID
 * - Recuperare tutti i biglietti di un utente
 * - Cancellare un biglietto
 * - Calcolare il prezzo di un biglietto
 *
 * Utilizza la classe Ticket.
 */
public class TicketModel extends AbstractModel {
    private PreparedStatement createTicketPreparedStatement;
    private PreparedStatement getTicketPreparedStatement;
    private PreparedStatement getAllUserTicketPreparedStatement;
    private PreparedStatement deleteTicketPreparedStatement;

    private EventModel eventModel;
    private UserModel userModel;
    private DiscountModel discountModel;

    public TicketModel() throws SQLException, ClassNotFoundException {
        super();
        eventModel = new EventModel();
        userModel = new UserModel();
        discountModel = new DiscountModel();
    }

    public TicketModel(Connection connection) throws SQLException{
        super(connection);
        eventModel = new EventModel(connection);
        userModel = new UserModel(connection);
        discountModel = new DiscountModel(connection);
    }

    protected void prepareStatements() throws SQLException {
        createTicketPreparedStatement = connection.prepareStatement("INSERT INTO PRENOTAZIONE_BIGLIETTI (id_evento, id_utente, id_sconto, quantita_poltrona, quantita_in_piedi, data_acquisto, prezzo) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        getTicketPreparedStatement = connection.prepareStatement("SELECT * FROM PRENOTAZIONE_BIGLIETTI WHERE id_prenotazione = ?");
        getAllUserTicketPreparedStatement = connection.prepareStatement("SELECT * FROM PRENOTAZIONE_BIGLIETTI WHERE id_utente = ?");
         deleteTicketPreparedStatement = connection.prepareStatement("DELETE FROM PRENOTAZIONE_BIGLIETTI WHERE id_prenotazione = ?");
    }

    /**
     * Compare un biglietto, si effettuano controlli sugli id, sulla disponibilità e poi si effettua l'acquisto,
     * viene aggiornato il numero di acquisti dell'utente e la disponibilità dell'evento
     * @param id_event
     * @param id_user
     * @param id_discount -1 se non ci sono sconti
     * @param n_seats numero posti a sedere
     * @param n_stands numero posti in piedi
     * @return
     * @throws SQLException
     */
    public Ticket buyTicket(int id_event, int id_user, int id_discount, int n_seats, int n_stands) throws SQLException {
        if (userModel.checkId(id_user) && eventModel.checkIdEvent(id_event) &&
                (id_discount == -1 || discountModel.checkId(id_discount)) &&
                eventModel.checkAvailability(id_event, n_seats, n_stands)) {
            createTicketPreparedStatement.setInt(1, id_event);
            createTicketPreparedStatement.setInt(2, id_user);
            if (id_discount == -1){
                createTicketPreparedStatement.setNull(3,  java.sql.Types.INTEGER);
            }else{
                createTicketPreparedStatement.setInt(3, id_discount);
            }
            createTicketPreparedStatement.setInt(4, n_seats);
            createTicketPreparedStatement.setInt(5, n_stands);

            BigDecimal price = calculatePrice(id_event, id_user, id_discount, n_seats, n_stands);
            createTicketPreparedStatement.setBigDecimal(6, price);

            int affectedRows = createTicketPreparedStatement.executeUpdate();
            if (affectedRows != 0) {
                eventModel.decrementAvailability(id_event, n_seats, n_stands);
                userModel.incrementPurchases(id_user, n_seats+n_stands);
                try (ResultSet generatedKeys = createTicketPreparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return getTicket(generatedKeys.getInt(1));
                    }
                }
            }
        }
        return null;
    }

    /**
     * Calcola il prezzo tenendo conto della offerta ogni 5 biglietto il quinto è gratis
     * @param id_event
     * @param id_user
     * @param id_discount -1 se non ci sono sconti
     * @param n_seats
     * @param n_stands
     * @return
     * @throws SQLException
     */
    public BigDecimal calculatePrice(int id_event, int id_user, int id_discount, int n_seats, int n_stands) throws SQLException {
        // dettagli evento
        Event event = eventModel.getEventById(id_event);
        BigDecimal seatPrice = event.getSeatPrice();
        BigDecimal standPrice = event.getStandingPrice();

        // biglietti offerti dalla policy
        int totalFreeTickets = calculateFreeTickets(userModel.getPurchases(id_user), n_seats + n_stands);

        // togliere prima i biglietti più costosi
        if (seatPrice.compareTo(standPrice) > 0) {
            if (totalFreeTickets >= n_seats) {
                totalFreeTickets -= n_seats;
                n_seats = 0;
            } else {
                n_seats -= totalFreeTickets;
                totalFreeTickets = 0;
            }
            n_stands = Math.max(n_stands - totalFreeTickets, 0);
        } else {
            if (totalFreeTickets >= n_stands) {
                totalFreeTickets -= n_stands;
                n_stands = 0;
            } else {
                n_stands -= totalFreeTickets;
                totalFreeTickets = 0;
            }
            n_seats = Math.max(n_seats - totalFreeTickets, 0);
        }

        BigDecimal seatTotalPrice = seatPrice.multiply(new BigDecimal(n_seats));
        BigDecimal standTotalPrice = standPrice.multiply(new BigDecimal(n_stands));
        BigDecimal price = seatTotalPrice.add(standTotalPrice);

        BigDecimal discount = BigDecimal.ZERO;
        if (id_discount > 0) {
            discount = discountModel.getDiscountById(id_discount).getDiscount().divide(new BigDecimal(100), RoundingMode.HALF_UP);
        }
        return price.subtract(price.multiply(discount));
    }

    public BigDecimal calculatePriceNoFreeTickets(int id_event, int id_discount, int n_seats, int n_stands) throws SQLException{
        Event event = eventModel.getEventById(id_event);
        BigDecimal seatPrice = event.getSeatPrice();
        BigDecimal standPrice = event.getStandingPrice();

        BigDecimal seatTotalPrice = seatPrice.multiply(new BigDecimal(n_seats));
        BigDecimal standTotalPrice = standPrice.multiply(new BigDecimal(n_stands));
        BigDecimal price = seatTotalPrice.add(standTotalPrice);

        BigDecimal discount = BigDecimal.ZERO;
        if (id_discount > 0) {
            discount = discountModel.getDiscountById(id_discount).getDiscount().divide(new BigDecimal(100), RoundingMode.HALF_UP);
        }
        return price.subtract(price.multiply(discount));
    }

    public int calculateFreeTickets(int nPurchases,int nTickets){
        return ((nPurchases % 5) + nTickets )/5;
    }

    public List<Ticket> getAllUserTicket (int idUser) throws SQLException {
        getAllUserTicketPreparedStatement.setInt(1, idUser);
        ResultSet rs = getAllUserTicketPreparedStatement.executeQuery();
        List<Ticket> tickets = new ArrayList<>();
        while (rs.next()){
            int idPrenotazione = rs.getInt("id_prenotazione");
            int idEvento = rs.getInt("id_evento");
            int idUtente = rs.getInt("id_utente");

            int idSconto = rs.wasNull() ? -1 : rs.getInt("id_sconto");// Gestire id_sconto che potrebbe essere NULL

            int quantita_poltrona = rs.getInt("quantita_poltrona");
            int quantita_in_piedi = rs.getInt("quantita_in_piedi");
            String dataAcquisto = rs.getString("data_acquisto");
            BigDecimal prezzo = rs.getBigDecimal("prezzo");

            tickets.add(new Ticket(idPrenotazione, idEvento, idUtente, idSconto, quantita_poltrona, quantita_in_piedi, dataAcquisto, prezzo));
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

            int quantita_poltrona = rs.getInt("quantita_poltrona");
            int quantita_in_piedi = rs.getInt("quantita_in_piedi");
            String dataAcquisto = rs.getString("data_acquisto");
            BigDecimal prezzo = rs.getBigDecimal("prezzo");
            return new Ticket(idPrenotazione, idEvento, idUtente, idSconto, quantita_poltrona, quantita_in_piedi, dataAcquisto, prezzo);
        }
        return null;
    }

    public void deleteTicket(int id) throws SQLException {
        Ticket ticket = getTicket(id);
        if (ticket != null) {
            if (deleteTicketPreparedStatement.executeUpdate() > 0) {
                eventModel.incrementAvailability(ticket.idEvento(), ticket.quantita_poltrona(), ticket.quantita_in_piedi());
            }
        }
    }
}

