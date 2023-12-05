package model;
public class Aircraft {

    // Aircraft Class Attributes
    private int aircraftID;
    private String aircraftType;
    private Seat seats[];

    // Aircraft Constructor
    public Aircraft(int aircraftID, String aircraftType) {
        this.aircraftID = aircraftID;
        this.aircraftType = aircraftType;
    }

    // Aircraft Getters
    public int getAircraftID(){
        return this.aircraftID;
    }

    public String getAircraftType(){
        return this.aircraftType;
    }

    public Seat[] getSeats(){
        return this.seats;
    }

    // Aircraft Setters
    public void setAircraftID(int aircraftID){
        this.aircraftID = aircraftID;
    }

    public void setAircraftType(String aircraftType){
        this.aircraftType = aircraftType;
    }

    public void setSeats(Seat[] seats){
        this.seats = seats;
    }

}
