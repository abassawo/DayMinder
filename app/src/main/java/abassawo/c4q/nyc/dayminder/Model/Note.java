package abassawo.c4q.nyc.dayminder.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
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

    private Date mDueDate = null;
    private Date reminderTime;


    public boolean hasLabel(){
        return this.getmLabel() != null && this.getmLabel().length() > 0;
    }

    public Date getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Date reminderTime) {
        this.reminderTime = reminderTime;
    }


    public String getDrawable() {
        return this.drawable;
    }

        public void setDrawable(String drawable){
        this.drawable = drawable;

    }

    public void setDuetoday(Context ctx){
        this.setDueDate(NotePad.get(ctx).getTodaysDate());
    }


    public Date getDueDate() {
        return this.mDueDate;
    }

    public void setDueDate(Date dueDate) {
        this.mDueDate = dueDate;
    }

    private String drawable;

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
        drawable = defaultImgStr;

        this.label = "Personal";
    }

    public static String defaultImgStr =  String.valueOf(R.drawable.c4qlogo);

    public Note(String title){
        drawable = defaultImgStr;
        this.title = title;
        this.label = "Personal";
    }

    public Note(JSONObject json) throws JSONException {
        drawable = defaultImgStr;
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
        json.put(JSON_LABEL_TAG, label);
        return json;
    }

//    public String toJSON(){
//        final GsonBuilder builder = new GsonBuilder();
//        final Gson gson = builder.create();
//        final String json = gson.toJson(this);
//        Log.d("Serialised: %s%n", json);
//        return json;
//    }

//    public Note(String json, boolean gson){
//        if(gson) {
//            this = gson.fromJson(json, Note.class);
//        }
//    }



    public void setDueToday(Context ctx){
        this.mDueDate = (NotePad.get(ctx).getTodaysDate());
    }

    public void setDueTomorrow(Context ctx){
        this.mDueDate = (NotePad.get(ctx).getTomorrowsDate());
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDueinOneWeek(Context ctx){
        this.mDueDate = NotePad.get(ctx).getNextWeekDate();
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


    public boolean hasCustomImage() {
        return this.getDrawable() != Note.defaultImgStr && this.getDrawable() != null;
    }
}
