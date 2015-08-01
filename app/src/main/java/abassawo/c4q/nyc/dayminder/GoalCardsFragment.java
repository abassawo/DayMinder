package abassawo.c4q.nyc.dayminder;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by c4q-Abass on 7/19/15.
 */
public class GoalCardsFragment extends Fragment implements ExpandCollapseListener{
    private View rv;
    private CustomExpandableAdapter mExpandableAdapter;
    private ArrayList<Long> mDurationList;

    RecyclerView mRecyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private static final String CUSTOM_EXPAND_BUTTON_CHECKED = "CUSTOM_EXPAND_BUTTON_CHECKED";
    private static final String CUSTOM_ANIMATION_DURATION_POSITION = "CUSTOM_ANIMATION_DURATION_POSITION";
    private static final String CHILD_TEXT = "Child ";
    private static final String SECOND_CHILD_TEXT = "_2";
    private static final String PARENT_TEXT = "Parent ";
    private static final long INITIAL_ROTATION_SPEED_MS = 100;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      rv = inflater.inflate(R.layout.fragment_goal_list, container, false);
        //mExpandableAdapter = new CustomExpandableAdapter(getActivity(), setUpTestData(4));
        RecyclerView recList = (RecyclerView) rv.findViewById(R.id.cardList);
        recList.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        CardAdapter cardAdapter = new CardAdapter(createTestList(5)); //Testing
        recList.setAdapter(cardAdapter);

        mRecyclerView = recList;
        mDurationList = generateSpinnerSpeeds();

        // Create a new adapter with 20 test data items
        mExpandableAdapter = new CustomExpandableAdapter(getActivity(), setUpTestData(5));

        // Attach this activity to the Adapter as the ExpandCollapseListener
        mExpandableAdapter.addExpandCollapseListener(this);

        mRecyclerView.setAdapter(mExpandableAdapter);
        // Set the layout manager to a LinearLayout manager for vertical list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recList.setAdapter(mExpandableAdapter);

        return rv;
    }



    private List<Note> createTestList(int s){   //For test
        List<Note> NoteCardList = new ArrayList<Note>();
        Note ci = new Note();
            ci.setTitle("Urban Commune");
            NoteCardList.add(ci);
        Note c2 = new Note();
        c2.setTitle("Android Dev");
        NoteCardList.add(c2);
        NoteCardList.add(c2);
        NoteCardList.add(c2);
        NoteCardList.add(c2);
        NoteCardList.add(c2);
        NoteCardList.add(c2);

        return NoteCardList;
    }


        private ArrayList<ParentObject> setUpTestData(int numItems){
            ArrayList<ParentObject> parentObjectList = new ArrayList<>();
            for (int i = 0; i < numItems; i++) {
                ArrayList<Object> childObjectList = new ArrayList<>();

                // Evens get 2 children, odds get 1
                if (i % 2 == 0) {
                    CustomChildObject customChildObject = new CustomChildObject();
                    CustomChildObject customChildObject2 = new CustomChildObject();
                    customChildObject.setChildText("Urban Commune" + i);
                    customChildObject2.setChildText("UC" + i + "Test");
                    childObjectList.add(customChildObject);
                    childObjectList.add(customChildObject2);
                } else {
                    CustomChildObject customChildObject = new CustomChildObject();
                    customChildObject.setChildText("Urban Commune" + i);
                    childObjectList.add(customChildObject);
                }

                CustomParentObject customParentObject = new CustomParentObject();
                customParentObject.setChildObjectList(childObjectList);
                customParentObject.setParentNumber(i);
                customParentObject.setParentText("Urban Commune" + i);
                parentObjectList.add(customParentObject);
            }
            return parentObjectList;
        }

        /**
         * Method to set up the list of animation durations for the Toolbar's Spinner.
         * <p/>
         * The list contains long values that correspond to the length of time (in ms) of the animation.
         *
         * @return the list of times (in ms) to be populated into the Toolbar's spinner.
         */
    private ArrayList<Long> generateSpinnerSpeeds() {
        ArrayList<Long> speedList = new ArrayList<>();
        speedList.add(mExpandableAdapter.CUSTOM_ANIMATION_DURATION_NOT_SET);
        for (int i = 1; i <= 10; i++) {
            speedList.add(INITIAL_ROTATION_SPEED_MS * i);
        }
        return speedList;
    }


    @Override
    public void onRecyclerViewItemExpanded(int position) {

    }

    @Override
    public void onRecyclerViewItemCollapsed(int position) {

    }
}
