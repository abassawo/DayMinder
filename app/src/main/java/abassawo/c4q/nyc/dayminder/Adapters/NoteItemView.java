package abassawo.c4q.nyc.dayminder.Adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import abassawo.c4q.nyc.dayminder.R;

/**
 * Created by c4q-Abass on 9/21/15.
 */
public class NoteItemView extends LinearLayout {

    private TextView mTitle;
    private Button mLabelButton;

    public NoteItemView(Context context) {
        super(context);
    }

    public NoteItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoteItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTitle= (TextView) findViewById(R.id.note_title);
        mLabelButton = (Button) findViewById(R.id.note_label);
    }

    @Override
    public String toString() {
        return mTitle.getText() + "v" + mLabelButton.getText()
                + ": " + getLeft() + "," + getTop()
                + ": " + getMeasuredWidth() + "x" + getMeasuredHeight();
    }


}
