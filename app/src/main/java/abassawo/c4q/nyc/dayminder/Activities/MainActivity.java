package abassawo.c4q.nyc.dayminder.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import abassawo.c4q.nyc.dayminder.Adapters.CustomRecyclerAdapter;
import abassawo.c4q.nyc.dayminder.Adapters.ItemClickSupport;
import abassawo.c4q.nyc.dayminder.Adapters.SimpleItemTouchHelperCallback;
import abassawo.c4q.nyc.dayminder.Adapters.SwipeDismissRecyclerViewTouchListener;
import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
import abassawo.c4q.nyc.dayminder.Adapters.FragAdapter;
import abassawo.c4q.nyc.dayminder.Model.Note;
import abassawo.c4q.nyc.dayminder.Model.User;
import abassawo.c4q.nyc.dayminder.R;
import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.nav_view) NavigationView navView;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.fabAddNewNote) FloatingActionButton fab;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private CustomRecyclerAdapter mAdapter;
    private List<Note> mItems;
    private FragAdapter adapter;
    public static List<Note>mNotes;
    private User user;

    public static String EXTRA_NOTE_ID = "com.nyc.c4q.abassawo._id";
    private boolean gridFrag = true;
    private boolean firstRun;

    private void initData(){
        mItems = NotePad.get(this).getNotes();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initRV();
        setupRecyclerView(recyclerView);
        updateUI();
        initViews();
        setupDrawer(navView);
        initListeners();

    }

    private void initRV(){
        mAdapter = new CustomRecyclerAdapter(this);
    }
    public void setupRecyclerView(RecyclerView recyclerView){
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(getApplicationContext(), NotePagerActivity.class);
                i.putExtra(EXTRA_NOTE_ID, mItems.get(position).getId());
                startActivity(i);
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);


        recyclerView.setAdapter(mAdapter);

        recyclerView.callOnClick();

        SwipeDismissRecyclerViewTouchListener touchListener =
                new SwipeDismissRecyclerViewTouchListener(
                        recyclerView,
                        new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    mItems.remove(position);
                                }
                                // do not call notifyItemRemoved for every item, it will cause gaps on deleting items
                                mAdapter.notifyDataSetChanged();
                            }
                        });
        recyclerView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        recyclerView.setOnScrollListener(touchListener.makeScrollListener());
    }


    public void updateUI(){
        NotePad notePad = NotePad.get(this);
        List<Note>notes = notePad.getNotes();
        if(mAdapter == null) {
            mAdapter = new CustomRecyclerAdapter(notes);
            recyclerView.setAdapter(mAdapter);
        } else{
            mAdapter.setItems(notes);
            mAdapter.notifyDataSetChanged();
        }

    }


    public void initViews(){
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void initListeners(){
        fab.setOnClickListener(this);
    }



    public void setupDrawer(NavigationView nav){
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_new_task:
                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_places:
                        break;
                    case R.id.nav_all_tasks:
//                        Intent notesIntent = new Intent(MainActivity.this, AllNotesActivity.class);
//                        startActivity(notesIntent);
                        break;
                    case R.id.nav_labels:
                        break;
                }
                return false;
            }
        });
    }
//
//    public List<Label>setupLabelDrawerItems(){
//        return NotePad.get(this).initLabelData();
//    }


    @Override
    protected void onResume() {
        super.onResume();
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
                break;
            case R.id.action_settings:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
        }
    }
}



