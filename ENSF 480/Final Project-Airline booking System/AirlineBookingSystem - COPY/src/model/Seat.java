package model;
public class Seat{

    // Seat Class Attributes
    private int seatID;
    private int flightID;
    private String seatType;
    private double price;
    private boolean isAvailable;

    // Seat Constructor
    public Seat(int flightID, int seatID, String seatType, double price, boolean isAvailable){
        this.flightID = flightID;
        this.seatID = seatID;
        this.seatType = seatType;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    // Seat Getters
    public int getSeatID(){
        return this.seatID;
    }

    public int getFlightID(){
        return this.flightID;
    }

    public String getSeatType(){
        return this.seatType;
    }

    public double getPrice(){
        return this.price;
    }

    public boolean isAvailable(){
        return this.isAvailable;
    }

     // Seat Setters
    public void setSeatID(int seatID){
        this.seatID = seatID;
    }

     public void getFlightID(int flightID){
       this.flightID = flightID;
    }

    public void setSeatType(String seatType){
        this.seatType = seatType;
    }

    public void setPrice(double price){
       this.price = price;
    }

    public void setAvailability(boolean availability){
        this.isAvailable = availability;
    }
    
}
