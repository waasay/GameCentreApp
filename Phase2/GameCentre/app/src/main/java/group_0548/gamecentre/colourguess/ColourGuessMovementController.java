package group_0548.gamecentre.colourguess;

import android.content.Context;
import android.widget.Toast;

public class ColourGuessMovementController {
    /**
     * The board manager
     */
    private BoardManager boardManager = null;

    /**
     * The movement controller
     */
    ColourGuessMovementController() {
    }

    /**
     * Setting the board manager
     *
     * @param boardManager the board manager
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Process a tap, swapping tiles if tap is valid
     *
     * @param context  the context
     * @param position the position of the tile
     */
    void processTapMovement(Context context, int position, boolean display) {
        if (boardManager.isValidTap(position) && !boardManager.puzzleSolved()) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else if (boardManager.puzzleSolved()) {
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}

}
