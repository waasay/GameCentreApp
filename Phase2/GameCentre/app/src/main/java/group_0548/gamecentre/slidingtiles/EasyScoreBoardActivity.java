package group_0548.gamecentre.slidingtiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

import group_0548.gamecentre.R;


/**
 * The easy scoreboard activity
 */
public class EasyScoreBoardActivity extends AppCompatActivity {

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
     * Initialize the scoreboard for easy mode, 3 by 3
     *
     * @param savedInstanceState The instance saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_score_board);
        userText = findViewById(R.id.EasyUserName);
        scoreText = findViewById(R.id.EasyScore);
        printScoreboard();
    }

    /**
     * Displaying the score board of the best players
     */
    void printScoreboard() {
        ArrayList<String> list = StartingActivity.scoreBoardManager.getScoreBoard("Easy")
                .getScoreContent();
        if (list.size() != 0) {
            userText.setText(list.get(0));
            scoreText.setText(list.get(1));
        }
    }
}
