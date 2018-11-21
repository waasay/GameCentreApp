package group_0548.gamecentre;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


/**
 * The easy scoreboard activity
 */
public class ScoreBoardActivity extends AppCompatActivity {

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
     * Initialize the scoreboard.
     *
     * @param savedInstanceState The instance saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        userText = findViewById(R.id.ScoreUserNames);
        scoreText = findViewById(R.id.ScoreScores);
        userText.setText(ChooseScoreBoardActivity.userNames);
        scoreText.setText(ChooseScoreBoardActivity.userScores);
    }
}
