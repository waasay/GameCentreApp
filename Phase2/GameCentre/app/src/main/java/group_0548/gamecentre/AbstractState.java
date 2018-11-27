package group_0548.gamecentre;

import java.util.ArrayList;


public abstract class AbstractState<T> {

    /**
     * updates the states of the boards
     *
     * @param newBoard the new board
     * @param maxUndo  the maximum number of undo
     */
    abstract public void updateStates(T newBoard, int maxUndo);


    /**
     * Keeping all the states of the boards until the index upTill
     *
     * @param upTill index for the ArrayList that indicates which
     *               boards will be, (boards in the ArrayList that has
     *               a larger index than upTill will not be kept)
     */

    abstract public void keepStatesUpTill(int upTill);


    /**
     * Getting the boards
     *
     * @return the boards
     */

    abstract public ArrayList<T> getBoards();


}
