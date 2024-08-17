package web.example.progweb.controller.abstractClass;

import web.example.progweb.model.abstractClass.AbstractModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * AbstractController fornisce un template per le servlet del progetto.
 *
 * - Gestisce la connessione al database, creando e chiudendo la connessione al momento dell'istanza e distruzione della servlet.
 * - Offre metodi per inviare pagine di errore, messaggi di errore e risposte JSON al client.
 */
public abstract class AbstractController extends HttpServlet {
    protected Connection connection;

    /**
     * Crea una connessione al DB all'istanza della Servlet-> il progetto è pensato all'utilizzo di una singola connessione a Servlet
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();
        try{
            connection = AbstractModel.connectDB();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database", e);
        }
    }

    /**
     * Chiusura della connessione alla distruzione della Servlet
     */
    @Override
    public void destroy() {
        super.destroy();
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invia una pagina di errore al client
     * @param request
     * @param response
     * @param message messaggio
     * @param errorCode il codice dell'errore
     * @param errorString il messaggio dell'errore
     * @throws ServletException
     * @throws IOException
     */
    protected void sendErrorPage(HttpServletRequest request, HttpServletResponse response, String message, int errorCode, String errorString) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.setAttribute("errorCode", errorCode);
        request.setAttribute("errorString", errorString);
        request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
    }

    /**
     * Invia un messaggio di errore al client
     * @param request
     * @param response
     * @param message messaggio
     * @param errorCode il codice dell'errore
     * @param errorString il messaggio dell'errore
     * @throws IOException
     */
    protected void sendErrorMessage(HttpServletRequest request, HttpServletResponse response, String message, int errorCode, String errorString) throws IOException {
        response.setContentType("text/plain");
        response.setStatus(errorCode);
        try (PrintWriter out = response.getWriter()){
            out.println(errorString+ ": "+message);
        }
    }

    /**
     * Invio di un messaggio JSON al client
     * @param response
     * @param json stringa contenente un JSON
     * @throws IOException
     */
    protected void sendJsonMessage(HttpServletResponse response, String json) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.print(json);
            out.flush();
        }
    }
}
