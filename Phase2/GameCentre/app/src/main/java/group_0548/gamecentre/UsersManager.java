package group_0548.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;

public class UsersManager implements Serializable {

    /**
     * The current user.
     */
    private User currentUser = null;

    /**
     * The users that exist.
     */
    private ArrayList<User> users = null;


    UsersManager() {
        if (this.users == null) {
            this.users = new ArrayList<>();
        }
    }

    /**
     * Return currentUser.
     *
     * @return currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Set currentUser to newUser
     *
     * @param newUser user to set currentUser to.
     */
    void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

    /**
     * Add a user to users and update USER_FILENAME to reflect the updated users.
     *
     * @param newUser the user to add to users
     */
    void addUser(User newUser) {
        this.users.add(newUser);
    }

    /**
     * Return whether a user with username and password exists.
     *
     * @param username username to check.
     * @param password password to check
     * @return true iff user with username and password exists.
     */
    boolean checkUser(String username, String password) {
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
    boolean checkUsernameValid(String username) {
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
    User getUser(String username, String password) {
        for (User user : this.users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}