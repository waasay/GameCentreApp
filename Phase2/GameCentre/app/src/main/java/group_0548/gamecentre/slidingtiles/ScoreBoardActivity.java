package group_0548.gamecentre.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import group_0548.gamecentre.R;

/**
 * The scoreboard activity.
 */
public class ScoreBoardActivity extends AppCompatActivity {

    /**
     * Initialize the activity layout that directs the user to different
     * scoreboards.
     *
     * @param savedInstanceState the instance saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        addEasyButtonListener();
        addMediumButtonListener();
        addHardButtonListener();
    }

    /**
     * Activate the easy button.
     */
    private void addEasyButtonListener() {
        Button returnButton = findViewById(R.id.ScoreEasyButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToEasyScore();
            }
        });
    }

    /**
     * Activate the medium button.
     */
    private void addMediumButtonListener() {
        Button returnButton = findViewById(R.id.ScoreMediumButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMediumScore();
            }
        });
    }

    /**
     * Activate the easy button.
     */
    private void addHardButtonListener() {
        Button returnButton = findViewById(R.id.ScoreHardButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToHardScore();
            }
        });
    }

    /**
     * Switch to the scoreboard for easy difficult.
     */
    private void switchToEasyScore() {
        Intent tep = new Intent(this, EasyScoreBoardActivity.class);
        startActivity(tep);
    }

    /**
     * Switch to the scoreboard for medium difficult.
     */
    private void switchToMediumScore() {
        Intent tep = new Intent(this, MediumScoreBoardActivity.class);
        startActivity(tep);
    }

    /**
     * Switch to the scoreboard for hard difficult.
     */
    private void switchToHardScore() {
        Intent tep = new Intent(this, HardScoreBoardActivity.class);
        startActivity(tep);
    }
}
