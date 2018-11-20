package group_0548.gamecentre.slidingtiles;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import group_0548.gamecentre.CustomAdapter;
import group_0548.gamecentre.R;
import group_0548.gamecentre.UsersManager;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    private static int columnWidth, columnHeight;
    /**
     * The board manager.
     */
    private BoardManager boardManager;
    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;
    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
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
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getBoard().getNumCol());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getBoard().getNumCol();
                        columnHeight = displayHeight / boardManager.getBoard().getNumRow();

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
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getBoard().getNumRow(); row++) {
            for (int col = 0; col != boardManager.getBoard().getNumCol(); col++) {
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
        Board board = boardManager.getBoard();
        currentScore.setText(String.valueOf(board.getScore()));
        saveToScoreBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getBoard().getNumRow();
            int col = nextPos % boardManager.getBoard().getNumCol();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
        UsersManager.getCurrentUser().saveGame(StartingActivity.GAME_TYPE, boardManager);
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME, boardManager);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME, boardManager);
    }

    /**
     * Load the board manager from TEMP_SAVE_FILENAME.
     */
    private void loadFromFile() {

        try {
            InputStream inputStream = this.openFileInput(StartingActivity.TEMP_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
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
     * Save the board manager to fileName.
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
     * Implementing button for undo
     *
     * @param view view for the undo button
     */

    public void undo(View view) {

        if (this.boardManager.ableToUndo()) {
            this.boardManager.undoToPastState();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No more undo", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
        }
    }

    /**
     * Implementing button for redo
     *
     * @param view view for the undo button
     */
    public void redo(View view) {

        if (this.boardManager.ableToRedo()) {
            this.boardManager.redoToFutureState();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No more redo", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
        }
    }

    /**
     * Saving the score when the game is finish and when the maximum undo is default
     * at 3
     */

    public void saveToScoreBoard() {
        if (boardManager.puzzleSolved() && BoardManager.getMaxUndo() == 3) {
            StartingActivity.scoreBoardManager.updateScoreBoard(boardManager.getComplexity(),
                    UsersManager.getCurrentUser().getUserName(), boardManager.getBoard().getScore());
            saveToFile(StartingActivity.SCOREBOARD_SAVE_FILENAME, StartingActivity.scoreBoardManager);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
