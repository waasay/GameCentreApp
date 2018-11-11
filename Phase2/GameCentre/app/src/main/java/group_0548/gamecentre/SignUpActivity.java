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
 * The sign up activity class that registers a user
 */
public class SignUpActivity extends AppCompatActivity {
    /**
     * Class that manages and stores all the users
     */
    private UsersManager usersManager;
    private Context myContext = SignUpActivity.this;

    /**
     * Initialize the activity layout of the sign up screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        addSignUpButtonListener();
        usersManager = new UsersManager(myContext.getFilesDir().getPath());

    }

    /**
     * Initialize the signup button
     * Creates a new user if the username doesn't exist
     * Else will inform the user the username exists
     */
    private void addSignUpButtonListener() {
        Button signIn = findViewById(R.id.CreateAccountButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userName = findViewById(R.id.TypeUsername);
                EditText userPass = findViewById(R.id.TypePassword);
                String name = userName.getText().toString();
                String pass = userPass.getText().toString();
                if (usersManager.checkUsernameValid(name)) {
                    User newUser = new User(name, pass);
                    usersManager.addUser(newUser);
                    makeToastCreateUserText();
                    switchToLogin();
                } else {
                    makeToastExistUserText();
                }

            }
        });
    }

    /**
     * Switch to the login page
     */
    private void switchToLogin() {
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);
    }

    /**
     * Display that the user already exists
     */
    private void makeToastExistUserText() {
        Toast.makeText(this, "User already exists.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a user has been created
     */
    private void makeToastCreateUserText() {
        Toast.makeText(this, "Your account was successfully created.",
                Toast.LENGTH_SHORT).show();
    }
}