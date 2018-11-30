package group_0548.gamecentre;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


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
    private HashMap<String, AbstractManager> hashMapOfPastGames = new HashMap<>();

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
    public void saveGame(String gameType, AbstractManager game) {
        this.hashMapOfPastGames.put(gameType, game);
    }

    /**
     * Loading the game from the hash map
     *
     * @param gameType the game type to load
     * @return The game to be loaded
     */
    public AbstractManager loadGame(String gameType) {
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
    public void updateScore(String gameType, int score, String order) {
        if (this.hashMapOfHighScore.containsKey(gameType)) {
            if ((order.equals("Ascending") && this.hashMapOfHighScore.get(gameType) > score)
                    || (order.equals("Descending") && this.hashMapOfHighScore.get(gameType) < score)) {
                this.hashMapOfHighScore.put(gameType, score);
            }
        } else {
            this.hashMapOfHighScore.put(gameType, score);
        }
    }


    ArrayList<String> getUserScore() {
        ArrayList<String> scoreContent = new ArrayList<>();
        String name = "";
        String score = "";
        for (Object o : hashMapOfHighScore.keySet().toArray()) {
            name = name + o.toString() + "\r\n";
        }
        for (Object o : hashMapOfHighScore.values().toArray()) {
            score = score + o.toString() + "\r\n";
        }
        scoreContent.add(name);
        scoreContent.add(score);
        return scoreContent;
    }

    HashMap<String, Integer> getHashMapOfHighScore() {
        return this.hashMapOfHighScore;
    }
}