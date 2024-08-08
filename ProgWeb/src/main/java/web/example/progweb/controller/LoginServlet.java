package web.example.progweb.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="LoginServlet", value="/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Simulazione di autenticazione (in una vera applicazione dovresti controllare queste credenziali contro un database)
        if ("utente".equals(username) && "password".equals(password)) {
            response.sendRedirect("success.jsp"); // Pagina di successo dopo il login
        } else {
            response.sendRedirect("login.jsp"); // Ritorna alla pagina di login in caso di fallimento
        }
    }
}
