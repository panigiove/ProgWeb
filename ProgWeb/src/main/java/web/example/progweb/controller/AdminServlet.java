package web.example.progweb.controller;
import javax.servlet.annotation.MultipartConfig;

import web.example.progweb.controller.abstractClass.AbstractController;
import web.example.progweb.model.DiscountModel;
import web.example.progweb.model.EventModel;
import web.example.progweb.model.UserModel;
import web.example.progweb.model.entity.*;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MultipartConfig
@WebServlet(name = "AdminServlet", urlPatterns = {"/admin/*"})
public class AdminServlet extends AbstractController {
    private EventModel eventModel;
    private UserModel userModel;
    private DiscountModel discountModel;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.eventModel = new EventModel(connection);
            this.userModel = new UserModel(connection);
            this.discountModel = new DiscountModel(connection);
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database" + e.getMessage());
            throw new ServletException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String path = request.getPathInfo();
            if (path == null || "/".equals(path)) {
                request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
            } else if ("/gestioneEventi".equals(path)) {
                List<Event> events;
                List<Discount> discounts = discountModel.getValidDiscounts();
                String sortByClicks = request.getParameter("sortByClicks");
                String newEventCreated = request.getParameter("newEventCreated");
                List<Category> categories = eventModel.getAllCategory();
                List<AbstractMap.SimpleEntry<String, Integer>> nclicks = new ArrayList<>();;
                for(Category category : categories){
                    nclicks.add(new AbstractMap.SimpleEntry<>(category.getName(), eventModel.getCategoryTotalNclick(category.getId())));
                }
                if ("on".equals(sortByClicks)) {
                    events = eventModel.getEventsOrderedByClick();
                } else {
                    events = eventModel.getEvents();
                }
                if ("true".equals(newEventCreated)){
                    request.setAttribute("newEventCreated", true);
                }else{
                    request.setAttribute("newEventCreated", false);
                }
                request.setAttribute("nclicks", nclicks);
                request.setAttribute("events", events);
                request.setAttribute("discounts", discounts);
                request.getRequestDispatcher("/WEB-INF/view/eventManager.jsp").forward(request, response);
            } else if("/gestioneUtenti".equals(path)){
                List<User> users;
                String sortByPurchases = request.getParameter("sortByPurchases");
                if("on".equals(sortByPurchases)){
                    users = userModel.getUsersOrderedByPurchase();
                }
                else{
                    users = userModel.getUsers();
                }
                request.setAttribute("users", users);
                request.getRequestDispatcher("/WEB-INF/view/usersManager.jsp").forward(request, response);
            }else if ("/newEvent".equals(path)){
                List<Category> categories = eventModel.getAllCategory();
                List<Location> locations = eventModel.getAllLocation();
                request.setAttribute("locations", locations);
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/WEB-INF/view/newEvent.jsp").forward(request, response);
            }
            else {
                sendErrorPage(request, response, "page not founded", HttpServletResponse.SC_NOT_FOUND, "Not Found");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante le operazione di Admin get: " + e.getMessage());
            sendErrorPage(request, response,"Errore durante le operazione di Admin get", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if ("/inserisciEvento".equals(path)) {
            String nomeEvento = request.getParameter("nomeEvento");
            String descrizioneEvento = request.getParameter("descrizioneEvento");
            String dataInizioEvento = request.getParameter("dataInizioEvento");
            String dataFineEvento = request.getParameter("dataFineEvento");
            int categoriaEvento = Integer.parseInt(request.getParameter("categoriaEvento"));
            int localitaEvento = Integer.parseInt(request.getParameter("localitaEvento"));
            BigDecimal prezzoPostoSeduto = new BigDecimal(request.getParameter("prezzoPostoSeduto"));
            BigDecimal prezzoPostoInPiedi = new BigDecimal(request.getParameter("prezzoPostoInPiedi"));
            int totalePostiSeduti = Integer.parseInt(request.getParameter("totalePostiSeduti"));
            int totalePostiInPiedi = Integer.parseInt(request.getParameter("totalePostiInPiedi"));

            BigDecimal sconto = null;
            String scontoParam = request.getParameter("sconto");
            if (scontoParam != null && !scontoParam.trim().isEmpty()) {
                sconto = new BigDecimal(scontoParam);
            }
            String dataScadenzaSconto = request.getParameter("dataScadenzaSconto");

            Part filePart = request.getPart("immagineEvento");
            String imageName = generateRandomFileName(filePart.getSubmittedFileName());


            File imagesDir = new File(getServletContext().getRealPath("/images/"));
            System.out.println(imagesDir);
            if (!imagesDir.exists()) {
                imagesDir.mkdirs();
            }
            File file = new File(imagesDir, imageName);
            try (InputStream fileContent = filePart.getInputStream();
                 OutputStream out = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileContent.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            try {
                boolean hasDiscount = (sconto!=null && dataScadenzaSconto!= null);
                Discount newDiscount;
                Event newEvent = eventModel.insertEvent(
                        categoriaEvento,
                        localitaEvento,
                        nomeEvento,
                        descrizioneEvento,
                        dataInizioEvento,
                        dataFineEvento,
                        totalePostiSeduti,
                        totalePostiSeduti,
                        totalePostiInPiedi,
                        totalePostiInPiedi,
                        prezzoPostoSeduto,
                        prezzoPostoInPiedi,
                        imageName
                );

                int eventId = newEvent.getId();

                if(hasDiscount){
                    newDiscount = discountModel.createDiscount(
                            eventId,
                            dataScadenzaSconto,
                            sconto
                    );
                }

                if (newEvent != null) {
                    response.sendRedirect(request.getContextPath() + "/admin/gestioneEventi?newEventCreated=true");
                } else {
                    sendErrorMessage(request, response, "Failed to insert event", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Insertion Failure");
                }
            } catch (SQLException e) {
                System.err.println("Error during event insertion: " + e.getMessage());
                sendErrorPage(request, response, "Error inserting event", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            }
        } else if ("/deleteEvent".equals(request.getPathInfo())) {
            int eventId;
            try {
                eventId = Integer.parseInt(request.getParameter("eventId"));
            } catch (NumberFormatException e) {
                sendErrorMessage(request, response, "Invalid Event ID format", HttpServletResponse.SC_BAD_REQUEST, "Invalid Event ID");
                return;
            }
            try {
                if (!eventModel.checkIdEvent(eventId)) {
                    sendJsonMessage(response, "{\"success\": \"fail\"}");
                } else {
                    eventModel.deleteEvent(eventId);
                    sendJsonMessage(response, "{\"success\": \"success\"}");
                }
            } catch (SQLException e) {
                System.err.println("Error during event deletion: " + e.getMessage());
                sendErrorPage(request, response, "Error deleting event", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            }
        } else {
            sendErrorPage(request, response, "Path not found", HttpServletResponse.SC_NOT_FOUND, "Not Found");
        }
    }

    private String generateRandomFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String randomFileName = UUID.randomUUID().toString();
        return randomFileName + extension;
    }

}
