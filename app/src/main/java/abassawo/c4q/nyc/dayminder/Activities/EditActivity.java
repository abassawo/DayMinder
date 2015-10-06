package abassawo.c4q.nyc.dayminder.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;

import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
import abassawo.c4q.nyc.dayminder.Fragments.NoteEditFragment;
import abassawo.c4q.nyc.dayminder.Model.Note;
import abassawo.c4q.nyc.dayminder.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class EditActivity extends AppCompatActivity {
    @Bind(R.id.edit_toolbar)
    Toolbar toolbar;
    private static String TAG = "abassawo.c4q.nyc.dayminder.Activities.EditActivity";
    public static final String EXTRA_CRIME_ID = "com.nyc.c4q.abassawo._id";
    public static final String EXTRA_ID_FOR_LABEL = EXTRA_CRIME_ID + TAG;
    public static int REQUEST_LABEL = 4;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        setupActionBar();
        FragmentManager fm = getSupportFragmentManager();
//
//        UUID noteId = (UUID)getIntent()
//                .getSerializableExtra(MainActivity.EXTRA_NOTE_ID);
      //  fm.beginTransaction().add(NoteEditFragment.newInstance(noteId),TAG).commit();
        NoteEditFragment fragment = getNewNoteEditFragment();
        fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
    }


    public NoteEditFragment getNewNoteEditFragment() {
        note = new Note();
        UUID newID = note.getId();
        NoteEditFragment newNoteFrag  = NoteEditFragment.newInstance(newID);
        note.setTitle("");

        NotePad.get(getApplicationContext()).addNote(note);

        NotePad notePad = NotePad.get(getApplicationContext());
        notePad.addNote(note);
        notePad.saveNotes();

        return newNoteFrag;
    }

    public static NoteEditFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        NoteEditFragment fragment = new NoteEditFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public void setupActionBar(){
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("");
        ab.setDisplayShowTitleEnabled(false);
        ab.setDefaultDisplayHomeAsUpEnabled(true);
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditActivity.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fragment_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.label_button){

            if(note != null) {
                Intent intent = new Intent(EditActivity.this, LabelActivity.class);
                intent.putExtra(EXTRA_ID_FOR_LABEL, note.getId());
                startActivityForResult(intent, REQUEST_LABEL);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
