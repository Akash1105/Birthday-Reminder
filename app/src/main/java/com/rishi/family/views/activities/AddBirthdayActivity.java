package com.rishi.family.views.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.rishi.family.R;
import com.rishi.family.customcomponents.custombuttons.SmallAccentColorButton;
import com.rishi.family.customcomponents.customtextviews.MediumWhiteTextView;
import com.rishi.family.model.BirthdayBeans;
import com.rishi.family.sqlite.DatabaseHandler;
import com.rishi.family.utilities.ImageUtils;
import com.rishi.family.views.fragments.DatePickerFragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Calendar;

public class AddBirthdayActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    //Constants
    private static final int SELECT_PICTURE = 100;
    //Components
    private Toolbar mToolbar;
    private EditText mEditTextEntryName, mEditTextBirthday;
    private SmallAccentColorButton mButtonSave;
    private ImageButton mImageButtonBack, mImageButtonDateOfBirth;
    private ImageView mImageViewEntryPicture;
    private MediumWhiteTextView mTextViewDateOfBirth;
    private int mClickedValue;
    private String mType;

    //Objects
    private DatabaseHandler mObjectDatabaseHandler;
    private Calendar objectCalender;

    //Variables
    private int mSelectedYear, mSelectedMonth, mSelectedDayOfMonth;
    private String mSelectedDate;
    private Uri mSelectedImageUri;
    private byte[] mByteArrayEmpty = new byte[0];
    private int effectedRows = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_birthday);


        getComponents();
        setToolbarProperties();
        initiateObjects();
        getIntentValue();
        setListeners();
    }

    private void getIntentValue() {

        mType = getIntent().getExtras().getString("TYPE");
        if (mType.equals("update")) {
            mClickedValue = getIntent().getExtras().getInt("CLICKED_VALUE");
            updateValuesInFields();
        } else {

//            setDefaultValueOfDate();
        }

    }

    private void updateValuesInFields() {

        mEditTextEntryName.setText(mObjectDatabaseHandler.getAllBirthdays().get(mClickedValue).getmName());
        setDateToTextView(mObjectDatabaseHandler.getAllBirthdays().get(mClickedValue).getmYear(),
                mObjectDatabaseHandler.getAllBirthdays().get(mClickedValue).getmMonth(),
                mObjectDatabaseHandler.getAllBirthdays().get(mClickedValue).getmDayOfMonth());
    }

    void getComponents() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mEditTextEntryName = (EditText) findViewById(R.id.edittext_entryname);
