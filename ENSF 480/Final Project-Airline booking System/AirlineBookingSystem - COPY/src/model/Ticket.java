package model;

import model.users.Customer;

public class Ticket {

    // Ticket Class Attributes
    private int ticketID;
    private Customer customer;
    private Flight flight;
    private Seat seat;
    private boolean insuranceSelected;
    private boolean isCancelled;

    // Ticket Constructor
    public Ticket(int ticketID, Customer customer, Flight flight, Seat seat, boolean insuranceSelected, boolean isCancelled){
        this.ticketID = ticketID;
        this.customer = customer;
        this.flight = flight;
        this.seat = seat;
        this.insuranceSelected = insuranceSelected;
        this.isCancelled = isCancelled;
    }

    // Ticket Getters
    public int getTicketID(){
        return this.ticketID;
    }

    public double getTotalPrice(){
        double insurancePrice = 100;
        if (this.insuranceSelected == false){
            insurancePrice = 0;
        }
        return this.seat.getPrice() + insurancePrice;
    }

    public Customer getCustomer(){
        return this.customer;
    }

    public Flight getFlight(){
        return this.flight;
    }

    public Seat getSeat(){
        return this.seat;
    }
    public boolean getInsuranceSelection(){
        return this.insuranceSelected;
    }
    public boolean isCancelled(){
        return this.isCancelled;
    }

    // Ticket Setters
    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public void setFlight(Flight flight){
        this.flight = flight;
    }

    public void setSeat(Seat seat){
        this.seat = seat;
    }

    public void setInsuranceSelection(boolean insuranceSelected){
        this.insuranceSelected = insuranceSelected;
    }

    @Override
    public String toString() {
        
        return "FlightID: " + flight.getFlightID(); 
    }
}
