package abassawo.c4q.nyc.dayminder;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
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
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import java.util.zip.Inflater;


import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.viewpager) ViewPager viewpager;
    @Bind(R.id.tabs) TabLayout tabLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.nav_view) NavigationView navigationView;
    private FragAdapter adapter;
    public static List<Note>mNotes;
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    private ActionBarDrawerToggle mDrawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//
//        fetchCalendars();
//        fetchEvents();
//        updateEvent(255);

        mNotes = NotePad.get(this).getNotes();
        for(Note x : mNotes){
            if (x.getTitle().toString() == "") {
                mNotes.remove(x);
            }
        }

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

        Drawer = (DrawerLayout) findViewById(R.id.drawer_layout);        // Drawer object Assigned to the view

        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){ //fixme fix the strings

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State



        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        tabLayout.setupWithViewPager(viewpager);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(viewpager.getCurrentItem() == 0){
//                    fab.setVisibility(View.GONE);
//                } else {
//                    fab.setVisibility(View.VISIBLE);
//                }

                if(viewpager.getCurrentItem() == 0){
                    insertEvent("Testing");
                    Snackbar snackbar = Snackbar.make(view, "Select specific tasks from Ongoing that you want to accomplish today", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    snackbar.show();
                } else if (viewpager.getCurrentItem() == 1){
                    Intent editIntent = new Intent(MainActivity.this, NoteEditActivity.class);
                    startActivity(editIntent);

                    fab.setImageResource(R.drawable.ic_done);
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




    private void setupDrawerContent(NavigationView navigationView) {
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
        adapter.addFragment(new GoalCardsFragment(), "Ongoing");
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
        String date = new SimpleDateFormat("EEE, MM-dd-yyyy").format(new Date());
        getSupportActionBar().setSubtitle(date);
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
    public void fetchCalendars(){
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String[] columns = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.CALENDAR_COLOR_KEY};

        Cursor cursor =
                getContentResolver().
                        query(uri, columns, CalendarContract.Calendars.ACCOUNT_NAME + " = ?", new String[]{"abass.bayo@gmail.com"}, null); // = ? is for filtering. it means gimme only the calendars associated with that email

        while(cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex(CalendarContract.Calendars._ID));
            String accountName = cursor.getString(1);
            String displayName = cursor.getString(2);
            String owner = cursor.getString(3);
            Log.v("contentProvider", "ID: " + id +
                    "accountName" + accountName +
                    "displayName" + displayName +
                    "owner" + owner);


        }
    }

    public void fetchEvents(){
        Uri uri = CalendarContract.Events.CONTENT_URI;
        String[] columns = new String[] {
                CalendarContract.Events._ID,
                CalendarContract.Events.CALENDAR_ID,
                CalendarContract.Events.ORGANIZER,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.EVENT_LOCATION,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
        };

        String filter = CalendarContract.Events.CALENDAR_ID + " = ?";
        String[] filterArgs = new String[]{"5"}; //PUT FILTERARGS #5INTO A CONSTANT.


        String sortOrder = CalendarContract.Events.DTSTART + " DESC";

        Cursor cursor = getContentResolver().query(uri, columns, filter, filterArgs, sortOrder);

    }

    public void insertEvent(String title){
        Calendar startTime = Calendar.getInstance();
        startTime.set(2015, Calendar.JULY, 1, 22, 0);
        long startMillis = startTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        long endMillis = endTime.getTimeInMillis();


        ContentValues values = new ContentValues();
        //
        values.put(CalendarContract.Events.TITLE, title);
        values.put(CalendarContract.Events.DESCRIPTION, "additional test");

        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.CALENDAR_ID, 5);
        values.put(CalendarContract.Events.EVENT_LOCATION, "LIC");
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/New York");
         Uri uri =  getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);
    }

    public void updateEvent(long id){
        id = 255;
        ContentValues values = new ContentValues();
        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);

    }

}



