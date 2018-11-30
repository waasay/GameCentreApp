package group_0548.gamecentre.twentygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import group_0548.gamecentre.ChooseScoreBoardActivity;
import group_0548.gamecentre.LoginActivity;
import group_0548.gamecentre.R;
import group_0548.gamecentre.ScoreBoardManager;


public class TwentyStartingActivity extends AppCompatActivity {

    /**
     * The save file for scoreboard.
     */
    public static final String SCOREBOARD_SAVE_FILENAME = "twenty_scoreboard_save_file.ser";
    /**
     * The game type for current game.
     */
    public static final String GAME_TYPE = "2048";

    /**
     * The arrange method for current game.
     */
    public static final String ORDER = "Descending";

    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "twenty_save_file_tmp.ser";

    /**
     * The scoreboard manager.
     */
    public static ScoreBoardManager scoreBoardManager = new ScoreBoardManager();

    /**
     * The board manager.
     */
    private TwentyManager twentyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twenty_starting);
        addLoadButtonListener();
        addStartButtonListener();
        addScoreButtonListener();
        loadFromFile(SCOREBOARD_SAVE_FILENAME);
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.TwentyStartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToComplex();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.TwentyLoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twentyManager = (TwentyManager) LoginActivity.usersManager.getCurrentUser()
                        .loadGame(GAME_TYPE);
                if (twentyManager != null) {
                    saveToFile(TEMP_SAVE_FILENAME, twentyManager);
                    makeToastLoadedText();
                    switchToGame();
                }
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the scoreboard button.
     */
    private void addScoreButtonListener() {
        Button scoreButton = findViewById(R.id.TwentyScoreButton);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreBoard();
            }
        });
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the SlidingGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, TwentyGameActivity.class);
        saveToFile(TwentyStartingActivity.TEMP_SAVE_FILENAME, twentyManager);
        startActivity(tmp);
    }


    /**
     * Switch to the ChooseScoreBoardActivity view to see the leader board.
     */
    private void switchToScoreBoard() {
        ChooseScoreBoardActivity.gameType = GAME_TYPE;
        Intent tep = new Intent(this, ChooseScoreBoardActivity.class);
        startActivity(tep);
    }

    /**
     * Switch to the complexity view.
     */
    private void switchToComplex() {
        Intent tep = new Intent(this, TwentyChooseComplexityActivity.class);
        startActivity(tep);
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
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load the object from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {
        try {
            File file = this.getFileStreamPath(fileName);
            if (file.exists()) {
                InputStream inputStream = this.openFileInput(fileName);
                if (inputStream != null) {
                    if (fileName.equals(SCOREBOARD_SAVE_FILENAME)) {
                        ObjectInputStream input = new ObjectInputStream(inputStream);
                        scoreBoardManager = (ScoreBoardManager) input.readObject();
                        inputStream.close();
                    } else {
                        ObjectInputStream input = new ObjectInputStream(inputStream);
                        twentyManager = (TwentyManager) input.readObject();
                        inputStream.close();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            if (fileName.equals(SCOREBOARD_SAVE_FILENAME)) {
                saveToFile(SCOREBOARD_SAVE_FILENAME, scoreBoardManager);
            }
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }
}