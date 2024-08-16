package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.EventModel;
import web.example.progweb.model.UserModel;
import web.example.progweb.model.entity.Event;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "IndexServlet", value = "/index/*")
public class IndexServlet extends AbstractController {
    private EventModel eventModel;

    public void init() throws ServletException {
        super.init();
        try {
            this.eventModel = new EventModel(connection);
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        int categoryId;

        if("/updateCards".equals(path)){
            try{
                categoryId = Integer.parseInt(request.getParameter("categoryId"));
            } catch (NumberFormatException e) {
                sendErrorMessage(request, response, "Invalid Event ID format", HttpServletResponse.SC_BAD_REQUEST, "Invalid Event ID");
                return;
            }
            try{
                if (!eventModel.checkCategoryId(categoryId)) {
                    sendJsonMessage(response, "{\"success\": \"fail\"}");
                } else {
                    List<Event> events = eventModel.getEventByCategory(categoryId);
                    request.setAttribute("events", events);
                    request.getRequestDispatcher("/WEB-INF/view/eventsNavbar.jsp").forward(request, response);
                    sendJsonMessage(response, "{\"success\": \"success\"}");
                }
            } catch (SQLException e){
                System.err.println("Error during event deletion: " + e.getMessage());
                sendErrorPage(request, response, "Error updating Cards", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            }
        }
        else{
            sendErrorPage(request, response, "Path not found", HttpServletResponse.SC_NOT_FOUND, "Not Found");
        }
    }

    public void destroy() {
    }
}