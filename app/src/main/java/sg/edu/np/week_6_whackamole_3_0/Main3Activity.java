package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {

    private Button levelSelectBackToLoginButton;

    private static final String FILENAME = "Main3Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        levelSelectBackToLoginButton = (Button) findViewById(R.id.levelSelectBackToLoginButton);

        Intent fromMainActivity = getIntent();
        String username = fromMainActivity.getStringExtra("Username");

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        UserData data = dbHandler.findUser(username);
        dbHandler.close();

        final RecyclerView levelSelectRecyclerView = findViewById(R.id.levelSelectRecyclerView);
        final CustomScoreAdaptor levelSelectAdaptor = new CustomScoreAdaptor(data);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        levelSelectRecyclerView.setLayoutManager(linearLayoutManager);
        levelSelectRecyclerView.setItemAnimator(new DefaultItemAnimator());
        levelSelectRecyclerView.setAdapter(levelSelectAdaptor);

        levelSelectBackToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainActivity = new Intent(Main3Activity.this, MainActivity.class);
                startActivity(toMainActivity);
            }
        });
        /* Hint:
        This method receives the username account data and looks up the database for find the
        corresponding information to display in the recyclerView for the level selections page.

        Log.v(TAG, FILENAME + ": Show level for User: "+ userName);
         */
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
