package model;

public class Flight extends Aircraft {

    // Flight Class Attributes
    private int flightID;
    private String origin;
    private String destination;
    private String departureDate;
    private Crew crew[];

    // Flight Constructor
    public Flight(int aircraftID, String aircraftType, int flightID, String origin, String destination, String departureDate) {
        super(aircraftID, aircraftType);
        this.flightID = flightID;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
    }

    // Flight Getters
    public int getFlightID(){
        return this.flightID;
    }

    public String getOrigin(){
        return this.origin;
    }

    public String getDestination(){
        return this.destination;
    }

    public String getDepartureDate(){
        return this.departureDate;
    }

    public Crew[] getCrew(){
        return this.crew;
    }

    // Flight Setters
    public void setflightID(int flightID){
        this.flightID = flightID;
    }

    public void setOrigin(String origin){
        this.origin = origin;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }

    public void setDepartureDate(String date){
        this.departureDate = date;
    }

    public void setCrew(Crew[] crew){
        this.crew = crew;
    }

}
