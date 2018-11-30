package group_0548.gamecentre;

import org.junit.Test;



import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Unit test for features under the directory of gameCentre, such as states, user, userManager,
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

     private void setupAndReset(){

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
    private void setupAndResetStates(){
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
        assertTrue(this.integerStates.getBoards().contains(0));
        assertFalse(this.stringStates.getBoards().contains("0"));
    }

    /**
     * Test keepStatesUpTill actually works
     */
    @Test
    public void testKeepStatesUpTill(){
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
    /**
     * Test if the score board gets updated with three different users with scores in ascending
     * order
     */
    @Test
    public void testScoreBoard(){
        this.setupAndReset();
        HashMap<String, Integer> testHashMap = new HashMap<>();
        testHashMap.put("A", 0);
        testHashMap.put("B", 5);
        testHashMap.put("C", 10);
        this.scoreBoardManager.updateScoreBoard("Easy", "A", 0,
                "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "C", 10,
                "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "B", 5,
                "Ascending");
        assertEquals(testHashMap, this.scoreBoardManager.getScoreBoard(
                "Easy").getTopScores());

    }
    /**
     * Test if the scores of only top 5 users are in ascending order when 6 users have played
     */
    @Test
    public void testScoreBoard1(){
        this.setupAndReset();
        HashMap<String, Integer> testHashMap = new HashMap<>();
        testHashMap.put("A", 0);
        testHashMap.put("B", 5);
        testHashMap.put("C", 10);
        testHashMap.put("E", 15);
        testHashMap.put("D", 20);
        this.scoreBoardManager.updateScoreBoard("Easy", "A", 0,
                "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "C", 10,
                "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "B", 5,
                "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "D", 20,
                "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "E", 15,
                "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "F", 35,
                "Ascending");
        assertEquals(testHashMap, this.scoreBoardManager.getScoreBoard(
                "Easy").getTopScores());

    }
    /**
     * Test if the scores are in ascending order adn the users are unique
     */
    @Test
    public void testScoreBoard2(){
        HashMap<String, Integer> testHashMap = new HashMap<>();
        this.setupAndReset();
        testHashMap.put("A", 0);
        testHashMap.put("B", 5);
        testHashMap.put("C", 10);
        this.scoreBoardManager.updateScoreBoard("Easy", "A", 0,
                "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "C", 10,
                "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "B", 5,
                "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "A", 20,
                "Ascending");
        assertEquals(testHashMap, this.scoreBoardManager.getScoreBoard(
                "Easy").getTopScores());
    }
    /**
     * Test the string representation of the score board in ascending order
     */
    @Test
    public void testGetScoreContentAscending(){
        this.setupAndReset();
        ArrayList<String> testList = new ArrayList<>();
        String users =  "A" + "\r\n" + "B" + "\r\n" + "C" + "\r\n";
        String scores =  "0" + "\r\n" + "5" + "\r\n" + "10" + "\r\n";
        testList.add(users);
        testList.add(scores);
        this.scoreBoardManager.updateScoreBoard("Easy", "A", 0,
                "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "B", 5,
                "Ascending");
        this.scoreBoardManager.updateScoreBoard("Easy", "C", 10,
                "Ascending");
        assertEquals(testList, this.scoreBoardManager.getScoreBoard(
                "Easy").getScoreContent("Ascending"));
    }
    /**
     * Test the string representation of the score board in descending order
     */
    @Test
    public void testGetScoreContentDescending(){
        this.setupAndReset();
        ArrayList<String> testList = new ArrayList<>();
        String users = "C" + "\r\n" + "B" + "\r\n" + "A" + "\r\n";
        String scores = "10" + "\r\n" + "5" + "\r\n" + "0" + "\r\n";
        testList.add(users);
        testList.add(scores);
        this.scoreBoardManager.updateScoreBoard("Easy", "A", 0,
                "Descending");
        this.scoreBoardManager.updateScoreBoard("Easy", "B", 5,
                "Descending");
        this.scoreBoardManager.updateScoreBoard("Easy", "C", 10,
                "Descending");
        assertEquals(testList, this.scoreBoardManager.getScoreBoard(
                "Easy").getScoreContent("Descending"));
    }
    /**
     * Test if a user exists
     */
    @Test
    public void testCheckUser(){
        this.setupAndReset();
        assertTrue(this.usersManager.checkUser("A", "123"));
        assertFalse(this.usersManager.checkUser("D", "DDD"));
        assertFalse(this.usersManager.checkUser("A", "DDD"));
    }
    /**
     * Test if the username is valid
     */
    @Test
    public void testCheckUsernameValid(){
        this.setupAndReset();
        assertTrue(this.usersManager.checkUsernameValid("D"));
        assertFalse(this.usersManager.checkUsernameValid("A"));

    }
    /**
     * Test if the score of a user gets updated
     */
    @Test
    public void testUpdateScore(){
        this.setupAndReset();
        HashMap<String, Integer> testUserNameHashMap = new HashMap<>();
        testUserNameHashMap.put("Sliding Tile Easy", 0);
        testUserNameHashMap.put("Colour Guess Easy", 5);
        testUserNameHashMap.put("2048 Easy", 10);
        this.usersManager.getUser("A", "123").updateScore(
                "Sliding Tile Easy", 0, "Ascending");
        this.usersManager.getUser("A", "123").updateScore(
                "Colour Guess Easy", 5, "Ascending");
        this.usersManager.getUser("A", "123").updateScore(
                "2048 Easy", 10, "Ascending");
        assertEquals(testUserNameHashMap,this.usersManager.getUser(
                "A", "123").getHashMapOfHighScore() );
    }
    /**
     * Test the string representation of the users scores
     */
    @Test
    public void testUserScore(){
        this.setupAndReset();
        ArrayList<String> ScoreList = new ArrayList<>();
        String GameType = "Sliding Tile Easy" + "\r\n" + "Colour Guess Easy" + "\r\n" + "2048 Easy" + "\r\n";
        String scores = "0" + "\r\n" + "5" + "\r\n" + "10" + "\r\n";
        ScoreList.add(GameType);
        ScoreList.add(scores);
        this.usersManager.getUser("A", "123").updateScore(
                "Sliding Tile Easy", 0, "Ascending");
        this.usersManager.getUser("A", "123").updateScore(
                "Colour Guess Easy", 5, "Ascending");
        this.usersManager.getUser("A", "123").updateScore(
                "2048 Easy", 10, "Ascending");
        assertEquals(ScoreList,this.usersManager.getUser(
                "A", "123").getUserScore() );
    }

}

