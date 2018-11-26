package group_0548.gamecentre.colourguess;

import android.content.Context;

public class ColourGuessMovementController {
    /**
     * The board manager
     */
    private ColourGuessManager colourGuessManager = null;

    /**
     * The movement controller
     */
    ColourGuessMovementController() {
    }

    /**
     * Setting the board manager
     *
     * @param colourGuessManager the board manager
     */
    void setColourGuessManager(ColourGuessManager colourGuessManager) {
        this.colourGuessManager = colourGuessManager;
    }

    /**
     * Process a tap, swapping tiles if tap is valid
     *
     * @param context  the context
     * @param position the position of the tile
     */
    void processTapMovement(Context context, int position, boolean display) {
        colourGuessManager.select(position);
    }
}

