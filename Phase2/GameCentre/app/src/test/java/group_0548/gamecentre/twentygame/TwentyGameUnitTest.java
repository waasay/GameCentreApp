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

        this.regular2048Manager = new TwentyManager(4, 4, "medium", 3);
        this.hardCode2048Manager = new TwentyManager(4, 4, "medium", 1);
        this.solved2048Manager = new TwentyManager(4, 4, "medium", 1);
        this.lost2048Manager = new TwentyManager(4, 4, "medium", 1);
        List<Integer> hardCodeIDList = Arrays.asList(0, 1, 6, 3, 4, 5, 6, 0, 8, 9, 5, 5, 11, 11, 0, 1);
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
        ArrayList<Integer> idListAfterSwipe = new ArrayList<>();
        ArrayList<Integer> autogenList = new ArrayList<>();
        for (int pos = 0; pos < 16; pos++){
            int r = pos / 4;
            int c = pos % 4;
            if ((pos != 8) & (pos != 12) & (pos != 13)) {
                idListAfterSwipe.add(this.hardCode2048Manager.getBoard().getTile(r, c).getId());
            }
            else{
                autogenList.add(this.hardCode2048Manager.getBoard().getTile(r, c).getId());
            }
        }
        List<Integer> idListToCompare = Arrays.asList(0, 1, 6, 3, 4, 5, 6, 0, 8, 9, 6, 0, 1);
        assertEquals(idListToCompare, idListAfterSwipe);
        assertEquals(true, (autogenList.contains(0) || autogenList.contains(1)));
    }
    @Test
    public void testSwipeLeft(){
        this.setupAndReset();
        this.hardCode2048Manager.swipeLeft(this.hardCode2048Manager.getBoard().getTiles());
        ArrayList<Integer> idListAfterSwipe = new ArrayList<>();
        ArrayList<Integer> autogenList = new ArrayList<>();
        for (int pos = 0; pos < 16; pos++){
            int r = pos / 4;
            int c = pos % 4;
            if ((pos != 11) & (pos != 14) & (pos != 15)) {
                idListAfterSwipe.add(this.hardCode2048Manager.getBoard().getTile(r, c).getId());
            }
            else{
                autogenList.add(this.hardCode2048Manager.getBoard().getTile(r, c).getId());
            }
        }
        List<Integer> idListToCompare = Arrays.asList(0, 1, 6, 3, 4, 5, 6, 0, 8, 9, 6, 0, 1);
        assertEquals(idListToCompare, idListAfterSwipe);
        assertEquals(true, (autogenList.contains(0) || autogenList.contains(1)));
    }
    @Test
    public void testSwipeDown(){
        this.setupAndReset();
        this.hardCode2048Manager.swipeDown(this.hardCode2048Manager.getBoard().getTiles());
        ArrayList<Integer> idListAfterSwipe = new ArrayList<>();
        ArrayList<Integer> autogenList = new ArrayList<>();
        for (int pos = 0; pos < 16; pos++){
            int r = pos / 4;
            int c = pos % 4;
            if ((pos != 0) & (pos != 1) & (pos != 2)) {
                idListAfterSwipe.add(this.hardCode2048Manager.getBoard().getTile(r, c).getId());
            }
            else{
                autogenList.add(this.hardCode2048Manager.getBoard().getTile(r, c).getId());
            }
        }
        List<Integer> idListToCompare = Arrays.asList(3, 0, 1, 7, 0, 4, 5, 5, 5, 8, 9,0, 1);
        assertEquals(idListToCompare, idListAfterSwipe);
        assertEquals(true, (autogenList.contains(0) || autogenList.contains(1)));
    }
    @Test
    public void testSwipeUp(){
        this.setupAndReset();
        this.hardCode2048Manager.swipeUp(this.hardCode2048Manager.getBoard().getTiles());
        ArrayList<Integer> idListAfterSwipe = new ArrayList<>();
        ArrayList<Integer> autogenList = new ArrayList<>();
        for (int pos = 0; pos < 16; pos++){
            int r = pos / 4;
            int c = pos % 4;
            if ((pos != 12) & (pos != 13) & (pos != 14)) {
                idListAfterSwipe.add(this.hardCode2048Manager.getBoard().getTile(r, c).getId());
            }
            else{
                autogenList.add(this.hardCode2048Manager.getBoard().getTile(r, c).getId());
            }
        }
        List<Integer> idListToCompare = Arrays.asList(0, 1, 7, 3, 4, 5, 5, 0, 8, 9, 0, 5, 1);
        assertEquals(idListToCompare, idListAfterSwipe);
        assertEquals(true, (autogenList.contains(0) || autogenList.contains(1)));
    }
    @Test
    public void testAbleToUndo(){
        this.setupAndReset();
        assertEquals(false, this.hardCode2048Manager.ableToUndo());
        this.hardCode2048Manager.swipeLeft(this.hardCode2048Manager.getBoard().getTiles());
        assertEquals(true, this.hardCode2048Manager.ableToUndo());
        this.setupAndReset();
        assertEquals(false, this.solved2048Manager.ableToUndo());
    }
    @Test
    public void testAbleToRedo(){
        this.setupAndReset();
        assertEquals(false, this.hardCode2048Manager.ableToRedo());
        this.hardCode2048Manager.swipeLeft(this.hardCode2048Manager.getBoard().getTiles());
        this.hardCode2048Manager.undoToPastState();
        assertEquals(true, this.hardCode2048Manager.ableToRedo());
    }
    @Test
    public void testUndoToPastState(){
        this.setupAndReset();
        this.hardCode2048Manager.swipeLeft(this.hardCode2048Manager.getBoard().getTiles());
        int currUndo = this.hardCode2048Manager.getCurrUndo();
        this.hardCode2048Manager.undoToPastState();
        assertEquals(currUndo - 1, this.hardCode2048Manager.getCurrUndo());
    }
    @Test
    public void testRedoToFutureState(){
        this.setupAndReset();
        this.hardCode2048Manager.swipeLeft(this.hardCode2048Manager.getBoard().getTiles());
        this.hardCode2048Manager.undoToPastState();
        int currUndo = this.hardCode2048Manager.getCurrUndo();
        this.hardCode2048Manager.redoToFutureState();
        assertEquals(currUndo + 1, this.hardCode2048Manager.getCurrUndo());
    }
    @Test
    public void testUndoAfterMaxUndo(){
        this.setupAndReset();
        this.regular2048Manager.swipeLeft(this.regular2048Manager.getBoard().getTiles());
        this.regular2048Manager.swipeUp(this.regular2048Manager.getBoard().getTiles());
        this.regular2048Manager.swipeDown(this.regular2048Manager.getBoard().getTiles());
        this.regular2048Manager.swipeRight(this.regular2048Manager.getBoard().getTiles());
        this.regular2048Manager.undoToPastState();
        this.regular2048Manager.undoToPastState();
        this.regular2048Manager.undoToPastState();
        assertEquals(false, this.regular2048Manager.ableToUndo());
    }
    @Test
    public void testUpdateStatesAfterUndo(){
        this.setupAndReset();
        this.regular2048Manager.swipeLeft(this.regular2048Manager.getBoard().getTiles());
        this.regular2048Manager.swipeUp(this.regular2048Manager.getBoard().getTiles());
        this.regular2048Manager.swipeRight(this.regular2048Manager.getBoard().getTiles());
        this.regular2048Manager.undoToPastState();
        this.regular2048Manager.undoToPastState();
        this.regular2048Manager.swipeDown(this.regular2048Manager.getBoard().getTiles());
        assertEquals(3, this.regular2048Manager.getPastStates().getBoards().size());

        this.setupAndReset();
        this.regular2048Manager.swipeLeft(this.regular2048Manager.getBoard().getTiles());
        this.regular2048Manager.swipeUp(this.regular2048Manager.getBoard().getTiles());
        this.regular2048Manager.swipeDown(this.regular2048Manager.getBoard().getTiles());
        this.regular2048Manager.undoToPastState();
        this.regular2048Manager.redoToFutureState();
        this.regular2048Manager.undoToPastState();
        this.regular2048Manager.undoToPastState();
        this.regular2048Manager.undoToPastState();
        this.regular2048Manager.swipeRight(this.regular2048Manager.getBoard().getTiles());
        assertEquals(2, this.regular2048Manager.getPastStates().getBoards().size());

    }


}
