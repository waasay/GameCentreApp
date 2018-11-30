package group_0548.gamecentre;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A manager for the scoreboards
 */

public class ScoreBoardManager implements Serializable {

    /**
     * A hash map that maps the difficulty of the game
     * to the scoreboard that difficulty, each scoreboard
     * displays the best 5 players
     */
    private HashMap<String, ScoreBoard> scoreBoards = null;

    /**
     * Constructor for the ScoreBoardManager class
     */
    public ScoreBoardManager() {
        if (scoreBoards == null) {
            scoreBoards = new HashMap<>();
            ScoreBoard easyBoard = new ScoreBoard(5);
            ScoreBoard mediumBoard = new ScoreBoard(5);
            ScoreBoard hardBoard = new ScoreBoard(5);
            scoreBoards.put("Easy", easyBoard);
            scoreBoards.put("Medium", mediumBoard);
            scoreBoards.put("Hard", hardBoard);
        }
    }

    /**
     * Updating the scoreboard
     *
     * @param complexity the difficulty of the game
     * @param name       the username of the player to be updated to the scoreboard
     * @param score      the score of that player to be updated to the scoreboard
     */
    public void updateScoreBoard(String complexity, String name, int score, String order) {
        scoreBoards.get(complexity).addScore(name, score, order);
    }

    /**
     * Getter of a scoreboard specified by the difficulty
     *
     * @param complexity the difficulty of the game
     * @return the scoreboard of the given difficulty
     */
    ScoreBoard getScoreBoard(String complexity) {
        return scoreBoards.get(complexity);
    }
}
