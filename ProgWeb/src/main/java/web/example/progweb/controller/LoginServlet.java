package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.AdminModel;
import web.example.progweb.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name="LoginServlet", value="/LoginServlet")
public class LoginServlet extends AbstractController {

    private UserModel userModel;
    private AdminModel adminModel;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            userModel = new UserModel(connection);
            adminModel = new AdminModel(connection);
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/logIn.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // recupero sessione e parametri
        HttpSession session = request.getSession(false); // se non esiste ritorna a null
        try {
            if (session == null) { // non autenticato precedentemente
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                if (username != null && password != null) {
                    session = request.getSession(true); // creazione sessione
                    if (adminModel.checkAdmin(username, password)) { // verifico se è un admin
                        session.setAttribute("username", username);
                        session.setAttribute("isAdmin", true);
                    } else if (userModel.checkUser(username, password)) { // verifico se è un utente
                        session.setAttribute("username", username);
                        session.setAttribute("isAdmin", false);
                    } else { // credenziali sbagliate
                        session.invalidate();
                        sendErrorMessage(request, response, "Credenziali non corrette, riprova", 401, "Unauthorized");
                        request.getRequestDispatcher("/logIn.jsp").forward(request, response);
                    }
                } else { // formato request errato
                    sendErrorMessage(request, response, "username e password non presenti", 400, "Bad request");
                }
            }else { // precedentemente autenticato
                Boolean isAdmin = (Boolean)session.getAttribute("isAdmin");
                if (isAdmin != null && isAdmin) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                }else {
                    response.sendRedirect(request.getContextPath()+"/index.jsp");
                }
            }
        }catch (SQLException e) {
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database", e);
        }
    }
}
