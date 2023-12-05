package view;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;
import model.users.*;
import model.*;

public class AirlineAgentGUI implements ActionListener {
    private FlightSystem sys;
    private String airlineAgentFName;
    private int userID;
    private ArrayList<Flight> flightList;
    private ArrayList<Ticket> ticketList;
    private JFrame frame;
    private JPanel panel;
    private JComboBox<String> dropdown;
    private ArrayList<String> options;
    private ArrayList<String> passengerList;

    	public static void main(String[] args) {
        AirlineAgentGUI gui = new AirlineAgentGUI(2);
        gui.createUI();
    }

    // Airline Agent Constructor
    public AirlineAgentGUI(int userID){
        this.userID = userID;
        this.sys = FlightSystem.getInstance();
        this.flightList = sys.getFlightList();
        ArrayList<AirlineAgent> airlineAgentList = sys.getAirlineAgentList();
        

        // init Fname and Lname for the Airline agent
        for (AirlineAgent agent : airlineAgentList){
            if (agent.getUserID() == userID){
                this.airlineAgentFName = agent.getFirstName();
            }
        }
    }

    public void createUI() {
        frame = new JFrame();
        frame.setTitle("Airline Agent Welcome Page");
        panel = new JPanel();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome " + airlineAgentFName + "!");
        welcomeLabel.setBounds(30, 10, 200, 40);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(welcomeLabel);

        JButton browsePassengerListButton = new JButton("Browse Passenger List");
        browsePassengerListButton.setBounds(30, 80, 250, 25);
        browsePassengerListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBrowsePassengerListFrame();
            }
        });
        panel.add(browsePassengerListButton);

        frame.setVisible(true);
    }

	@Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<String> source = (JComboBox<String>) e.getSource();

        // Check if the selected index is valid
        int selectedIndex = source.getSelectedIndex();
        

        if (selectedIndex >= 1 && selectedIndex <= flightList.size()) {
            int selectedFlightID = Integer.parseInt(options.get(selectedIndex));
            displayPassengerList(selectedFlightID);
        } else {
            System.out.println("Invalid selection");
        }
    }

    private void openBrowsePassengerListFrame() {
        frame = new JFrame();
        frame.setTitle("Browse Passenger List");
        panel = new JPanel();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        JLabel welcomeLabel = new JLabel("Browse Passenger List");
        welcomeLabel.setBounds(30, 10, 200, 40);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(welcomeLabel);

        JLabel promptLabel = new JLabel("Please Select the Desired Flight");
        promptLabel.setBounds(30, 40, 200, 40);
        panel.add(promptLabel);

        options = new ArrayList<>();
        // Get flight IDs from flight list
        for (Flight flight : flightList) {
            int flightID = flight.getFlightID();
            options.add(Integer.toString(flightID));
        }
        options.add(0, "Select flight.."); 

        dropdown = new JComboBox<>(options.toArray(new String[0]));
        dropdown.setBounds(30, 80, 250, 25);
        dropdown.addActionListener(this);
        panel.add(dropdown);

        JLabel passengerListLabel = new JLabel("Passenger List:");
        passengerListLabel.setBounds(30, 120, 200, 25);
        panel.add(passengerListLabel);

        passengerList = new ArrayList<>();

        JTextArea passengerTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(passengerTextArea);
        scrollPane.setBounds(30, 150, 400, 200);
        panel.add(scrollPane);

        JButton backButton = new JButton("Back to Welcome Page");
        backButton.setBounds(30, 400, 200, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                createUI();
            }
        });
        panel.add(backButton);

        frame.setVisible(true);
    }
    public void displayPassengerList(int flightID){
        passengerList.clear();
        ticketList = sys.getTicketList();

        // Add passenger information to the list
        for (Ticket t : ticketList) {
            if (t.getFlight().getFlightID() == flightID) {
                String name = t.getCustomer().getFirstName() + " " + t.getCustomer().getLastName();
                String passengerClass = t.getSeat().getSeatType();
                int passengerID = t.getCustomer().getUserID();

                String passengerString = "ID: " + passengerID + " | " + "Class: " + passengerClass + " | Name: " + name;

                // Add passenger string to list
                passengerList.add(passengerString);
            }
        }

        StringBuilder passengerInfo = new StringBuilder();
        for (String passenger : passengerList) {
            passengerInfo.append(passenger).append("\n");
        }
        JTextArea passengerTextArea = (JTextArea) ((JScrollPane) panel.getComponent(4)).getViewport().getView();
        passengerTextArea.setText(passengerInfo.toString());
    }
}
