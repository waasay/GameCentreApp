package group_0548.gamecentre;

/**
 * Interface for any managerclass that requires undo (and redo) method
 */
public interface Undoable {

    /**
     * Check whether at the current phase it is possible to undo
     *
     * @return whether it is able to undo
     */
    boolean ableToUndo();

    /**
     * Check whether at the current phase it is possible to redo
     *
     * @return whether it is able to redo
     */
    boolean ableToRedo();

    /**
     * Undoing
     */
    void undoToPastState();

    /**
     * Redoing
     */
    void redoToFutureState();

    /**
     * Update the container class in any manager class that stores
     * past information after undoing
     */
    void updateStateAfterUndo();

}
