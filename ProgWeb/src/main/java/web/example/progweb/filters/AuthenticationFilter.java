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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (session == null || (session.getAttribute("username") == null &&  session.getAttribute("admin") == null)){ // se non presente una sessione si richiede di effetturare il login
            res.sendRedirect(req.getContextPath() + "/LoginServlet");
        } else {
            chain.doFilter(request, response);
        }
    }
}
