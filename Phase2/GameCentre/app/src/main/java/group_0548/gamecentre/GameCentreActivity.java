package group_0548.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import group_0548.gamecentre.slidingtiles.StartingActivity;
import group_0548.gamecentre.colourguess.ColourGuessStartingActivity;

/**
 * The GameCentreActivity class where all the available games can be viewed and selected
 */
public class GameCentreActivity extends AppCompatActivity {
    /**
     * Initialize the activity layout of the GameCentreActivity screen
     *
     * @param savedInstanceState The instance saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_centre);
        addStartSlidingTilesListener();
        addStartColourGuessListener();
    }

    /**
     * Initializing the Sliding Tile button to go to the sliding tile game
     */
    private void addStartSlidingTilesListener() {
        Button button = findViewById(R.id.SlidingTilesGame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSlidingTiles();
            }
        });
    }

    /**
     * Initializing the Colour Guess button to go to the sliding tile game
     */
    private void addStartColourGuessListener() {
        Button button = findViewById(R.id.ColourGuessGame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToColourGuess();
            }
        });
    }

    /**
     * Method to go the sliding tile game activity page
     */
    private void switchToSlidingTiles() {
        Intent tmp = new Intent(this, StartingActivity.class);
        startActivity(tmp);
    }

    /**
     * Method to go the colour guess game activity page
     */
    private void switchToColourGuess() {
        Intent tmp = new Intent(this, ColourGuessStartingActivity.class);
        startActivity(tmp);
    }
}
