package web.example.progweb.filters;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.AdminModel;
import web.example.progweb.model.UserModel;
import web.example.progweb.model.abstractClass.AbstractModel;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;

public class AuthenticationFilter implements Filter {
    private UserModel userModel;
    private AdminModel adminModel;
    private Connection connection;
    public void init(FilterConfig config) throws ServletException {
        try{
            connection = AdminModel.connectDB();
            userModel = new UserModel(connection);
            adminModel = new AdminModel(connection);
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
            HttpSession session = req.getSession(true);

            if (username != null && password != null ) {
                if (adminModel.checkAdmin(username, password)) {
                    session.setAttribute("username", username);
                    session.setAttribute("isAdmin", true);
                }else if (userModel.checkUser(username, password)){
                    session.setAttribute("username", username);
                    session.setAttribute("isAdmin", false);
                }else {
                    session.removeAttribute("username");
                    session.removeAttribute("isAdmin");
                }
            }

            chain.doFilter(request, response);
        } catch (SQLException e){
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
            throw new ServletException("Impossibile stabilire la connessione al database", e);
        }
    }

    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Errore durante la chiusura al database: " + e.getMessage());
        }
    }
}
