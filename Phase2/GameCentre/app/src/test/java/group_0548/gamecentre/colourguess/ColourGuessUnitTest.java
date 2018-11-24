package group_0548.gamecentre.colourguess;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for the colour guess game
 */
public class ColourGuessUnitTest {

    /**
     * The medium complexity board for testing
     */
    MemoryManager regularMemoryManager;
    /**
     * The medium complexity board for testing, but the board is hard coded
     */
    MemoryManager hardcodedMemoryManager;

    /**
     * The method for setting up and resetting the board for testing.
     */
    private void setupAndResetBoard(){
        this.regularMemoryManager = new MemoryManager(4,4,"Medium");
        this.hardcodedMemoryManager = new MemoryManager(4,4,"Medium");
        List<ColourTile> hardcodedTiles= new ArrayList<>();
        List<Integer> idList = Arrays.asList(0,1,2,3,4,5,0,1,2,3,4,5,0,1,2,5);
        for (int j = 0; j < this.hardcodedMemoryManager.getBoard1().numTiles(); j++){
            hardcodedTiles.add(new ColourTile(idList.get(j)));
        }
        ColourBoard board = new ColourBoard(hardcodedTiles,4,4);
        ColourTile[][] newTile = board.getTiles();
        this.hardcodedMemoryManager.getBoard1().setTiles(newTile);

    }
}
