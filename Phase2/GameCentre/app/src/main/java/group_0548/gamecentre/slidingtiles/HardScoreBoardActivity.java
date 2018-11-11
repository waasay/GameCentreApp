package group_0548.gamecentre.slidingtiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

import group_0548.gamecentre.R;

/**
 * The hard mode scoreboard activity
 */
public class HardScoreBoardActivity extends AppCompatActivity {

    /**
     * TextView to display the username
     */
    TextView userText;

    /**
     * TextView to display the score of the username
     * displayed by useText
     */
    TextView scoreText;

    /**
     * Initialize the scoreboard for hard mode, 5 by 5
     *
     * @param savedInstanceState The instance saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_score_board);
        userText = findViewById(R.id.HardUserName);
        scoreText = findViewById(R.id.HardScore);
        printScoreboard();
    }

    /**
     * Displaying the score board of the best players
     */
    void printScoreboard() {
        ArrayList<String> list = StartingActivity.scoreBoardManager.getScoreBoard("Hard")
                .getScoreContent();
        if (list.size() != 0) {
            userText.setText(list.get(0));
            scoreText.setText(list.get(1));
        }
    }
}
