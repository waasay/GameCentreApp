package group_0548.gamecentre.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    /** The board manager for testing. */
    SlidingManager slidingManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<SlidingTile> makeTiles(int rowNum, int colNum) {
        List<SlidingTile> tiles = new ArrayList<>();
        int i = 0;
        while (i < colNum*rowNum){
            tiles.add(new SlidingTile(i+1,rowNum,colNum));
        }


        return tiles;
    }

    /**
     * Make a solved SlidingBoard.
     */
    private void setUpCorrect() {


        SlidingManager testSolvedManager = new SlidingManager(4, 4,
                "Medium", 3);

        int rowNum = testSolvedManager.getBoard().getNumRow();
        int colNum = testSolvedManager.getBoard().getNumCol();
        List<SlidingTile> tiles = makeTiles(rowNum,colNum);
        SlidingBoard slidingBoard = new SlidingBoard(tiles, rowNum, colNum);
        SlidingTile[][] newTiles = slidingBoard.getTiles();
        testSolvedManager.getBoard().setTiles(newTiles);
        this.slidingManager = testSolvedManager;




    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        slidingManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertEquals(true, slidingManager.puzzleSolved());
        swapFirstTwoTiles();
        assertEquals(false, slidingManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, slidingManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, slidingManager.getBoard().getTile(0, 1).getId());
        slidingManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, slidingManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, slidingManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, slidingManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, slidingManager.getBoard().getTile(3, 3).getId());
        slidingManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, slidingManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, slidingManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertEquals(true, slidingManager.isValidTap(11));
        assertEquals(true, slidingManager.isValidTap(15));
        assertEquals(false, slidingManager.isValidTap(10));
    }

    /**
     * Test whether ableToUndo works
     */
    public void testUndoToPastState(){
        setUpCorrect();
        slidingManager.touchMove(12);
    }
}