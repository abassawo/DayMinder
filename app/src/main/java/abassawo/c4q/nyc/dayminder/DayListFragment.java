package abassawo.c4q.nyc.dayminder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-Abass on 7/19/15.
 */
public class DayListFragment extends Fragment {
    private ArrayList<Note> mNotes;
    @Bind(R.id.DateTV_id)
    TextView dateTV;
    private Date mDate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_list, container, false);
        mDate = Calendar.getInstance().getTime();

        String date = new SimpleDateFormat("EEE, MM-dd-yyyy").format(new Date());


        Log.d("test", mDate.toString());
        ButterKnife.bind(this, view);
        dateTV.setText(date);


        // mNotes = new ArrayList<>(); ///Testing this to see if it removes blank notes
//        for (Note x : mNotes) {
//            if ((x.getTitle().toString()).equals("")) {
//                mNotes.remove(x);
//            }
//        }
//        final ListView listView = (ListView) view.findViewById(R.id.listview);
//        listView.setAdapter(customadapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }


}
