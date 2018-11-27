package group_0548.gamecentre.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
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
        int numOfTiles = board.numTiles();
        SlidingTile toReturn = board.getTile(3,3);
        for (SlidingTile t : board) {
            if (t.getId() == numOfTiles) {
                toReturn = t;
                break;
            }
        }
        return toReturn;
    }

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
     * and 3 other boards at different complexity.
     */
    private void setUpAndResetBoard() {
        this.mediumSlidingManager = new SlidingManager(4, 4, "Medium", 3);
        this.solvedSlidingManager = new SlidingManager(4, 4, "Medium", 3);
        List<SlidingTile> tiles = makeTiles(4,4);
        SlidingBoard board = new SlidingBoard(tiles, 4,4);
        SlidingTile[][] newTiles = board.getTiles();
        this.solvedSlidingManager.getBoard().setTiles(newTiles);

    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved(){

        this.setUpAndResetBoard();
        assertEquals(true, this.solvedSlidingManager.puzzleSolved());
        assertEquals(false, this.mediumSlidingManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        this.setUpAndResetBoard();
        this.solvedSlidingManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, this.solvedSlidingManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, this.solvedSlidingManager.getBoard().getTile(0, 1).getId());
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
        assertEquals(null,around.get("below"));
        assertEquals(null,around.get("right"));
        assertEquals(15,around.get("left").getId());
        assertEquals(12,around.get("above").getId());

    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpAndResetBoard();
        SlidingTile emptyTile = this.findEmptyTile(this.mediumSlidingManager.getBoard());
        int emptyPosition = this.getPosition(emptyTile,this.mediumSlidingManager.getBoard());
        HashMap<String, SlidingTile> around = this.mediumSlidingManager.getSurroundTiles(emptyPosition);
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
            assertEquals(true, this.mediumSlidingManager.isValidTap(abovePos));
        }
        else{
            int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
            assertEquals(true, this.mediumSlidingManager.isValidTap(belowPos));
        }
        assertEquals(false, this.mediumSlidingManager.isValidTap(emptyPosition));
    }

    /**
     * Test score increase after touch move and whether undo resets after a
     * touch move is called
     */
    @Test
    public void testScoreIncreaseAfterTouchMove() {
        this.setUpAndResetBoard();
        SlidingTile emptyTile = this.findEmptyTile(this.mediumSlidingManager.getBoard());
        int emptyPos = this.getPosition(emptyTile, this.mediumSlidingManager.getBoard());
        HashMap<String, SlidingTile> around = this.mediumSlidingManager.getSurroundTiles(emptyPos);
        int oldScore = this.mediumSlidingManager.getBoard().getScore();
        int valueOfResetUndo = this.mediumSlidingManager.getMaxUndo() - 1;
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(abovePos);
            assertEquals(oldScore + 1, this.mediumSlidingManager.getBoard().getScore());
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(belowPos);
            assertEquals(oldScore + 1, this.mediumSlidingManager.getBoard().getScore());
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
     * because how similar the two tests are.
     */
    @Test
    public void testAbleToUndoAndAbleToRedo(){
        this.setUpAndResetBoard();
        assertEquals(false,this.solvedSlidingManager.ableToUndo());
        SlidingTile emptyTile = this.findEmptyTile(this.mediumSlidingManager.getBoard());
        int emptyPos = this.getPosition(emptyTile,this.mediumSlidingManager.getBoard());
        HashMap<String, SlidingTile> around = this.mediumSlidingManager.getSurroundTiles(emptyPos);
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(abovePos);
            assertEquals(true, this.mediumSlidingManager.ableToUndo());
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumSlidingManager.getBoard());
            this.mediumSlidingManager.touchMove(belowPos);
            assertEquals(true, this.mediumSlidingManager.ableToUndo());
        }
        this.mediumSlidingManager.undoToPastState();
        assertEquals(true, this.mediumSlidingManager.ableToRedo());
        assertEquals(false,this.solvedSlidingManager.ableToRedo());
        assertEquals(false, this.solvedSlidingManager.ableToUndo());


    }

    @Test
    /**
     * Test whether updates states work when performing a series of moves
     */

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

    @Test
    /**
     * Test whether undo and redo actually points to the current board
     * in pastStates (These two are put together because how similar the two test are)
     */
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
    public void testGetBackgroundAndCompareTo(){
        this.setUpAndResetBoard();
        SlidingTile emptyTile = findEmptyTile(this.solvedSlidingManager.getBoard());
        SlidingTile toCompare = this.solvedSlidingManager.getBoard().getTile(0,0);
        assertEquals(false, emptyTile.getBackground() == toCompare.getBackground());
    }

    /**
     * Test CompareTo actually works
     */
    @Test
    public void testCompareTo(){
        this.setUpAndResetBoard();
        SlidingTile emptyTile = findEmptyTile(this.solvedSlidingManager.getBoard());
        SlidingTile toCompare = this.solvedSlidingManager.getBoard().getTile(0,0);
        assertEquals(-15, emptyTile.compareTo(toCompare));
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
        assertEquals(false,this.mediumSlidingManager.ableToUndo());
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