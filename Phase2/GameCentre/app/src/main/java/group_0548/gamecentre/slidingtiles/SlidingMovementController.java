package group_0548.gamecentre.slidingtiles;

import android.content.Context;
import android.widget.Toast;

/**
 * Controls the movement of the game
 */
public class SlidingMovementController {
    /**
     * The board manager
     */
    private SlidingManager slidingManager = null;

    /**
     * The movement controller
     */
    SlidingMovementController() {
    }

    /**
     * Setting the board manager
     *
     * @param slidingManager the board manager
     */
    void setSlidingManager(SlidingManager slidingManager) {
        this.slidingManager = slidingManager;
    }

    /**
     * Process a tap, swapping tiles if tap is valid
     *
     * @param context  the context
     * @param position the position of the tile
     */
    void processTapMovement(Context context, int position, boolean display) {
        if (slidingManager.isValidTap(position) && !slidingManager.puzzleSolved()) {
            slidingManager.touchMove(position);
            if (slidingManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else if (slidingManager.puzzleSolved()) {
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Process a tap for the undo button
     *
     * @param context the context
     */
    void processUndo(Context context) {
        if (this.slidingManager.ableToUndo()) {
            this.slidingManager.undoToPastState();
        } else {
            Toast.makeText(context, "No more undo", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Process a tap for the redo button
     *
     * @param context the context
     */
    void processRedo(Context context) {
        if (this.slidingManager.ableToRedo()) {
            this.slidingManager.redoToFutureState();
        } else {
            Toast.makeText(context, "No more redo", Toast.LENGTH_SHORT).show();
        }
    }

}
