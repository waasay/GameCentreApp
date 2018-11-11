package group_0548.gamecentre.slidingtiles;

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

/**
 * The layout activity that allows the user to pick the difficulty
 * and amount of undo's times of the sliding tile game.
 */
public class ChooseComplexityActivity extends AppCompatActivity {

    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";

    /**
     * The maximum undo of a game
     */
    private static int MAX_UNDO = 3;
    /**
     * The board manager.
     */
    private BoardManager boardManager;

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
        addEasyButtonListener();
        addMediumButtonListener();
        addHardButtonListener();
        customUndo();
        currentUndo = findViewById(R.id.CurrUndo);
    }

    /**
     * Initialize the listener for the easy complexity button
     */
    private void addEasyButtonListener() {
        Button threeButton = findViewById(R.id.EasyButton);
        threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(3, 3, "Easy", MAX_UNDO);
                switchToGame();
            }
        });
    }

    /**
     * Initialize the listener for the medium complexity button
     */
    private void addMediumButtonListener() {
        Button fourButton = findViewById(R.id.MediumButton);
        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(4, 4, "Medium", MAX_UNDO);
                switchToGame();
            }
        });
    }

    /**
     * Initialize the listener for the hard complexity button
     */
    private void addHardButtonListener() {
        Button fiveButton = findViewById(R.id.HardButton);
        fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(5, 5, "Hard", MAX_UNDO);
                switchToGame();
            }
        });
    }

    /**
     * Initialize the EditText for the user to input custom maximum
     * input, and the confirm button that saves it
     */
    private void customUndo() {
        Button confirmButton = findViewById(R.id.SetUndo);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText maxUndo = findViewById(R.id.CustomUndo);
                String undos = maxUndo.getText().toString();
                if (!undos.equals("")) {
                    MAX_UNDO = Integer.parseInt(undos);
                    currentUndo.setText(String.valueOf(MAX_UNDO));
                }
            }
        });

    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        saveToFile(TEMP_SAVE_FILENAME);
        startActivity(tmp);
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
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
