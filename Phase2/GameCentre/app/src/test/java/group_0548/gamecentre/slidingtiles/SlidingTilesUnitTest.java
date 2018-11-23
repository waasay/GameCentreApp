package group_0548.gamecentre.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SlidingTilesUnitTest {
    /** The board manager for testing. */
    private BoardManager mediumBoardManager ;
    private BoardManager solvedBoardManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int rowNum, int colNum) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < colNum*rowNum; i++){
            tiles.add(new Tile(i,rowNum,colNum));
        }

        return tiles;
    }

    /**
     * Find the empty tile in board
     * @param board the board to look for the empty tile
     * @return the empty tile
     */

    private Tile findEmptyTile(Board board) {
        int numOfTiles = board.numTiles();
        Tile toReturn = board.getTile(3,3);
        for (Tile t : board) {
            if (t.getId() == numOfTiles) {
                toReturn = t;
                break;
            }
        }
        return toReturn;
    }

    private int getPosition(Tile tile, Board board) {
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
        this.mediumBoardManager = new BoardManager(4, 4, "Medium", 3);
        this.solvedBoardManager = new BoardManager(4, 4, "Medium", 3);
        List<Tile> tiles = makeTiles(4,4);
        Board board = new Board(tiles, 4,4);
        Tile[][] newTiles = board.getTiles();
        this.solvedBoardManager.getBoard().setTiles(newTiles);

    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved(){

        this.setUpAndResetBoard();
        assertEquals(true, this.solvedBoardManager.puzzleSolved());
        assertEquals(false, this.mediumBoardManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        this.setUpAndResetBoard();
        this.solvedBoardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, this.solvedBoardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, this.solvedBoardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test getSurroundTile works, so could use it to test other methods.
     */
    @Test
    public void testGetSurroundTiles(){
        this.setUpAndResetBoard();
        Tile emptyTile = this.findEmptyTile(this.solvedBoardManager.getBoard());
        int emptyPos = this.getPosition(emptyTile,this.mediumBoardManager.getBoard());
        HashMap<String, Tile> around = this.mediumBoardManager.getSurroundTiles(emptyPos);

    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpAndResetBoard();
        Tile emptyTile = this.findEmptyTile(this.mediumBoardManager.getBoard());
        int emptyPosition = this.getPosition(emptyTile,this.mediumBoardManager.getBoard());
        HashMap<String, Tile> around = this.mediumBoardManager.getSurroundTiles(emptyPosition);
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumBoardManager.getBoard());
            assertEquals(true, this.mediumBoardManager.isValidTap(abovePos));
        }
        else{
            int belowPos = this.getPosition(around.get("below"), this.mediumBoardManager.getBoard());
            assertEquals(true, this.mediumBoardManager.isValidTap(belowPos));
        }
        assertEquals(false, this.mediumBoardManager.isValidTap(emptyPosition));
    }

    /**
     * Test score increase after touch move and whether undo resets after a
     * touch move is called
     */
    @Test
    public void testScoreIncreaseAndCurrentUndoResetAfterTouchMove() {
        this.setUpAndResetBoard();
        Tile emptyTile = this.findEmptyTile(this.mediumBoardManager.getBoard());
        int emptyPos = this.getPosition(emptyTile, this.mediumBoardManager.getBoard());
        HashMap<String, Tile> around = this.mediumBoardManager.getSurroundTiles(emptyPos);
        int oldScore = this.mediumBoardManager.getBoard().getScore();
        int valueOfResetUndo = this.mediumBoardManager.getMaxUndo() - 1;
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumBoardManager.getBoard());
            this.mediumBoardManager.touchMove(abovePos);
            assertEquals(oldScore + 1, this.mediumBoardManager.getBoard().getScore());
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumBoardManager.getBoard());
            this.mediumBoardManager.touchMove(belowPos);
            assertEquals(oldScore + 1, this.mediumBoardManager.getBoard().getScore());
        }
        assertEquals(valueOfResetUndo, this.mediumBoardManager.getCurrUndo());
    }

    /**
     * Test whether ableToUndo and ableToRedo actually works
     */
    @Test
    public void testAbleToUndoAndAbleToRedo(){
        this.setUpAndResetBoard();
        assertEquals(false,this.solvedBoardManager.ableToUndo());
        Tile emptyTile = this.findEmptyTile(this.mediumBoardManager.getBoard());
        int emptyPos = this.getPosition(emptyTile,this.mediumBoardManager.getBoard());
        HashMap<String, Tile> around = this.mediumBoardManager.getSurroundTiles(emptyPos);
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumBoardManager.getBoard());
            this.mediumBoardManager.touchMove(abovePos);
            assertEquals(true, this.mediumBoardManager.ableToUndo());
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumBoardManager.getBoard());
            this.mediumBoardManager.touchMove(belowPos);
            assertEquals(true, this.mediumBoardManager.ableToUndo());
        }
        this.mediumBoardManager.undoToPastState();
        assertEquals(true, this.mediumBoardManager.ableToRedo());
        assertEquals(false,this.solvedBoardManager.ableToRedo());

    }

    @Test
    /**
     * Test whether updates states work when performing a series of moves
     */

    public void testUpdateStatesWith10Moves(){
        this.setUpAndResetBoard();
        for (int moves = 0; moves < 10; moves++){
            Tile emptyTile = findEmptyTile(this.mediumBoardManager.getBoard());
            int emptyPos = this.getPosition(emptyTile,this.mediumBoardManager.getBoard());
            HashMap<String, Tile> around = this.mediumBoardManager.getSurroundTiles(emptyPos);
            if (around.get("above") != null) {
                int abovePos = this.getPosition(around.get("above"), this.mediumBoardManager.getBoard());
                this.mediumBoardManager.touchMove(abovePos);
            } else {
                int belowPos = this.getPosition(around.get("below"), this.mediumBoardManager.getBoard());
                this.mediumBoardManager.touchMove(belowPos);
            }
        }

        assertEquals(4,this.mediumBoardManager.getPastStates().getBoards().size());

    }

    @Test
    /**
     * Test whether undo and redo actually points to the current board
     * in pastStates
     */
    public void testUpdateToPastStateAndRedoToFutureState(){
        this.setUpAndResetBoard();
        Tile emptyTile = findEmptyTile(this.mediumBoardManager.getBoard());
        int emptyPos = this.getPosition(emptyTile,this.mediumBoardManager.getBoard());
        HashMap<String, Tile> around = this.mediumBoardManager.getSurroundTiles(emptyPos);
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumBoardManager.getBoard());
            this.mediumBoardManager.touchMove(abovePos);
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumBoardManager.getBoard());
            this.mediumBoardManager.touchMove(belowPos);
        }
        emptyTile = findEmptyTile(this.mediumBoardManager.getBoard());
        emptyPos = this.getPosition(emptyTile,this.mediumBoardManager.getBoard());
        around = this.mediumBoardManager.getSurroundTiles(emptyPos);
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumBoardManager.getBoard());
            this.mediumBoardManager.touchMove(abovePos);
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumBoardManager.getBoard());
            this.mediumBoardManager.touchMove(belowPos);
        }
        int currUndo = this.mediumBoardManager.getCurrUndo();
        this.mediumBoardManager.undoToPastState();

        assertEquals(currUndo-1,this.mediumBoardManager.getCurrUndo());

        this.mediumBoardManager.undoToPastState();
        currUndo = this.mediumBoardManager.getCurrUndo();
        this.mediumBoardManager.redoToFutureState();
        assertEquals(currUndo+1, this.mediumBoardManager.getCurrUndo());
    }

    @Test
    /**
     * Test getBackground and compareTo in Tile
     */
    public void testGetBackgroundAndCompareTo(){
        this.setUpAndResetBoard();
        Tile emptyTile = findEmptyTile(this.solvedBoardManager.getBoard());
        Tile toCompare = this.solvedBoardManager.getBoard().getTile(0,0);
        assertEquals(false, emptyTile.getBackground() == toCompare.getBackground());
        assertEquals(-15, emptyTile.compareTo(toCompare));
    }

    @Test
    /**
     * Test whether UpdateStatesAfterUndo works
     * (Note this will be a long test method as the code to setup the board to test
     * for different scenarios to test is long)
     */
    public void testUpdateStatesAfterUndo(){
        this.setUpAndResetBoard();
        for (int moves = 0; moves < 3; moves++){
            Tile emptyTile = findEmptyTile(this.mediumBoardManager.getBoard());
            int emptyPos = this.getPosition(emptyTile,this.mediumBoardManager.getBoard());
            HashMap<String, Tile> around = this.mediumBoardManager.getSurroundTiles(emptyPos);
            if (around.get("above") != null) {
                int abovePos = this.getPosition(around.get("above"), this.mediumBoardManager.getBoard());
                this.mediumBoardManager.touchMove(abovePos);
            } else {
                int belowPos = this.getPosition(around.get("below"), this.mediumBoardManager.getBoard());
                this.mediumBoardManager.touchMove(belowPos);
            }
        }
        this.mediumBoardManager.undoToPastState();
        this.mediumBoardManager.undoToPastState();
        this.mediumBoardManager.undoToPastState();
        Tile emptyTile = this.findEmptyTile(this.mediumBoardManager.getBoard());
        int emptyPos = this.getPosition(emptyTile,this.mediumBoardManager.getBoard());
        HashMap<String,Tile> around = this.mediumBoardManager.getSurroundTiles(emptyPos);
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumBoardManager.getBoard());
            this.mediumBoardManager.touchMove(abovePos);
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumBoardManager.getBoard());
            this.mediumBoardManager.touchMove(belowPos);
        }

        assertEquals(2,this.mediumBoardManager.getPastStates().getBoards().size());


        this.setUpAndResetBoard();
        for (int moves = 0; moves < 3; moves++){
            emptyTile = findEmptyTile(this.mediumBoardManager.getBoard());
            emptyPos = this.getPosition(emptyTile,this.mediumBoardManager.getBoard());
            around = this.mediumBoardManager.getSurroundTiles(emptyPos);
            if (around.get("above") != null) {
                int abovePos = this.getPosition(around.get("above"), this.mediumBoardManager.getBoard());
                this.mediumBoardManager.touchMove(abovePos);
            } else {
                int belowPos = this.getPosition(around.get("below"), this.mediumBoardManager.getBoard());
                this.mediumBoardManager.touchMove(belowPos);
            }
        }

        this.mediumBoardManager.undoToPastState();
        this.mediumBoardManager.undoToPastState();
        emptyTile = this.findEmptyTile(this.mediumBoardManager.getBoard());
        emptyPos = this.getPosition(emptyTile,this.mediumBoardManager.getBoard());
        around = this.mediumBoardManager.getSurroundTiles(emptyPos);
        if (around.get("above") != null) {
            int abovePos = this.getPosition(around.get("above"), this.mediumBoardManager.getBoard());
            this.mediumBoardManager.touchMove(abovePos);
        } else {
            int belowPos = this.getPosition(around.get("below"), this.mediumBoardManager.getBoard());
            this.mediumBoardManager.touchMove(belowPos);
        }
        assertEquals(3,this.mediumBoardManager.getPastStates().getBoards().size());
    }





}