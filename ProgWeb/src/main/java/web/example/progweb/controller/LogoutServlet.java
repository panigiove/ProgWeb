package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends AbstractController {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session != null) {
            session.removeAttribute("username");
            session.removeAttribute("isAdmin");
            session.setAttribute("isLogged", false);
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
