package abassawo.c4q.nyc.dayminder.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.Collections;
import java.util.List;

import abassawo.c4q.nyc.dayminder.Activities.NotePagerActivity;
import abassawo.c4q.nyc.dayminder.Adapters.CustomRecyclerAdapter;
import abassawo.c4q.nyc.dayminder.Adapters.ItemClickSupport;
import abassawo.c4q.nyc.dayminder.Adapters.SimpleItemTouchHelperCallback;
import abassawo.c4q.nyc.dayminder.Adapters.SwipeDismissRecyclerViewTouchListener;
import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
import abassawo.c4q.nyc.dayminder.Model.Note;
import abassawo.c4q.nyc.dayminder.R;
import butterknife.Bind;
import butterknife.ButterKnife;

import static abassawo.c4q.nyc.dayminder.Adapters.CustomRecyclerAdapter.*;

/**
 * Created by c4q-Abass on 9/21/15.
 */

public class StaggeredDayFragment extends Fragment {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CustomRecyclerAdapter mAdapter;
    public static String EXTRA_NOTE_ID = "abassawo.c4q.nyc.dayminder.Fragments.StaggeredDayFragment";
    //private SimpleRVAdapter mAdapter;
    private List<Note> mItems;
    private ItemTouchHelper mItemTouchHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_staggered_grid, container, false);
        ButterKnife.bind(this, view);


        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        //layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        initData();
        initRV();
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(getActivity(), NotePagerActivity.class);
                i.putExtra(EXTRA_NOTE_ID, mItems.get(position).getId());
                startActivity(i);
            }
        });


        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);


        recyclerView.setAdapter(mAdapter);
        //recyclerView.callOnClick();


        SwipeDismissRecyclerViewTouchListener touchListener = new SwipeDismissRecyclerViewTouchListener(
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

                        notify();


                    }
                });
        recyclerView.setOnTouchListener(touchListener);

        recyclerView.setOnScrollListener(touchListener.makeScrollListener());

        return view;
    }

    private void initData() {
        mItems = NotePad.get(getActivity()).getNotes();
    }

    //    private void initRV() {
//        mAdapter = new CustomRecyclerAdapter(getActivity());
//    }
    private void initRV() {
        mAdapter = new CustomRecyclerAdapter(getActivity());
    }



    //INNER CLASS


}