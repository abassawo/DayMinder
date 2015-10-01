package abassawo.c4q.nyc.dayminder.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import abassawo.c4q.nyc.dayminder.Model.Label;
import abassawo.c4q.nyc.dayminder.Model.Note;

import static abassawo.c4q.nyc.dayminder.Model.Database.DatabaseContract.*;

/**
 * Created by c4q-Abass on 9/30/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    private List<Note> mNotes;
    private List<Label> mLabels;
    private SQLiteDatabase appDB;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

//    private static final String CREATE_NOTES_TABLE =
//            "create table " + TABLE_USER_NOTE + "("
//            + NoteColumns._ID + " INTEGER PRIMARY KEY, "
//            + NoteColumns.NOTE_TITLE + " TEXT, "
//            + NoteColumns.NOTE_LABEL_S + " TEXT" + ")";

    private static final String CREATE_NOTES_TABLE =
            "CREATE TABLE " + DatabaseContract.TABLE_USER_NOTE + " (_id INTEGER PRIMARY KEY, "
                    + NoteColumns.NOTE_TITLE + " TEXT, "
                    + NoteColumns.IMAGE_URI+ " TEXT "
                    +
                    ")";

    private static final String CREATE_LABELS_TABLE = "CREATE TABLE " + TABLER_USER_LABELS +
            "(" + " _id integer primary key autoincremen, " +
            LabelColumns._ID  + ", " +
            LabelColumns.LABEL_NAME + ", " +
            LabelColumns.LABEL_COLOR + ")";



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTES_TABLE);
        //appDB = db;
        //db.execSQL(CREATE_LABELS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IS EXISTS " + DatabaseContract.TABLE_USER_NOTE);
        onCreate(db);
    }

    //CRUD Functions for Labels
    public String getLabels(String name){
        SQLiteDatabase db = getReadableDatabase();
        final String[] projection = {LabelColumns._ID, LabelColumns.LABEL_NAME, LabelColumns.LABEL_COLOR};
        final String selection = LabelColumns.LABEL_NAME + " =?";
        final String[] selectionArgs = { name };
        Cursor cursor = db.query(LabelColumns.LABEL_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if (cursor.getCount() < 1) { return null; }

        cursor.moveToFirst();
        String result =
                "id: " + cursor.getInt(0) + "\n" +
                        "name";

        db.close();
        return result;
    }

    public void addLabelToDB(Label label){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LabelColumns.LABEL_NAME, label.getTitle());
        cv.put(LabelColumns.LABEL_COLOR, label.getColor());
        cv.put(LabelColumns.LABEL_ID, label.getId().toString());
    }

    //CRUD Fucntions for Notes
    public List<Note>getNotes(){
        List<Note>mNotes = new ArrayList<Note>();
        String selectQuery = "SELECT * FROM " + TABLE_USER_NOTE + " ORDER BY " + NoteColumns.NOTE_ID;
        mNotes = new ArrayList<>();
        //FIXME - Read functions for DB;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do{
                Note note = new Note(UUID.fromString(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setDrawable(cursor.getString(2));
                note.setmLabel(cursor.getString(3));
                mNotes.add(note);
            }while (cursor.moveToNext());

        }
        cursor.close();
        return mNotes;
    }



    public void addNoteToDB(Note note){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NoteColumns.NOTE_TITLE, note.getTitle());
        cv.put(NoteColumns.IMAGE_URI, note.getDrawable());
        cv.put(NoteColumns.NOTE_LABEL_S, note.getmLabel());
        db.insert(TABLE_USER_NOTE, null, cv);
        if(db.isOpen()){
            db.close();
        }
    }

    public int updateNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NoteColumns.NOTE_TITLE, note.getTitle());
        cv.put(NoteColumns.IMAGE_URI, note.getDrawable());
        cv.put(NoteColumns.NOTE_LABEL_S, note.getmLabel());
        db.insert(TABLE_USER_NOTE, null, cv);
        if(db.isOpen()){
            db.close();
        }
        return db.update(TABLE_USER_NOTE, cv, NoteColumns.NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER_NOTE, NoteColumns.NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public int getTotalNoteCount(){
        String countQryStr = "SELECT * FROM " + TABLE_USER_NOTE;
        Cursor cursor = appDB.rawQuery(countQryStr, null);
        cursor.close();
        return cursor.getCount();
    }

    public int filterbyLabel(String label){
       // boolean labelValid =
        return 0; //fixme
    }

    public List<Note>getNotesfromcursorWrapper(){
        List<Note> notes = new ArrayList<>();

        NoteCursorWrapper cursorWrapper = queryNotes(null, null);
        try{
            cursorWrapper.moveToFirst();
            while(!cursorWrapper.isAfterLast()){
                notes.add(cursorWrapper.getNote());;
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return notes;

    }
    private NoteCursorWrapper queryNotes(String whereClause, String[] whereArgs){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USER_NOTE,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new NoteCursorWrapper(cursor);
    }


    public Note getNote(UUID id){

        NoteCursorWrapper cursor = queryNotes(DatabaseContract.NoteColumns.NOTE_ID + " = ?)", new String[] {id.toString()});

        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getNote();
        } finally {
            {
                cursor.close();
            }
        }


    }



}
