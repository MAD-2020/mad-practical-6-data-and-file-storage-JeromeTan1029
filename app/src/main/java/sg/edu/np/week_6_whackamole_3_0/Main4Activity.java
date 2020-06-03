package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main4Activity extends AppCompatActivity {

    private boolean autoSave = true;

    private List<Integer> locations = new ArrayList<>();

    private Button topLeftButton, topMiddleButton, topRightButton, middleLeftButton, middleMiddleButton, middleRightButton, bottomLeftButton, bottomMiddleButton, bottomRightButton;
    private Button backButton;
    private TextView scoreText, countdownText;

    private List<Button> buttonList = new ArrayList<>();

    private static final String FILENAME = "Main4Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    CountDownTimer readyTimer;
    CountDownTimer newMolePlaceTimer;

    private String username;
    private int level;
    private int score;

    private void readyTimer(){
        readyTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "Ready Countdown!" + millisUntilFinished / 1000);
                Toast.makeText(getApplicationContext(), "Get Ready in " + millisUntilFinished / 1000 + " seconds", Toast.LENGTH_SHORT).show();
                if (millisUntilFinished < 999){
                    countdownText.setText("GO!");
                }
                else{
                    countdownText.setText("Game starting in " + millisUntilFinished / 1000);
                }
            }

            @Override
            public void onFinish() {
                countdownText.setVisibility(View.GONE);
                scoreText.setVisibility(View.VISIBLE);
                for (Button button : buttonList){
                    button.setVisibility(View.VISIBLE);
                    button.setClickable(true);
                }
                backButton.setVisibility(View.VISIBLE);
                backButton.setClickable(true);
                Log.v(TAG, "Ready Countdown Complete!");
                Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT);
                setNewMole(buttonList);
                placeMoleTimer();
            }
        };

        readyTimer.start();
    }
    private void placeMoleTimer(){

        int interval = 11000 - (level * 1000);

        newMolePlaceTimer = new CountDownTimer(interval, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "New Mole Location!");
                setNewMole(buttonList);
            }

            @Override
            public void onFinish() {
                placeMoleTimer();
            }
        };
        newMolePlaceTimer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        autoSave = true;

        Intent fromMain3Activity = getIntent();
        level = fromMain3Activity.getIntExtra("Level", 0);
        username = fromMain3Activity.getStringExtra("Username");

        if (level < 1 || level > 10){
            Toast.makeText(getApplicationContext(), "Error occurred - Invalid Level", Toast.LENGTH_SHORT).show();
            Intent toMain3Activity = new Intent(Main4Activity.this, Main3Activity.class);
            startActivity(toMain3Activity);
        }

        score = 0;

        countdownText = (TextView) findViewById(R.id.countdownText);
        scoreText = (TextView) findViewById(R.id.scoreText);

        topLeftButton = (Button) findViewById(R.id.topLeftButton);
        topMiddleButton = (Button) findViewById(R.id.topMiddleButton);
        topRightButton = (Button) findViewById(R.id.topRightButton);
        middleLeftButton = (Button) findViewById(R.id.middleLeftButton);
        middleMiddleButton = (Button) findViewById(R.id.middleMiddleButton);
        middleRightButton = (Button) findViewById(R.id.middleRightButton);
        bottomLeftButton = (Button) findViewById(R.id.bottomLeftButton);
        bottomMiddleButton = (Button) findViewById(R.id.bottomMiddleButton);
        bottomRightButton = (Button) findViewById(R.id.bottomRightButton);

        backButton = (Button) findViewById(R.id.gamePageBackButton);

        buttonList.add(topLeftButton);
        buttonList.add(topMiddleButton);
        buttonList.add(topRightButton);
        buttonList.add(middleLeftButton);
        buttonList.add(middleMiddleButton);
        buttonList.add(middleRightButton);
        buttonList.add(bottomLeftButton);
        buttonList.add(bottomMiddleButton);
        buttonList.add(bottomRightButton);

        scoreText.setVisibility(View.INVISIBLE);
        scoreText.setText(score + "");

        for (Button button : buttonList){
            button.setVisibility(View.INVISIBLE);
            button.setClickable(false);
            final Button buttonInner = button;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (doCheck(buttonInner)){
                        score += 1;
                    }
                    else{
                        score -= 1;
                    }
                    scoreText.setText(score + "");
                    setNewMole(buttonList);
                }
            });
        }

        backButton.setVisibility(View.INVISIBLE);
        backButton.setClickable(false);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserScore();
                autoSave = false;
                Intent toMain3Activity = new Intent(Main4Activity.this, Main3Activity.class);
                toMain3Activity.putExtra("Username", username);
                startActivity(toMain3Activity);
            }
        });


        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares level difficulty.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
            It also prepares the back button and updates the user score to the database
            if the back button is selected.
         */
    }

    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (autoSave == true){
            updateUserScore();
        }
        finish();
    }

    private boolean doCheck(Button checkButton)
    {
        if (checkButton.getText().equals("*")){
            Log.v(TAG, FILENAME + ": Hit, score added!");
            return true;
        }
        else{
            Log.v(TAG, FILENAME + ": Missed, point deducted!");
            return false;
        }
    }

    public void setNewMole(List<Button> buttonList)
    {
        locations.clear();
        for (Button button : buttonList){
            button.setText("O");
        }

        int moles;
        if (level >= 1 && level <= 5){
            moles = 1;
        }
        else{
            moles = 2;
        }
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole. Adds additional mole if the level difficulty is from 6 to 10.
         */
        Random ran = new Random();
        while (locations.size() != moles){
            int randomLocation = ran.nextInt(buttonList.size());
            if (!(locations.contains(randomLocation))){
                locations.add(randomLocation);
                buttonList.get(randomLocation).setText("*");
            }
        }
    }

    private void updateUserScore()
    {
        newMolePlaceTimer.cancel();
        readyTimer.cancel();

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        UserData userData = dbHandler.findUser(username);

        ArrayList<Integer> levels = userData.getLevels();
        ArrayList<Integer> scores = userData.getScores();

        int index = levels.indexOf(level);
        if (scores.get(index) < score){
            Log.v(TAG, FILENAME + ": Update User Score...");

            scores.set(index, score);
            userData.setScores(scores);

            dbHandler.deleteAccount(username);
            dbHandler.addUser(userData);
            dbHandler.close();
            return;
        }
        else{
            dbHandler.close();
            return;
        }
    }

}
