
package sg.edu.np.week_6_whackamole_3_0;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomScoreAdaptor extends RecyclerView.Adapter<CustomScoreViewHolder> {

    /* Hint:
        1. This is the custom adaptor for the recyclerView list @ levels selection page
    */

    private static final String FILENAME = "CustomScoreAdaptor.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    private UserData data = new UserData();

    public UserData getData() {
        return data;
    }

    public CustomScoreAdaptor(UserData userdata){
        data = userdata;
    }

    public CustomScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_select, parent, false);
        return new CustomScoreViewHolder(item);
    }

    public void onBindViewHolder(final CustomScoreViewHolder holder, final int position){
        Integer level = data.getLevels().get(position);
        Integer score = data.getScores().get(position);

        holder.levelText.setText("Level " + level);
        holder.scoreText.setText("Highest Score: " + score);

        Log.v(TAG, FILENAME + ": Showing level: " + data.getLevels().get(position) + " with highest score: " + data.getScores().get(position));

        holder.levelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain4Activity = new Intent(v.getContext(), Main4Activity.class);
                toMain4Activity.putExtra("Username", data.getMyUserName());
                toMain4Activity.putExtra("Level", Integer.parseInt(holder.levelText.getText().toString().split(" ")[1]));
                v.getContext().startActivity(toMain4Activity);

                Log.v(TAG, FILENAME + ": Load level " + (position + 1) + " for: " + data.getMyUserName());
            }
        });

        holder.scoreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain4Activity = new Intent(v.getContext(), Main4Activity.class);
                toMain4Activity.putExtra("Username", data.getMyUserName());
                toMain4Activity.putExtra("Level", Integer.parseInt(holder.levelText.getText().toString().split(" ")[1]));
                v.getContext().startActivity(toMain4Activity);

                Log.v(TAG, FILENAME + ": Load level " + (position + 1) + " for: " + data.getMyUserName());
            }
        });

/* Hint:
        This method passes the data to the viewholder upon bounded to the viewholder.
        It may also be used to do an onclick listener here to activate upon user level selections.

        Log.v(TAG, FILENAME + " Showing level " + level_list.get(position) + " with highest score: " + score_list.get(position));
        Log.v(TAG, FILENAME+ ": Load level " + position +" for: " + list_members.getMyUserName());
         */

    }

    public int getItemCount(){
        return data.getLevels().size();
    }
}
