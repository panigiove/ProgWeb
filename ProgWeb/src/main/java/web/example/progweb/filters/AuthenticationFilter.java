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

    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String  username = req.getParameter("username");
        String  password = req.getParameter("password");

        if(username.equals("admin") && password.equals("01nimda!")) {
            HttpSession session = req.getSession();
            session.setAttribute("admin", username);
            res.sendRedirect(req.getContextPath()+"/index.jsp");
        }

        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
