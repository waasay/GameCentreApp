package group_0548.gamecentre.colourguess;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import group_0548.gamecentre.CustomAdapter;
import group_0548.gamecentre.R;
import group_0548.gamecentre.UsersManager;
import group_0548.gamecentre.slidingtiles.Board;
import group_0548.gamecentre.slidingtiles.StartingActivity;

public class ColourGuessChoosePhaseActivity extends AppCompatActivity {

    private ColourGuessGestureDetectGridView gridView;

    /**
     * The TextView for the score.
     */
    private TextView currentScore;

    /**
     * The TextView for the timer.
     */
    private TextView countdownTimerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_memory_phase);

        loadFromFile();
        createTileButtons(this);

        // Add View to activity
        countdownTimerText = findViewById(R.id.MemoryTime);
        startTimer(60000);
        currentScore = findViewById(R.id.ColourGuessScore);
        gridView = findViewById(R.id.ChooseGrid);
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
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
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

    private void startTimer(int milliSecond) {
        CountDownTimer countDownTimer = new CountDownTimer(milliSecond, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format(Locale.US, "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millis),
                        TimeUnit.MILLISECONDS.toSeconds(millis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownTimerText.setText(hms);//set text
            }
            public void onFinish() {
                countdownTimerText.setText("TIME'S UP!"); //On finish change timer text
                switchToChoose();
            }
        }.start();
    }

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
    public void update(Observable o, Object arg) {
        display();
    }
}

