package group_0548.gamecentre.twentygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectOutputStream;

import group_0548.gamecentre.R;


public class TwentyChooseComplexityActivity extends AppCompatActivity {

    /**
     * The maximum undo of a game
     */
    private static int MAX_UNDO = 3;
    /**
     * The board manager for 2048.
     */
    private TwentyManager twentyManager;

    /**
     * TextView for the current undo to display on the app
     */
    private TextView currentUndo;

    /**
     * Initialize the layout activity of Complete
     *
     * @param savedInstanceState The instance saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_complexity);
        MAX_UNDO = 3;
        customUndo();
        currentUndo = findViewById(R.id.GameCurrUndo);
        addEasyButtonListener();
        addMediumButtonListener();
        addHardButtonListener();
    }

    /**
     * Initialize the listener for the easy complexity button
     */
    private void addEasyButtonListener() {
        Button threeButton = findViewById(R.id.GameEasyButton);
        threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twentyManager = new TwentyManager(5, 5, "Easy", MAX_UNDO);
                switchToGame();
            }
        });
    }

    /**
     * Initialize the listener for the medium complexity button
     */
    private void addMediumButtonListener() {
        Button fourButton = findViewById(R.id.GameMediumButton);
        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twentyManager = new TwentyManager(4, 4, "Medium", MAX_UNDO);
                switchToGame();
            }
        });
    }

    /**
     * Initialize the listener for the hard complexity button
     */
    private void addHardButtonListener() {
        Button fiveButton = findViewById(R.id.GameHardButton);
        fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twentyManager = new TwentyManager(3, 3, "Hard", MAX_UNDO);
                switchToGame();
            }
        });
    }

    /**
     * Switch to the TwentyGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, TwentyGameActivity.class);
        saveToFile(TwentyStartingActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Initialize the EditText for the user to input custom maximum
     * input, and the confirm button that saves it
     */
    private void customUndo() {
        Button confirmButton = findViewById(R.id.GameSetUndo);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText maxUndo = findViewById(R.id.GameCustomUndo);
                String undo = maxUndo.getText().toString();
                if (!undo.equals("")) {
                    MAX_UNDO = Integer.parseInt(undo);
                    currentUndo.setText(String.valueOf(MAX_UNDO));
                }
            }
        });
    }


    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(twentyManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
