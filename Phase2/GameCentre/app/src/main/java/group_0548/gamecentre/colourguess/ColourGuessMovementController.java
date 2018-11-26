package group_0548.gamecentre.colourguess;

import android.content.Context;

public class ColourGuessMovementController {
    /**
     * The board manager
     */
    private ColourManager colourManager = null;

    /**
     * The movement controller
     */
    ColourGuessMovementController() {
    }

    /**
     * Setting the board manager
     *
     * @param colourManager the board manager
     */
    void setColourManager(ColourManager colourManager) {
        this.colourManager = colourManager;
    }

    /**
     * Process a tap, swapping tiles if tap is valid
     *
     * @param context  the context
     * @param position the position of the tile
     */
    void processTapMovement(Context context, int position, boolean display) {
        colourManager.select(position);
    }
}

