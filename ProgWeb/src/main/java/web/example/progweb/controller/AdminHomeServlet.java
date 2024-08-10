package web.example.progweb.controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "adminSevlet", value = "/admin")
public class AdminHomeServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }
}
