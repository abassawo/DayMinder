//package abassawo.c4q.nyc.dayminder.Adapters;
//
//import android.net.Uri;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import abassawo.c4q.nyc.dayminder.Model.Note;
//import abassawo.c4q.nyc.dayminder.R;
//
///**
// * Created by c4q-Abass on 9/30/15.
// */
//private class NoteViewHolder extends RecyclerView.ViewHolder {
//
////        private TextView mTextView;
////       // private ImageView mImage;
////        private Note mNote;
//
//    private TextView mTitle;
//    private TextView mLabel;
//    private ImageView mImageView;
//    private Note mNote;
//    private RecyclerView.Adapter<NoteViewHolder> adapter;
//
//    public NoteViewHolder(View itemView) {
//        super(itemView);
//        mTitle = (TextView) itemView.findViewById(R.id.note_title);
//        mLabel = (TextView) itemView.findViewById(R.id.note_label);
//        mImageView = (ImageView) itemView.findViewById(R.id.note_imageView);
//    }
//
//    public void bindNote(Note note) {
//        mNote = note;
//        mTitle.setText(note.getTitle());
//        if (note.hasLabel()) {
//            mLabel.setText(note.getmLabel().toString());
//            mLabel.setVisibility(View.VISIBLE);
//        }
//
//        if (mNote.getDrawable() != String.valueOf(R.drawable.c4qlogo) && (mNote.getDrawable() != null)) {
//            mImageView.setImageURI(Uri.parse(mNote.getDrawable()));
//            //Glide.with().load(Uri.parse(mNote.getDrawable())).into(mImageView);
//        }
//    }
//
//
//}