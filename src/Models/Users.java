package Models;

/** This class handles the Users.
 @author Stephen Moyer
 */
public class Users {

    private static String username;

    public Users() {}

    /** Gets the Username. */
    public static String getUsername() {
        return username;
    }

    /** Sets the Username.
     @param username Passes the username
      */
    public void setUsername(String username) {
        this.username = username;
    }

}
