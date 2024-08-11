package web.example.progweb.controller;

import web.example.progweb.controller.abstractClass.AbstractController;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "adminSevlet", value = "/admin")
public class AdminHomeServlet extends AbstractController {
    @Override
    public void init() throws ServletException {
        super.init();
    }
}
