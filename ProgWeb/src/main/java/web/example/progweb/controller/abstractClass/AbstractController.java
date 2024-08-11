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

public abstract class AbstractController extends HttpServlet {
    protected Connection connection;
    @Override
    public void init() throws ServletException {
        // Initialization code, if needed
        super.init();
        try{
            connection = AbstractModel.connectDB();
        } catch (SQLException | ClassNotFoundException e) {
            // Log dell'errore e rilancio dell'eccezione
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database", e);
        }
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Default implementation or leave it abstract if subclasses must implement it
        // This can be overridden in subclasses
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Default implementation or leave it abstract if subclasses must implement it
        // This can be overridden in subclasses
    }

    protected void sendErrorMessage(HttpServletRequest request, HttpServletResponse response, String message, int errorCode) throws IOException {
        response.setContentType("text/plain");
        response.setStatus(errorCode);
        try (PrintWriter out = response.getWriter()){
            out.println(message);
        }
    }

    protected void sendErrorPage(HttpServletRequest request, HttpServletResponse response, String message, int errorCode) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.setAttribute("errorCode", errorCode);
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
    }

}
