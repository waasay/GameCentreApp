package group_0548.gamecentre;

import java.io.Serializable;

public abstract class AbstractManager implements Serializable {

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


}
