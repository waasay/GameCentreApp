package group_0548.gamecentre.twentygame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
     * The board manager.
     */
    private TwentyManager twentyManager;

    /**
     * TextView for the current undo to display on the app
     */
    private TextView currentUndo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twenty_choose_complexity);
        MAX_UNDO = 3;

        customUndo();
        currentUndo = findViewById(R.id.SlidingCurrUndo);
    }

    /**
     * Initialize the listener for the hard complexity button
     */
    private void addStartButtonListener() {
        Button fiveButton = findViewById(R.id.SlidingHardButton);
        fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twentyManager = new TwentyManager(5, 5, "Hard", MAX_UNDO);
                switchToGame();
            }
        });
    }

    /**
     * Initialize the EditText for the user to input custom maximum
     * input, and the confirm button that saves it
     */
    private void customUndo() {
        Button confirmButton = findViewById(R.id.SlidingSetUndo);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText maxUndo = findViewById(R.id.SlidingCustomUndo);
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
