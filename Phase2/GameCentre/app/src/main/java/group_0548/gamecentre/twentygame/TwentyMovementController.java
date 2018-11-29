package group_0548.gamecentre.twentygame;



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

}
