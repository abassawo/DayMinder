package abassawo.c4q.nyc.dayminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-Abass on 7/19/15.
 */
public class NoteListFragment extends Fragment {
    private ArrayList<Note> mNotes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        mNotes = new ArrayList<>(); ///Testing this to see if it removes blank notes
//        for (Note x : mNotes) {
//            if ((x.getTitle().toString()).equals("")) {
//                mNotes.remove(x);
//            }
//        }
//        final ListView listView = (ListView) view.findViewById(R.id.listview);
//        listView.setAdapter(customadapter);
        //ButterKnife.bind(this, rv);
        return view;
    }

//
//    NoteAdapter customadapter = new NoteAdapter(mNotes);
//
//    customadapter.setNotifyOnChange(true);
//    customadapter.notifyDataSetChanged();
//
//        @Override
//        public void onDestroyActionMode (ActionMode mode){
//    }
//
//        @Override
//        public boolean onCreateActionMode (ActionMode mode, Menu menu){
//        MenuInflater inflater = mode.getMenuInflater();
//        inflater.inflate(R.menu.menu_list_item_context, menu);
//        return true;
//    }
//
//        @Override
//        public boolean onActionItemClicked (ActionMode mode, MenuItem item){
//
//        if (item.getItemId() == R.id.menu_item_delete_note) {
//
//            NotePad notePad = NotePad.get(getApplicationContext());
//            for (int i = customadapter.getCount() - 1; i >= 0; i--) {
//                if (listView.isItemChecked(i)) {
//                    notePad.deleteNote(customadapter.getItem(i));
//                }
//            }
//            mode.finish();
//            customadapter.notifyDataSetChanged();
//            notePad.saveNotes();
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//        @Override
//        public void onItemCheckedStateChanged (ActionMode mode,int position, long id,
//        boolean checked){
//
//    }
//    }
//
//    );
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    public void showCreateNote(View v) {
//        Note note = new Note();
//        note.setTitle("");
//        NotePad.get(getActivity().getApplicationContext()).addNote(note);
//        Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
//        i.putExtra(NoteFragment.EXTRA_NOTE_ID, note.getId());
//        startActivityForResult(i, 0);
//    }
//
////    @Override
////    public void onResume() {
////        super.onResume();
////        customadapter.notifyDataSetChanged();
////    }
////
//
//
//}
}
