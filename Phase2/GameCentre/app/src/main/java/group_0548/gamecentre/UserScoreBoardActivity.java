package group_0548.gamecentre;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class UserScoreBoardActivity extends AppCompatActivity {

    /**
     * TextView to display the username
     */
    TextView gameText;

    /**
     * TextView to display the score of the username
     * displayed by useText
     */
    TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_score_board);
        gameText = findViewById(R.id.UserScoreGames);
        scoreText = findViewById(R.id.UserScoreScores);
        showScore();
    }

    private void showScore() {
        ArrayList<String> score = UsersManager.getCurrentUser().getUserScore();
        gameText.setText(score.get(0));
        scoreText.setText(score.get(1));
    }


}
