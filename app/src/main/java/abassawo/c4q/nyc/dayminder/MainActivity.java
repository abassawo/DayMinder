package abassawo.c4q.nyc.dayminder;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.viewpager) ViewPager viewpager;
    @Bind(R.id.tabs) TabLayout tabLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.nav_view) NavigationView navigationView;
    private FragAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        tabLayout.setupWithViewPager(viewpager);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewpager.getCurrentItem() == 0){
                    Snackbar snackbar = Snackbar.make(view, "Select specific tasks from Ongoing that you want to accomplish today", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    snackbar.show();
                } else if (viewpager.getCurrentItem() == 1){
                    NoteFragment newFrag = getNewNoteFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    fm.popBackStack();
                    fm.beginTransaction().replace(R.id.mainContainer, newFrag).commit();


                } else {
                    Snackbar snackbar = Snackbar.make(view, "Add new items to your calendar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    snackbar.show();
                }
            }
        });


    }

    public void showCreateNote() {
        Note note = new Note();
        note.setTitle("");
        NotePad.get(getApplicationContext()).addNote(note);
        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, new NoteFragment());
       // i.putExtra(NoteFragment.EXTRA_NOTE_ID, note.getId());
    }

    private void setupDrawerContent(NavigationView navigationView) {
       // ViewGroup header;
        //View view = getLayoutInflater().inflate(R.layout.nav_header, header);
        //navigationView.addHeaderView(view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new FragAdapter(getSupportFragmentManager());
        adapter.addFragment(new DayListFragment(), "Today");
        adapter.addFragment(new GoalListFragment(), "Ongoing");
        adapter.addFragment(new MonthViewFragment(), "Calendar");
        viewPager.setAdapter(adapter);
    }

    public NoteFragment getNewNoteFragment() {
        Note note = new Note();
        UUID newID = note.getId();
        NoteFragment newNoteFrag = NoteFragment.newInstance(newID);

        note.setTitle("");
        NotePad.get(getApplicationContext()).addNote(note);
        return newNoteFrag;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
