package model.users;
public class TourismAgent extends User{

    // TourismAgent Class Attributes
    private String email;

    // TourismAgent Constructor
    public TourismAgent (int userID, String username, String password, String firstName, String lastName, String email){
        super(userID, username, password, firstName, lastName);
        this.email = email;
    }

    // TourismAgent Getters
    public String getEmail(){
        return this.email;
    }

    // TourismAgent Setters
    public void setEmail(String email){
        this.email = email;
    }
    
}
