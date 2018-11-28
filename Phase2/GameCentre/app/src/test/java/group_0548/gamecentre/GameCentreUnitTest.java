package group_0548.gamecentre;

import org.junit.Test;
import org.junit.rules.ExpectedException;


import static org.junit.Assert.*;

/**
 * Unit test for features under the directory of gamecentre, such as states, user, usermanager,
 * scoreboard and scoreboard manager.
 */
public class GameCentreUnitTest {

    /**
     * State object that contains string for testing
     */
    private States<String> stringStates;

    /**
     * State object that contains ints for testing
     */
    private States<Integer> integerStates;


    /**
     * Testing State class, since it is only a container class, will test it with strings and ints
     * instead of the various board classes, functionally the state object will treat them
     * the same compare to the board classes.
     */
    public void setupAndResetStates(){
        this.stringStates = new States<>(3);
        this.integerStates = new States<>(3);
        this.stringStates.updateStates("0");
        this.stringStates.updateStates("1");
        this.stringStates.updateStates("2");
        this.stringStates.updateStates("3");
        this.integerStates.updateStates(0);
        this.integerStates.updateStates(1);
    }

    /**
     * Test updateStates actually works as desired
     */
    @Test
    public void testUpdateStates(){
        this.setupAndResetStates();
        this.stringStates.updateStates("4");
        this.integerStates.updateStates(2);
        assertEquals(true,this.integerStates.getBoards().contains(0));
        assertEquals(false,this.stringStates.getBoards().contains("0"));
    }

    /**
     * Test keepStatesUptill actually works
     */
    @Test
    public void testKeepStatesUptill(){
        Throwable e = null;
        this.setupAndResetStates();
        this.stringStates.keepStatesUpTill(2);
        assertEquals(3, this.stringStates.getBoards().size());
        try {
            this.integerStates.keepStatesUpTill(2);
        } catch (Throwable ex) {
            e = ex;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
    }
}