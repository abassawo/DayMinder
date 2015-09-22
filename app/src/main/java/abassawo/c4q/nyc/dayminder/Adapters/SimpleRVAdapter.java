package abassawo.c4q.nyc.dayminder.Adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import abassawo.c4q.nyc.dayminder.Activities.MainActivity;
import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
import abassawo.c4q.nyc.dayminder.Model.Note;
import abassawo.c4q.nyc.dayminder.R;

/**
 * Created by c4q-Abass on 9/21/15.
 */
public class SimpleRVAdapter extends RecyclerView.Adapter<SimpleRVAdapter.VerticalItemHolder>{

    private ArrayList<Note> mItems;

    private AdapterView.OnItemClickListener mOnItemClickListener;

    public SimpleRVAdapter(Context ctx) {
        mItems = NotePad.get(ctx).getNotes();
    }

    /*
     * A common adapter modification or reset mechanism. As with ListAdapter,
     * calling notifyDataSetChanged() will trigger the RecyclerView to update
     * the view. However, this method will not trigger any of the RecyclerView
     * animation features.
     */
    public void setItemCount(int count) {
        mItems.clear();
        //mItems.addAll(generateDummyData(count));

        notifyDataSetChanged();
    }

    /*
     * Inserting a new item at the head of the list. This uses a specialized
     * RecyclerView method, notifyItemInserted(), to trigger any enabled item
     * animations in addition to updating the view.
     */
    public void addItem(int position) {
        if (position > mItems.size()) return;

        mItems.add(position, new Note());
        notifyItemInserted(position);
    }

    /*
     * Inserting a new item at the head of the list. This uses a specialized
     * RecyclerView method, notifyItemRemoved(), to trigger any enabled item
     * animations in addition to updating the view.
     */
    public void removeItem(int position) {
        if (position >= mItems.size()) return;

        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public VerticalItemHolder onCreateViewHolder(ViewGroup container, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View root = inflater.inflate(R.layout.view_match_item, container, false);

        return new VerticalItemHolder(root, this);
    }

    @Override
    public void onBindViewHolder(VerticalItemHolder itemHolder, int position) {
        Note item = mItems.get(position);
        itemHolder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void onItemHolderClick(VerticalItemHolder itemHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(null, itemHolder.itemView,
                    itemHolder.getAdapterPosition(), itemHolder.getItemId());
        }
    }


    public static class VerticalItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle, mLabel;
        private Note mNote;
        private ImageView mImageView;


        private SimpleRVAdapter mAdapter;

        public VerticalItemHolder(View itemView, SimpleRVAdapter adapter) {
            super(itemView);
            itemView.setOnClickListener(this);

            mAdapter = adapter;
            mTitle = (TextView) itemView.findViewById(R.id.note_title);
            mLabel = (TextView) itemView.findViewById(R.id.note_label);
            mImageView = (ImageView) itemView.findViewById(R.id.note_imageView);
        }

        public void bindView(Note note){
            mNote = note;
            mTitle.setText(note.getTitle());
            mLabel.setText(note.getmLabel().toString());

            if(mNote.getDrawable() != String.valueOf(R.drawable.c4qlogo)){
                mImageView.setImageURI(Uri.parse(mNote.getDrawable()));

                //Glide.with().load(Uri.parse(mNote.getDrawable())).into(mImageView);
            }
        }

        @Override
        public void onClick(View v) {
            mAdapter.onItemHolderClick(this);
        }


    }

}
