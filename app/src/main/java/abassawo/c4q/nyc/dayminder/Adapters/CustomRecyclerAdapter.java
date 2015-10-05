package abassawo.c4q.nyc.dayminder.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
import abassawo.c4q.nyc.dayminder.Model.Database.DBHelper;
import abassawo.c4q.nyc.dayminder.Model.Note;
import abassawo.c4q.nyc.dayminder.R;

/**
 * Created by c4q-Abass on 9/30/15.
 */
public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.NoteViewHolder> implements ItemTouchHelperAdapter {
    private List<Note> mItems;
    private Context ctx;

    public CustomRecyclerAdapter(Context ctx) {
        mItems = NotePad.get(ctx).getNotes();
        DBHelper helper = new DBHelper(ctx);
        //mItems = helper.getNotes();

    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_match_item, parent, false);
        // View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1
        //    , parent, false);
        view.setBackgroundResource(android.R.drawable.list_selector_background);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = mItems.get(position);
        holder.bindNote(note, ctx);
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mItems, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        private TextView mTextView;
        //       // private ImageView mImage;
//        private Note mNote;

        private TextView mTitle;
        private TextView mLabel;
        private ImageView mImage;
        private Note mNote;
        private RecyclerView.Adapter<NoteViewHolder> adapter;

        public NoteViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.note_title);
            mLabel = (TextView) itemView.findViewById(R.id.note_label);
            mImage = (ImageView) itemView.findViewById(R.id.note_imageView);
        }

        //            public void bindNote(Note note){
//                mNote = note;
//                mTextView.setText(note.getTitle());
//                mTextView.setPressed(false);
//                mImage.setImageResource(mNote.getDrawable());
//
//
//
//
        public void bindNote(Note note, Context ctx) {
            mNote = note;
            mTitle.setPressed(false);
            mTitle.setText(note.getTitle());
            if (note.hasLabel()) {
                mLabel.setText(note.getmLabel().toString());
                mLabel.setVisibility(View.VISIBLE);
            }

            if (mNote.getDrawable() != String.valueOf(R.drawable.c4qlogo) && (mNote.getDrawable() != null)) {
                mImage.setImageURI(Uri.parse(mNote.getDrawable()));
                Glide.with(ctx).load(Uri.parse(mNote.getDrawable())).into(mImage);
            }


        }


        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {

        }
    }


}