package abassawo.c4q.nyc.dayminder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by c4q-Abass on 7/19/15.
 */
public class MonthViewFragment extends Fragment {
    @Bind(R.id.taskList) ListView lv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month_view, container, false);

        return view;
    }

}
