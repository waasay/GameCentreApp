package group_0548.gamecentre.twentygame;


import android.content.Context;
import android.widget.Toast;

public class TwentyMovementController {
    /**
     * The board manager
     */
    private TwentyManager twentyManager = null;

    /**
     * The movement controller
     */
    TwentyMovementController() {
    }

    /**
     * Setting the board manager
     *
     * @param slidingManager the board manager
     */
    void setTwentyManager(TwentyManager slidingManager) {
        this.twentyManager = slidingManager;
    }


    void processUpMovement() {
        twentyManager.swipeUp();
    }

    void processDownMovement() {
        twentyManager.swipeDown();
    }

    void processLeftMovement() {
        twentyManager.swipeLeft();
    }

    void processRightMovement() {
        twentyManager.swipeRight();
    }

    /**
     * Process a tap for the undo button
     * @param context the context
     */
    void processUndo(Context context){
        if (this.twentyManager.ableToUndo()){
            this.twentyManager.undoToPastState();
        }
        else{
            Toast.makeText(context, "No more undo", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Process a tap for the redo button
     * @param context the context
     */
    void processRedo(Context context){
        if (this.twentyManager.ableToRedo()){
            this.twentyManager.redoToFutureState();
        }
        else{
            Toast.makeText(context, "No more redo", Toast.LENGTH_SHORT).show();
        }
    }
}
