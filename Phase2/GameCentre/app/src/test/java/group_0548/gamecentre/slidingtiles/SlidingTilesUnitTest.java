package group_0548.gamecentre.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



import static org.junit.Assert.*;

/**
 * Unit test for the sliding tiles game
 */
public class SlidingTilesUnitTest {
    /**
     * The medium complexity board manager for testing
     */
    private SlidingManager mediumSlidingManager;
    /**
     * The medium complexity board manger for testing, the board for this
     * one however is solved
     */
    private SlidingManager solvedSlidingManager;
    /**
     *  Medium complexity SlidingManager with the hardcoded board
     */
    private SlidingManager hardcodedSlidingManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<SlidingTile> makeTiles(int rowNum, int colNum) {
        List<SlidingTile> tiles = new ArrayList<>();
        for (int i = 0; i < colNum*rowNum; i++){
            tiles.add(new SlidingTile(i,rowNum,colNum));
        }

        return tiles;
    }

    /**
     * Find the empty tile in board
     * @param board the board to look for the empty tile
     * @return the empty tile
     */
    private SlidingTile findEmptyTile(SlidingBoard board) {
        for (int i = 0; i < board.getNumRow(); i++){
            for (int j = 0; j < board.getNumCol(); j++){
                if (board.getTile(i,j).getId() == 24) {
                    return board.getTile(i, j);
                }
            }
        }
        return null;
    }


    /**
     * Get the position of a given tile in the given board
     * @param tile the tile to look for the position
     * @param board the board to look for the tile
     * @return the position
     */
    private int getPosition(SlidingTile tile, SlidingBoard board) {
        int toReturn = 0;
        for (int n = 0; n < board.numTiles(); n++) {
            int i = n / board.getNumRow();
            int j = n % board.getNumCol();
            if (board.getTile(i, j) == tile) {
                toReturn = n;
                break;
            }
        }
        return toReturn;
    }

