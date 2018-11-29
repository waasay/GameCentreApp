package group_0548.gamecentre;

import org.junit.Test;
import org.junit.rules.ExpectedException;


import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Unit test for features under the directory of gamecentre, such as states, user, usermanager,
 * scoreboard and scoreboard manager.
 */
public class GameCentreUnitTest {

    /**
     * User Manager
     */
    private UsersManager usersManager;

    /**
     * ScoreboardManager for an arbitrary game A;
     */
    private ScoreBoardManager scoreBoardManager;

    /**
     * State object that contains string for testing
     */
    private States<String> stringStates;

    /**
     * State object that contains ints for testing
     */
    private States<Integer> integerStates;

    /**
     * Setup the scoreboard of an arbitrary game with some arbitrary user.
     */

    void setupAndReset(){

        User userA = new User("A","123");
        User userB = new User("B","123");
        User userC = new User("C","123");

        this.usersManager = new UsersManager();
        this.usersManager.addUser(userA);
        this.usersManager.addUser(userB);
        this.usersManager.addUser(userC);

        this.scoreBoardManager = new ScoreBoardManager();

    }


    /**
     * Testing State class, since it is only a container class, will test it with strings and ints
     * instead of the various board classes, functionally the state object will treat them
     * the same compare to the board classes.
     */
    void setupAndResetStates(){
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
    @Test
    public void testScoreBoardManager(){
        this.setupAndReset();
        HashMap<String, Integer> testHashMap = new HashMap<>();
        testHashMap.put("A", 0);
        testHashMap.put("B", 5);
        testHashMap.put("C", 10);
        this.scoreBoardManager.updateScoreBoard("Easy", "A", 0, "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "C", 10, "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "B", 5, "Ascending");
        assertEquals(testHashMap, this.scoreBoardManager.getScoreBoard("Easy").getTopScores());

        testHashMap.put("E", 15);
        testHashMap.put("D", 20);
        this.scoreBoardManager.updateScoreBoard("Easy", "D", 20, "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "E", 15, "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "F", 35, "Ascending");
        assertEquals(testHashMap, this.scoreBoardManager.getScoreBoard("Easy").getTopScores());

        this.setupAndReset();
        testHashMap.put("A", 0);
        testHashMap.put("B", 5);
        testHashMap.put("C", 10);
        this.scoreBoardManager.updateScoreBoard("Easy", "A", 0, "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "C", 10, "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "B", 5, "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "A", 20, "Ascending");
        assertEquals(testHashMap, this.scoreBoardManager.getScoreBoard("Easy").getTopScores());
    }

    @Test
    public void testScoreBoardManager2(){
        HashMap<String, Integer> testHashMap = new HashMap<>();
        this.setupAndReset();
        testHashMap.put("A", 0);
        testHashMap.put("B", 5);
        testHashMap.put("C", 10);
        this.scoreBoardManager.updateScoreBoard("Easy", "A", 0, "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "C", 10, "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "B", 5, "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "A", 20, "Ascending");
        assertEquals(testHashMap, this.scoreBoardManager.getScoreBoard("Easy").getTopScores());
    }

    @Test
    public void testGetScoreContentAscending(){
        this.setupAndReset();
        ArrayList<String> testList = new ArrayList<>();
        String users =  "A" + "\r\n" + "B" + "\r\n" + "C" + "\r\n";
        String scores =  "0" + "\r\n" + "5" + "\r\n" + "10" + "\r\n";
        testList.add(users);
        testList.add(scores);
        this.scoreBoardManager.updateScoreBoard("Easy", "A", 0, "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "B", 5, "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "C", 10, "Ascending");
        assertEquals(testList, this.scoreBoardManager.getScoreBoard("Easy").getScoreContent("Ascending"));
    }

    @Test
    public void testGetScoreContentDescending(){
        this.setupAndReset();
        ArrayList<String> testList = new ArrayList<>();
        String users = "C" + "\r\n" + "B" + "\r\n" + "A" + "\r\n";
        String scores = "10" + "\r\n" + "5" + "\r\n" + "0" + "\r\n";
        testList.add(users);
        testList.add(scores);
        this.scoreBoardManager.updateScoreBoard("Easy", "A", 0, "Descending");
        this.scoreBoardManager.updateScoreBoard("Easy", "B", 5, "Descending");
        this.scoreBoardManager.updateScoreBoard("Easy", "C", 10, "Descending");
        assertEquals(testList, this.scoreBoardManager.getScoreBoard("Easy").getScoreContent("Descending"));
    }
}