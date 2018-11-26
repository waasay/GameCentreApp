package group_0548.gamecentre.twentygame;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



import static org.junit.Assert.assertEquals;

/**
 * Unit test for the 2048 game
 */
public class TwentyGameUnitTest {

    /**
     * The 2048 board for testing
     */
    private TwentyManager regular2048Manager;

    /**
     * The hardcoded board for testing (the configuration of the board will
     * be hardcoded.
     */
    private TwentyManager hardCode2048Manager;

    /**
     * A solved board for testing (contains the 2048 tile)
     */
    private TwentyManager solved2048Manager;

    /**
     * A board where no moves are possible and the game is lost.
     */
    private TwentyManager lost2048Manager;

    /**
     * Setup and reset the boards for testing
     */
    public void setupAndReset(){

        this.regular2048Manager = new TwentyManager(4,4,"medium",1);
        this.hardCode2048Manager = new TwentyManager(4,4,"medium",1);
        this.solved2048Manager = new TwentyManager(4,4,"medium",1);
        List<Integer> hardCodeIDList = Arrays.asList(0,1,2,3,4,5,6,7,8,9,6,5,4,3,11,11);
        List<Integer> solvedIDList = Arrays.asList(11,11,11,11,6,7,8,9,9,8,7,10,9,8,7,6);
        List<TwentyTile> hardcodedTiles = new ArrayList<>();
        List<TwentyTile> solvedTiles = new ArrayList<>();
        for (int j = 0; j < this.regular2048Manager.getBoard().numTiles(); j++) {
            hardcodedTiles.add(new TwentyTile(hardCodeIDList.get(j)));
            solvedTiles.add(new TwentyTile(solvedIDList.get(j)));
        }
        TwentyBoard hardcodeBoard = new TwentyBoard(hardcodedTiles,0, 4, 4);
        TwentyBoard solvedBoard = new TwentyBoard(solvedTiles,0, 4, 4);
        TwentyTile[][] hardcodeTiles = hardcodeBoard.getTiles();
        TwentyTile[][] newTiles = solvedBoard.getTiles();
        this.solved2048Manager.getBoard().setTiles(newTiles);
        this.hardCode2048Manager.getBoard().setTiles(hardcodeTiles);
    }


}
