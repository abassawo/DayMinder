package abassawo.c4q.nyc.dayminder;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

/**
 * Created by c4q-Abass on 7/30/15.
 */
public class CustomChildViewHolder extends ChildViewHolder {

    public TextView dataText;

    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public CustomChildViewHolder(View itemView) {
        super(itemView);

        dataText = (TextView) itemView.findViewById(R.id.recycler_cards_child);
    }
}