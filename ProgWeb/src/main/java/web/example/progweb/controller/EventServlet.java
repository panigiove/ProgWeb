package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.EventModel;
import web.example.progweb.model.entity.Event;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="EventServlet", urlPatterns = "/event/*")
public class EventServlet extends AbstractController {
    private EventModel eventModel;

    @Override
    public void init() throws ServletException {
        try {
            this.eventModel = new EventModel();
        } catch (ClassNotFoundException | SQLException e) {
            if(e instanceof ClassNotFoundException){
                System.err.println("Errore, classe non trovata" + e.getMessage());
                throw new ServletException(e);
            }
            else{
                System.err.println("Errore durante la connessione al database" + e.getMessage());
                throw new ServletException(e);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int eventId = Integer.parseInt(req.getParameter("eventId"));
            Event event = eventModel.getEventById(eventId);
            req.setAttribute("event", event);
            req.getRequestDispatcher("/WEB-INF/view/eventInformation.jsp").forward(req, resp);
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database" + e.getMessage());
            throw new ServletException(e);
        }
    }
}
