package view;

import javax.swing.*;

import model.Flight;
import model.Ticket;
import model.users.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuestGUI implements ActionListener {

    private String firstName;
    private String lastName;
    private int guestID;
    private String guestUsername;
    private FlightSystem sys = FlightSystem.getInstance();

    public void createUI() {
        JFrame frame = new JFrame();
        frame.setTitle("Guest Information");
        JPanel panel = new JPanel();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(30, 20, 80, 25);
        panel.add(firstNameLabel);

        JTextField firstNameText = new JTextField();
        firstNameText.setBounds(130, 20, 165, 25);
        panel.add(firstNameText);

        JButton registerButton = new JButton("Continue as Guest");
        registerButton.setBounds(30, 100, 210, 25);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstName = firstNameText.getText();

                if (isValidName(firstName)) {
                    frame.dispose(); 
                    Customer guestCustomer = new Customer("guest", "guest", firstName, lastName, null, null, false);
                    guestID = guestCustomer.getGuestID();
                    guestUsername = guestCustomer.getUsername();
        
                    openOptionsPanel(guestCustomer);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a name");
                }
            }
        });
        panel.add(registerButton);

        frame.setVisible(true);
    }

    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
    private void openOptionsPanel(Customer customer) {
        JFrame frame = new JFrame();
        frame.setTitle("Options Panel");
        JPanel panel = new JPanel();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

           panel.setLayout(null);

        JLabel welcomeLabel = new JLabel("Hello, " + customer.getFirstName() + "("+ guestUsername + ")" + "!");
        welcomeLabel.setBounds(30, 10, 300, 40);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(welcomeLabel);

        JButton browseFlightsButton = new JButton("Browse Flights");
        browseFlightsButton.setBounds(30, 60, 180, 40);
        browseFlightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
                //open browse flights
                openBrowseFlightListFrame();
            }
        });
        panel.add(browseFlightsButton);
        
        JButton cancelFlightButton = new JButton("Cancel Flight");
        cancelFlightButton.setBounds(30, 120, 180, 40);
        cancelFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user's ticket list
                ArrayList<Ticket> userTicketList = getTicketsForUser(guestID);

                // Check if the list is null or empty
                if (userTicketList == null || userTicketList.isEmpty()) {
                    // Display a message to the user
                    JOptionPane.showMessageDialog(null, "You have no flights to cancel.", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Check if all tickets are cancelled
                    boolean allCancelled = userTicketList.stream().allMatch(Ticket::isCancelled);

                    if (allCancelled) {
                        JOptionPane.showMessageDialog(null, "All your flights are already cancelled.", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        frame.dispose();
                        new FlightCancellationFrame(guestID).createUI();
                    }
                }
            }
        });


        panel.add(cancelFlightButton);

        frame.setVisible(true);
    }

    private ArrayList<Ticket> getTicketsForUser(int userID) {
        FlightSystem sys = FlightSystem.getInstance();

        ArrayList<Ticket> userTicketList = new ArrayList<>();
        ArrayList<Ticket> ticketList = sys.getTicketList();
        
        for (Ticket ticket : ticketList) {
            if (ticket.getCustomer().getUserID() == userID) {
                userTicketList.add(ticket);
            }
        }
    
        return userTicketList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // empty
    }
    private ArrayList<String> getAvailableFlights() {
        ArrayList<Flight> flightList = sys.getFlightList();
        ArrayList<String> strFlightList = new ArrayList<String>();

        for (Flight flight : flightList){
            int flightID = flight.getFlightID();
            String origin = flight.getOrigin();
            String destination = flight.getDestination();
            String departureDate = flight.getDepartureDate();

            String flightInfo = "ID: " + flightID + " | " + origin + " -> " + destination + " : " + departureDate;
            strFlightList.add(flightInfo);
        }

        return strFlightList;
    }

    private void openBrowseFlightListFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("Browse Flight List");
        JPanel panel = new JPanel();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
    
        panel.setLayout(null);
    
        JLabel welcomeLabel = new JLabel("Browse Flight List");
        welcomeLabel.setBounds(30, 10, 200, 40);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(welcomeLabel);
    
        JLabel promptLabel = new JLabel("Please Select the Desired Flight");
        promptLabel.setBounds(30, 40, 200, 40);
        panel.add(promptLabel);
    
        // Retrieve the list of available flights from the database
        ArrayList<String> flightList = getAvailableFlights();
        flightList.add(0, "Select flight.."); 
    
        JComboBox<String> dropdown = new JComboBox<>(flightList.toArray(new String[0]));
        dropdown.setBounds(30, 80, 250, 25);
        panel.add(dropdown);
    
        JButton selectButton = new JButton("Select Flight");
        selectButton.setBounds(30, 120, 150, 40);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFlightStr = (String) dropdown.getSelectedItem();

                int flightID = -99; //default value most likely not a flight ID

                Pattern pattern = Pattern.compile("ID: (\\d+)");
                Matcher matcher = pattern.matcher(selectedFlightStr);

                // Check if the pattern matches
                if (matcher.find()) {
                    // Extract the matched ID as a string
                    String idString = matcher.group(1);

                    // Convert the string ID to an integer
                    flightID = Integer.parseInt(idString);

                } 
                //loop through flight list
                ArrayList<Flight> flightList = sys.getFlightList();
                Flight selectedFlight = null;
                for (Flight flight : flightList){
                    if (flight.getFlightID() == flightID){
                        selectedFlight = flight;
                    }
                }

                if (!selectedFlightStr.equals("Select flight..")) {
                    frame.dispose(); 
                    openBrowseSeatFrame(selectedFlight, guestID);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a valid flight.");
                }
            }
        });
        panel.add(selectButton);
    
        frame.setVisible(true);
    }

    private void openBrowseSeatFrame(Flight selectedFlight, int guestID) {
        BrowseSeatFrame seatGUI = new BrowseSeatFrame(selectedFlight, guestID);
        seatGUI.createUI();
    }
}