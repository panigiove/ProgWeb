package web.example.progweb.model;

import web.example.progweb.model.abstractClass.AbstractModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * AdminModel gestisce le operazioni strettamente relative agli amministratori.
 *
 * - Fornisce metodi per verificare le credenziali dell'amministratore durante il login.
 */
public class AdminModel extends AbstractModel {
    private PreparedStatement checkAdminPreparedStatement;

    public AdminModel () throws SQLException, ClassNotFoundException {
        super();
    }

    public AdminModel(Connection connection)  throws SQLException{
        super(connection);
    }

    protected void prepareStatements() throws SQLException{
        checkAdminPreparedStatement = connection.prepareStatement( "SELECT * FROM AMMINISTRATORI WHERE username = ? AND password = ?");
    }

    /**
     * Controlla le credenziali
     * @param username
     * @param password
     * @return True se esiste, False altrimenti
     * @throws SQLException
     */
    public boolean checkAdmin (String username, String password) throws SQLException{
        checkAdminPreparedStatement.setString(1, username);
        checkAdminPreparedStatement.setString(2, password);
        ResultSet resultSet = checkAdminPreparedStatement.executeQuery();
        return resultSet.next();
    }
}
