package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;
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

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            userModel = new UserModel(connection);
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database", e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera i parametri di login
        HttpSession session = request.getSession(false);

        if (session.getAttribute("username") != null) { //autenticato da Filtro
            Boolean isAdmin = (Boolean)session.getAttribute("isAdmin");
            if (isAdmin != null && isAdmin) {
                request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
            }else {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }else{
            sendErrorMessage(request, response, "Credenziali non corrette, riprova", 401, "Unauthorized");
            request.getRequestDispatcher("/login.jsp").forward(request, response); 
        }
    }
}
