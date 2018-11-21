package group_0548.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The LoginActivity Activity class
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * The UserManager class that store all the users
     */
    private UsersManager usersManager;

    /**
     * The context for the activity class.
     */
    private Context myContext = LoginActivity.this;

    /**
     * Initialize the activity layout of the login screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usersManager = new UsersManager(myContext.getFilesDir().getPath());
        addSignInButtonListener();
        addSignUpButtonListener();
    }

    /**
     * Initialize the sign in button
     * After clicking sign in, this method redirects the user to
     * the GameCentreActivity if sign in is valid
     * else redirects to the sign up page
     */
    private void addSignInButtonListener() {
        Button signIn = findViewById(R.id.SignInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userName = findViewById(R.id.Username);
                EditText userPass = findViewById(R.id.Password);
                String name = userName.getText().toString();
                String pass = userPass.getText().toString();
                if (usersManager.checkUser(name, pass)) {
                    UsersManager.setCurrentUser(usersManager.getUser(name, pass));
                    switchToGameCentre();
                } else {
                    makeToastNoUserText();
                }
            }
        });
    }

    /**
     * Initialize the sign up button
     */
    private void addSignUpButtonListener() {
        Button signUpButton = findViewById(R.id.SignUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignUp();
            }
        });
    }

    /**
     * Switch to the GameCentreActivity page
     */
    private void switchToGameCentre() {
        Intent tmp = new Intent(this, GameCentreActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the sign up page
     */
    private void switchToSignUp() {
        Intent tmp = new Intent(this, SignUpActivity.class);
        startActivity(tmp);
    }

    /**
     * Display that the user does not exist.
     */
    private void makeToastNoUserText() {
        Toast.makeText(this, "The username or password is not correct.", Toast.LENGTH_SHORT).show();
    }
}

