package abassawo.c4q.nyc.dayminder.Model.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import abassawo.c4q.nyc.dayminder.Model.Note;

/**
 * Created by c4q-Abass on 9/30/15.
 */
public class NoteCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote(){
        String note_id = getString(getColumnIndex(DatabaseContract.NoteColumns._ID));
        String title = getString(getColumnIndex(DatabaseContract.NoteColumns.NOTE_TITLE));
        String imageUri = getString(getColumnIndex(DatabaseContract.NoteColumns.IMAGE_URI));
        String label = getString(getColumnIndex(DatabaseContract.NoteColumns.NOTE_LABEL_S));
        Note note = new Note(UUID.fromString(note_id));
        note.setTitle(title);
        note.setDrawable(imageUri);
        note.setmLabel(label);
        return note;

    }
}
