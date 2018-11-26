package group_0548.gamecentre.colourguess;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import group_0548.gamecentre.CustomAdapter;
import group_0548.gamecentre.LoginActivity;
import group_0548.gamecentre.R;

public class ColourGuessChoosePhaseActivity extends AppCompatActivity implements Observer {

    private ColourGuessGestureDetectGridView gridView;

    /**
     * The TextView for the score.
     */
    private TextView currentScore;

    /**
     * The TextView for the timer.
     */
    private TextView countdownTimerText;
    private TextView chooseColour;

    private static CountDownTimer countDownTimer;

    private Context myContext = this;

    private static int columnWidth, columnHeight;

    private ArrayList<Button> tileButtons;

    private String[] colours = {"Purple", "Blue", "Green", "Yellow", "Orange", "Red"};

    private int startTime;

    private int endTime;

    /**
     * The colour guess manager.
     */
    private ColourGuessManager colourGuessManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_choose_phase);

        loadFromFile();
        createTileButtons(this);
        addConfirmButtonListener();
        Random randomGen = new Random();
        int randomInt = randomGen.nextInt(6);
        colourGuessManager.setId(randomInt);
        String colour = colours[randomInt];
        chooseColour = findViewById(R.id.ChooseColour);
        chooseColour.setText("Please choose all the " + colour + " tiles.");

        // Add Timer to activity
        countdownTimerText = findViewById(R.id.ChooseTime);
        startTimer(colourGuessManager.getTime());
        startTime= (int) System.currentTimeMillis();

        currentScore = findViewById(R.id.ColourGuessScore);
        currentScore.setText(String.valueOf(colourGuessManager.getScore()));
        gridView = findViewById(R.id.ChooseGrid);
        gridView.setNumColumns(colourGuessManager.getBoard2().getNumCol());
        gridView.setColourGuessManager(colourGuessManager);
        colourGuessManager.addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / colourGuessManager.getBoard2().getNumCol();
                        columnHeight = displayHeight / colourGuessManager.getBoard2().getNumRow();

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
        ColourGuessBoard board = colourGuessManager.getBoard2();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != colourGuessManager.getBoard2().getNumRow(); row++) {
            for (int col = 0; col != colourGuessManager.getBoard2().getNumCol(); col++) {
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
        ColourGuessBoard board = colourGuessManager.getBoard2();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / colourGuessManager.getBoard2().getNumRow();
            int col = nextPos % colourGuessManager.getBoard2().getNumCol();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
        saveToFile(ColourGuessStartingActivity.TEMP_SAVE_FILENAME, colourGuessManager);
    }

    /**
     * Load the board manager from TEMP_SAVE_FILENAME.
     */
    private void loadFromFile() {

        try {
            InputStream inputStream = this.openFileInput(ColourGuessStartingActivity
                    .TEMP_SAVE_FILENAME);
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

    public void addConfirmButtonListener() {
        Button threeButton = findViewById(R.id.ChooseConfirmButton);
        threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMemory();
            }
        });
    }

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
                Toast.makeText(myContext, "Time's up! Your score is : " + colourGuessManager
                        .getScore(), Toast.LENGTH_LONG).show();
                switchToStart();
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

    public void saveToScoreBoard() {
        String gameType = ColourGuessStartingActivity.GAME_TYPE + " " +
                colourGuessManager.getComplexity();
        ColourGuessStartingActivity.scoreBoardManager.updateScoreBoard(colourGuessManager
                .getComplexity(), LoginActivity.usersManager.getCurrentUser().getUserName(),
                colourGuessManager.getScore(), ColourGuessStartingActivity.ORDER);
        LoginActivity.usersManager.getCurrentUser().updateScore(gameType, colourGuessManager
                .getScore(), ColourGuessStartingActivity.ORDER);
        saveToFile(ColourGuessStartingActivity.SCOREBOARD_SAVE_FILENAME,
                ColourGuessStartingActivity.scoreBoardManager);
        saveToFile(LoginActivity.USER_SAVE_FILENAME, LoginActivity.usersManager);
    }

    public void switchToStart() {
        saveToScoreBoard();
        Intent tep = new Intent(this, ColourGuessStartingActivity.class);
        startActivity(tep);
    }

    public void switchToMemory() {
        endTime= (int) System.currentTimeMillis();
        stopCountdown();
        colourGuessManager.setTime(colourGuessManager.getTime() - endTime + startTime);
        if (colourGuessManager.puzzleSolved()) {
            colourGuessManager.increaseScore(1);
            colourGuessManager.reset();
            saveToFile(ColourGuessStartingActivity.TEMP_SAVE_FILENAME, colourGuessManager);
            Intent tep = new Intent(this, ColourGuessMemoryPhaseActivity.class);
            startActivity(tep);
        } else {
            Toast.makeText(myContext, "Incorrect! Your score is : " + colourGuessManager
                    .getScore(), Toast.LENGTH_LONG).show();
            switchToStart();
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopCountdown();
        startActivity(new Intent(this, ColourGuessStartingActivity.class));
        finish();
    }

    private void stopCountdown() {
        countDownTimer.cancel();
    }
}
