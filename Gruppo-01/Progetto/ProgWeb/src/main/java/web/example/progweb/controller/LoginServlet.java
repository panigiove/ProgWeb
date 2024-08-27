package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.AdminModel;
import web.example.progweb.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name="LoginServlet", value="/login")
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
        HttpSession session = request.getSession(true);
        request.getRequestDispatcher("/WEB-INF/view/logIn.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Boolean isAdmin = (session != null) ? (Boolean) session.getAttribute("isAdmin") : null;
        Boolean isLogged = (session != null) ? (Boolean) session.getAttribute("isLogged") : null;

        try {
            if (session != null && Boolean.TRUE.equals(isLogged)) {
                if (Boolean.TRUE.equals(isAdmin)) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                } else {
                    response.sendRedirect(request.getContextPath() + "/");
                }
            } else {
                session = request.getSession(true);

                String username = request.getParameter("username");
                String password = request.getParameter("password");

                if (username != null && password != null) {
                    if (adminModel.checkAdmin(username, password)) {
                        session.setAttribute("isLogged", true);
                        session.setAttribute("isAdmin", true);
                        response.sendRedirect(request.getContextPath() + "/admin");
                    } else if (userModel.checkUser(username, password)) {
                        session.setAttribute("isLogged", true);
                        session.setAttribute("username", username);
                        session.setAttribute("isAdmin", false);
                        response.sendRedirect(request.getContextPath() + "/");
                    } else {
                        session.setAttribute("isLogged", false);
                        request.setAttribute("error", true);
                        request.getRequestDispatcher("/WEB-INF/view/logIn.jsp").forward(request, response);
                    }
                } else {
                    sendErrorMessage(request, response, "Username e password non presenti", 400, "Bad Request");
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database", e);
        }
    }
}
