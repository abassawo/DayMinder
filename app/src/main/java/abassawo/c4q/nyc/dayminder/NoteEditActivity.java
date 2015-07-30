package abassawo.c4q.nyc.dayminder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;


public class NoteEditActivity extends ActionBarActivity {
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    ImageButton camButton;
    private ImageView imgPreview;
    private int CAPTURE_IMAGE = 9;
    private Uri imageUri;
    private Note mNote;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedBox;
    private ImageButton delButton;
    private void updateDate()
    {
        Date d = mNote.getDate();
        CharSequence c = DateFormat.format("EEEE, MMM dd, yyyy", d);
        CharSequence t = DateFormat.format("h:mm a", d);
        mDateButton.setText(c);
        mTimeButton.setText(t);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        mNote = new Note(); //fixme
        imgPreview = (ImageView) findViewById(R.id.note_imageView);
        camButton = (ImageButton) findViewById(R.id.camButton);
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCameraIntent();
            }
        });
        mDateButton = (Button) findViewById(R.id.note_date);
        //mTimeButton = (Button) findViewById(R.id.note_time);
        updateDate();

        mTitleField = (EditText) findViewById(R.id.edit_note_title);

        mTitleField.setText(mNote.getTitle());

        mTitleField.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mNote.setTitle(c.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence c, int start, int count,
                                          int after) {
                // left blank
            }

            @Override
            public void afterTextChanged(Editable c) {

            }
        });


        mDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mNote.getDate());
                //dialog.setTargetFragment(NoteFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

//        mTimeButton = (Button) findViewById(R.id.note_time);
//        mTimeButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                FragmentManager fm = getSupportFragmentManager();
//                TimePickerFragment dialog = TimePickerFragment.newInstance(mNote.getDate());
//               // dialog.setTargetFragment(NoteFragment.this, REQUEST_TIME);
//                dialog.show(fm, DIALOG_TIME);
//            }
//        });

    }



    public void startCameraIntent(){
        //Destination
        String mediaStorageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).getPath();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File mediaFile = new File(mediaStorageDir + File.separator + "IMG_" + timeStamp + ".jpg");
        // Send intent to take picture
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = Uri.fromFile(mediaFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mediaFile));
        startActivityForResult(cameraIntent, CAPTURE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(  (resultCode == RESULT_OK) && (requestCode == CAPTURE_IMAGE)) {
            imgPreview.setImageURI(imageUri);
        }
    }
}



