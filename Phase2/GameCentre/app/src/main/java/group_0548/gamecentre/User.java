package group_0548.gamecentre;


import java.io.Serializable;
import java.util.HashMap;

import group_0548.gamecentre.slidingtiles.BoardManager;


/**
 * A user of the game center
 */
public class User implements Serializable {

    /**
     * The username of the user
     */
    private String userName;

    /**
     * The password of the user
     */
    private String password;

    /**
     * The hash map that maps a a user's game type (a game type is referring to
     * any arbitrary game's type complexity
     */
    private HashMap<String, BoardManager> hashMapOfPastGames = new HashMap<>();

    /**
     * The hash map that maps a a user's game type to its highest score in that
     * game type
     */
    private HashMap<String, Integer> hashMapOfHighScore = new HashMap<>();

    /**
     * A User with his corresponding username and password as input.
     *
     * @param userName the username
     * @param password the password
     */

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Saving a game into the hash map
     *
     * @param gameType a game type
     * @param game     the game that will be saved
     */
    public void saveGame(String gameType, BoardManager game) {
        this.hashMapOfPastGames.put(gameType, game);
    }

    /**
     * Loading the game from the hash map
     *
     * @param gameType the game type to load
     * @return The game to be loaded
     */
    public BoardManager loadGame(String gameType) {
        return this.hashMapOfPastGames.get(gameType);
    }

    /**
     * Getting the user name
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Getting the password
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Updating the score once the game is finished and
     * the new score is better than the last score.
     *
     * @param gameType a game type's score to be updated
     * @param order    the method for ordering the score
     * @param score    the new score
     */
    public void updateScore(String gameType, String order, int score) {
        if (this.hashMapOfHighScore.containsKey(gameType)) {
            if (order.equals("Ascending")) {
                if (this.hashMapOfHighScore.get(gameType) > score) {
                    this.hashMapOfHighScore.put(gameType, score);
                }
            } else if (order.equals("Descending")) {
                if (this.hashMapOfHighScore.get(gameType) < score) {
                    this.hashMapOfHighScore.put(gameType, score);
                }

            } else {
                this.hashMapOfHighScore.put(gameType, score);
            }
        }
    }
}