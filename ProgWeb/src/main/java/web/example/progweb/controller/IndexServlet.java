package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.DiscountModel;
import web.example.progweb.model.EventModel;
import web.example.progweb.model.entity.Category;
import web.example.progweb.model.entity.Discount;
import web.example.progweb.model.entity.Event;

import com.google.gson.Gson;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "IndexServlet", urlPatterns = {"/", "/user/*"})
public class IndexServlet extends AbstractController {
    private EventModel eventModel;
    private DiscountModel discountModel;

    public void init() throws ServletException {
        super.init();
        try {
            this.discountModel = new DiscountModel(connection);
            this.eventModel = new EventModel(connection);
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String path = request.getPathInfo();
            if ("/getEvents".equals(path))
            {
                String categoryIdParam = request.getParameter("categoryId");
                if (categoryIdParam != null){
                    try{
                        int categoryId = Integer.parseInt(categoryIdParam);
                        if (eventModel.checkCategoryId(categoryId)){
                            sendJsonMessage(response, new Gson().toJson(eventModel.getEventByCategory(categoryId)));
                        }else {
                            sendErrorMessage(request, response, "Categoria non trovata", HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
                        }
                    }catch (NumberFormatException e){
                        sendErrorMessage(request, response, "Formato ID categoria non valido", HttpServletResponse.SC_BAD_REQUEST, "Invalid Category ID");
                    }
                }else{
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }
            }else if ("/getDiscounts".equals(path)){
                System.out.println(new Gson().toJson(discountModel.getValidDiscounts()));
                sendJsonMessage(response, new Gson().toJson(discountModel.getValidDiscounts()));
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            sendErrorMessage(request,response,"Errore durante il recupero dei scounti", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
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
                    sendErrorMessage(request, response, "Bad request", HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
                } else {
                    List<Category> categories = eventModel.getAllCategory();
                    List<Event> events = eventModel.getEventByCategory(categoryId);
                    request.setAttribute("events", events);
                    request.setAttribute("categories", categories);
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
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