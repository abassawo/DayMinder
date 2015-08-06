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
    public static List<Note> mNotes;
    public static List<Label> mLabels;
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
      rv = inflater.inflate(R.layout.fragment_bottom, container, false);
        //mExpandableAdapter = new CustomExpandableAdapter(getActivity(), setUpTestData(4));
        mNotes = NotePad.get(getActivity()).getNotes();
        
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
        mExpandableAdapter = new CustomExpandableAdapter(getActivity(), setUpData());

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


        private ArrayList<ParentObject> setUpData(){
            ArrayList<ParentObject> parentObjectList = new ArrayList<>();
            ArrayList<Object> childObjectList = new ArrayList<>();

            CustomParentObject c4qLabel = new CustomParentObject("C4Q");
            CustomParentObject androidDev = new CustomParentObject("Android Development");


            CustomChildObject finalProject = new CustomChildObject("Final Project");
            CustomChildObject androidchild = new CustomChildObject("Release Cycle");

            ArrayList<Object> androidChildList = new ArrayList<>();

            c4qLabel.setChildObjectList(childObjectList);
            childObjectList.add(androidchild);
            androidChildList.add(finalProject);
            androidDev.setChildObjectList(androidChildList);

            parentObjectList.add(androidDev);
            parentObjectList.add(c4qLabel);
            return parentObjectList;
        }

        public void addtoGoalsList(Label x, Note y){
            //fixme create logic to add the Label parent object and Note object to be arranged.
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
