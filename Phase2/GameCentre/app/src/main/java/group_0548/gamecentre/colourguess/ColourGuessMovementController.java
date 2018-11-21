package group_0548.gamecentre.colourguess;

import android.content.Context;
import android.widget.Toast;

public class ColourGuessMovementController {
    /**
     * The board manager
     */
    private MemoryManager memoryManager = null;

    /**
     * The movement controller
     */
    ColourGuessMovementController() {
    }

    /**
     * Setting the board manager
     *
     * @param memoryManager the board manager
     */
    void setBoardManager(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }

    /**
     * Process a tap, swapping tiles if tap is valid
     *
     * @param context  the context
     * @param position the position of the tile
     */
    void processTapMovement(Context context, int position, boolean display) {
        memoryManager.select(position);
    }
}

