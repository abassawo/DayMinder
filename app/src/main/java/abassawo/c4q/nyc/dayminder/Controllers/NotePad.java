package abassawo.c4q.nyc.dayminder.Controllers;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

import abassawo.c4q.nyc.dayminder.Model.JSONParser;
import abassawo.c4q.nyc.dayminder.Model.Label;
import abassawo.c4q.nyc.dayminder.Model.Note;

/**
 * Created by c4q-Abass on 7/19/15.
 */
public class NotePad {
    private static final String TAG = "NotePad";
    private static final String FILENAME = "notes.json";

    private ArrayList<Note> mNotes;

    public ArrayList<Label> getLabels() {
        return mLabels;
    }


    private ArrayList<Label>mLabels;
    private JSONParser mSerializer;
    private Label label;
    private Date todaysDate, tomorrowsDate, weekfromToday;
    DateFormat dateFormat;
    GregorianCalendar calendar = new GregorianCalendar();
    private static NotePad sNotePad;
    private Context mAppContext;

    private NotePad(Context appContext) {
        mAppContext = appContext;
        mSerializer = new JSONParser(mAppContext, FILENAME);
        //gSerializer = new GSONHelper(mAppContext, FILENAME);

        try {
            mNotes = mSerializer.loadNotes();
            mLabels = mSerializer.loadLabels();



        } catch (Exception e) {
            mNotes = new ArrayList<Note>();
            Log.e(TAG, "Error loading labels: ", e);
        }

    }

    public static NotePad get(Context c) {
        if (sNotePad == null) {
            sNotePad = new NotePad(c.getApplicationContext());
        }
        return sNotePad;
    }

    public ArrayList<Note> getNotes() {
        return mNotes;
    }

    public ArrayList<Note>getTodaysNotes(){
     initDate();
        ArrayList<Note> result = new ArrayList();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        for(Note note: getNotes()){
            if(note.getDueDate()!= null) {
                String dateStr = dateFormat.format(note.getDueDate());
                String todaydateStr = dateFormat.format(Calendar.getInstance().getTime());
                if (dateStr.equals(todaydateStr)) {
                    result.add(note);
                }
            }
        }
        return result;
    }



    public Note getNote(UUID id) {
        for (Note c : mNotes) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    public void addNote(Note c) {
        mNotes.add(c);
    }

    public void addLabel(Label c){
        mLabels.add(c);
    }

    public ArrayList<Label> initLabelData(){
        ArrayList<Label> usersLabels = new ArrayList<Label>();
        usersLabels.add(new Label("Personal"));
        usersLabels.add(new Label("Family"));
        usersLabels.add(new Label("C4Q"));
        usersLabels.add(new Label("Home"));
        //usersLabels.add(0, "Create new");
        return usersLabels;
    }

    public void deleteNote(Note c) {
        
        mNotes.remove(c);
    }


    public void deleteAllNotes(){
        
        mNotes.clear();
    }

    public boolean saveNotes() {
        try {
            mSerializer.saveNotes(mNotes);
            //gSerializer.saveNotes(mNotes);
            Log.d(TAG, "notes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving notes: ", e);
            return false;
        }
    }

    private void CreateNotes(int count) {  //just for quick testing
        for (int i=0; i < count; i++) {
            Note c = new Note();
            c.setTitle("Note #" + i);
            c.setSolved(i %2 == 0); // Alternate ones are checked.
            mNotes.add(c);
        }
    }
    private void CreateLabels(int count) {  //just for quick testing
        for (int i=0; i < count; i++) {
            Label c = new Label();
            c.setTitle("Label" + i);
            mLabels.add(c);
        }
    }

    private void initDate(){
        todaysDate = Calendar.getInstance().getTime();
        Log.d(todaysDate.toString(), "today's date");
        // Date weekFromToday =;
    }

    public Date getTodaysDate(){
        return Calendar.getInstance().getTime();
    }

    public Date getTomorrowsDate(){
        calendar.add(calendar.DAY_OF_MONTH, 1);
        Date tomorrowsDate = calendar.getTime();
        return (tomorrowsDate);
    }

    public Date getNextWeekDate(){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(calendar.DAY_OF_MONTH, 7);
        weekfromToday = calendar.getTime();
        return (weekfromToday);
    }
}

