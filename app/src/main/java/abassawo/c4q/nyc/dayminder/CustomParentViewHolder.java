package abassawo.c4q.nyc.dayminder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

/**
 * Created by c4q-Abass on 7/30/15.
 */
public class CustomParentViewHolder extends ParentViewHolder {

    public TextView numberText;
    public TextView dataText;
    public ImageView arrowExpand;

    /**
     * Public constructor for the CustomViewHolder.
     *
     * @param itemView the view of the parent item. Find/modify views using this.
     */
    public CustomParentViewHolder(View itemView) {
        super(itemView);

        numberText = (TextView) itemView.findViewById(R.id.recycler_item_number_parent);
        dataText = (TextView) itemView.findViewById(R.id.recycler_item_text_parent);
        arrowExpand = (ImageView) itemView.findViewById(R.id.recycler_item_arrow_parent);
    }
}