package group_0548.gamecentre.twentygame;

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


public class TwentyGameActivity extends AppCompatActivity implements Observer {

    /**
     * The column width and the column height.
     */
    private static int columnWidth, columnHeight;

    /**
     * The board manager.
     */
    private TwentyManager twentyManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The GestureDetectGridView for the current game.
     */
    private TwentyGestureDetectGridView gridView;

    /**
     * The TextView for the score.
     */
    private TextView currentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twenty_game);
        loadFromFile();
        createTileButtons(this);
        currentScore = findViewById(R.id.TwentyScore);
        gridView = findViewById(R.id.TwentyGrid);
        gridView.setNumColumns(twentyManager.getBoard().getNumCol());
        gridView.setTwentyManager(twentyManager);
        twentyManager.addObserver(this);
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / twentyManager.getBoard().getNumCol();
                        columnHeight = displayHeight / twentyManager.getBoard().getNumRow();

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
        TwentyBoard board = twentyManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != twentyManager.getBoard().getNumRow(); row++) {
            for (int col = 0; col != twentyManager.getBoard().getNumCol(); col++) {
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
        TwentyBoard board = twentyManager.getBoard();
        currentScore.setText(String.valueOf(twentyManager.getScore()));
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / twentyManager.getBoard().getNumRow();
            int col = nextPos % twentyManager.getBoard().getNumCol();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
        LoginActivity.usersManager.getCurrentUser().saveGame(TwentyStartingActivity.GAME_TYPE,
                twentyManager);
        saveToFile(LoginActivity.USER_SAVE_FILENAME, LoginActivity.usersManager);
        saveToFile(TwentyStartingActivity.TEMP_SAVE_FILENAME, twentyManager);
        if (gridView.checkGameSituation(this).equals("Win")) {
            saveToScoreBoard();
        }
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(TwentyStartingActivity.TEMP_SAVE_FILENAME, twentyManager);
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
     * Load the board manager from TEMP_SAVE_FILENAME.
     */
    private void loadFromFile() {

        try {
            InputStream inputStream = this.openFileInput(TwentyStartingActivity.TEMP_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                twentyManager = (TwentyManager) input.readObject();
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
     * Saving the score when the game is finish and when the maximum undo is 3.
     */
    public void saveToScoreBoard() {
        String gameType = TwentyStartingActivity.GAME_TYPE + " " + twentyManager.getComplexity();
        if (twentyManager.getMaxUndo() == 3) {
            TwentyStartingActivity.scoreBoardManager.updateScoreBoard(twentyManager.getComplexity(),
                    LoginActivity.usersManager.getCurrentUser().getUserName(),
                    twentyManager.getScore(), TwentyStartingActivity.ORDER);
            LoginActivity.usersManager.getCurrentUser().updateScore(gameType,
                    twentyManager.getScore(), TwentyStartingActivity.ORDER);
            saveToFile(TwentyStartingActivity.SCOREBOARD_SAVE_FILENAME,
                    TwentyStartingActivity.scoreBoardManager);
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
}
