package group_0548.gamecentre.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    /** The board manager for testing. */
    BoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int rowNum, int colNum) {
        List<Tile> tiles = new ArrayList<>();
        int i = 0;
        while (i < colNum*rowNum){
            tiles.add(new Tile(i+1,rowNum,colNum));
        }


        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {


        BoardManager testSolvedManager = new BoardManager(4, 4,
                "Medium", 3);

        int rowNum = testSolvedManager.getBoard().getNumRow();
        int colNum = testSolvedManager.getBoard().getNumCol();
        List<Tile> tiles = makeTiles(rowNum,colNum);
        Board board = new Board(tiles, rowNum, colNum);
        Tile[][] newTiles = board.getTiles();
        testSolvedManager.getBoard().setTiles(newTiles);
        this.boardManager = testSolvedManager;




    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertEquals(true, boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertEquals(false, boardManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        boardManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, boardManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertEquals(true, boardManager.isValidTap(11));
        assertEquals(true, boardManager.isValidTap(15));
        assertEquals(false, boardManager.isValidTap(10));
    }

    /**
     * Test whether ableToUndo works
     */
    public void testUndoToPastState(){
        setUpCorrect();
        boardManager.touchMove(12);
    }
}