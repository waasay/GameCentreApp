package group_0548.gamecentre.colourguess;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for the colour guess game
 */
public class ColourGuessUnitTest {

    /**
     * The medium complexity board for testing
     */
    ColourGuessManager regularMemoryManager;
    /**
     * The medium complexity board for testing, but the board is hard coded
     */
    ColourGuessManager hardcodedMemoryManager;

    /**
     * The method for setting up and resetting the board for testing.
     */
    private void setupAndResetBoard(){
        this.regularMemoryManager = new ColourGuessManager(4,4,"Medium");
        this.hardcodedMemoryManager = new ColourGuessManager(4,4,"Medium");
        List<ColourGuessTile> hardcodedTiles= new ArrayList<>();
        List<Integer> idList = Arrays.asList(0,1,2,3,4,5,2,1,2,3,4,5,3,1,2,5);
        for (int j = 0; j < this.hardcodedMemoryManager.getBoard1().numTiles(); j++){
            hardcodedTiles.add(new ColourGuessTile(idList.get(j)));
        }
        ColourGuessBoard board = new ColourGuessBoard(hardcodedTiles,4,4);
        ColourGuessTile[][] newTile = board.getTiles();
        this.hardcodedMemoryManager.getBoard1().setTiles(newTile);

    }
    /**
     * Test whether Board2 check marks correspond to the given color in Board1.
     */
    @Test
    public void testIsSolved(){

        this.setupAndResetBoard();
        this.regularMemoryManager.select(0);
        assertEquals(true, this.regularMemoryManager.puzzleSolved());
    }
    /**
     * Test whether the tile has a checkmark if tapped
     */
    @Test
    public void testIfTileHasCheckAfterTap(){

        this.setupAndResetBoard();
        this.regularMemoryManager.select(1);
        assertEquals(7, this.regularMemoryManager.getBoard2().getTile(1 / 4, 1 % 4).getId());



    }

    /**
     * Test whether the tile has a checkmark if tapped
     */
    @Test
    public void testIfTileIsBlankAfterTap(){

        this.setupAndResetBoard();
        this.regularMemoryManager.select(1);
        this.regularMemoryManager.select(1);
        assertEquals(6, this.regularMemoryManager.getBoard2().getTile(1 / 4, 1 % 4).getId());



    }

    /**
     * Test whether Board2 check marks correspond to the given color in Board1.
     */
    @Test
    public void testScoreIncreased(){

        this.setupAndResetBoard();
        this.regularMemoryManager.select(0);
        assertEquals(1, this.regularMemoryManager.getScore());
    }

}
