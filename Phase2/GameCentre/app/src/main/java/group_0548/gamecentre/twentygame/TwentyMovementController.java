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
        twentyManager.swipeUp(twentyManager.getBoard().getTiles());
    }

    void processDownMovement() {
        twentyManager.swipeDown(twentyManager.getBoard().getTiles());
    }

    void processLeftMovement() {
        twentyManager.swipeLeft(twentyManager.getBoard().getTiles());
    }

    void processRightMovement() {
        twentyManager.swipeRight(twentyManager.getBoard().getTiles());
    }

}
