package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.EventModel;
import web.example.progweb.model.entity.Event;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.util.List;

@WebServlet(name = "adminSevlet", value = "/admin/*")
public class AdminServlet extends AbstractController {
    private EventModel eventModel;
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            eventModel = new EventModel(connection);
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database" + e.getMessage());
            throw new ServletException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String path = request.getPathInfo();
            if (path == null || "/".equals(path)) {
                List<Event> events;
                String sortByClicks = request.getParameter("sortByClicks");
                if ("on".equals(sortByClicks)) {
                    events = eventModel.getEventsOrderedByClick();
                }
                else {
                    events = eventModel.getEvents();
                }
                request.setAttribute("events", events);

                request.getRequestDispatcher("eventManager.jsp").forward(request, response);
            } else if ("/gestioneEventi".equals(path)) {
                request.getRequestDispatcher("WEB-INF/view/eventManager.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            sendErrorPage(request, response,"Errore durante la ricerca di eventi", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");

        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("/deleteEvent".equals(request.getPathInfo())) {
            handlePostDeleteEvent(request,response);
        }
    }

    private void handlePostDeleteEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        try{
            if (!eventModel.checkId(eventId)){
                sendErrorMessage(request,response,"Invalid EventId", HttpServletResponse.SC_BAD_REQUEST, "Bad Event ID");
            }else{
                eventModel.deleteEvent(eventId);
            }
            sendSuccessMessage(request,response,"evento cancellato");
        } catch (SQLException e) {
            sendErrorMessage(request, response, "Errore durante la cancellazione degli eventi", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }


}