    /**
     * Setup the boards for testing, 1 solved board (at medium complexity)
     * and 1 board at medium complexity
     */
    private void setUpAndResetBoard() {
        this.mediumSlidingManager = new SlidingManager(4, 4, "Medium", 3);
        this.solvedSlidingManager = new SlidingManager(4, 4, "Medium", 3);
        this.hardcodedSlidingManager = new SlidingManager(4,4,"Medium",3);
        List<SlidingTile> tiles = makeTiles(4,4);
        SlidingBoard board = new SlidingBoard(tiles, 4,4);
        SlidingTile[][] newTiles = board.getTiles();
        this.solvedSlidingManager.getBoard().setTiles(newTiles);
        List<SlidingTile> hardcodedTiles = new ArrayList<>();
        List<Integer> idList = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,15,12,13,14);
        for (int j = 0; j < this.hardcodedSlidingManager.getBoard().numTiles(); j++) {
            hardcodedTiles.add(new SlidingTile(idList.get(j),
                    this.hardcodedSlidingManager.getBoard().getNumRow(),
                    this.hardcodedSlidingManager.getBoard().getNumRow()));
        }
        SlidingBoard board2= new SlidingBoard(hardcodedTiles, 4, 4);
        SlidingTile[][] newTiles2 = board2.getTiles();
        this.hardcodedSlidingManager.getBoard().setTiles(newTiles2);

    }

    /**
     * Test whether isSolved works
     */
    @Test
    public void testIsSolved(){
        this.setUpAndResetBoard();
        assertTrue(this.solvedSlidingManager.puzzleSolved());
        assertFalse( this.mediumSlidingManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        this.setUpAndResetBoard();
        this.solvedSlidingManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(1, this.solvedSlidingManager.getBoard().getTile(0, 0).getId());
        assertEquals(0, this.solvedSlidingManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test getSurroundTile works, so could use it to test other methods.
     */
    @Test
    public void testGetSurroundTiles(){
        this.setUpAndResetBoard();
        SlidingTile emptyTile = this.findEmptyTile(this.solvedSlidingManager.getBoard());
        int emptyPos = this.getPosition(emptyTile,this.solvedSlidingManager.getBoard());
        HashMap<String, SlidingTile> around = this.solvedSlidingManager.getSurroundTiles(emptyPos);
        assertNull(around.get("below"));
        assertNull(around.get("right"));
        assertEquals(14,around.get("left").getId());
        assertEquals(11,around.get("above").getId());

    }

    /**
     * Test whether isValidTap works.
     */
    @Test
    public void testIsValidTap() {
        setUpAndResetBoard();
        SlidingTile emptyTile = this.findEmptyTile(this.hardcodedSlidingManager.getBoard());
        int emptyPosition = this.getPosition(emptyTile,this.hardcodedSlidingManager.getBoard());
        HashMap<String, SlidingTile> around = this.hardcodedSlidingManager.getSurroundTiles(emptyPosition);
        int abovePos = this.getPosition(around.get("above"), this.hardcodedSlidingManager.getBoard());
        assertTrue(this.hardcodedSlidingManager.isValidTap(abovePos));
        assertFalse(this.hardcodedSlidingManager.isValidTap(emptyPosition));
    }

    /**
     * Test score increase after touch move
     */
    @Test
    public void testScoreIncreaseAfterTouchMove() {
        this.setUpAndResetBoard();
        SlidingTile emptyTile = this.findEmptyTile(this.mediumSlidingManager.getBoard());
        int emptyPos = this.getPosition(emptyTile, this.mediumSlidingManager.getBoard());
        HashMap<String, SlidingTile> around = this.mediumSlidingManager.getSurroundTiles(emptyPos);
        int oldScore = this.mediumSlidingManager.getScore();
        int valueOfResetUndo = this.mediumSlidingManager.getMaxUndo() - 1;
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(abovePos);
            assertEquals(oldScore + 1, this.mediumSlidingManager.getScore());
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(belowPos);
            assertEquals(oldScore + 1, this.mediumSlidingManager.getScore());
        }
        assertEquals(valueOfResetUndo, this.mediumSlidingManager.getCurrUndo());
    }

    /**
     * Test whether current undo resets after each touch move
     */
    @Test
    public void testCurrentUndoResetAfterTouchMove(){
        this.setUpAndResetBoard();
        SlidingTile emptyTile = this.findEmptyTile(this.mediumSlidingManager.getBoard());
        int emptyPos = this.getPosition(emptyTile, this.mediumSlidingManager.getBoard());
        HashMap<String, SlidingTile> around = this.mediumSlidingManager.getSurroundTiles(emptyPos);
        int valueOfResetUndo = this.mediumSlidingManager.getMaxUndo() - 1;
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(abovePos);
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(belowPos);
        }
        assertEquals(valueOfResetUndo, this.mediumSlidingManager.getCurrUndo());
    }

    /**
     * Test whether ableToUndo and ableToRedo actually works, and whether it actually
     * prevents the user from undoing when the board is solved, these are put together
     * because how similar the two methods are.
     */
    @Test
    public void testAbleToUndoAndAbleToRedo(){
        this.setUpAndResetBoard();
        assertFalse(this.solvedSlidingManager.ableToUndo());
        SlidingTile emptyTile = this.findEmptyTile(this.mediumSlidingManager.getBoard());
        int emptyPos = this.getPosition(emptyTile,this.mediumSlidingManager.getBoard());
        HashMap<String, SlidingTile> around = this.mediumSlidingManager.getSurroundTiles(emptyPos);
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(abovePos);
            assertTrue(this.mediumSlidingManager.ableToUndo());
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(belowPos);
            assertTrue(this.mediumSlidingManager.ableToUndo());
        }
        this.mediumSlidingManager.undoToPastState();
        assertTrue(this.mediumSlidingManager.ableToRedo());
        assertFalse(this.solvedSlidingManager.ableToRedo());
        assertFalse(this.solvedSlidingManager.ableToUndo());


    }
    /**
     * Test whether updates states work when performing a series of moves
     */
    @Test
    public void testUpdateStatesWith10Moves(){
        this.setUpAndResetBoard();
        for (int moves = 0; moves < 10; moves++){
            SlidingTile emptyTile = findEmptyTile(this.mediumSlidingManager.getBoard());
            int emptyPos = this.getPosition(emptyTile,this.mediumSlidingManager.getBoard());
            HashMap<String, SlidingTile> around = this.mediumSlidingManager.getSurroundTiles(emptyPos);
            if (around.get("above") != null) {
                int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
                this.mediumSlidingManager.touchMove(abovePos);
            } else {
                int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
                this.mediumSlidingManager.touchMove(belowPos);
            }
        }

        assertEquals(4,this.mediumSlidingManager.getPastStates().getBoards().size());

    }

    /**
     * Test whether undo and redo actually points to the current board
     * in pastStates (These two are put together because how similar the two methods are
     *
     */
    @Test
    public void testUpdateToPastStateAndRedoToFutureState(){
        this.setUpAndResetBoard();
        SlidingTile emptyTile = findEmptyTile(this.mediumSlidingManager.getBoard());
        int emptyPos = this.getPosition(emptyTile,this.mediumSlidingManager.getBoard());
        HashMap<String, SlidingTile> around = this.mediumSlidingManager.getSurroundTiles(emptyPos);
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(abovePos);
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(belowPos);
        }
        emptyTile = findEmptyTile(this.mediumSlidingManager.getBoard());
        emptyPos = this.getPosition(emptyTile,this.mediumSlidingManager.getBoard());
        around = this.mediumSlidingManager.getSurroundTiles(emptyPos);
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(abovePos);
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(belowPos);
        }
        int currUndo = this.mediumSlidingManager.getCurrUndo();
        this.mediumSlidingManager.undoToPastState();

        assertEquals(currUndo-1,this.mediumSlidingManager.getCurrUndo());

        this.mediumSlidingManager.undoToPastState();
        currUndo = this.mediumSlidingManager.getCurrUndo();
        this.mediumSlidingManager.redoToFutureState();
        assertEquals(currUndo+1, this.mediumSlidingManager.getCurrUndo());
    }




    /**
     * Test getBackground SlidingTile
     */
    @Test
    public void testGetBackground(){
        this.setUpAndResetBoard();
        SlidingTile emptyTile = findEmptyTile(this.solvedSlidingManager.getBoard());
        SlidingTile toCompare = this.solvedSlidingManager.getBoard().getTile(0,0);
        assertNotEquals(emptyTile.getBackground(),toCompare.getBackground());
    }

    /**
     * Test CompareTo actually works
     */
    @Test
    public void testCompareTo(){
        this.setUpAndResetBoard();
        SlidingTile emptyTile = findEmptyTile(this.solvedSlidingManager.getBoard());
        SlidingTile toCompare = this.solvedSlidingManager.getBoard().getTile(0,0);
        assertEquals(-24, emptyTile.compareTo(toCompare));
    }

    /**
     * Test whether code actually stops the user from undoing after undoing MAX_UNDO number
     * of times.
     */
    @Test
    public void testUndoingAfterMaxUndo(){
        this.setUpAndResetBoard();
        for (int moves = 0; moves < 3; moves++){
            SlidingTile emptyTile = findEmptyTile(this.mediumSlidingManager.getBoard());
            int emptyPos = this.getPosition(emptyTile,this.mediumSlidingManager.getBoard());
            HashMap<String, SlidingTile> around = this.mediumSlidingManager.getSurroundTiles(emptyPos);
            if (around.get("above") != null) {
                int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
                this.mediumSlidingManager.touchMove(abovePos);
            } else {
                int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
                this.mediumSlidingManager.touchMove(belowPos);
            }
        }
        this.mediumSlidingManager.undoToPastState();
        this.mediumSlidingManager.undoToPastState();
        this.mediumSlidingManager.undoToPastState();
        assertFalse(this.mediumSlidingManager.ableToUndo());
    }

    /**
     * Test whether UpdateStatesAfterUndo works (Note this will be a long test method as the code
     * to setup the board to test for different scenarios to test is long) (Two edge cases were
     * considered, one when undoing actually undoing MAX_UNDO number of times and twice, since
     * how similar the circumstances are they were put together.)
     */
    @Test
    public void testUpdateStatesAfterUndo(){
        this.setUpAndResetBoard();
        for (int moves = 0; moves < 3; moves++){
            SlidingTile emptyTile = findEmptyTile(this.mediumSlidingManager.getBoard());
            int emptyPos = this.getPosition(emptyTile,this.mediumSlidingManager.getBoard());
            HashMap<String, SlidingTile> around = this.mediumSlidingManager.getSurroundTiles(emptyPos);
            if (around.get("above") != null) {
                int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
                this.mediumSlidingManager.touchMove(abovePos);
            } else {
                int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
                this.mediumSlidingManager.touchMove(belowPos);
            }
        }
        this.mediumSlidingManager.undoToPastState();
        this.mediumSlidingManager.undoToPastState();
        this.mediumSlidingManager.undoToPastState();
        SlidingTile emptyTile = this.findEmptyTile(this.mediumSlidingManager.getBoard());
        int emptyPos = this.getPosition(emptyTile,this.mediumSlidingManager.getBoard());
        HashMap<String,SlidingTile> around = this.mediumSlidingManager.getSurroundTiles(emptyPos);
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(abovePos);
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(belowPos);
        }

        assertEquals(2,this.mediumSlidingManager.getPastStates().getBoards().size());
        this.setUpAndResetBoard();
        for (int moves = 0; moves < 3; moves++){
            emptyTile = findEmptyTile(this.mediumSlidingManager.getBoard());
            emptyPos = this.getPosition(emptyTile,this.mediumSlidingManager.getBoard());
            around = this.mediumSlidingManager.getSurroundTiles(emptyPos);
            if (around.get("above") != null) {
                int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
                this.mediumSlidingManager.touchMove(abovePos);
            } else {
                int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
                this.mediumSlidingManager.touchMove(belowPos);
            }
        }

        this.mediumSlidingManager.undoToPastState();
        this.mediumSlidingManager.undoToPastState();
        emptyTile = this.findEmptyTile(this.mediumSlidingManager.getBoard());
        emptyPos = this.getPosition(emptyTile,this.mediumSlidingManager.getBoard());
        around = this.mediumSlidingManager.getSurroundTiles(emptyPos);
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(abovePos);
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(belowPos);
        }
        assertEquals(3,this.mediumSlidingManager.getPastStates().getBoards().size());
    }
}