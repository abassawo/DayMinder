package abassawo.c4q.nyc.dayminder.Controllers;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
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


    private static NotePad sNotePad;
    private Context mAppContext;

    private NotePad(Context appContext) {
        mAppContext = appContext;
        mSerializer = new JSONParser(mAppContext, FILENAME);

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

    public void deleteNote(Note c) {
        
        mNotes.remove(c);
    }


    public void deleteAllNotes(){
        
        mNotes.clear();
    }

    public boolean saveNotes() {
        try {
            mSerializer.saveNotes(mNotes);
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
}

