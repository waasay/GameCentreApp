package group_0548.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;

import group_0548.gamecentre.slidingtiles.Board;

/**
 * The states of the boards
 */
public class States implements Serializable {


    /**
     * An Arraylist of board.
     */
    private ArrayList<Board> boards;

    /**
     * Initialize the states.
     */
    public States() {
        this.boards = new ArrayList<>();
    }

    /**
     * updates the states of the boards
     *
     * @param newBoard the new board
     * @param maxUndo  the maximum number of undo
     */
    public void updateStates(Board newBoard, int maxUndo) {

        if (this.boards.size() < maxUndo + 1) {
            this.boards.add(newBoard);
        } else {
            this.boards.remove(0);
            this.boards.add(newBoard);
        }
    }

    /**
     * Keeping all the states of the boards until the index upTill
     *
     * @param upTill index for the ArrayList that indicates which
     *               boards will be, (boards in the ArrayList that has
     *               a larger index than upTill will not be kept)
     */
    public void keepStatesUpTill(int upTill) {
        ArrayList<Board> temp = new ArrayList<>();
        for (int i = 0; i <= upTill; i++) {
            temp.add(this.getBoards().get(i));
        }
        this.boards = temp;

    }

    /**
     * Getting the boards
     *
     * @return the boards
     */

    public ArrayList<Board> getBoards() {
        return this.boards;
    }
}
