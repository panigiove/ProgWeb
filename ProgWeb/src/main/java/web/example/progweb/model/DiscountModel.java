package web.example.progweb.model;

import web.example.progweb.model.abstractClass.AbstractModel;
import web.example.progweb.model.entity.Discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountModel extends AbstractModel {

    private PreparedStatement getValidDiscountsOfEventPreparedStatement;
    private PreparedStatement getDiscountByIdPreparedStatement;
    private PreparedStatement deleteDiscountPreparedStatement;
    private PreparedStatement createDiscountPreparedStatement;

    public DiscountModel() throws SQLException, ClassNotFoundException {
        super();
        prepareStatements();
    }

    public DiscountModel(Connection connection) throws SQLException{
        super(connection);
        prepareStatements();
    }

    private void prepareStatements() throws  SQLException {
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
            discount = new Discount(
                    resultSet.getInt("id_sconto"),
                    resultSet.getInt("id_evento"),
                    resultSet.getString("data_scadenza"),
                    resultSet.getBigDecimal("sconto")
            );
        }
        return discount;
    }

    public List<Discount> getValidDiscountsOfEvent(int idEvent) throws SQLException {
        List<Discount> discounts = new ArrayList<>();

        // Use the prepared statement to get valid discounts for the event
        try (PreparedStatement statement = getValidDiscountsOfEventPreparedStatement) {
            statement.setInt(1, idEvent);  // Set the event ID parameter in the prepared statement

            // Execute the query and process the results
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    // Assuming the Discount class has a constructor that takes all relevant columns from the SCONTI_EVENTO table
                    Discount discount = new Discount(
                            rs.getInt("id_sconto"),
                            rs.getInt("id_evento"),
                            rs.getString("data_scadenza"),
                            rs.getBigDecimal("sconto")
                    );
                    discounts.add(discount);
                }
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

    public Discount createDiscount(int id_event, String expiration_date, double discount) throws SQLException {
        createDiscountPreparedStatement.setInt(1, id_event);
        createDiscountPreparedStatement.setString(2, expiration_date);
        createDiscountPreparedStatement.setDouble(3, discount);
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
            Discount discount = new Discount(
                    rs.getInt("id_sconto"),
                    rs.getInt("id_evento"),
                    rs.getString("data_scadenza"),
                    rs.getBigDecimal("sconto")
            );
            discounts.add(discount);
        }
        return discounts;
    }
}
