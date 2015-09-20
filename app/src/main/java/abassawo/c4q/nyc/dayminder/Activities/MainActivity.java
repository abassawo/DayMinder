package abassawo.c4q.nyc.dayminder.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
import abassawo.c4q.nyc.dayminder.Fragments.CalendarFragment;
import abassawo.c4q.nyc.dayminder.Fragments.DayListFragment;
import abassawo.c4q.nyc.dayminder.Adapters.FragAdapter;
import abassawo.c4q.nyc.dayminder.Fragments.NoteEditFragment;
import abassawo.c4q.nyc.dayminder.Model.AccountFetcher;
import abassawo.c4q.nyc.dayminder.Model.Note;
import abassawo.c4q.nyc.dayminder.Model.User;
import abassawo.c4q.nyc.dayminder.R;
import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.tabs) TabLayout tabLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fabAddNewNote) FloatingActionButton fab;
    private FragAdapter adapter;
    public static List<Note>mNotes;
    private User user;
    private String userName;
    private String emailAddress;
    private IProfile userProfile;
    private AccountHeader header;
    private Drawer drawer = null;
    public static String EXTRA_NOTE_ID = "com.nyc.c4q.abassawo._id";;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
        setupDrawer();
        drawer = new DrawerBuilder()
                .withActivity(this).
                withAccountHeader(header, false)
                .withActionBarDrawerToggleAnimated(true)
                .withActionBarDrawerToggle(true)
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .withSliderBackgroundColor(getResources().getColor(R.color.primary_dark_material_light))
                .withToolbar(toolbar)

                .addDrawerItems(
                        new PrimaryDrawerItem().withName("New Task").withIcon(getResources().getDrawable(R.drawable.ic_menu)).withIdentifier(R.id.nav_discussion),
                        new PrimaryDrawerItem().withName("Places").withIcon(getResources().getDrawable(R.drawable.ic_menu)).withIdentifier(R.id.nav_friends),
                        new PrimaryDrawerItem().withName("All Tasks").withIcon(getResources().getDrawable(R.drawable.ic_menu)).withIdentifier(R.id.nav_home),
                        new PrimaryDrawerItem().withName("Contact").withIcon(getResources().getDrawable(R.drawable.ic_menu)).withIdentifier(R.id.nav_messages))
                                .build();
    }

    public void initViews(){
       ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        tabLayout.setupWithViewPager(this.viewPager);
    }

    public void initListeners(){
        fab.setOnClickListener(this);
    }

    public void initState(){  //FIXME
        mNotes = NotePad.get(this).getNotes();
        for(Note x : mNotes){
            if (x.getTitle().toString() == "") {
                mNotes.remove(x);
            }
        }

    }

    public void setupDrawer(){
        userProfile = new ProfileDrawerItem()
                .withName(AccountFetcher.getName(this))
                .withNameShown(true)
                .withIcon(R.drawable.abassicon);

        header = new AccountHeaderBuilder().withActivity(this)
                .addProfiles(userProfile, new ProfileSettingDrawerItem())
                .withHeaderBackground(R.drawable.background_poly)
                .build();


    }



    private void setupViewPager(ViewPager viewPager) {
        adapter = new FragAdapter(getSupportFragmentManager());
        adapter.addFragment(new DayListFragment(), "Today");
        adapter.addFragment(new CalendarFragment(), "Calendar");
        viewPager.setAdapter(adapter);
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
                drawer.openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabAddNewNote:
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);



        }
    }
}



