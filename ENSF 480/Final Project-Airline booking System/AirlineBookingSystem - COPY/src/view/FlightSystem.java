package view;

import java.util.*;

import model.*;
import model.users.*;

import java.sql.*;

public class FlightSystem {

    // MySQL Connection Variables
    private Connection dbConnect;
    private ResultSet results;

    // Flight System Database Lists
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Customer> customers = new ArrayList<Customer>();
    private ArrayList<TourismAgent> tourismAgents = new ArrayList<TourismAgent>();
    private ArrayList<AirlineAgent> airlineAgents = new ArrayList<AirlineAgent>();
    private ArrayList<FlightAttendant> flightAttendants = new ArrayList<FlightAttendant>();
    private ArrayList<SystemAdministrator> systemAdministrators = new ArrayList<SystemAdministrator>();
    private ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
    private ArrayList<Flight> flights = new ArrayList<Flight>();
    private ArrayList<Seat> seats = new ArrayList<Seat>();
    private ArrayList<Ticket> tickets = new ArrayList<Ticket>();

    //Singleton instance
    private static FlightSystem onlyInstance;

    //Private constructor
    private FlightSystem(){
        createConnection();
        initializeData();
    }
    
    /**
     * Getter method returns the singleton instance of FlightSystem.
     * If the instance does not exist, it creates a new one.
     *
     * @return the singleton instance of FlightSystem.
     */
    public static FlightSystem getInstance() {
        if (onlyInstance == null) {
            synchronized (FlightSystem.class) {
                if (onlyInstance == null) {
                    onlyInstance = new FlightSystem();
                }
            }
        }
        return onlyInstance;
    }

