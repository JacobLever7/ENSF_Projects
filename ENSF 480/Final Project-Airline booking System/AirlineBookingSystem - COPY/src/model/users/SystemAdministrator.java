package model.users;
import java.util.Date;

import model.*;

public class SystemAdministrator extends User{

   // SystemAdministrator Constructor
   public SystemAdministrator (int userID, String username, String password, String firstName, String lastName){
        super(userID, username, password, firstName, lastName);
    }

}
