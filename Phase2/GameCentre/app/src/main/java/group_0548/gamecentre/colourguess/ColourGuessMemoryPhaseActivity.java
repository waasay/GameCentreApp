package group_0548.gamecentre.colourguess;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import android.os.CountDownTimer;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;
import java.util.Locale;

import group_0548.gamecentre.CustomAdapter;
import group_0548.gamecentre.R;

public class ColourGuessMemoryPhaseActivity extends AppCompatActivity {

    /**
     * The TextView for the timer.
     */
    private TextView countdownTimerText;

    private ColourGuessGestureDetectGridView gridView;

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
        CountDownTimer  countDownTimer = new CountDownTimer(milliSecond, 1000) {
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

    private void switchToChoose() {
        Intent tep = new Intent(this, ColourGuessChoosePhaseActivity.class);
        startActivity(tep);
    }
}
