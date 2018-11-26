package group_0548.gamecentre.colourguess;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for the colour guess game
 */
public class ColourGuessUnitTest {

    /**
     * The medium complexity board for testing
     */
    private ColourGuessManager regularColourManager;
    /**
     * The medium complexity board for testing, but the board is hard coded
     */
    private ColourGuessManager hardcodedColourManager;

    /**
     * The method for setting up and resetting the board for testing.
     */
    private void setupAndResetBoard(){
        this.regularColourManager = new ColourGuessManager(4,4,"Medium");
        this.hardcodedColourManager = new ColourGuessManager(4,4,"Medium");
        List<ColourGuessTile> hardcodedTiles= new ArrayList<>();
        List<Integer> idList = Arrays.asList(0,1,2,3,4,5,0,1,2,3,4,5,3,1,2,5);
        for (int j = 0; j < this.hardcodedColourManager.getBoard1().numTiles(); j++){
            hardcodedTiles.add(new ColourGuessTile(idList.get(j)));
        }
        ColourGuessBoard board = new ColourGuessBoard(hardcodedTiles,4,4);
        ColourGuessTile[][] newTile = board.getTiles();
        this.hardcodedColourManager.getBoard1().setTiles(newTile);

    }
    /**
     * Test whether Board2 check marks correspond to the given color in Board1.
     */
    @Test
    public void testIsSolved(){

        this.setupAndResetBoard();
        assertEquals(false, this.hardcodedColourManager.puzzleSolved());
        this.hardcodedColourManager.select(0);
        this.hardcodedColourManager.select(6);
        assertEquals(true, this.hardcodedColourManager.puzzleSolved());

    }
    /**
     * Test whether after a tile is tap, the id of that tile changed into a corresponding id
     * that represents a tile with a checkmark
     */
    @Test
    public void testIfTileHasCheckAfterTap(){

        this.setupAndResetBoard();
        this.regularColourManager.select(0);
        assertEquals(7, this.regularColourManager.getBoard2().getTile(0, 0).getId());

    }

    /**
     * Test whether the after a tile with a checkmark is tap, the id of that tile changed back into
     * the id that correspond a blank tile
     */
    @Test
    public void testIfTileIsBlankAfterTap(){

        this.setupAndResetBoard();
        this.regularColourManager.select(0);
        this.regularColourManager.select(0);
        assertEquals(6, this.regularColourManager.getBoard2().getTile(0,0).getId());


    }
    /**
     * Test whether Board2 check marks correspond to the given color in Board1.
     */
    @Test
    public void testScoreIncreased(){

        this.setupAndResetBoard();
        this.hardcodedColourManager.select(0);
        this.hardcodedColourManager.select(6);
        this.hardcodedColourManager.puzzleSolved();
        assertEquals(1, this.hardcodedColourManager.getScore());
    }



}
