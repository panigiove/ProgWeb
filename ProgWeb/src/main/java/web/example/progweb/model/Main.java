package web.example.progweb.model;

import web.example.progweb.model.entity.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Main {
    /*public static void main(String[] args) {
        try {
//            // test to User Model
//            UserModel userService = new UserModel();
//            System.out.println("Checking username...");
//            boolean isUsernameAvailable = userService.checkUsername("gbianchi");
//            System.out.println("Username check result: " + isUsernameAvailable);
//            if (isUsernameAvailable) {
//                System.out.println("Deleting user...");
//                userService.deleteUser("gbianchi");
//            }
//            System.out.println("Inserting user...");
//            userService.insertUser("martina", "sono_una_malandrina", "daniele", "daniele", "08/03/2003", "elfonatale@babbonatale.company.it", "123456");
//            System.out.println("Checking user...");
//            boolean isUserChecked = userService.checkUser("martina", "sono_una_malandrina");
//            System.out.println("User check result: " + isUserChecked);
//            System.out.println("Getting user ID...");
//            int userId = userService.getUserId("martina");
//            System.out.println("User ID: " + userId);
//            System.out.println("Incrementing purchases...");
//            userService.incrementPurchases(userId);
//            System.out.println("Getting user by ID...");
//            User user = userService.getUser(userId);
//            System.out.println("User details: " + user);
//            System.out.println("Deleting user...");
//            userService.deleteUser("martina");
//            System.out.println("Getting all users...");
//            List<User> allUsers = userService.getUsers();
//            System.out.println("All users: " + allUsers);
//            System.out.println("Getting users ordered by purchases...");
//            List<User> usersOrderedByPurchases = userService.getUsersOrderedByPurchase();
//            System.out.println("Users ordered by purchases: " + usersOrderedByPurchases);

//            // test to Event Model
//            EventModel eventModel = new EventModel();
//            System.out.println(eventModel.insertEvent(1,2,"concerto di Albano e Snoop Dog", "01/01/2024 20:00:00", "01/01/2024 22:00:00", 200,200,200,200, new BigDecimal("10.00"), new BigDecimal("5.00")));
//            System.out.println(eventModel.getEventByCategory(1));
//            System.out.println(eventModel.decrementAvailability(1, false, 5));
//            System.out.println(eventModel.decrementAvailability(1, true, 2000));
//            System.out.println(eventModel.getEventById(1));
//            eventModel.incrementClick(1);
//            System.out.println(eventModel.getEventById(1));
//            System.out.println(eventModel.get3MostClickedEvent());
//            System.out.println(eventModel.getEventsOrderedByClick());
//            System.out.println(eventModel.getPrice(1, false));
//            eventModel.deleteEvent(5);
//
//            TicketModel ticketModel = new TicketModel();
//            System.out.println(ticketModel.getTicket(1));
//            System.out.println(ticketModel.buyTicket(1,1,1,false));
//            System.out.println(ticketModel.getAllUserTicket(1));

        } catch(SQLException e){
            throw new RuntimeException(e);
        } catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }*/
}

