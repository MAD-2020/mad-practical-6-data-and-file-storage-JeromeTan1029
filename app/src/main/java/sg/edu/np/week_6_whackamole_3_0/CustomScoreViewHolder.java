package sg.edu.np.week_6_whackamole_3_0;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class CustomScoreViewHolder extends RecyclerView.ViewHolder {
    /* Hint:
        1. This is a customised view holder for the recyclerView list @ levels selection page
     */
    public TextView levelText;
    public TextView scoreText;

    private static final String FILENAME = "CustomScoreViewHolder.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    public CustomScoreViewHolder(final View itemView){
        super(itemView);
        levelText = (TextView) itemView.findViewById(R.id.recyclerViewLevelText);
        scoreText = (TextView) itemView.findViewById(R.id.recyclerViewScoreText);
        /* Hint:
        This method dictates the viewholder contents and links the widget to the objects for manipulation.
         */
    }
}
