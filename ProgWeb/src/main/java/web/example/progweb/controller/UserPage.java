package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.EventModel;
import web.example.progweb.model.TicketModel;
import web.example.progweb.model.UserModel;
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
import java.util.List;

@WebServlet(name="UserPage", value="/personalArea")
public class UserPage extends AbstractController {
    private UserModel userModel;
    private TicketModel ticketModel;
    private User currentUser;
    private List<Ticket> userTickets;

    @Override
    public void init() throws ServletException {
        super.init();
        try{
            this.userModel = new UserModel(connection);
            this.ticketModel = new TicketModel(connection);
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
        req.getRequestDispatcher("/WEB-INF/views/personalPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
