package group_0548.gamecentre.slidingtiles;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import group_0548.gamecentre.CustomAdapter;
import group_0548.gamecentre.LoginActivity;
import group_0548.gamecentre.R;

/**
 * The game activity.
 */
public class SlidingGameActivity extends AppCompatActivity implements Observer {

    private static int columnWidth, columnHeight;
    /**
     * The board manager.
     */
    private SlidingManager slidingManager;
    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;
    // Grid View and calculated column height and width based on device size
    private SlidingGestureDetectGridView gridView;
    /**
     * The TextView for the score.
     */
    private TextView currentScore;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile();
        createTileButtons(this);
        setContentView(R.layout.activity_main);

        // Add View to activity
        currentScore = findViewById(R.id.SlidingScore);
        gridView = findViewById(R.id.SlidingGrid);
        gridView.setNumColumns(slidingManager.getBoard().getNumCol());
        gridView.setSlidingManager(slidingManager);
        slidingManager.addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / slidingManager.getBoard().getNumCol();
                        columnHeight = displayHeight / slidingManager.getBoard().getNumRow();

                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        SlidingBoard board = slidingManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != slidingManager.getBoard().getNumRow(); row++) {
            for (int col = 0; col != slidingManager.getBoard().getNumCol(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles and the current score.
     */
    private void updateTileButtons() {
        SlidingBoard board = slidingManager.getBoard();
        currentScore.setText(String.valueOf(slidingManager.getScore()));
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / slidingManager.getBoard().getNumRow();
            int col = nextPos % slidingManager.getBoard().getNumCol();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
        LoginActivity.usersManager.getCurrentUser().saveGame(SlidingStartingActivity.GAME_TYPE, slidingManager);
        saveToFile(LoginActivity.USER_SAVE_FILENAME, LoginActivity.usersManager);
        saveToScoreBoard();
        saveToFile(SlidingStartingActivity.TEMP_SAVE_FILENAME, slidingManager);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(SlidingStartingActivity.TEMP_SAVE_FILENAME, slidingManager);
    }

    /**
     * Load the board manager from TEMP_SAVE_FILENAME.
     */
    private void loadFromFile() {

        try {
            InputStream inputStream = this.openFileInput(SlidingStartingActivity.TEMP_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                slidingManager = (SlidingManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the object to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName, Object obj) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(obj);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception=", "File write failed: " + e.toString());
        }
    }

    /**
     * Saving the score when the game is finish and when the maximum undo is default
     * at 3
     */

    public void saveToScoreBoard() {
        String gameType = SlidingStartingActivity.GAME_TYPE + " " + slidingManager.getComplexity();
        if (slidingManager.puzzleSolved() && slidingManager.getMaxUndo() == 3) {
            SlidingStartingActivity.scoreBoardManager.updateScoreBoard(slidingManager.getComplexity(),
                    LoginActivity.usersManager.getCurrentUser().getUserName(),
                    slidingManager.getScore(), SlidingStartingActivity.ORDER);
            LoginActivity.usersManager.getCurrentUser().updateScore(gameType,
                    slidingManager.getScore(), SlidingStartingActivity.ORDER);
            saveToFile(SlidingStartingActivity.SCOREBOARD_SAVE_FILENAME,
                    SlidingStartingActivity.scoreBoardManager);
            saveToFile(LoginActivity.USER_SAVE_FILENAME, LoginActivity.usersManager);
        }
    }

    /**
     * Implementing button for undo
     *
     * @param view view for the undo button
     */

    public void undo(View view) {
        this.gridView.undoEvent();
    }

    /**
     * Implementing button for redo
     *
     * @param view view for the undo button
     */
    public void redo(View view) {
        this.gridView.redoEvent();
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
