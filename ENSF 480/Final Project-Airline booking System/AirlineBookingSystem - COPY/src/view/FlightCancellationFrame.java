package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.*;

public class FlightCancellationFrame {
    private JFrame frame;
    private JPanel panel;
    private int userID; 
    private JList<String> ticketListJL; 
    private DefaultListModel<String> listModel; 

    public FlightCancellationFrame(int userID) {
        this.userID = userID;
    }

    public void createUI() {
        frame = new JFrame();
        frame.setTitle("Flight Cancellation");
        panel = new JPanel();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Tickets for User ID: " + userID);
        titleLabel.setBounds(30, 10, 300, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(titleLabel);

        
        listModel = new DefaultListModel<>();  

        // Retrieve and display the list of tickets for the user
        displayTickets();

        ticketListJL = new JList<String>(listModel);
        ticketListJL.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(ticketListJL);
        scrollPane.setBounds(30, 60, 500, 400);
        panel.add(scrollPane);

        JButton cancelSelectedButton = new JButton("Cancel Selected Ticket");
        cancelSelectedButton.setBounds(30, 500, 200, 40);
        cancelSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCancellation();
            }
        });
        panel.add(cancelSelectedButton);

        frame.setVisible(true);
    }

    //display looks good
    private void displayTickets() {
        ArrayList<Ticket> ticketList = getTicketsForUser(userID);

         listModel.clear();

        for (Ticket ticket : ticketList) {
            int ticketID = ticket.getTicketID();
            String destination = ticket.getFlight().getDestination();
            String origin = ticket.getFlight().getOrigin(); 
            String departureDate = ticket.getFlight().getDepartureDate(); 
            String seatType = ticket.getSeat().getSeatType(); 
            
            String ticketInfo = "ID: " + ticketID + " | " + origin + " -> " + destination + " : " + departureDate + " | Seat Class: " + seatType;

            if(!ticket.isCancelled()){
                //only add to the list is ticket is not cancelled
                listModel.addElement(ticketInfo);
            }
        }
    }
    
    private ArrayList<Ticket> getTicketsForUser(int userID) {
        FlightSystem sys = FlightSystem.getInstance();

        ArrayList<Ticket> userTicketList = new ArrayList<>();
        ArrayList<Ticket> fullTicketList = sys.getTicketList();

        for (Ticket ticket : fullTicketList) {
            if (ticket.getCustomer().getUserID() == userID) {
                userTicketList.add(ticket);
            }
        }

        return userTicketList;
    }

    // Helper function to extract ticketID from String in format: "ID: XX | Origin -> Destination : Date"
    private static String extractTicketID(String ticketInfo) {
        Pattern pattern = Pattern.compile("ID: (\\d+)");
        Matcher matcher = pattern.matcher(ticketInfo);

        // Check if the pattern is found
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            // Return an empty string or handle the case when no match is found
            return "";
        }
    }

    private String getSelectedTicket() {
        return ticketListJL.getSelectedValue();
    }

    private void handleCancellation() {
        String selectedTicket = getSelectedTicket();

        if (selectedTicket == null) {
            JOptionPane.showMessageDialog(null, "No ticket selected for cancellation.");
            return;
        }

        String ticketID = extractTicketID(selectedTicket);
        

        FlightSystem sys = FlightSystem.getInstance();
        String updateQuery = "UPDATE TICKETS SET IsCancelled = TRUE WHERE TicketID = " + ticketID;

        // Execute the SQL query to mark the ticket as cancelled
        try {
            PreparedStatement updateStmt = sys.getConnection().prepareStatement(updateQuery);
            updateStmt.executeUpdate();
            updateStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error canceling the ticket. Please try again.");
            return;
        }

        // Update flight system
        sys.synchronizeFlightSys();

        JOptionPane.showMessageDialog(null, "Ticket canceled successfully.");
        frame.dispose(); 
    }
}
