package group_0548.gamecentre.colourguess;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import group_0548.gamecentre.CustomAdapter;
import group_0548.gamecentre.R;

public class ColourGuessMemoryPhaseActivity extends AppCompatActivity {

    /**
     * The dimensions of the boards
     */
    private static int columnWidth, columnHeight;
    /**
     * The countdown timer
     */
    private static CountDownTimer countDownTimer;
    /**
     * The TextView for the timer.
     */
    private TextView countdownTimerText;
    /**
     * The gridview of the game
     */
    private ColourGuessGestureDetectGridView gridView;
    /**
     * An array of buttons to tap that correspond to the tiles
     */
    private ArrayList<Button> tileButtons;
    /**
     * The colour guess manager.
     */
    private ColourGuessManager colourGuessManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_memory_phase);

        loadFromFile();
        createTileButtons(this);

        // Add View to activity
        countdownTimerText = findViewById(R.id.MemoryTime);
        startTimer(5000);
        gridView = findViewById(R.id.ColourGrid);
        gridView.setNumColumns(colourGuessManager.getBoard().getNumCol());
        gridView.setColourGuessManager(colourGuessManager);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / colourGuessManager.getBoard().getNumCol();
                        columnHeight = displayHeight / colourGuessManager.getBoard().getNumRow();

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
        ColourGuessBoard board = colourGuessManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != colourGuessManager.getBoard().getNumRow(); row++) {
            for (int col = 0; col != colourGuessManager.getBoard().getNumCol(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Load the board manager from TEMP_SAVE_FILENAME.
     */
    private void loadFromFile() {

        try {
            InputStream inputStream = this.openFileInput(ColourGuessStartingActivity.TEMP_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                colourGuessManager = (ColourGuessManager) input.readObject();
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
     * Start the count down timer at the given input time
     *
     * @param milliSecond the starting time of the count down timer in milliseconds
     */
    private void startTimer(int milliSecond) {
        countDownTimer = new CountDownTimer(milliSecond, 1000) {
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
                switchToChoose();
            }
        }.start();
    }

    /**
     * Switch to the ColourGuessChoosePhaseActivity
     */
    private void switchToChoose() {
        Intent tep = new Intent(this, ColourGuessChoosePhaseActivity.class);
        startActivity(tep);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopCountdown();
        startActivity(new Intent(this, ColourGuessStartingActivity.class));
        finish();
    }

    /**
     * Stopping the countdown
     */
    private void stopCountdown() {
        countDownTimer.cancel();
    }
}
