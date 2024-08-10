package web.example.progweb.controller;

import web.example.progweb.model.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "SignupServlet", value="/SignupServlet")
public class SignupServlet extends HttpServlet {
    private UserModel newUser;

    @Override
    public void init() throws ServletException {
        super.init();
        try{
            newUser = new UserModel();
        } catch (SQLException e) {
            // Log dell'errore e rilancio dell'eccezione
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database", e);
        } catch (ClassNotFoundException e) {
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database, non presente il driver", e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        create_response(request, response);
    }

    /*public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        create_response(request, response);
    }*/

    public void create_response(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        response.setContentType("text/plain");
        try{
            if(newUser.checkUsername(username)){
                sendErrorMessage(request, response, "Il nome utente esiste già!", 500);
            }
            else{
                String password = request.getParameter("password");
                String email = request.getParameter("email");
                String name = request.getParameter("nome");
                String surname = request.getParameter("cognome");
                String birthDate = request.getParameter("data_nascita");
                String phone = request.getParameter("telefono");

                newUser.insertUser(username, password, name, surname, birthDate, email, phone);
            }
        } catch (SQLException e) {
            System.err.println("Non è stato possibile accedere al database!"+ e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database, non è presente il driver", e);
        }
    }

    private void sendErrorMessage(HttpServletRequest request, HttpServletResponse response, String errorMessage, int errorCode) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setStatus(errorCode);
        try (PrintWriter out = response.getWriter()){
            out.println(errorMessage);
        }
    }

}
