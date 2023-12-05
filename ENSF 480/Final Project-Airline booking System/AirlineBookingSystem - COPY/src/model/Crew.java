package model;
import java.util.ArrayList;
import model.users.FlightAttendant;

public class Crew {

    // Crew Class Attributes
    private ArrayList<FlightAttendant> crewMembers;

    // Crew Constructor
    public Crew (ArrayList<FlightAttendant> crewMembers){
        this.crewMembers = crewMembers;
    }

    // Crew Getters
    public ArrayList<FlightAttendant> getCrew(){
        return this.crewMembers;
    }

    // Crew Setters
    public void setCrew(ArrayList<FlightAttendant> crewMembers){
        this.crewMembers = crewMembers;
    }
    
}
