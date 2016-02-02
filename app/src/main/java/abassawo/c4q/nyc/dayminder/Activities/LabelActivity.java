package abassawo.c4q.nyc.dayminder.Activities;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
import abassawo.c4q.nyc.dayminder.Model.Note;
import abassawo.c4q.nyc.dayminder.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class LabelActivity extends AppCompatActivity implements View.OnClickListener {
    private String selectedLabel;
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @Bind(R.id.edittext_label)
    EditText edittext;
    @Bind(R.id.labels_list)
    ListView listView;
    @Bind(R.id.add_new_label_button)
    ImageButton addNewButton;
    private ArrayAdapter mAdapter;
    private ArrayList<String> usersLabels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        ButterKnife.bind(this);
        UUID id = (UUID) getIntent().getExtras().get(EditActivity.EXTRA_ID_FOR_LABEL);
        final Note mNote = NotePad.get(getApplicationContext()).getNote(id);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, NotePad.get(this).initLabelData());
        mAdapter.setNotifyOnChange(true);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mNote.setmLabel(mAdapter.getItem(position).toString());
            }
        });
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mNote.setmLabel(mAdapter.getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        initListeners();
     //  mAdapter = new LabelAdapter(this, android.R.layout.simple_list_item_single_choice, initLabelData());
        //listView.setAdapter(mAdapter);
        listView.setTextFilterEnabled(true);
        //setupActionBar();
       // UUID id = (UUID) getIntent().getExtras().get(EditActivity.EXTRA_ID_FOR_LABEL);

        edittext.addTextChangedListener(new TextWatcher() {
            String addPrompt;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // addPrompt ="Create \"  " + edittext.getText() + "\"";
                mAdapter.getFilter().filter(s);
                //mAdapter.add(addPrompt);

            }

            @Override
            public void afterTextChanged(Editable s) {
                //mAdapter.remove(addPrompt);

                if (!(usersLabels.contains(s.toString()))) {
                    addNewButton.setVisibility(View.VISIBLE);
                    //make add new label option visible
                }
                //  mNote.setmLabel(edittext.getText().toString()); //Testing
                //mNote.setmLabel(s.toString());
            }
        });

    }



    private void initListeners(){
        addNewButton.setOnClickListener(this);
    }


//    public void setupActionBar(){
//        setSupportActionBar(toolbar);
//        final ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("");
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
////        actionBar.setDisplayHomeAsUpEnabled(true);
////        actionBar.setDisplayShowHomeEnabled(true);
////        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_label, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_new_label_button:
            if (edittext.getText().length() > 0) {
                String newLabel = edittext.getText().toString();
                usersLabels.add(newLabel);
                mAdapter.notifyDataSetChanged();
                mAdapter.getFilter().filter(newLabel);

                //may need to readapt

            }
        }
    }


}
