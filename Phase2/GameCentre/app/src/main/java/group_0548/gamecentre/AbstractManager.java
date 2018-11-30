package group_0548.gamecentre;

import java.io.Serializable;
import java.util.Observable;

public abstract class AbstractManager<T> extends Observable implements Serializable {


    /**
     * The complexity of the game
     */
    protected String complexity;


    /**
     * The score of this board.
     */
    protected int score;

    /**
     * Abstract method to determine whether the board is solved, since it is intrinsically
     * linked to the game, it is implemented as an abstract method.
     *
     * @return Whether the board is solved.
     */
    public abstract boolean puzzleSolved();

    /**
     * Changing and notify the board for the user
     */
    protected void changeAndNotify() {
        setChanged();
        notifyObservers();
    }

    /**
     * Getter for complexity
     */
    public String getComplexity() {
        return this.complexity;
    }

    /**
     * Abstract getter for the board, since it is intrinsically
     * linked to the game, it is implemented as an abstract method.
     *
     * @return the board
     */
    public abstract T getBoard();

    /**
     * Return the score for the board.
     *
     * @return the score for the board.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Increase the score by adjustment
     */
    protected void increaseScore(int adjustment) {
        this.score += adjustment;
    }


}
