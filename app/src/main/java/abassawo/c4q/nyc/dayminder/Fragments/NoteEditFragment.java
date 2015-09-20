package abassawo.c4q.nyc.dayminder.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cocosw.bottomsheet.BottomSheet;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.ImagePickerSheetView;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import abassawo.c4q.nyc.dayminder.Controllers.NotePad;
import abassawo.c4q.nyc.dayminder.Model.Note;
import abassawo.c4q.nyc.dayminder.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-Abass on 9/17/15.
 */
public class NoteEditFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_NOTE_ID = "com.nyc.c4q.abassawo._id";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private static final int REQUEST_STORAGE = 0;
    private static final int REQUEST_IMAGE_CAPTURE = REQUEST_STORAGE + 1;
    private static final int REQUEST_LOAD_IMAGE = REQUEST_IMAGE_CAPTURE + 1;
    public static final int REQUEST_TIME = 3;
    public static final int REQUEST_DATE = REQUEST_TIME + REQUEST_TIME;
    public static final int REQUEST_LOCATION = REQUEST_DATE + REQUEST_TIME;
    public static final int REQUEST_LABEL = REQUEST_LOCATION + REQUEST_LOCATION;
    private Note mNote;
    private Uri cameraImageUri;
    private View view;
    private Context ctx;
    @Bind(R.id.bottomsheet)
    BottomSheetLayout controlSheet;
    @Bind(R.id.edit_task_title)
    EditText edittext;
    @Bind(R.id.note_imageView)
    ImageView imgView;
    @Bind(R.id.fab_reveal_layout)
    FABRevealLayout fabRevealLayout;
    private BottomSheet locationSheet, dateSheet, reminderSheet;

    private void configureFABReveal(FABRevealLayout fabRevealLayout) {

        fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {
                //showMainViewItems();
            }

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
                hideKeyboard();
                secondaryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //dateSheet.show();
                        prepareBackTransition(fabRevealLayout);

                    }
                });

            }

        });
    }

    private void prepareBackTransition(final FABRevealLayout fabRevealLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fabRevealLayout.revealMainView();
            }
        }, 200);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note_edit, container, false);
        ctx = getActivity().getApplicationContext();
        initViews(view);
        setupListeners();
        configureFABReveal(fabRevealLayout);
        return view;
    }

    public void initViews(View v) {
        ButterKnife.bind(this, v);
        setupDateSheets();
//        setupReminderSheet();
//        setupLocationSheet();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID noteId = (UUID) getArguments().getSerializable(EXTRA_NOTE_ID);
        mNote = NotePad.get(getActivity()).getNote(noteId);
        setHasOptionsMenu(true);
    }

    public void setupDateSheets() {
        // long id = cupboard().withDatabase(db).put(mTask);

        dateSheet = new BottomSheet.Builder(getActivity()).title("Due Date").sheet(R.menu.menu_date).listener(new SheetOnClickListener())
        .build();


    }


    public static NoteEditFragment newInstance(UUID noteId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_NOTE_ID, noteId);
        NoteEditFragment fragment = new NoteEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setupListeners() {
        view.findViewById(R.id.camButton).setOnClickListener(this);
        edittext.setText(mNote.getTitle());
        edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mNote.setTitle(c.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // left blank
            }

            @Override
            public void afterTextChanged(Editable c) {
                // left blank
            }
        });

        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    hideKeyboard();
                    // Perform action on key press
                    return true;
                }
                return false;
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = createCameraIntent();
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent != null) {
            // Create the File where the photo should go
            try {
                File imageFile = createImageFile();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException e) {
                // Error occurred while creating the File
                //genericError("Could not create imageFile for camera");
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        // Save a file: path for use with ACTION_VIEW intents
        cameraImageUri = Uri.fromFile(imageFile);
        return imageFile;
    }


    private void showGalleryPickerSheetView() {
        ImagePickerSheetView sheetView = new ImagePickerSheetView.Builder(getActivity())
                .setMaxItems(30)
                .setShowCameraOption(createCameraIntent() != null)
                .setShowPickerOption(createPickIntent() != null)
                .setImageProvider(new ImagePickerSheetView.ImageProvider() {
                    @Override
                    public void onProvideImage(ImageView imageView, Uri imageUri, int size) {
                        Glide.with(NoteEditFragment.this)
                                .load(imageUri)
                                .centerCrop()
                                .crossFade()
                                .into(imageView);
                    }
                })
                .setOnTileSelectedListener(new ImagePickerSheetView.OnTileSelectedListener() {
                    @Override
                    public void onTileSelected(ImagePickerSheetView.ImagePickerTile selectedTile) {
                        controlSheet.dismissSheet();
                        if (selectedTile.isCameraTile()) {
                            dispatchTakePictureIntent();
                        } else if (selectedTile.isPickerTile()) {
                            startActivityForResult(createPickIntent(), REQUEST_LOAD_IMAGE);
                        } else if (selectedTile.isImageTile()) {
                            showSelectedImage(selectedTile.getImageUri());
                        } else {
                            //genericError();
                        }
                    }
                })
                .setTitle("Choose an image...")
                .create();


        controlSheet.showWithSheetView(sheetView);

    }

    private Intent createPickIntent() {
        Intent picImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (picImageIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            return picImageIntent;
        } else {
            return null;
        }
    }

    private void showSelectedImage(Uri selectedImageUri) {
        imgView.setImageDrawable(null);
        Glide.with(this).load(selectedImageUri).crossFade().fitCenter().into(imgView);
        //mTask.setUriStr(imageUri.toString());
//        mTask.setUriStr(selectedImageUri.toString());
//        mTask.setCustomPhoto(true);
    }


    private Intent createCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            return takePictureIntent;
        } else {
            return null;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camButton:
                if (!controlSheet.isSheetShowing()) {
                    showGalleryPickerSheetView();
                } else {
                    controlSheet.dismissSheet();
                }
                break;
        }

    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}

   class SheetOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int id) {
            switch (id) {
//                case R.id.today_item:
//                    mNote.setDueToday();
//                    // taskList.add(mTask);
//                    Log.d(mTask.toString(), "due date test");
//                    prepareBackTransition(fabRevealLayout);
//                    reminderSheet.show();
//                    // startActivity(new Intent(EditActivity.this, MainActivity.class));
//                    break;
//                case R.id.tomorrow:
//                    mTask.setDueTomorrow(ctx); //setDueTomorrow fixme
//                    //taskList.add(mTask);
//                    Log.d(mTask.toString(), "due date test");
//                    prepareBackTransition(fabRevealLayout);
//                    reminderSheet.show();
//                    //startActivity(new Intent(EditActivity.this, MainActivity.class));
//                    break;
//                case R.id.choosedate:
//                    dateSheet.dismiss();//testing
//                    showCustomDatePicker();
//                    Calendar mcurrentDate = Calendar.getInstance();
//                    int mYear = mcurrentDate.get(Calendar.YEAR);
//                    int mMonth = mcurrentDate.get(Calendar.MONTH);
//                    int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
//                    // fm.beginTransaction().add(dateDialog, "DATE").commit(); //open datepicker
//                    reminderSheet.show();
//                    break;
//                case R.id.nextweek:
//                    mTask.setDueinOneWeek(ctx);
//                    Log.d(mTask.getDueDate().toString(), "due date test");
//                    prepareBackTransition(fabRevealLayout);
//                    reminderSheet.show();
//                    //startActivity(new Intent(EditActivity.this, MainActivity.class));
//                    break;
                default:
                    break;
            }

        }


    }
