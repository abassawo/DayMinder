package abassawo.c4q.nyc.dayminder.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
import abassawo.c4q.nyc.dayminder.Model.Note;
import abassawo.c4q.nyc.dayminder.R;

/**
 * Created by c4q-Abass on 9/21/15.
 */
public class SimpleRVAdapter extends RecyclerView.Adapter<SimpleRVAdapter.VerticalItemHolder> {

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

//        itemHolder.setAwayScore(item.getTitle());
//        //itemHolder.setHomeScore(item.getDate().toString());
//
//        itemHolder.setAwayName(String.valueOf(item.getmLabel()));
//        //itemHolder.setHomeName(String.valueOf(item.getDrawable()));
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

//    public static class GameItem {
//        public String homeTeam;
//        public String awayTeam;
//        public int homeScore;
//        public int awayScore;
//
//        public GameItem(String homeTeam, String awayTeam, int homeScore, int awayScore) {
//            this.homeTeam = homeTeam;
//            this.awayTeam = awayTeam;
//            this.homeScore = homeScore;
//            this.awayScore = awayScore;
//        }
//    }

    public static class VerticalItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle, mLabel;
        private Note mNote;


        private SimpleRVAdapter mAdapter;

        public VerticalItemHolder(View itemView, SimpleRVAdapter adapter) {
            super(itemView);
            itemView.setOnClickListener(this);

            mAdapter = adapter;
            mTitle = (TextView) itemView.findViewById(R.id.note_title);
            mLabel = (TextView) itemView.findViewById(R.id.note_label);
        }

        public void bindView(Note note){
            mNote = note;
            mTitle.setText(note.getTitle());
            mLabel.setText(note.getmLabel().toString());
        }

        @Override
        public void onClick(View v) {
            mAdapter.onItemHolderClick(this);
        }

//        public void setHomeScore(CharSequence homeScore) {
//            mHomeScore.setText(homeScore);
//        }
//
//        public void setAwayScore(CharSequence awayScore) {
//            mAwayScore.setText(awayScore);
//        }
//
//        public void setHomeName(CharSequence homeName) {
//            mHomeName.setText(homeName);
//        }
//
//        public void setAwayName(CharSequence awayName) {
//            mAwayName.setText(awayName);
//        }
    }

//    public static GameItem generateDummyItem() {
//        Random random = new Random();
//        return new GameItem("Upset Home", "Upset Away",
//                random.nextInt(100),
//                random.nextInt(100) );
//    }

//    public static List<SimpleRVAdapter> generateDummyData(int count) {
//        ArrayList<Note> items = new ArrayList<Note>();
//
//        for (int i=0; i < count; i++) {
//            items.add(new SimpleRVAdapter.GameItem("Losers", "Winners", i, i+5));
//        }
//
//        return items;
//    }
}
