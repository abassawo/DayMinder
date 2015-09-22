package abassawo.c4q.nyc.dayminder.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import abassawo.c4q.nyc.dayminder.Activities.MainActivity;
import abassawo.c4q.nyc.dayminder.Adapters.SimpleRVAdapter;
import abassawo.c4q.nyc.dayminder.Adapters.SwipeDismissRecyclerViewTouchListener;
import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
import abassawo.c4q.nyc.dayminder.Model.Note;
import abassawo.c4q.nyc.dayminder.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-Abass on 9/21/15.
 */

public class StaggeredDayFragment extends Fragment {
   @Bind(R.id.recyclerView)
   RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutmanager;
    private RecyclerView.Adapter<NoteViewHolder> mAdapter;
    private List<Note> mItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_staggered_grid, container, false);
        ButterKnife.bind(this, view);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        initData();
        initRV();
        //recyclerView.setAdapter(mAdapter);
        recyclerView.setAdapter(new SimpleRVAdapter(getActivity().getApplicationContext()));

        SwipeDismissRecyclerViewTouchListener touchListener =
                new SwipeDismissRecyclerViewTouchListener(
                        recyclerView,
                        new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    mItems.remove(position);
                                }
                                // do not call notifyItemRemoved for every item, it will cause gaps on deleting items
                                mAdapter.notifyDataSetChanged();
                            }
                        });
        recyclerView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        recyclerView.setOnScrollListener(touchListener.makeScrollListener());
//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(RecyclerView,
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Toast.makeText(MainActivity.this, "Clicked " + mItems.get(position), Toast.LENGTH_SHORT).show();
//                    }
//                }));



        return view;
    }

    private void initData(){
        mItems = NotePad.get(getActivity()).getNotes();
    }

    private void initRV(){
        mAdapter = new RecyclerView.Adapter<NoteViewHolder>() {
            @Override
            public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1
                        , parent, false);
                view.setBackgroundResource(android.R.drawable.list_selector_background);
                return new NoteViewHolder(view);
            }

            @Override
            public void onBindViewHolder(NoteViewHolder holder, int position) {
                Note note = mItems.get(position);
                holder.bindNote(note);
            }

            @Override
            public int getItemCount() {
                return mItems.size();
            }
        };
    }




    private class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
       // private ImageView mImage;
        private Note mNote;

        public NoteViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
           // mImage = (ImageView) itemView.findViewById(R.id.rv_imageview);
        }

        public void bindNote(Note note){
            mNote = note;
            mTextView.setText(note.getTitle());
            mTextView.setPressed(false);

               // mImage.setImageResource(mNote.getDrawable());


        }
    }


//    public class RecyclerItemClickListener(RecyclerView recyclerView, AdapterView.
//    android.widget.AdapterView.OnItemClickListener listener) {
//        GestureDetector mGestureDetector;
//        AdapterView.OnItemClickListener mListener;
//        recyclerView = recyclerView;
//        mGestureDetector = new GestureDetector(recyclerView.getContext(), new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onDown(MotionEvent e) {;
//                super.onDown(e);
//                return false;
//            }
//
//            @Override
//            public void onShowPress(MotionEvent e) {
//                super.onShowPress(e);
//            }
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//
//                return true;
//            }
//        });
//    }



}
