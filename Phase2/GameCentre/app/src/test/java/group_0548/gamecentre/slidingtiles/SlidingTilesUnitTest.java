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
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        this.setUpAndResetBoard();
        this.solvedBoardManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, this.solvedBoardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, this.solvedBoardManager.getBoard().getTile(3, 3).getId());
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
     * Test score increase after touch move
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





}