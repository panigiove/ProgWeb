package web.example.progweb.model;

import web.example.progweb.model.abstractClass.AbstractModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class AdminModel extends AbstractModel {
    private PreparedStatement checkAdminPreparedStatement;

    public AdminModel () throws SQLException, ClassNotFoundException {
        super();
    }

    public AdminModel(Connection connection)  throws SQLException{
        super(connection);
    }

    private void prepareStatement() throws SQLException{
        checkAdminPreparedStatement = connection.prepareStatement( "SELECT * FROM AMMINISTRATORI WHERE username = ? AND password = ?");
    }

    public boolean checkAdmin (String username, String password) throws SQLException{
        checkAdminPreparedStatement.setString(1, username);
        checkAdminPreparedStatement.setString(2, password);
        ResultSet resultSet = checkAdminPreparedStatement.executeQuery();
        return resultSet.next();
    }
}
