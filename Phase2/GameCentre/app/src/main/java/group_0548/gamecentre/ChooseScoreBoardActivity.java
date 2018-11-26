package group_0548.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import group_0548.gamecentre.colourguess.ColourGuessStartingActivity;
import group_0548.gamecentre.slidingtiles.SlidingStartingActivity;

/**
 * The scoreboard activity.
 */
public class ChooseScoreBoardActivity extends AppCompatActivity {

    /**
     * List of user names.
     */
    public static String userNames;

    /**
     * List of user scores.
     */
    public static String userScores;

    /**
     * The game type of the current game.
     */
    public static String gameType = null;


    /**
     * Initialize the activity layout that directs the user to different
     * scoreboards.
     *
     * @param savedInstanceState the instance saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_score_board);
        addEasyButtonListener();
        addMediumButtonListener();
        addHardButtonListener();
        addUserButtonListener();
    }

    /**
     * Activate the easy button.
     */
    private void addEasyButtonListener() {
        Button returnButton = findViewById(R.id.ScoreBoardEasyButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScore("Easy");
            }
        });
    }

    /**
     * Activate the medium button.
     */
    private void addMediumButtonListener() {
        Button returnButton = findViewById(R.id.ScoreBoardMediumButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScore("Medium");
            }
        });
    }

    /**
     * Activate the easy button.
     */
    private void addHardButtonListener() {
        Button returnButton = findViewById(R.id.ScoreBoardHardButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScore("Hard");
            }
        });
    }

    /**
     * Activate the user button.
     */
    private void addUserButtonListener() {
        Button returnButton = findViewById(R.id.ScoreBoardUserButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToUser();
            }
        });
    }

    /**
     * Switch to the scoreboard for current user.
     */
    private void switchToUser() {
        Intent tep = new Intent(this, UserScoreBoardActivity.class);
        startActivity(tep);
    }

    /**
     * Switch to the scoreboard for easy difficult.
     */
    private void switchToScore(String complexity) {
        ArrayList<String> list = new ArrayList<>();
        if (gameType.equals(SlidingStartingActivity.GAME_TYPE)) {
            list = SlidingStartingActivity.scoreBoardManager.getScoreBoard(complexity)
                    .getScoreContent(SlidingStartingActivity.ORDER);
        } else if (gameType.equals(ColourGuessStartingActivity.GAME_TYPE)) {
            list = ColourGuessStartingActivity.scoreBoardManager.getScoreBoard(complexity)
                    .getScoreContent(ColourGuessStartingActivity.ORDER);
        }
        if (list.size() != 0) {
            userNames = list.get(0);
            userScores = list.get(1);
        }
        Intent tep = new Intent(this, ScoreBoardActivity.class);
        startActivity(tep);
    }
}
