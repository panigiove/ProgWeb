package web.example.progweb.model;

import web.example.progweb.model.abstractClass.AbstractModel;
import web.example.progweb.model.entity.Discount;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DiscountModel gestisce le operazioni relative agli sconti.
 *
 * Fornisce metodi per creare, cancellare, ottenere sconti per evento e ottenere tutti gli sconti validi.
 *
 * Classe Discount per passare i risultati delle query
 */
public class DiscountModel extends AbstractModel {

    private PreparedStatement getValidDiscountsOfEventPreparedStatement;
    private PreparedStatement getDiscountByIdPreparedStatement;
    private PreparedStatement deleteDiscountPreparedStatement;
    private PreparedStatement createDiscountPreparedStatement;

    private EventModel eventModel;

    public DiscountModel() throws SQLException, ClassNotFoundException {
        super();
        eventModel = new EventModel(connection);
    }

    public DiscountModel(Connection connection) throws SQLException{
        super(connection);
        eventModel = new EventModel(connection);
    }

    protected void prepareStatements() throws  SQLException {
        getDiscountByIdPreparedStatement = connection.prepareStatement("SELECT * FROM SCONTI_EVENTO WHERE id_sconto = ?");
        deleteDiscountPreparedStatement = connection.prepareStatement("DELETE FROM SCONTI_EVENTO WHERE id_sconto = ?");
        createDiscountPreparedStatement = connection.prepareStatement("INSERT INTO SCONTI_EVENTO (id_evento, data_scadenza, sconto) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        getValidDiscountsOfEventPreparedStatement = connection.prepareStatement("SELECT * FROM SCONTI_EVENTO WHERE id_evento = ? AND data_scadenza >= CURRENT_DATE");
    }

    public boolean checkId(int id) throws SQLException {
        getDiscountByIdPreparedStatement.setInt(1, id);
        ResultSet resultSet = getDiscountByIdPreparedStatement.executeQuery();
        return resultSet.next();
    }

    public Discount getDiscountById(int id) throws SQLException {
        getDiscountByIdPreparedStatement.setInt(1, id);
        ResultSet resultSet = getDiscountByIdPreparedStatement.executeQuery();
        Discount discount = null;
        if (resultSet.next()) {
            String nomeEvento = eventModel.getEventName(resultSet.getInt("id_evento"));
            discount = new Discount(
                    resultSet.getInt("id_sconto"),
                    resultSet.getInt("id_evento"),
                    nomeEvento,
                    resultSet.getString("data_scadenza"),
                    resultSet.getBigDecimal("sconto")
            );
        }
        return discount;
    }

    public List<Discount> getValidDiscountsOfEvent(int idEvent) throws SQLException {
        List<Discount> discounts = new ArrayList<>();

        getValidDiscountsOfEventPreparedStatement.setInt(1, idEvent);
        try (ResultSet rs = getValidDiscountsOfEventPreparedStatement.executeQuery()) {
            while (rs.next()) {
                String nomeEvento = eventModel.getEventName(rs.getInt("id_evento"));
                Discount discount = new Discount(
                        rs.getInt("id_sconto"),
                        rs.getInt("id_evento"),
                        nomeEvento,
                        rs.getString("data_scadenza"),
                        rs.getBigDecimal("sconto")
                );
                discounts.add(discount);
            }
        }
        return discounts;
    }

    public void deleteDiscount(int id) throws SQLException {
        Discount discount = getDiscountById(id);
        if (discount != null) {
            deleteDiscountPreparedStatement.setInt(1, id);
            deleteDiscountPreparedStatement.executeUpdate();
        }
    }

    public Discount createDiscount(int id_event, String expiration_date, BigDecimal discount) throws SQLException {
        createDiscountPreparedStatement.setInt(1, id_event);
        createDiscountPreparedStatement.setString(2, expiration_date);
        createDiscountPreparedStatement.setBigDecimal(3, discount);
        int affectedRows = createDiscountPreparedStatement.executeUpdate();
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = createDiscountPreparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return getDiscountById(generatedKeys.getInt(1));
                }
            }
        }
        return null;
    }

    public List<Discount> getValidDiscounts() throws SQLException {
        List<Discount> discounts = new ArrayList<>();
        String query = "SELECT * FROM SCONTI_EVENTO WHERE data_scadenza >= CURRENT_DATE";
        ResultSet rs = unsafeExecuteQuery(query);
        while (rs.next()) {
            String nomeEvento = eventModel.getEventName(rs.getInt("id_evento"));
            Discount discount = new Discount(
                    rs.getInt("id_sconto"),
                    rs.getInt("id_evento"),
                    nomeEvento,
                    rs.getString("data_scadenza"),
                    rs.getBigDecimal("sconto")
            );
            discounts.add(discount);
        }
        return discounts;
    }
}
