package group_0548.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The container class of a past states of a board
 */
public class States<T> implements Serializable {


    /**
     * An Arraylist of board.
     */
    private List<T> boards;

    /**
     * The number of undos that game can do.
     */
    private int maxUndo;

    /**
     * Initialize the states.
     */
    public States(int maxUndo) {
        this.boards = new ArrayList<>();
        this.maxUndo = maxUndo;
    }

    /**
     * updates the states of the boards
     *
     * @param newBoard the new board
     */
    public void updateStates(T newBoard) {

        if (this.boards.size() < this.maxUndo + 1) {
            this.boards.add(newBoard);
        } else {
            this.boards.remove(0);
            this.boards.add(newBoard);
        }
    }

    /**
     * Keeping all the states of the boards until the index upTill, if upTill is larger
     * or equal to the size of the boards index out of bound exception will be thrown.
     *
     * @param upTill index for the ArrayList that indicates which
     *               boards will be, (boards in the ArrayList that has
     *               a larger index than upTill will not be kept)
     */
    public void keepStatesUpTill(int upTill) {
        List<T> temp = new ArrayList<>();
        if (upTill < this.getBoards().size()) {
            for (int i = 0; i <= upTill; i++) {
                temp.add(this.getBoards().get(i));
            }
            this.boards = temp;
        } else {
            throw new IndexOutOfBoundsException("Index " + Integer.toString(upTill) + " is out of" +
                    " bound.");
        }

    }

    /**
     * Getting the boards
     *
     * @return the boards
     */

    public List<T> getBoards() {
        return this.boards;
    }

}
