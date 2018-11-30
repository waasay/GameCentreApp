package group_0548.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The score board.
 */
public class ScoreBoard implements Serializable {
    /**
     * The number of scores to be stored.
     */
    private int numScore;

    /**
     * The LinkedHashMap stores the top five users with score.
     */
    private LinkedHashMap<String, Integer> topScores;

    /**
     * The scoreboard.
     *
     * @param num maximum number of users to store in the scoreboard.
     */
    ScoreBoard(int num) {
        this.numScore = num;
        this.topScores = new LinkedHashMap<>();
    }

    /**
     * Add score to the score board.
     *
     * @param name  the name of the user.
     * @param score the score for the current finished game.
     */
    void addScore(String name, int score, String order) {
        if (this.topScores.size() < this.numScore) {
            if (this.topScores.containsKey(name)) {
                if ((order.equals("Ascending") && this.topScores.get(name) > score)
                        || (order.equals("Descending") && this.topScores.get(name) < score)) {
                    this.topScores.put(name, score);
                }
            } else {
                this.topScores.put(name, score);
            }
        } else {
            this.topScores.put(name, score);
            this.topScores = sort(this.topScores);
            Object key = this.topScores.keySet().toArray()[this.topScores.keySet().size() - 1];
            String lastKey = key.toString();
            this.topScores.remove(lastKey);
        }
        this.topScores = sort(this.topScores);
    }


    /**
     * Sort the scoreboard in ascending order. The lower the score, the better.
     *
     * @param unsorted the unsorted scoreboard.
     * @return sorted scoreboard.
     */
    private LinkedHashMap<String, Integer> sort(LinkedHashMap<String, Integer> unsorted) {
        return unsorted.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1,
                        LinkedHashMap::new));
    }

    /**
     * Get a list of the best players and their scores
     *
     * @return an array list of the best players with their user names
     * and their corresponding score.
     */
    ArrayList<String> getScoreContent(String order) {
        ArrayList<String> ids = new ArrayList<>();
        String name = "";
        String score = "";
        if (order.equals("Ascending")) {
            for (Object o : topScores.keySet().toArray()) {
                name = name + o.toString() + "\r\n";
            }
            for (Object o : topScores.values().toArray()) {
                score = score + o.toString() + "\r\n";
            }
        } else if (order.equals("Descending")) {
            for (Object o : topScores.keySet().toArray()) {
                name = o.toString() + "\r\n" + name;
            }
            for (Object o : topScores.values().toArray()) {
                score = o.toString() + "\r\n" + score;
            }
        }
        ids.add(name);
        ids.add(score);
        return ids;
    }

    /**
     * Getters for top scores
     *
     * @return the top scores
     */
    LinkedHashMap<String, Integer> getTopScores() {
        return this.topScores;
    }
}