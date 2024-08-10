package web.example.progweb.controller;

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
public class LoginServlet extends HttpServlet {

    private UserModel user;

    @Override
    public void init() throws ServletException {
        super.init();
        try{
            user = new UserModel();
        } catch (SQLException e) {
            // Log dell'errore e rilancio dell'eccezione
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database", e);
        } catch (ClassNotFoundException e) {
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database, non presente il driver", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try{
            if(user.checkUser(username, password)){
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect("index.jsp");

            }
            else{
                System.out.println("Non login");
                response.sendRedirect("logIn.jsp");
            }
        } catch (SQLException e) {
            System.err.println("Non è stato possibile accedere al database!"+ e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database, non è presente il driver", e);
        }
    }
}
