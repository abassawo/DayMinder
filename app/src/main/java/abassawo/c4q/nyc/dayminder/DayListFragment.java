package abassawo.c4q.nyc.dayminder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-Abass on 7/19/15.
 */
public class DayListFragment extends Fragment {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_list, container, false);
        ButterKnife.bind(this, view);
        mDate = Calendar.getInstance().getTime();
        mNotes =  NotePad.get(getActivity()).getNotes();

        ArrayAdapter basicAdapter = new ArrayAdapter<Note>(getActivity(), android.R.layout.simple_list_item_1, mNotes);
        dailyNoteLV.setAdapter(basicAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }




}