//        mEditTextBirthday = (EditText) findViewById(R.id.edittext_Birthday);
//        mImageViewEntryPicture = (ImageView) findViewById(R.id.imageview_entrypicture);
        mButtonSave = (SmallAccentColorButton) findViewById(R.id.button_save);
        mImageButtonBack = (ImageButton) findViewById(R.id.imagebutton_backarrow);
        mImageButtonDateOfBirth = (ImageButton) findViewById(R.id.imagebutton_dateofbirth);
        mTextViewDateOfBirth = (MediumWhiteTextView) findViewById(R.id.textview_dataofbirth);
    }

    void setToolbarProperties() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(null);
    }

    void initiateObjects() {
        mObjectDatabaseHandler = new DatabaseHandler(this);
        objectCalender = Calendar.getInstance();
    }

    void setDefaultValueOfDate() {

        mSelectedYear = objectCalender.get(Calendar.YEAR);
        mSelectedMonth = objectCalender.get(Calendar.MONTH);
        mSelectedDayOfMonth = objectCalender.get(Calendar.DAY_OF_MONTH);

        mTextViewDateOfBirth.setText("" + DateFormat.getDateInstance().format(objectCalender.getTime()));
    }

    void setListeners() {
        mImageButtonDateOfBirth.setOnClickListener(this);
        mButtonSave.setOnClickListener(this);
        mImageButtonBack.setOnClickListener(this);
//        mImageViewEntryPicture.setOnClickListener(this);
    }


    void gotoBirthdaysListActivity() {
        Intent intent = new Intent(AddBirthdayActivity.this, BirthdaysListActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    // Choose an image from Gallery
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                mSelectedImageUri = data.getData();

                if (null != mSelectedImageUri) {

                    mImageViewEntryPicture.setImageURI(mSelectedImageUri);


                }

            }
        }
    }


    byte[] getImageBytes(Uri selectedImageUri) {

        InputStream iStream = null;
        try {
            iStream = getContentResolver().openInputStream(selectedImageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] inputData = new byte[0];
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            inputData = ImageUtils.getBytes(iStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputData;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        //Storing the values in class variables
        mSelectedYear = year;
        mSelectedMonth = month;
        mSelectedDayOfMonth = dayOfMonth;

     /*   if (isEnteredDateAfterToday(mSelectedYear, mSelectedMonth, mSelectedDayOfMonth)) {
            Toast.makeText(this, "Kindly enter date less than current date", Toast.LENGTH_SHORT).show();
        } else {*/
        //Setting the calendar object's date to the date selected by the user
        objectCalender.set(Calendar.YEAR, year);
        objectCalender.set(Calendar.MONTH, month);
        objectCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        //Creating a string and setting the string value to the date selected by the user
        mSelectedDate = DateFormat.getDateInstance().format(objectCalender.getTime());
        mTextViewDateOfBirth.setText("" + mSelectedDate);
    }


    public boolean isEnteredDateAfterToday(int year, int month, int day) {

        Calendar today = Calendar.getInstance();
        Calendar myDate = Calendar.getInstance();

        myDate.set(year, month, day);

        if (myDate.before(today)) {
            return false;
        }
        return true;
    }

    void setDateToTextView(int year, int month, int dayOfMonth) {

        //Storing the values in class variables
        mSelectedYear = year;
        mSelectedMonth = month;
        mSelectedDayOfMonth = dayOfMonth;

        //Setting the calendar object's date to the date selected by the user
        objectCalender.set(Calendar.YEAR, year);
        objectCalender.set(Calendar.MONTH, month);
        objectCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        //Creating a string and setting the string value to the date selected by the user
        mSelectedDate = DateFormat.getDateInstance().format(objectCalender.getTime());
        mTextViewDateOfBirth.setText("" + mSelectedDate);
    }

    @Override
    public void onClick(View v) {

        if (v == mImageButtonDateOfBirth) {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        }
        if (v == mButtonSave) {

            if (mTextViewDateOfBirth.getText().toString().equalsIgnoreCase("") || mEditTextEntryName.getText().toString()
                    .equalsIgnoreCase("") || mTextViewDateOfBirth.getText().toString().equalsIgnoreCase("Select date of birth")) {
                Toast.makeText(this, "Kindly enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                if (mType.equals("update")) {

                    /*Toast.makeText(this, "Update Called", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "" + mSelectedYear, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "" + mSelectedMonth, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "" + mSelectedDayOfMonth, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "" + mObjectDatabaseHandler.getAllBirthdays().get(mClickedValue).getmId(), Toast.LENGTH_SHORT).show();*/


                    if (mSelectedImageUri != null) {
                        effectedRows = mObjectDatabaseHandler.updateBirthday(new BirthdayBeans(mObjectDatabaseHandler.getAllBirthdays().get(mClickedValue).getmId(), getImageBytes(mSelectedImageUri), mEditTextEntryName.getText().toString(), mSelectedYear, mSelectedMonth,
                                mSelectedDayOfMonth));

                    } else {
                        effectedRows = mObjectDatabaseHandler.updateBirthday(new BirthdayBeans(mObjectDatabaseHandler.getAllBirthdays().get(mClickedValue).getmId(), mByteArrayEmpty, mEditTextEntryName.getText().toString(), mSelectedYear, mSelectedMonth,
                                mSelectedDayOfMonth));

                    }

                } else {
                    if (mSelectedImageUri != null) {
                        mObjectDatabaseHandler.addBirthday(new BirthdayBeans(getImageBytes(mSelectedImageUri), mEditTextEntryName.getText().toString(), mSelectedYear, mSelectedMonth,
                                mSelectedDayOfMonth));
                    } else {
                        mObjectDatabaseHandler.addBirthday(new BirthdayBeans(mByteArrayEmpty, mEditTextEntryName.getText().toString(), mSelectedYear, mSelectedMonth,
                                mSelectedDayOfMonth));
                    }

                }
                if (mType.equals("update")) {
                    if (effectedRows > 0) {
                        Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
                        gotoBirthdaysListActivity();

                    } else {
                        Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
                        gotoBirthdaysListActivity();
                    }

                } else {
                    Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
                    gotoBirthdaysListActivity();


                }

            }

        }
        if (v == mImageViewEntryPicture) {

            openImageChooser();

        }

        if (v == mImageButtonBack) {
            gotoBirthdaysListActivity();
        }
    }

    @Override
    public void onBackPressed() {
        gotoBirthdaysListActivity();
    }
}