    /**
     * This method creates a connection to the SQL database.
     */
    public void createConnection() {
        // Load database properties
        Properties properties = DBUtils.loadProperties("AirlineBookingSystem/config/database.properties");
        if (properties == null) {
            // Handle the error appropriately
            return;
        }

        String url = properties.getProperty("db.url");
        String dbUsername = properties.getProperty("db.username");
        String dbPassword = properties.getProperty("db.password");

        
        try {
            dbConnect = DriverManager.getConnection(url, dbUsername, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     /**
     * Getter method returns the SQL Connection object.
     * @return the Connection object to the SQL database.
     */
    public Connection getConnection() {
        return dbConnect;
    }

    // Getters
    public ArrayList<User> getUserList() {
        return this.users;
    }

    public ArrayList<Customer> getCustomerList() {
        return this.customers;
    }

    public ArrayList<TourismAgent> getTourismAgentList(){
        return this.tourismAgents;
    }
    
    public ArrayList<AirlineAgent> getAirlineAgentList(){
        return this.airlineAgents;
    }

    public ArrayList<FlightAttendant> getFlightAttendantList(){
        return this.flightAttendants;
    }

    public ArrayList<Aircraft> getAircraftList() {
        return this.aircrafts;
    }

    public ArrayList<Flight> getFlightList() {
        return this.flights;
    }

    public ArrayList<Seat> getSeatList() {
        return this.seats;
    }

    public ArrayList<Ticket> getTicketList() {
        return this.tickets;
    }

    // Helper methods to find objects by ID
    private Customer findCustomerByID(int userID) {
        for (Customer customer : customers) {
            if (customer.getUserID() == userID) {
                return customer;
            }
        }
        return null; // Handle appropriately if not found
    }

    private Flight findFlightByID(int flightID) {
        for (Flight flight : flights) {
            if (flight.getFlightID() == flightID) {
                return flight;
            }
        }
        return null; // Handle appropriately if not found
    }

    private Seat findSeatByID(int seatID) {
        for (Seat seat : seats) {
            if (seat.getSeatID() == seatID) {
                return seat;
            }
        }
        return null; // Handle appropriately if not found
    }

    private void initializeData() {

        // List of Users
         try {
            PreparedStatement myStmt = dbConnect.prepareStatement("SELECT * FROM USERS");
            results = myStmt.executeQuery();

            while (results.next()) {
                User user = new User(results.getInt("UserID"), 
                results.getString("UserName"),
                results.getString("UserPassword"),
                results.getString("FirstName"),
                results.getString("LastName"));
                users.add(user);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // List of Customers
         try {
            PreparedStatement myStmt = dbConnect.prepareStatement("SELECT * FROM CUSTOMERS");
            results = myStmt.executeQuery();

            while (results.next()) {
                Customer customer = new Customer(results.getInt("UserID"), 
                results.getString("UserName"),
                results.getString("UserPassword"),
                results.getString("FirstName"),
                results.getString("LastName"),
                results.getString("Email"),
                results.getString("Address"),
                results.getBoolean("isRegistered"),
                results.getString("CreditCardNumber"));
                customers.add(customer);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // List of Tourism Agents
        try {
            PreparedStatement myStmt = dbConnect.prepareStatement("SELECT * FROM TOURISM_AGENTS");
            results = myStmt.executeQuery();

            while (results.next()) {
                TourismAgent tourismAgent = new TourismAgent(results.getInt("UserID"), 
                results.getString("UserName"),
                results.getString("UserPassword"),
                results.getString("FirstName"),
                results.getString("LastName"),
                results.getString("Email"));
                tourismAgents.add(tourismAgent);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // List of Airline Agents
        try {
            PreparedStatement myStmt = dbConnect.prepareStatement("SELECT * FROM AIRLINE_AGENTS");
            results = myStmt.executeQuery();

            while (results.next()) {
                AirlineAgent airlineAgent = new AirlineAgent(results.getInt("UserID"), 
                results.getString("UserName"),
                results.getString("UserPassword"),
                results.getString("FirstName"),
                results.getString("LastName"));
                airlineAgents.add(airlineAgent);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // List of Flight Attendants
         try {
            PreparedStatement myStmt = dbConnect.prepareStatement("SELECT * FROM FLIGHT_ATTENDANTS");
            results = myStmt.executeQuery();

            while (results.next()) {
                FlightAttendant flightAttendant = new FlightAttendant(results.getInt("UserID"), 
                results.getString("UserName"),
                results.getString("UserPassword"),
                results.getString("FirstName"),
                results.getString("LastName"));
                flightAttendants.add(flightAttendant);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // List of System Admins
         try {
            PreparedStatement myStmt = dbConnect.prepareStatement("SELECT * FROM SYSTEM_ADMINS");
            results = myStmt.executeQuery();

            while (results.next()) {
                SystemAdministrator systemAdministrator = new SystemAdministrator(results.getInt("UserID"), 
                results.getString("UserName"),
                results.getString("UserPassword"),
                results.getString("FirstName"),
                results.getString("LastName"));
                systemAdministrators.add(systemAdministrator);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

       // List of Aircrafts
        try {
            PreparedStatement myStmt = dbConnect.prepareStatement("SELECT * FROM AIRCRAFTS");
            results = myStmt.executeQuery();

            while (results.next()) {
                Aircraft aircraft = new Aircraft(
                    results.getInt("AircraftID"),
                    results.getString("AircraftType")
                    
                );
                aircrafts.add(aircraft);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // List of Flights
         try {
            PreparedStatement myStmt = dbConnect.prepareStatement("SELECT FLIGHTS.*, AIRCRAFTS.AircraftType " +
                "FROM FLIGHTS " +
                "JOIN AIRCRAFTS ON FLIGHTS.AircraftID = AIRCRAFTS.AircraftID");
                
            results = myStmt.executeQuery();

            while (results.next()) {
                Flight flight = new Flight(results.getInt("AircraftID"), 
                results.getString("AircraftType"), 
                results.getInt("FlightID"), 
                results.getString("Origin"), 
                results.getString("Destination"), 
                results.getString("DepartureDate"));
                flights.add(flight);
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // List of Seats
         try {
            PreparedStatement myStmt = dbConnect.prepareStatement("SELECT s.FlightID, s.SeatID, sl.SeatType, sl.Price, s.IsAvailable " +
                "FROM SEATS s " +
                "JOIN SEAT_LAYOUTS sl ON s.LayoutID = sl.LayoutID");

            results = myStmt.executeQuery();

            while (results.next()) {
                Seat seat = new Seat(results.getInt("FlightID"), 
                results.getInt("SeatID"), 
                results.getString("SeatType"), 
                results.getDouble("Price"), 
                results.getBoolean("IsAvailable"));
                seats.add(seat);
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

         // List of Tickets
         try {
            PreparedStatement myStmt = dbConnect.prepareStatement("SELECT * FROM TICKETS");
            results = myStmt.executeQuery();
    
            while (results.next()) {
                int ticketID = results.getInt("TicketID");
                int userID = results.getInt("UserID");
                int flightID = results.getInt("FlightID");
                int seatID = results.getInt("SeatID");
                boolean insuranceSelected = results.getBoolean("InsuranceSelected");
                boolean isCancelled = results.getBoolean("IsCancelled");
                //double total = results.getDouble("Total");
    
                // Find corresponding Customer, Flight, and Seat objects
                Customer customer = findCustomerByID(userID);
                Flight flight = findFlightByID(flightID);
                Seat seat = findSeatByID(seatID);
    
                // Create Ticket
                Ticket ticket = new Ticket(ticketID, customer, flight, seat, insuranceSelected, isCancelled);
                tickets.add(ticket);
            }
    
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
  
    }

    // Simple function to reset the singleton instance
    private void resetInstance() {
        onlyInstance = null;
    }
    
    public void synchronizeFlightSys() {
        // Close the existing database connection
        try {
            if (dbConnect != null && !dbConnect.isClosed()) {
                dbConnect.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Reset the singleton instance
        resetInstance();

        // Create new instance
        FlightSystem.getInstance();  
    }
}