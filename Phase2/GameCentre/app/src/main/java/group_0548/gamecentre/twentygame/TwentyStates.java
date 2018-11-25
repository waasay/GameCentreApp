package group_0548.gamecentre.twentygame;

import java.util.ArrayList;

public class TwentyStates {

    /**
     * An Arraylist of board.
     */
    private ArrayList<TwentyBoard> boards;

    /**
     * Initialize the states.
     */
    public TwentyStates() {
        this.boards = new ArrayList<>();
    }

    /**
     * updates the states of the boards
     *
     * @param newBoard the new board
     * @param maxUndo  the maximum number of undo
     */
    public void updateStates(TwentyBoard newBoard, int maxUndo) {

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
        ArrayList<TwentyBoard> temp = new ArrayList<>();
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

    public ArrayList<TwentyBoard> getBoards() {
        return this.boards;
    }
}

