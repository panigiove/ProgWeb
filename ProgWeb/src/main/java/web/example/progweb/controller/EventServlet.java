package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.DiscountModel;
import web.example.progweb.model.EventModel;
import web.example.progweb.model.TicketModel;
import web.example.progweb.model.UserModel;
import web.example.progweb.model.entity.Discount;
import web.example.progweb.model.entity.Event;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

@WebServlet(name="EventServlet", urlPatterns = "/event/*")
public class EventServlet extends AbstractController {
    private EventModel eventModel;
    private DiscountModel discountModel;
    private UserModel userModel;
    private TicketModel ticketModel;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.eventModel = new EventModel(connection);
            this.discountModel = new DiscountModel(connection);
            userModel = new UserModel(connection);
            ticketModel = new TicketModel(connection);
        } catch ( SQLException e) {
            System.err.println("Errore durante la connessione al database" + e.getMessage());
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String path = req.getPathInfo();
            String eventId = req.getParameter("eventId");
            if(eventId != null) {
                int id = Integer.parseInt(eventId);
                Event event = eventModel.getEventById(id);
                List<Discount> discounts = discountModel.getValidDiscountsOfEvent(id);
                req.setAttribute("event", event);
                req.setAttribute("discounts", discounts);
                if(path == null || "/".equals(path)){
                    eventModel.incrementClick(event.getId());
                    req.getRequestDispatcher("/WEB-INF/view/eventInformation.jsp").forward(req, resp);
                }else if ("/buyTicketForm".equals(path)){
                    req.getRequestDispatcher("/WEB-INF/view/buyTicket.jsp").forward(req, resp);
                }else {
                    sendErrorPage(req, resp, "page not founded", HttpServletResponse.SC_NOT_FOUND, "Not Found");
                }
            }else{
                sendErrorPage(req, resp, "Missing Event ID", HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
            }

        } catch (SQLException e) {
            System.err.println("Errore relativo al SQL" + e.getMessage());
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            System.out.println("username " + req.getParameter("username"));
            Event event = eventModel.getEventById(Integer.parseInt(req.getParameter("event")));
            int idUser = userModel.getUserId(req.getParameter("username"));
            String username = req.getParameter("username");
            int nSeats = Integer.parseInt(req.getParameter("nSeats"));
            int nStands = Integer.parseInt(req.getParameter("nStands"));
            int idDiscount = -1;

            if (!req.getParameter("idDiscount").equals("")){
                idDiscount = Integer.parseInt(req.getParameter("idDiscount"));
                req.setAttribute("discount", discountModel.getDiscountById(idDiscount));
            }else req.setAttribute("discount", null);


            if (nSeats == 0 && nStands ==0){
                sendErrorPage(req, resp, "", HttpServletResponse.SC_BAD_REQUEST, "BAD REQUEST");
                return;
            }

            if ("/buyTicket".equals(path)) {
                BigDecimal price = ticketModel.calculatePrice(event.getId(), idUser, idDiscount, nSeats, nStands);
                req.setAttribute("events", event);
                req.setAttribute("nSeats", nSeats);
                req.setAttribute("nStands", nStands);
                req.setAttribute("username", username);
                req.setAttribute("price", new DecimalFormat("0.00").format(price));
                req.setAttribute("isFree", (userModel.getPurchases(idUser)+1) % 5== 0);
                req.getRequestDispatcher("/WEB-INF/view/orderConfirm.jsp").forward(req, resp);
            } else if ("/buyTicketConferm".equals(path)){
                System.out.println(event.getId() + " " + idUser + " " + idDiscount + " " + nSeats + " " + nStands);
                if (ticketModel.buyTicket(event.getId(), idUser, idDiscount, nSeats, nStands) != null){
                    req.setAttribute("status", "success");
                    req.getRequestDispatcher("/WEB-INF/view/successPayment.jsp").forward(req, resp);
                }else{
                    req.setAttribute("status", "failed");
                    req.getRequestDispatcher("/WEB-INF/view/successPayment.jsp").forward(req, resp);
                }
            }else sendErrorPage(req, resp, "", HttpServletResponse.SC_NOT_FOUND, "NOT FOUND");

        } catch (NumberFormatException e) {
            System.err.println("Error during event insertion: " + e.getMessage());
            sendErrorPage(req, resp, "Bad Request", HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database" + e.getMessage());
            throw new ServletException(e);
        }
    }
}
