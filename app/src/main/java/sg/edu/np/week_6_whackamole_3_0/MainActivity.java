package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText loginUsername;
    private EditText loginPassword;
    private Button loginButton;
    private TextView loginSignUpText;

    private static final String FILENAME = "MainActivity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginUsername = (EditText) findViewById(R.id.loginUsernameBlank);
        loginPassword = (EditText) findViewById(R.id.loginPasswordBlank);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginSignUpText = (TextView) findViewById(R.id.loginSignUpText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(loginUsername.getEditableText().toString().replace(" ", "").isEmpty() ||
                        loginPassword.getEditableText().toString().replace(" ", "").isEmpty())){
                    String username = loginUsername.getEditableText().toString();
                    String password = loginPassword.getEditableText().toString();
                    Log.v(TAG, FILENAME + ": Logging in with: " + username + ": " + password);
                    if (isValidUser(username, password) == true){
                        Log.v(TAG, FILENAME + ": Valid User! Logging in");
                        Intent toMain3Activity = new Intent(MainActivity.this, Main3Activity.class);
                        toMain3Activity.putExtra("Username", username);
                        startActivity(toMain3Activity);
                    }
                    else{
                        Log.v(TAG, FILENAME + ": Invalid user!");
                        Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginSignUpText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent toMain2Activity = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(toMain2Activity);
                Log.v(TAG, FILENAME + ": Create new user!");
                return false;
            }
        });
    }

    protected void onStop(){
        super.onStop();
        finish();
    }

    public boolean isValidUser(String userName, String password){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        UserData savedUserData = dbHandler.findUser(userName);
        if (savedUserData != null){
            Log.v(TAG, FILENAME + ": Running Checks... " + savedUserData.getMyUserName() + ": " + savedUserData.getMyPassword() + " <--> " + userName + ": " + password);
            String savedPassword = savedUserData.getMyPassword();
            if (savedPassword.equals(password)){
                dbHandler.close();
                return true;
            }
        }
        dbHandler.close();
        return false;
    }



}
