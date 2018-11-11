package group_0548.gamecentre;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersManager {

    /**
     * Logger to log messages
     */
    private static final Logger logger = Logger.getLogger(UsersManager.class.getName());
    /**
     * Handler to export messages from logger to console
     */
    private static final Handler consoleHandler = new ConsoleHandler();
    /**
     * The name of the file which stores the users.
     */
    private static final String USER_FILENAME = "users.ser";
    /**
     * The current user.
     */
    private static User currentUser = null;
    /**
     * The full path of USER_FILENAME.
     */
    private static String fullPath;
    /**
     * The users that exist.
     */
    private ArrayList<User> users = new ArrayList<>();

    /**
     * Manages the users in USER_FILENAME.
     *
     * @param dirPath the full path of the directory the USER_FILENAME is in.
     */
    public UsersManager(String dirPath) {

        // Code modified from CSC207 lecture 7 serialize file
        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);


        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        // By precondition filePath file already exists.
        try {
            fullPath = dirPath + "/" + USER_FILENAME;
            File file = new File(fullPath);
            if (file.exists() && file.length() != 0) {
                readFromFile();
            } else {
                file.createNewFile();
                this.users = new ArrayList<>();
                System.out.println("Writes to file");
                writeToFile();
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }

    /**
     * Return currentUser.
     *
     * @return currentUser
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Set currentUser to newUser
     *
     * @param newUser user to set currentUser to.
     */
    public static void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

    /**
     * Reads users from USER_FILENAME and updates users to reflect this.
     */
    private void readFromFile() {

        // Code modified from CSC207 lecture 7 serialize file
        try {
            InputStream file = new FileInputStream(fullPath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            //deserialize the ArrayList<User>
            this.users = (ArrayList<User>) input.readObject();
            input.close();

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        } catch (ClassNotFoundException ex2) {
            logger.log(Level.SEVERE, "Class not found.", ex2);
        }
    }

    /**
     * Writes users to USER_FILENAME.
     */
    private void writeToFile() {

        // Code modified from CSC207 lecture 7 serialize file
        try {
            OutputStream file = new FileOutputStream(fullPath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            // serialize users
            output.writeObject(this.users);
            output.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot write to a file.", ex);
        }
    }

    /**
     * Add a user to users and update USER_FILENAME to reflect the updated users.
     *
     * @param newUser the user to add to users
     */
    public void addUser(User newUser) {
        this.users.add(newUser);
        writeToFile();
    }

    /**
     * Return whether a user with username and password exists.
     *
     * @param username username to check.
     * @param password password to check
     * @return true iff user with username and password exists.
     */
    public boolean checkUser(String username, String password) {
        if (this.users != null) {
            for (User curr : this.users) {
                if (curr.getUserName().equals(username) && curr.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return whether there does not already exist a user with username.
     *
     * @param username username to check
     * @return true iff no user exists with username
     */
    public boolean checkUsernameValid(String username) {
        if (this.users != null) {
            for (User curr : this.users) {
                if (curr.getUserName().equals(username)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Precondition: A User with username and password exists. Should call checkUser before.
     * <p>
     * Return the User in users with username and password.
     *
     * @param username username to check
     * @param password password to check
     * @return User with username and password from users asd
     */
    public User getUser(String username, String password) {
        for (User user : this.users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}