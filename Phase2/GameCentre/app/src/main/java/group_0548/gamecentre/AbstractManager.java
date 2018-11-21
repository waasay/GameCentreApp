package group_0548.gamecentre;

public abstract class AbstractManager {

    /**
     * The board being managed.
     */
    private AbstractBoard board;
    /**
     * The state object that represents the past MAX_UNDO number of states
     * and the current states
     */
    private States pastStates = new States();
    /**
     * The complexity of the board.
     */
    private String complexity;



    public abstract boolean puzzleSolved();

    public abstract AbstractBoard getBoard();

    public abstract String getComplexity();

    public abstract String getGameType();


}
