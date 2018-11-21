package group_0548.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The LoginActivity Activity class
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * A save file for users.
     */
    public static final String USER_SAVE_FILENAME = "user_save_file.ser";
    /**
     * The UserManager class that store all the users
     */
    public static UsersManager usersManager = new UsersManager();

    /**
     * Initialize the activity layout of the login screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadFromFile(USER_SAVE_FILENAME);
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
                    usersManager.setCurrentUser(usersManager.getUser(name, pass));
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

    /**
     * Load the object from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {
        try {
            File file = this.getFileStreamPath(fileName);
            if (file.exists()) {
                InputStream inputStream = this.openFileInput(fileName);
                if (inputStream != null) {
                    ObjectInputStream input = new ObjectInputStream(inputStream);
                    usersManager = (UsersManager) input.readObject();
                    inputStream.close();
                }
            }
        } catch (FileNotFoundException e) {
            if (fileName.equals(USER_SAVE_FILENAME)) {
                saveToFile(USER_SAVE_FILENAME, usersManager);
            }
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the object to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName, Object obj) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(obj);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}

