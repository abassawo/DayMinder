package abassawo.c4q.nyc.dayminder.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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
        recyclerView.setAdapter(mAdapter);
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
                holder.mTextView.setText(mItems.get(position).getTitle());
                holder.mTextView.setPressed(false);
            }

            @Override
            public int getItemCount() {
                return mItems.size();
            }
        };
    }




    private class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public NoteViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }




}
