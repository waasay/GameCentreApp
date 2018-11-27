package group_0548.gamecentre;

import java.io.Serializable;
import java.util.Observable;

public abstract class AbstractManager<T> extends Observable implements Serializable {

    /**
     * The board to manage
     */
    protected T board;

    /**
     * The complexity of the game
     */
    protected String complexity;


    /**
     * The score of this board.
     */
    protected int score;


    public abstract boolean puzzleSolved();


    public void changeAndNotify() {
        setChanged();
        notifyObservers();
    }

    /**
     * Getter for complexity
     */
    public String getComplexity(){
        return this.complexity;
    }


    public T getBoard(){
        return this.board;
    }

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
    public void increaseScore(int adjustment){
        this.score += adjustment;
    }


}
