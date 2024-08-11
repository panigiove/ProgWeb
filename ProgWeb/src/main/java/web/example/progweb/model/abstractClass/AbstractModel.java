package web.example.progweb.model.abstractClass;

import javax.ws.rs.container.ConnectionCallback;
import java.sql.*;

public abstract class AbstractModel {
    protected Connection connection;
    protected Statement statement;

    public AbstractModel() throws SQLException, ClassNotFoundException {
        String url = "jdbc:derby://localhost:1527/ProgWebDB";
        String user = "App";
        String password = "App";
        Class.forName("org.apache.derby.jdbc.ClientDriver"); // Caricamento del driver
        connection = DriverManager.getConnection(url, user, password); // Connessione al database
        statement = connection.createStatement();
    }

    public AbstractModel(Connection connection) throws SQLException {
        this.connection = connection;
        statement = connection.createStatement();
    }

    public ResultSet unsafeExecuteQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public void close() throws SQLException {
        connection.close();
    }

//  expect input in the format dd/mm/yyyy and return in the format yyyy-mm-dd
    public String formatDate(String date) {
        String[] dateParts = date.split("/");
        return dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];
    }

//  expect input in the format dd/mm/yyyy hh:mm:ss and return in the format yyyy-mm-dd hh:mm:ss
    public String formatDateTime(String date) {
        String[] dateParts = date.split(" ");
        String[] dateParts2 = dateParts[0].split("/");
        return dateParts2[2] + "-" + dateParts2[1] + "-" + dateParts2[0] + " " + dateParts[1];
    }

    public static Connection connectDB() throws SQLException, ClassNotFoundException {
        String url = "jdbc:derby://localhost:1527/ProgWebDB";
        String user = "App";
        String password = "App";
        Class.forName("org.apache.derby.jdbc.ClientDriver"); // Caricamento del driver
        return DriverManager.getConnection(url, user, password); // Connessione al database
    }

}
