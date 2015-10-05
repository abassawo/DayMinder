package abassawo.c4q.nyc.dayminder.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
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
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
import abassawo.c4q.nyc.dayminder.Fragments.CalendarFragment;
import abassawo.c4q.nyc.dayminder.Adapters.FragAdapter;
import abassawo.c4q.nyc.dayminder.Fragments.LinearDayFragment;
import abassawo.c4q.nyc.dayminder.Fragments.StaggeredDayFragment;
import abassawo.c4q.nyc.dayminder.Model.AccountFetcher;
import abassawo.c4q.nyc.dayminder.Model.Label;
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
    private List<Label>labels;
    private Drawer drawer = null;
    public static String EXTRA_NOTE_ID = "com.nyc.c4q.abassawo._id";
    private boolean gridFrag = true;

    public List<PrimaryDrawerItem> setupLabelDrawerItems(){
        List<PrimaryDrawerItem> labelList = new ArrayList<PrimaryDrawerItem>();
        for (int i = 0; i < labels.size() ; i++) {
            labelList.add(new PrimaryDrawerItem().withName(labels.get(i).toString()).withIdentifier(i));
        }
        return labelList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
        setupDrawer(savedInstanceState);
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


    public void setupDrawer(Bundle savedInstanceState){
        userProfile = new ProfileDrawerItem()
                .withName(AccountFetcher.getName(this))
                .withNameShown(true)
                .withIcon(R.drawable.abassicon);

        header = new AccountHeaderBuilder().withActivity(this)
                .addProfiles(userProfile, new ProfileSettingDrawerItem())
                .withHeaderBackground(R.drawable.background_poly)
                .build();

        labels = new ArrayList(); //fixme

        DrawerBuilder builder = new DrawerBuilder()
                .withActivity(this).
                        withAccountHeader(header, false)
                .withActionBarDrawerToggleAnimated(true)
                .withActionBarDrawerToggle(true)
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .withSliderBackgroundColor(getResources().getColor(R.color.primary_dark_material_light))
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("New Task").withIcon(getResources().getDrawable(R.drawable.ic_menu)).withIdentifier(R.id.nav_new_task),
                        new PrimaryDrawerItem().withName("Places").withIcon(getResources().getDrawable(R.drawable.ic_menu)).withIdentifier(R.id.nav_places),
                        new PrimaryDrawerItem().withName("All Tasks").withIcon(getResources().getDrawable(R.drawable.ic_menu)).withIdentifier(R.id.nav_all_tasks),
                        new PrimaryDrawerItem().withName("Labels").withIcon(getResources().getDrawable(R.drawable.ic_menu)).withIdentifier(R.id.nav_labels));
        builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                switch (iDrawerItem.getIdentifier()) {
                    case R.id.nav_new_task:
                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_places:
                        break;
                    case R.id.nav_all_tasks:
                        break;
                    case R.id.nav_labels:
                        break;
                }
                return false;
            }
        });
        for (int i = 0; i < setupLabelDrawerItems().size() ; i++) {
            List<PrimaryDrawerItem> usersLabels = setupLabelDrawerItems();
            builder.addDrawerItems(usersLabels.get(i));
        }
        drawer = builder.build();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(this.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String date = new SimpleDateFormat("EEE, MM-dd-yyyy").format(new Date());
        getSupportActionBar().setSubtitle(date);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new FragAdapter(getSupportFragmentManager());
        //if (gridFrag) {
            adapter.addFragment(new StaggeredDayFragment(), "Today");
       // } else {
            //adapter.addFragment(new LinearDayFragment(), "Today");
      //  }

        adapter.addFragment(new CalendarFragment(), "Calendar");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer();
                break;
            case R.id.action_settings:
                if(gridFrag){
                    gridFrag = false;
                    adapter.addFragment(new LinearDayFragment(), "Today");
                } else {
                    gridFrag = true;
                    adapter.addFragment(new StaggeredDayFragment(), "Today");
                }
                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(this.viewPager);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showCreateNote(View v) {
        Note note = new Note();
        note.setTitle("");
        NotePad.get(getApplicationContext()).addNote(note);
        Intent i = new Intent(getApplicationContext(), NotePagerActivity.class);
        i.putExtra(MainActivity.EXTRA_NOTE_ID, note.getId());
        startActivityForResult(i, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NotePad.get(getApplicationContext()).saveNotes();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabAddNewNote:
//                showCreateNote(v);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);



        }
    }
}



