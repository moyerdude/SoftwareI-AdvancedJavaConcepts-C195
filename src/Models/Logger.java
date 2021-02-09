package Models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

/** This class Writes info to the Log note.
 @author Stephen Moyer
 */
public class Logger {


        private static final String FILENAME = "login_activity.txt";

        public Logger() {}

        /** The method is called when something needs to be writen to the log.
         @param success Sends whether the the login was a failure or not.
         @param username Sends the username that attempted to log in.
         */
        public static void log (String username, boolean success) {

            try (FileWriter fw = new FileWriter(FILENAME, true);

                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter pw = new PrintWriter(bw)) {
                 pw.println(ZonedDateTime.now() + " " + username + (success ? " Success" : " Failure"));

            } catch (IOException e) {

                System.out.println("Logger Error: " + e.getMessage());
            }
        }
}
