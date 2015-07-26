package abassawo.c4q.nyc.dayminder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;


public class NoteEditActivity extends ActionBarActivity {
    ImageButton camButton;
    private ImageView imgPreview;
    private int CAPTURE_IMAGE = 9;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        imgPreview = (ImageView) findViewById(R.id.note_imageView);
        camButton = (ImageButton) findViewById(R.id.camButton);
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCameraIntent();
            }
        });
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




