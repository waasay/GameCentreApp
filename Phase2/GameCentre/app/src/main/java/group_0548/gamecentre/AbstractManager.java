package group_0548.gamecentre;

import java.io.Serializable;
import java.util.Observable;

public abstract class AbstractManager extends Observable implements Serializable {

    /**
     * The board being managed.
     */
    protected AbstractBoard board;

    /**
     * The complexity of the board.
     */
    protected String complexity;



    public abstract boolean puzzleSolved();

    public abstract String getComplexity();

    public abstract String getGameType();

    public void changeAndNotify(){
        setChanged();
        notifyObservers();
    }


}
