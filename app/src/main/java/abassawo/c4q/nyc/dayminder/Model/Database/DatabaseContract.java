package abassawo.c4q.nyc.dayminder.Model.Database;

import android.provider.BaseColumns;

import java.util.UUID;

/**
 * Created by c4q-Abass on 9/30/15.
 */
public class DatabaseContract {
    public static final String DB_NAME = "DayMinder.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_USER_NOTE = "usernotes";
    public static final String TABLER_USER_LABELS = "userlabels";

    public static abstract class NoteColumns implements BaseColumns{
        public static final String NOTE_ID = BaseColumns._ID;
        public static final String NOTE_TITLE = "title";
        public static final String IMAGE_URI = "imageUri";
        public static final String NOTE_LABEL_S = "note_label(s)";
    }

    public static abstract class LabelColumns implements BaseColumns{
        public static final String LABEL_ID = BaseColumns._ID;
        public static final String LABEL_NAME = "label_name";
        public static final String LABEL_COLOR = "label_color";
    }
}
