package group_0548.gamecentre.twentygame;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

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
    public void setupAndReset() {

        this.regular2048Manager = new TwentyManager(4, 4, "medium", 1);
        this.hardCode2048Manager = new TwentyManager(4, 4, "medium", 1);
        this.solved2048Manager = new TwentyManager(4, 4, "medium", 1);
        this.lost2048Manager = new TwentyManager(4, 4, "medium", 1);
        List<Integer> hardCodeIDList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 0, 8, 9, 5, 5, 11, 11, 0, 1);
        List<Integer> solvedIDList = Arrays.asList(11, 11, 11, 11, 6, 7, 8, 9, 9, 8, 7, 10, 9, 8, 7, 6);
        List<Integer> lostIDList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 0, 1, 2, 3, 4);
        List<TwentyTile> hardcodedTiles = new ArrayList<>();
        List<TwentyTile> solvedTiles = new ArrayList<>();
        List<TwentyTile> lostTiles = new ArrayList<>();
        for (int j = 0; j < this.regular2048Manager.getBoard().numTiles(); j++) {
            hardcodedTiles.add(new TwentyTile(hardCodeIDList.get(j)));
            solvedTiles.add(new TwentyTile(solvedIDList.get(j)));
            lostTiles.add(new TwentyTile(lostIDList.get(j)));
        }
        TwentyBoard hardcodeBoard = new TwentyBoard(hardcodedTiles, 4, 4);
        TwentyBoard solvedBoard = new TwentyBoard(solvedTiles, 4, 4);
        TwentyBoard lostBoard = new TwentyBoard(lostTiles, 4, 4);
        TwentyTile[][] hardcodedTiles2D = hardcodeBoard.getTiles();
        TwentyTile[][] solvedTiles2D = solvedBoard.getTiles();
        TwentyTile[][] lostTiles2D = lostBoard.getTiles();
        this.solved2048Manager.getBoard().setTiles(solvedTiles2D);
        this.hardCode2048Manager.getBoard().setTiles(hardcodedTiles2D);
        this.lost2048Manager.getBoard().setTiles(lostTiles2D);
    }


    /**
     * Test puzzleLost actually works
     */
    @Test
    public void testPuzzleLost() {
        this.setupAndReset();
        assertEquals(true, this.lost2048Manager.puzzleLost());
        assertEquals(false, this.hardCode2048Manager.puzzleLost());
    }

    /**
     * Test puzzleSolved actually works
     */
    @Test
    public void testPuzzleSolved(){
        this.setupAndReset();
        assertEquals(true,this.solved2048Manager.puzzleSolved());
        assertEquals(false,this.regular2048Manager.puzzleSolved());
    }
    @Test
    public void testSurroundTile(){
        this.setupAndReset();
        HashMap<String, TwentyTile> map = new HashMap<>();
        // test the surround tiles at position 0
        map.put("above", null);
        map.put("below", this.hardCode2048Manager.getBoard().getTile(4 / 4, 4 % 4));
        map.put("left", null);
        map.put("right", this.hardCode2048Manager.getBoard().getTile(1 / 4, 1 % 4));
        assertEquals(map,this.hardCode2048Manager.getSurroundTiles(0));
        // test the surround tiles at position 5
        map.put("above", this.hardCode2048Manager.getBoard().getTile(1 / 4, 1 % 4));
        map.put("below", this.hardCode2048Manager.getBoard().getTile(9 / 4, 9 % 4));
        map.put("left", this.hardCode2048Manager.getBoard().getTile(4/ 4, 4 % 4));
        map.put("right", this.hardCode2048Manager.getBoard().getTile(6 / 4, 6 % 4));
        assertEquals(map,this.hardCode2048Manager.getSurroundTiles(5));
    }
    @Test
    public void testSwipeRight(){
        this.setupAndReset();
        this.hardCode2048Manager.swipeRight(this.hardCode2048Manager.getBoard().getTiles());
        ArrayList<Integer> mainList = new ArrayList<>();
        /*for (int r = 0; r < 4; r++){
            for (int c = 0; c < 4; c++){
                int position =
                if ((r != 2 && c != 0) & (r != 3 && c != 0) & (r != 3 && c != 1)){
                    mainList.add(this.hardCode2048Manager.getBoard().getTile(r, c).getId());
                }

            }
        }*/
        for (int pos = 0; pos < 16; pos++){
            int r = pos / 4;
            int c = pos % 4;
            if ((pos != 8) & (pos != 12) & (pos != 13)) {
                mainList.add(this.hardCode2048Manager.getBoard().getTile(r, c).getId());
            }
        }
        List<Integer> otherList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 0, 8, 9, 6, 0, 1);
        assertEquals(otherList, mainList);
    }

}
