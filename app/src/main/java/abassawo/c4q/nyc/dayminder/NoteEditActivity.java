package abassawo.c4q.nyc.dayminder;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NoteEditActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<Note> mNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);


        mNotes = NotePad.get(this).getNotes();

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewpager);

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return mNotes.size();
            }

            @Override
            public android.support.v4.app.Fragment getItem(int p) {
                Note note = mNotes.get(p);
                return NoteFragment.newInstance(note.getId());
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int pos) {

                Note note = mNotes.get(pos);
                if (note.getTitle() != null) {
                    setTitle(note.getTitle());
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) { }

            @Override
            public void onPageScrollStateChanged(int arg0) { }
        });

        UUID noteId = (UUID)getIntent()
                .getSerializableExtra(NoteFragment.EXTRA_NOTE_ID);

        for (int i=0; i< mNotes.size(); i++) {
            if (mNotes.get(i).getId().equals(noteId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
