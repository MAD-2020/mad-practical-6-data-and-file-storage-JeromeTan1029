package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private EditText signUpUsername;
    private EditText signUpPassword;
    private Button signUpCancelButton;
    private Button signUpCreateButton;

    private static final String FILENAME = "Main2Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        signUpUsername = (EditText) findViewById(R.id.signUpUsernameBlank);
        signUpPassword = (EditText) findViewById(R.id.signUpPasswordBlank);
        signUpCancelButton = (Button) findViewById(R.id.signUpCancelButton);
        signUpCreateButton = (Button) findViewById(R.id.signUpCreateButton);

        signUpCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainActivity = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(toMainActivity);
            }
        });

        signUpCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(signUpUsername.getEditableText().toString().replace(" ", "").isEmpty() ||
                        signUpPassword.getEditableText().toString().replace(" ", "").isEmpty())){
                    String username = signUpUsername.getEditableText().toString();
                    String password = signUpPassword.getEditableText().toString();
                    if (username.contains(" ") || password.contains(" ")){
                        Toast.makeText(getApplicationContext(), "Whitespace is not allowed in username or password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        if (addNewUser(username, password) == true){
                            Log.v(TAG, FILENAME + ": New user created successfully!");
                            Toast.makeText(getApplicationContext(), "User account created successfully", Toast.LENGTH_SHORT).show();
                            Intent toMainActivity = new Intent(Main2Activity.this, MainActivity.class);
                            startActivity(toMainActivity);
                            return;
                        }
                        else{
                            Log.v(TAG, FILENAME + ": User already exist during new user creation!");
                            Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                else{
                   Toast.makeText(getApplicationContext(), "Username and Password cannot be empty", Toast.LENGTH_SHORT).show();
                   return;
                }
            }
        });
    }

    protected void onStop() {
        super.onStop();
        finish();
    }

    private boolean addNewUser(String username, String password){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        if (dbHandler.findUser(username) == null){
            ArrayList<Integer> levels = new ArrayList<>();
            for (int i = 1; i <= 10; i++){
                levels.add(i);
            }
            ArrayList<Integer> scores = new ArrayList<>();
            for (int i = 0; i < 10; i++){
                scores.add(0);
            }
            dbHandler.addUser(new UserData(username, password, levels, scores));
            dbHandler.close();
            return true;
        }
        else{
            dbHandler.close();
            return false;
        }
    }
}
