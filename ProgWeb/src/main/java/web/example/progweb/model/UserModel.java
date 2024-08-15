package web.example.progweb.model;

import web.example.progweb.model.abstractClass.AbstractModel;
import web.example.progweb.model.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
CREATE TABLE UTENTI (
    id_utente INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    data_nascita DATE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telefono CHAR(10) NOT NULL,
    n_acquisti INT DEFAULT 0,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
*/

public class UserModel extends AbstractModel {
    private PreparedStatement checkUserPreparedStatement;
    private PreparedStatement checkUsernamePreparedStatement;
    private PreparedStatement insertUserPreparedStatement;
    private PreparedStatement deleteUserPreparedStatement;
    private PreparedStatement getUserPreparedStatement;
    private PreparedStatement getUserIdPreparedStatement;
    private PreparedStatement incrementPurchasesPreparedStatement;
    private PreparedStatement getPurchasePreparedStatement;
    private PreparedStatement checkIdPreparedStatement;

    public UserModel() throws SQLException, ClassNotFoundException {
        super();
        prepareStatements();
    }

    public UserModel(Connection connection) throws SQLException {
        super(connection);
        prepareStatements();
    }

    private void prepareStatements() throws SQLException {
        checkUserPreparedStatement = connection.prepareStatement("SELECT * FROM UTENTI WHERE username = ? AND password = ?");
        checkUsernamePreparedStatement = connection.prepareStatement("SELECT * FROM UTENTI WHERE username = ?");
        insertUserPreparedStatement = connection.prepareStatement("INSERT INTO UTENTI (nome, cognome, data_nascita, email, telefono, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        deleteUserPreparedStatement = connection.prepareStatement("DELETE FROM UTENTI WHERE username = ?");
        getUserPreparedStatement = connection.prepareStatement("SELECT * FROM UTENTI WHERE id_utente = ?");
        getUserIdPreparedStatement = connection.prepareStatement("SELECT id_utente FROM UTENTI WHERE username = ?");
        incrementPurchasesPreparedStatement = connection.prepareStatement("UPDATE UTENTI SET n_acquisti = n_acquisti + 1 WHERE id_utente = ?");
        getPurchasePreparedStatement = connection.prepareStatement("SELECT n_acquisti FROM UTENTI WHERE id_utente = ?");
        checkIdPreparedStatement = connection.prepareStatement("SELECT * FROM UTENTI WHERE id_utente = ?");
    }

    public User insertUser(String username, String password, String name, String surname, String birthDate, String email, String phone) throws SQLException {
        insertUserPreparedStatement.setString(1, name);
        insertUserPreparedStatement.setString(2, surname);
        insertUserPreparedStatement.setString(3, formatDate(birthDate));
        insertUserPreparedStatement.setString(4, email);
        insertUserPreparedStatement.setString(5, phone);
        insertUserPreparedStatement.setString(6, username);
        insertUserPreparedStatement.setString(7, password);
        int affectedRows =  insertUserPreparedStatement.executeUpdate();
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = insertUserPreparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return getUser(generatedKeys.getInt(1));
                }
            }
        }
        return null;
    }

    public void deleteUser(String username) throws SQLException {
        deleteUserPreparedStatement.setString(1, username);
        deleteUserPreparedStatement.executeUpdate();
    }

    public boolean checkUser(String username, String password) throws SQLException {
        checkUserPreparedStatement.setString(1, username);
        checkUserPreparedStatement.setString(2, password);
        ResultSet resultSet = checkUserPreparedStatement.executeQuery();
        return resultSet.next();
    }

    public boolean checkUsername (String username) throws SQLException {
        checkUsernamePreparedStatement.setString(1, username);
        ResultSet resultSet = checkUsernamePreparedStatement.executeQuery();
        return resultSet.next();
    }

    public boolean checkId (int id) throws SQLException {
        checkIdPreparedStatement.setInt(1, id);
        ResultSet resultSet = checkIdPreparedStatement.executeQuery();
        return resultSet.next();
    }

    public void incrementPurchases(int id) throws SQLException {
        incrementPurchasesPreparedStatement.setInt(1, id);
        incrementPurchasesPreparedStatement.executeUpdate();
    }

    public User getUser(int id) throws SQLException {
        getUserPreparedStatement.setInt(1, id);
        ResultSet resultSet = getUserPreparedStatement.executeQuery();
        resultSet.next();
        return new User(resultSet.getInt("id_utente"), resultSet.getString("nome"), resultSet.getString("cognome"), resultSet.getString("data_nascita"), resultSet.getString("email"), resultSet.getString("telefono"), resultSet.getInt("n_acquisti"), resultSet.getString("username"));
    }

    public int getUserId(String username) throws SQLException {
        getUserIdPreparedStatement.setString(1, username);
        ResultSet resultSet = getUserIdPreparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id_utente");
    }

    public List<User> getUsers() throws SQLException {
        String query = "SELECT * FROM UTENTI";
        ResultSet resultSet = unsafeExecuteQuery(query);
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User(
                    resultSet.getInt("id_utente"),
                    resultSet.getString("nome"),
                    resultSet.getString("cognome"),
                    resultSet.getString("data_nascita"),
                    resultSet.getString("email"),
                    resultSet.getString("telefono"),
                    resultSet.getInt("n_acquisti"),
                    resultSet.getString("username")
            );
            users.add(user);
        }
        return users;
    }

    public List<User> getUsersOrderedByPurchase () throws SQLException {
        String query = "SELECT * FROM UTENTI ORDER BY n_acquisti DESC";
        ResultSet resultSet = unsafeExecuteQuery(query);
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User(
                    resultSet.getInt("id_utente"),
                    resultSet.getString("nome"),
                    resultSet.getString("cognome"),
                    resultSet.getString("data_nascita"),
                    resultSet.getString("email"),
                    resultSet.getString("telefono"),
                    resultSet.getInt("n_acquisti"),
                    resultSet.getString("username")
            );
            users.add(user);
        }
        return users;
    }

    public int getPurchases(int id) throws SQLException {
        getPurchasePreparedStatement.setInt(1, id);
        ResultSet resultSet = getPurchasePreparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("n_acquisti");
    }
}
