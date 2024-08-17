package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.DiscountModel;
import web.example.progweb.model.EventModel;
import web.example.progweb.model.entity.Discount;
import web.example.progweb.model.entity.Event;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name="EventServlet", urlPatterns = "/event/*")
public class EventServlet extends AbstractController {
    private EventModel eventModel;
    private DiscountModel discountModel;

    @Override
    public void init() throws ServletException {
        try {
            this.eventModel = new EventModel();
            this.discountModel = new DiscountModel();
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
            List<Discount> discounts = discountModel.getValidDiscountsOfEvent(eventId);
            System.out.println(event);
            System.out.println(discounts);
            req.setAttribute("event", event);
            req.setAttribute("discounts", discounts);
            req.getRequestDispatcher("/WEB-INF/view/eventInformation.jsp").forward(req, resp);
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database" + e.getMessage());
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if("/buyTicket".equals(path)){
            try {
                int eventId = (int) req.getAttribute("eventId");
                Event event = eventModel.getEventById(eventId);
                List<Discount> discounts = discountModel.getValidDiscountsOfEvent(eventId);
                req.setAttribute("event", event);
                req.setAttribute("discounts", discounts);
                req.getRequestDispatcher("/WEB-INF/view/buyTicket.jsp").forward(req, resp);
            } catch (SQLException e) {
                System.err.println("Errore durante la connessione al database" + e.getMessage());
                throw new ServletException(e);
            }
        }
    }
}
