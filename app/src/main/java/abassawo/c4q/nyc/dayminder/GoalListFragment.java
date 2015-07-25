package abassawo.c4q.nyc.dayminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by c4q-Abass on 7/19/15.
 */
public class GoalListFragment extends Fragment {
   private static List<JLabel> mLabels;
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> labelCollection;
    ExpandableListView expListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_goal_list, container, false);
       //mNotes =  NotePad.get(getActivity()).getNotes();
        expListView = (ExpandableListView) view.findViewById(R.id.label_list);
        setUpGoalLists();
        List<String> labels;
        mLabels = NotePad.get(getActivity()).getLabels();
        JLabel reading = new JLabel("Reading");
        JLabel urbanCommune = new JLabel("Urban Commune");
        mLabels.add(reading);
        mLabels.add(urbanCommune);

        setGroupIndicatorToRight();
        //setupRecyclerView(rv);
        return view;
    }

    public void setUpGoalLists(){
        getDipsFromPixel(1);
        createGroupList();
        createCollection();


        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(getActivity(), groupList, labelCollection);
        expListView.setAdapter(expListAdapter);

        setGroupIndicatorToRight();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getActivity(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });
       setGroupIndicatorToRight();
    }

    private void createGroupList(){
        groupList = new ArrayList<String>();
        groupList.add("Health");
        groupList.add("Reading");
        groupList.add("Finance");
        groupList.add("Programming");
        groupList.add("Urban Commune");
        groupList.add("Job Search");

    }

    private void createCollection(){
        // preparing laptops collection(child)
        String[] health = { "Exercise", "Nutrition",
                "Swimming" };
        String[] reading= { "The Art of Programming", "Clean Code", "Effective Java" };
        String[] finance = { "Balance Checkbook", "Pay bills"};
        String[] programming= { "Work on Day Minder" };
        String[] urbancommune = { "Work on UC Website", "Install environment to test Arcgis apps", "NYC Big Apps" };
        String[] jobsearch = { "10 apps a day", "Focus on reinventing"};
        labelCollection = new LinkedHashMap<String, List<String>>();

        for (String label : groupList) {
            if (label.equals("Health")) {
                loadChild(health);
            } else if (label.equals("Reading"))
                loadChild(reading);
            else if (label.equals("Finance"))
                loadChild(finance);
            else if (label.equals("Programming"))
                loadChild(programming);
            else if (label.equals("Urban Commune"))
                loadChild(urbancommune);
            else if (label.equals("Job Search"))
                loadChild(jobsearch);


            labelCollection.put(label, childList);
        }
    }
    private void loadChild(String[] labels) {
        childList = new ArrayList<String>();
        for (String labelChild : labels)
            childList.add(labelChild);
    }

    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }



    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;


        public static class ViewHolder extends RecyclerView.ViewHolder {
            public JLabel mBoundString;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public JLabel getValueAt(int position) {
            return mLabels.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<JLabel> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mLabels = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundString = mLabels.get(position);
//            holder.mTextView.setText(mNotes.get(position).toString());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, NoteDetailActivity.class);
                    //intent.putExtra(NoteDetailActivity.EXTRA_NAME, holder.mBoundString.toString());

                    context.startActivity(intent);
                }
            });

            Glide.with(holder.mImageView.getContext())
                    .load(R.drawable.abc_btn_borderless_material) //replace test image.
                    .fitCenter()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            //return mLabels.size();
            return 5;
        }
    }


}
