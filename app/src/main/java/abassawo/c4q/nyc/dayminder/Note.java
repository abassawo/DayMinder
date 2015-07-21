package abassawo.c4q.nyc.dayminder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by c4q-Abass on 7/19/15.
 */
public class Note {

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_LABEL_TAG = "label";

    private UUID mId;
    private String mTitle;

    public String label = "|";   //Add new label tags to this string if they don't exist. can use | to search index.
    //TODO=Incorporate color for labels.


    private Date mDate;
    private boolean mSolved;
    private String mLabel;

    public Note() {
        // Generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Note(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTitle = json.getString(JSON_TITLE);
        mDate = new Date(json.getLong(JSON_DATE));
        mSolved = json.getBoolean(JSON_SOLVED);
       // mLabel = json.getString(JSON_LABEL_TAG);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_SOLVED, mSolved);
        json.put(JSON_DATE, mDate.getTime());
       // json.put(JSON_LABEL_TAG, label);
        return json;
    }

    public String getTitle() {
        return mTitle;
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
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    @Override
    public String toString() {
        return mTitle;
    }



}
