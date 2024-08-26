package web.example.progweb.model;

import web.example.progweb.model.abstractClass.AbstractModel;
import web.example.progweb.model.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * UserModel gestisce tutte le operazioni riguardanti l'utente
 *
 * - Controllo sull'username se non è già presente nel database
 * - Controllo credenziali per il login
 * - Inserire nuovo User nel db
 * - Cancellare un utente
 * - Ottenere le informazioni di un utente
 * - Ottenere l'id di un user dato il suo username
 * - Ottenere il numero di acquisti
 * - Incrementare il numero di acquisti
 */
public class UserModel extends AbstractModel {
    private PreparedStatement checkUsernamePreparedStatement;
    private PreparedStatement insertUserPreparedStatement;
    private PreparedStatement deleteUserPreparedStatement;
    private PreparedStatement getUserPreparedStatement;
    private PreparedStatement getUserIdPreparedStatement;
    private PreparedStatement incrementPurchasesPreparedStatement;
    private PreparedStatement getPurchasePreparedStatement;
    private PreparedStatement checkIdPreparedStatement;
    private PreparedStatement anonimizzareBigliettiPreparedStatement;

    public UserModel() throws SQLException, ClassNotFoundException {
        super();
    }

    public UserModel(Connection connection) throws SQLException {
        super(connection);
    }

    protected void prepareStatements() throws SQLException {
        checkUsernamePreparedStatement = connection.prepareStatement("SELECT * FROM UTENTI WHERE username = ?");
        insertUserPreparedStatement = connection.prepareStatement("INSERT INTO UTENTI (nome, cognome, data_nascita, email, telefono, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        deleteUserPreparedStatement = connection.prepareStatement("DELETE FROM UTENTI WHERE id_utente = ?");
        anonimizzareBigliettiPreparedStatement = connection.prepareStatement("UPDATE PRENOTAZIONE_BIGLIETTI SET id_utente = -1 WHERE id_utente = ?");
        getUserPreparedStatement = connection.prepareStatement("SELECT * FROM UTENTI WHERE id_utente = ?");
        getUserIdPreparedStatement = connection.prepareStatement("SELECT id_utente FROM UTENTI WHERE username = ?");
        incrementPurchasesPreparedStatement = connection.prepareStatement("UPDATE UTENTI SET n_acquisti = n_acquisti + ? WHERE id_utente = ?");
        getPurchasePreparedStatement = connection.prepareStatement("SELECT n_acquisti FROM UTENTI WHERE id_utente = ?");
        checkIdPreparedStatement = connection.prepareStatement("SELECT * FROM UTENTI WHERE id_utente = ?");
    }

    /**
     *
     * @param username
     * @param password
     * @param name
     * @param surname
     * @param birthDate
     * @param email
     * @param phone
     * @return
     * @throws SQLException
     */
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

    /**
     * Cancella l'utente con username dato e anonimizza le righe biglietti ad esso collegato
     * @param username
     * @throws SQLException
     */
    public void deleteUser(String username) throws SQLException {
        int userId = getUserId(username);
        if (userId != -1) {
            deleteUserPreparedStatement.setInt(1, userId);
            deleteUserPreparedStatement.executeUpdate();
        }
    }

    /**
     * Controllo credenziali
     * @param username
     * @param password
     * @return True se esiste, False altrimenti
     * @throws SQLException
     */
    public boolean checkUser(String username, String password) throws SQLException {
        checkUsernamePreparedStatement.setString(1, username);
        ResultSet resultSet = checkUsernamePreparedStatement.executeQuery();
        return (resultSet.next() && password.equals("utente!01"));
    }

    /**
     * Controllo se username già preso
     * @param username
     * @return True se esiste, False altrimenti
     * @throws SQLException
     */
    public boolean checkUsername (String username) throws SQLException {
        checkUsernamePreparedStatement.setString(1, username);
        ResultSet resultSet = checkUsernamePreparedStatement.executeQuery();
        return resultSet.next();
    }

    /**
     * Controllo ID
     * @param id
     * @return True se esiste, False altrimenti
     * @throws SQLException
     */
    public boolean checkId (int id) throws SQLException {
        checkIdPreparedStatement.setInt(1, id);
        ResultSet resultSet = checkIdPreparedStatement.executeQuery();
        return resultSet.next();
    }

    public void incrementPurchases(int id, int nPurchases) throws SQLException {
        incrementPurchasesPreparedStatement.setInt(1, nPurchases);
        incrementPurchasesPreparedStatement.setInt(2, id);
        incrementPurchasesPreparedStatement.executeUpdate();
    }

    public User getUser(int id) throws SQLException {
        getUserPreparedStatement.setInt(1, id);
        ResultSet resultSet = getUserPreparedStatement.executeQuery();
        resultSet.next();
        return new User(resultSet.getInt("id_utente"), resultSet.getString("nome"), resultSet.getString("cognome"), resultSet.getString("data_nascita"), resultSet.getString("email"), resultSet.getString("telefono"), resultSet.getInt("n_acquisti"), resultSet.getString("username"));
    }

    /**
     * Ottenere l'id dato un username
     * @param username
     * @return -1 se non esiste altrimenti l'id
     * @throws SQLException
     */
    public int getUserId(String username) throws SQLException {
        getUserIdPreparedStatement.setString(1, username);
        try (ResultSet resultSet = getUserIdPreparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("id_utente");
            } else {
                return -1;
            }
        }
    }

    /**
     * Ottenere la lista degli utenti dal db
     * @return
     * @throws SQLException
     */
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

    /**
     * Ottenere la lista degli utenti ordinati per acquisti
     * @return
     * @throws SQLException
     */
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

    @Override
    public void close() throws SQLException {
        try {
            checkUsernamePreparedStatement.close();
            insertUserPreparedStatement.close();
            deleteUserPreparedStatement.close();
            anonimizzareBigliettiPreparedStatement.close();
            getUserPreparedStatement.close();
            getUserIdPreparedStatement.close();
            incrementPurchasesPreparedStatement.close();
            getPurchasePreparedStatement.close();
            checkIdPreparedStatement.close();
        } finally {
            super.close();
        }
    }
}
