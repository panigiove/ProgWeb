package web.example.progweb.model.abstractClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;

/**
 * AbstractModel fornisce una base per i model del progetto, gestendo la connessione al database
 * e le operazioni di preparazione e esecuzione delle query.
 *
 * - Costruisce una connessione al database o utilizza una connessione esistente.
 * - Fornisce metodi protetti per eseguire query e preparare dichiarazioni SQL.
 * - Offre funzioni per formattare date e datetime.
 * - Include un metodo statico per creare una connessione al database.
 */
public abstract class AbstractModel {
    protected Connection connection;

    public AbstractModel() throws SQLException, ClassNotFoundException {
        String url = "jdbc:derby://localhost:1527/ProgWebDB";
        String user = "App";
        String password = "App";
        Class.forName("org.apache.derby.jdbc.ClientDriver"); // Caricamento del driver
        connection = DriverManager.getConnection(url, user, password); // Connessione al database
        prepareStatements();
    }

    public AbstractModel(Connection connection) throws SQLException {
        this.connection = connection;
        prepareStatements();
    }

    /**
     * Funzione che serve per prepare i vari PreparedStatement molto usati in questo progetto, chiamato nel costruttore
     * @throws SQLException
     */
    protected abstract void prepareStatements() throws SQLException;


    /**
     * Esegue query tramite la Statement e non PreparedStatement, per query semplici
     * @param query
     * @return risultato della query
     * @throws SQLException
     */
    public ResultSet unsafeExecuteQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public void close() throws SQLException {
        connection.close();
    }

    /**
     * Da formato format dd/mm/yyyy a yyyy-mm-dd (DATE)
     * @param date
     * @return data rielaborata
     */
    public String formatDate(String date) {
        String[] dateParts = date.split("/");
        return dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];
    }

    /**
     * Da formato format dd/mm/yyyyThh:mm:ss a  yyyy-mm-dd hh:mm:ss (DATETIME)
     * @param date
     * @return datatime rielaborata
     */
    public String formatDateTime(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, inputFormatter);
        return dateTime.format(outputFormatter);
    }

    /**
     * Per creare connessioni al DB senza istanziare un Model, utile per avere controllo sulla propria connessione
     * @return connessione al db
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection connectDB() throws SQLException, ClassNotFoundException {
        String url = "jdbc:derby://localhost:1527/ProgWebDB";
        String user = "App";
        String password = "App";
        Class.forName("org.apache.derby.jdbc.ClientDriver"); // Caricamento del driver
        return DriverManager.getConnection(url, user, password); // Connessione al database
    }

}
