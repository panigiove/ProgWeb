package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.EventModel;
import web.example.progweb.model.TicketModel;
import web.example.progweb.model.UserModel;
import web.example.progweb.model.entity.Event;
import web.example.progweb.model.entity.Ticket;
import web.example.progweb.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="UserPage", value="/personalArea")
public class UserPage extends AbstractController {
    private UserModel userModel;
    private TicketModel ticketModel;
    private EventModel eventModel;
    private User currentUser;
    private List<Ticket> userTickets;

    @Override
    public void init() throws ServletException {
        super.init();
        try{
            this.userModel = new UserModel(connection);
            this.ticketModel = new TicketModel(connection);
            this.eventModel = new EventModel(connection);
        } catch (SQLException e){
            System.err.println("Errore durante la connessione al database" + e.getMessage());
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        try {
            int userId = userModel.getUserId(username);
            this.currentUser = userModel.getUser(userId);
            this.userTickets = ticketModel.getAllUserTicket(userId);
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database" + e.getMessage());
            throw new ServletException(e);
        }
        req.setAttribute("currentUser", currentUser);
        req.setAttribute("userTickets", userTickets);
        req.setAttribute("eventModel", eventModel);
        req.getRequestDispatcher("/WEB-INF/view/personalPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        if("/deleteAccount".equals(path)){
            try {
                userModel.deleteUser(username);
                session.invalidate();
                req.getRequestDispatcher(req.getContextPath()+"/index").forward(req, resp);
            } catch (SQLException e) {
                System.err.println("Errore durante la connessione al database" + e.getMessage());
                throw new ServletException(e);
            }
        }
    }
}
