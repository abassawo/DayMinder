package abassawo.c4q.nyc.dayminder.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import java.util.ArrayList;
import java.util.UUID;

import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
import abassawo.c4q.nyc.dayminder.Fragments.DayListFragment;
import abassawo.c4q.nyc.dayminder.Fragments.NoteEditFragment;
import abassawo.c4q.nyc.dayminder.Model.Note;
import abassawo.c4q.nyc.dayminder.R;

/**
 * Created by c4q-Abass on 9/20/15.
 */
public class NotePagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ArrayList<Note> mNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewpager);
        setContentView(mViewPager);

        mNotes = NotePad.get(this).getNotes();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return mNotes.size();
            }

            @Override
            public Fragment getItem(int p) {
                Note note = mNotes.get(p);
                return NoteEditFragment.newInstance(note.getId());
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
                .getSerializableExtra(DayListFragment.EXTRA_NOTE_ID);

        for (int i=0; i< mNotes.size(); i++)
        {
            if (mNotes.get(i).getId().equals(noteId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fragment_note, menu);
        return true;
    }
}