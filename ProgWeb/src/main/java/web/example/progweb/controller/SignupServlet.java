package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SignupServlet", value="/SignupServlet")
public class SignupServlet extends AbstractController {
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
            if(userModel.checkUsername(username)){
                sendErrorMessage(request, response, "Il nome utente esiste già!", 409 , "Conflict");
                response.sendRedirect(request.getContextPath()+"signUp.jsp");
            }
            else{
                String password = request.getParameter("password");
                String email = request.getParameter("email");
                String name = request.getParameter("nome");
                String surname = request.getParameter("cognome");
                String birthDate = request.getParameter("data_nascita");
                String phone = request.getParameter("telefono");

                userModel.insertUser(username, password, name, surname, birthDate, email, phone);
                response.sendRedirect(request.getContextPath() + "WEB-INF/view/goodLogin.jsp");
            }
        } catch (SQLException e) {
            System.err.println("Non è stato possibile accedere al database!"+ e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database, non è presente il driver", e);
        }
    }

}
