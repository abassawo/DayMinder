package abassawo.c4q.nyc.dayminder.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;
import java.util.List;


import abassawo.c4q.nyc.dayminder.Activities.EditActivity;
import abassawo.c4q.nyc.dayminder.Activities.NotePagerActivity;
import abassawo.c4q.nyc.dayminder.Model.Note;
import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
import abassawo.c4q.nyc.dayminder.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-Abass on 7/19/15.
 */
public class DayListFragment extends Fragment{



    //@Bind(R.id.DateTV_id) TextView dateTV;
    @Bind(R.id.listview) ListView dailyNoteLV;
    public static Date mDate;
    public static List<Note> mNotes;
    private Note mNote;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;

    private CheckBox mSolvedBox;
    private ImageButton delButton;
    private  NoteAdapter customadapter;
    public static String EXTRA_NOTE_ID = "abassawo.c4q.nyc.dayminder.Fragments.DayListFragment";
    private static final String TAG = "DayListFragment";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_list, container, false);
        ButterKnife.bind(this, view);
        mDate = Calendar.getInstance().getTime();
        mNotes =  NotePad.get(getActivity()).getNotes();
        //Note note = new Note("Make Wire frame for C4Q");
        //mNotes.add(note);


        customadapter = new NoteAdapter(mNotes);
        dailyNoteLV.setAdapter(customadapter);
        customadapter.setNotifyOnChange(true);
        customadapter.notifyDataSetChanged();

        dailyNoteLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note c = (Note)   dailyNoteLV.getItemAtPosition(position);
                Log.d(" tag", c.getTitle() + " was clicked");

                Intent i = new Intent(getActivity(), NotePagerActivity.class);
                i.putExtra(DayListFragment.EXTRA_NOTE_ID, c.getId());
                startActivity(i);
            }
        });
        dailyNoteLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        dailyNoteLV.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
               // inflater.inflate(R.menu.menu_list_item_context, menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                if (item.getItemId() == R.id.menu_item_delete_note) {

                    NotePad notePad = NotePad.get(getActivity());
                    for (int i = customadapter.getCount() - 1; i >= 0; i--) {
                        if (dailyNoteLV.isItemChecked(i)) {
                            notePad.deleteNote(customadapter.getItem(i));
                        }
                    }
                    mode.finish();
                    customadapter.notifyDataSetChanged();
                    notePad.saveNotes();
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }
        });
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        ArrayAdapter basicAdapter = new ArrayAdapter<Note>(getActivity(), android.R.layout.simple_list_item_1, mNotes);
        dailyNoteLV.setAdapter(basicAdapter);
    }




    public class NoteAdapter extends ArrayAdapter<Note> {

        public int getCount() {
            return mNotes.size();
        }

        @Override
        public Note getItem(int position) {

            return mNotes.get(position);
        }

        public NoteAdapter(List<Note> notes) {

            super(getActivity(), 0, notes);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.fragment_day_list, parent, false); //try list_item_note nxt
            }

            // Configure for this note
            Note c = getItem(position);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.note_list_title);
            if (c != null) {
                titleTextView.setText(c.getTitle());
            }


            TextView dateTextView = (TextView) convertView.findViewById(R.id.note_list_item_dateTV);

            if (c != null) {
                CharSequence cs = DateFormat.format("EEEE, MMM dd, yyyy", c.getDate());
                dateTextView.setText(cs);
//            }


                CheckBox solvedCheckedBox = (CheckBox) convertView.findViewById(R.id.note_list_item_CheckBox);
                solvedCheckedBox.setChecked(c.isSolved());
                return convertView;
            }
            return convertView;
        }
    }

}
