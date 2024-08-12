package web.example.progweb.filters;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.UserModel;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class AuthenticationFilter implements Filter {
    UserModel userModel;

    public void init(FilterConfig config) throws ServletException {
        try{
            userModel = new UserModel();
        }  catch (SQLException | ClassNotFoundException e) {
            if(e instanceof SQLException){
                System.err.println("Errore durante la connessione al database: " + e.getMessage());
                throw new ServletException("Impossibile stabilire la connessione al database", e);
            }
            else{
                System.err.println("Errore, classe non trovata "+e.getMessage());
                throw new ServletException("Impossibile trovare la classe", e);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            String  username = req.getParameter("username");
            String  password = req.getParameter("password");

            if(username.equals("admin") && password.equals("01nimda!")) {
                HttpSession session = req.getSession();
                session.setAttribute("admin", username);
            }
            else if(userModel.checkUser(username, password)){
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
            }

            chain.doFilter(request, response);
        } catch (SQLException e){
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database", e);
        }
    }

    public void destroy() {

    }
}
