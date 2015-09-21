package abassawo.c4q.nyc.dayminder.Model;

import android.graphics.drawable.Drawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

import abassawo.c4q.nyc.dayminder.R;

/**
 * Created by c4q-Abass on 7/19/15.
 */
public class Note{

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_LABEL_TAG = "label";

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    private int drawable;

    private UUID mId;
    private String title;

    public String getmLabel() {
        return this.label;
    }

    public void setmLabel(String mLabel) {

        this.label = mLabel;
    }




    private String label;   //Add new label tags to this string if they don't exist. can use | to search index.
    //TODO=Incorporate color for labels.


    private Date mDate;
    private boolean mSolved;


    public Note() {
        // Generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();
        drawable = R.drawable.c4qlogo;
        this.label = "Personal";
    }

    public Note(String title){

        this.title = title;
        this.label = "Personal";
    }

    public Note(JSONObject json) throws JSONException {
        drawable = R.drawable.c4qlogo;
        mId = UUID.fromString(json.getString(JSON_ID));
        title = json.getString(JSON_TITLE);
        mDate = new Date(json.getLong(JSON_DATE));
        mSolved = json.getBoolean(JSON_SOLVED);
        this.label =  "Personal";
       // mLabel = json.getString(JSON_LABEL_TAG);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, title);
        json.put(JSON_SOLVED, mSolved);
        json.put(JSON_DATE, mDate.getTime());
       // json.put(JSON_LABEL_TAG, label);
        return json;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getId() {
        return mId;
    }

    @Override
    public String toString() {
        return title;
    }
    
   
    
}
