package group_0548.gamecentre.colourguess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Observable;

import group_0548.gamecentre.R;

public class ColourGuessChooseComplexityActivity extends AppCompatActivity {

    /**
     * The colour guess manager.
     */
    private MemoryManager memoryManager;

    /**
     * Initialize the layout activity of Complete
     *
     * @param savedInstanceState The instance saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_choose_complexity);
        addEasyButtonListener();
        addMediumButtonListener();
        addHardButtonListener();
    }

    /**
     * Initialize the listener for the easy complexity button
     */
    private void addEasyButtonListener() {
        Button threeButton = findViewById(R.id.ColourEasyButton);
        threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoryManager = new MemoryManager(3, 3, "Easy");
                switchToGame();
            }
        });
    }

    /**
     * Initialize the listener for the medium complexity button
     */
    private void addMediumButtonListener() {
        Button fourButton = findViewById(R.id.ColourMediumButton);
        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoryManager = new MemoryManager(4, 4, "Medium");
                switchToGame();
            }
        });
    }

    /**
     * Initialize the listener for the hard complexity button
     */
    private void addHardButtonListener() {
        Button fiveButton = findViewById(R.id.ColourHardButton);
        fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoryManager = new MemoryManager(5, 5, "Hard");
                switchToGame();
            }
        });
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, ColourGuessMemoryPhaseActivity.class);
        saveToFile(ColourGuessStartingActivity.TEMP_SAVE_FILENAME);
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
            outputStream.writeObject(memoryManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}

