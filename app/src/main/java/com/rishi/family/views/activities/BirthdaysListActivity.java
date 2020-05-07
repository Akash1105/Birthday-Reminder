package com.rishi.family.views.activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.rishi.family.R;
import com.rishi.family.controllers.BirthdaysListAdapter;
import com.rishi.family.controllers.TodaysBirthdaysListAdapter;
import com.rishi.family.customcomponents.customtextviews.MediumWhiteTextView;
import com.rishi.family.listeners.RecyclerViewClickListener;
import com.rishi.family.model.BirthdayBeans;
import com.rishi.family.receivers.AlarmReceiver;
import com.rishi.family.sharedpreference.SharedPreferencesManager;
import com.rishi.family.sqlite.DatabaseHandler;
import com.rishi.family.sqlitebackup.SqliteBackupManager;
import com.rishi.family.utilities.TodaysBirthdayManager;
import com.rishi.family.views.activities.sms.SmsSchedulerActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BirthdaysListActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewClickListener {

    //Components
    Toolbar mToolbar;
    //Properties
    BottomSheetBehavior mBottomSheetBehaviour;
    private FloatingActionButton mFabAddBirthday;
    private RecyclerView mRecyclerviewBirthdaysList, mRecyclerViewTodaysBirthdaysList;
    private MediumWhiteTextView mTextViewToolbarTitle;
    private TextView mTextViewTodaysBirthdaysHeading;
    private TextView mTextViewNoBirthdaysText;
    private ImageButton mImageButtonOptionsMenu;
    private View mBottomSheet;
    private TableRow mTableRowBsEdit;
    private TableRow mTableRowBsDelete;
    private TableRow mTableRowBsCancel;
    private TextView mTextViewBirthdaysHeading;
    private View mViewDimBackground;
    //Variables
    private boolean mDoubleBackToExitPressedOnce = false;
    private List<BirthdayBeans> mListBirthdayBeans = new ArrayList<>();
    //Constants
    private String MANUFACTURER = "xiaomi";
    //Permision code that will be checked in the method onRequestPermissionsResult
    private int READ_STORAGE_PERMISSION_CODE = 23;
    private int WRITE_STORAGE_PERMISSION_CODE = 24;
    //Adapters
    private BirthdaysListAdapter mAdapterBirthdaysList;
    private TodaysBirthdaysListAdapter mAdapterTodaysBirthdaysList;
    //Objects
    private DatabaseHandler mObjectDataBaseHandler;
    private AlarmManager mObjectAlarmManager;
    private Calendar mObjectCalendar, mObjectCalendarCurrent;
    //Runtime variables
    private int mRecyclerViewClickedPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthdayslist);

        getComponents();
        initiateObjects();
        setToolbarProperties();
        setListeners();
        //Getting the Bottom sheet behaviour
        getBottomSheetBehaviour();
        //Setting the Bottom sheet callback
        mBottomSheetBehaviour.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        mFabAddBirthday.setVisibility(View.INVISIBLE);


                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        mFabAddBirthday.setVisibility(View.INVISIBLE);


                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        mFabAddBirthday.setVisibility(View.VISIBLE);
                        mViewDimBackground.setVisibility(View.GONE);

                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                mViewDimBackground.setVisibility(View.VISIBLE);
                mViewDimBackground.setAlpha(slideOffset);


            }
        });

        if (MANUFACTURER.equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
            if (SharedPreferencesManager.isAppAlreadyOpened(this) == false) {
                showAutostartDialog();
                SharedPreferencesManager.appAlreadyOpened(this, true);
            }
        }

    }

    // TODO: 26-04-2020  add the activities and remove the comments
    //
//    public void addevents(View view) {
//        Intent intent = new Intent(this, InsertDataActivity.class);
//        startActivity(intent);
//    }
//
//    public void viewevents(View view) {
//        Intent intent = new Intent(this, DatesActivity.class);
//        startActivity(intent);
//    }
//
//    public void familytree(View view) {
//        //      Toast.makeText(this, "Family Tree Coming Soon", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, FamilyTreeActivity.class);
//        startActivity(intent);
//    }
//
//    public void gifts(View view) {
//        //     Toast.makeText(this, "Gift Recommendation Coming Soon", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, AutoSmsScheduler.class);
//        startActivity(intent);
//    }
    @Override
    protected void onStart() {
        super.onStart();
        setRecyclerViewData();
        setTodaysRecyclerViewData();
        //Setting Notification time by calling methods
        setNotificationTime();
    }

    private void getBottomSheetBehaviour() {
        mBottomSheetBehaviour = BottomSheetBehavior.from(mBottomSheet);

    }

    private void setTodaysRecyclerViewData() {

        mListBirthdayBeans = mObjectDataBaseHandler.getAllBirthdays();
        if (TodaysBirthdayManager.getTodaysBirthdaysList(mListBirthdayBeans).size() != 0) {
            mTextViewTodaysBirthdaysHeading.setText(R.string.todays_birthdays_title);
        } else {
            mTextViewTodaysBirthdaysHeading.setVisibility(View.GONE);
        }

        mAdapterTodaysBirthdaysList = new TodaysBirthdaysListAdapter(TodaysBirthdayManager.getTodaysBirthdaysList(mListBirthdayBeans));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerViewTodaysBirthdaysList.setLayoutManager(mLayoutManager);
        mRecyclerViewTodaysBirthdaysList.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewTodaysBirthdaysList.setHasFixedSize(true);
        mRecyclerViewTodaysBirthdaysList.setNestedScrollingEnabled(false);
        mRecyclerViewTodaysBirthdaysList.setAdapter(mAdapterTodaysBirthdaysList);
    }

    void getComponents() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFabAddBirthday = (FloatingActionButton) findViewById(R.id.floatingactionbutton_addbirthday);
        mRecyclerviewBirthdaysList = (RecyclerView) findViewById(R.id.recyclerview_birthdayslist);
        mTextViewToolbarTitle = (MediumWhiteTextView) findViewById(R.id.textview_toolbartitle);
        mRecyclerViewTodaysBirthdaysList = (RecyclerView) findViewById(R.id.recyclerview_todays_birthdayslist);
        mTextViewTodaysBirthdaysHeading = (TextView) findViewById(R.id.textview_todaysbirthdays_heading);
        mTextViewBirthdaysHeading = (TextView) findViewById(R.id.textview_birthdays_heading);
        mTextViewNoBirthdaysText = (TextView) findViewById(R.id.textview_noBirthdaysText);
        mImageButtonOptionsMenu = (ImageButton) findViewById(R.id.imagebutton_options_menu);
        mBottomSheet = findViewById(R.id.bottom_sheet);
        mTableRowBsEdit = (TableRow) findViewById(R.id.linearlayout_edit);
        mTableRowBsDelete = (TableRow) findViewById(R.id.linearlayout_delete);
        mTableRowBsCancel = (TableRow) findViewById(R.id.linearlayout_cancel);
        mViewDimBackground = (View) findViewById(R.id.view_dimBackground);
    }

    void setToolbarProperties() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(null);
        mTextViewToolbarTitle.setText(R.string.actionbar_title);
    }

    void initiateObjects() {
        mObjectDataBaseHandler = new DatabaseHandler(this);
        mObjectAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mObjectCalendar = Calendar.getInstance();
        mObjectCalendarCurrent = Calendar.getInstance();
    }

    void setListeners() {
        mFabAddBirthday.setOnClickListener(this);
        mRecyclerviewBirthdaysList.setOnClickListener(this);
        mImageButtonOptionsMenu.setOnClickListener(this);
        mTableRowBsEdit.setOnClickListener(this);
        mTableRowBsDelete.setOnClickListener(this);
        mTableRowBsCancel.setOnClickListener(this);
        mViewDimBackground.setOnClickListener(this);

    }

    void setRecyclerViewData() {

        mListBirthdayBeans = mObjectDataBaseHandler.getAllBirthdays();

        if (mListBirthdayBeans.size() == 0) {
            mTextViewNoBirthdaysText.setVisibility(View.VISIBLE);
            mTextViewNoBirthdaysText.setText(R.string.add_birthdays_note);
            mTextViewBirthdaysHeading.setVisibility(View.GONE);
        } else {
            mTextViewNoBirthdaysText.setVisibility(View.GONE);
            mTextViewBirthdaysHeading.setText(R.string.all_birthdays_title);

        }

        mAdapterBirthdaysList = new BirthdaysListAdapter(mListBirthdayBeans);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerviewBirthdaysList.setLayoutManager(mLayoutManager);
        mRecyclerviewBirthdaysList.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerviewBirthdaysList.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL, 0));
        mRecyclerviewBirthdaysList.setHasFixedSize(true);
        mRecyclerviewBirthdaysList.setNestedScrollingEnabled(false);
        mRecyclerviewBirthdaysList.setAdapter(mAdapterBirthdaysList);
        mAdapterBirthdaysList.setClickListener(this);
    }


    void setNotificationTime() {


        mObjectCalendar.set(Calendar.HOUR_OF_DAY, SharedPreferencesManager.getNotificationTimeHour(this));
        mObjectCalendar.set(Calendar.MINUTE, SharedPreferencesManager.getNotificationTimeMinute(this));
        mObjectCalendar.set(Calendar.SECOND, 0);
        mObjectCalendar.set(Calendar.MILLISECOND, 0);


        if (mObjectCalendar.before(mObjectCalendarCurrent))
            mObjectCalendar.add(Calendar.DATE, 1);

        Intent intentThisToAlarmReceiver = new Intent(this, AlarmReceiver.class);
        intentThisToAlarmReceiver.setAction("Normal alarm intent");
        PendingIntent pendingIntentBroadcast = PendingIntent.getBroadcast(this, 100, intentThisToAlarmReceiver,
                PendingIntent.FLAG_UPDATE_CURRENT);


//        mObjectAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, mObjectCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntentBroadcast);

//        mObjectAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, mObjectCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntentBroadcast);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mObjectAlarmManager.setExact(AlarmManager.RTC_WAKEUP, mObjectCalendar.getTimeInMillis(), pendingIntentBroadcast);
        } else {
            mObjectAlarmManager.set(AlarmManager.RTC_WAKEUP, mObjectCalendar.getTimeInMillis(), pendingIntentBroadcast);
        }

    }

    private void showDeleteAllConfirmationDialogue() {

        AlertDialog.Builder alert = new AlertDialog.Builder(BirthdaysListActivity.this);
        alert.setTitle("Delete all birthdays");
        alert.setMessage("Are you sure you want to delete all birthdays?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteAllBirthdays();
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void deleteAllBirthdays() {

        for (int i = 0; i < mListBirthdayBeans.size(); i++) {
            mObjectDataBaseHandler.deleteBirthday(mListBirthdayBeans.get(i));
        }
        //Setting the recyclerview again
        mListBirthdayBeans.clear();
        setRecyclerViewData();
        setTodaysRecyclerViewData();

    }


    @Override
    public void onBackPressed() {
        if (mDoubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.mDoubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mDoubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {

        if (v == mFabAddBirthday) {
            Intent intent = new Intent(BirthdaysListActivity.this, AddBirthdayActivity.class);
            intent.putExtra("TYPE", "new");
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
        if (v == mRecyclerviewBirthdaysList) {

        }

        if (v == mImageButtonOptionsMenu) {
            //Creating the instance of PopupMenu
            PopupMenu popup = new PopupMenu(BirthdaysListActivity.this, mImageButtonOptionsMenu);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.options_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getTitle().equals("Notification settings")) {

                        Intent intentBirthdaysListToNotificationSettings = new Intent(BirthdaysListActivity.this,
                                NotificationsSettingsActivity.class);
                        startActivity(intentBirthdaysListToNotificationSettings);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();

                    } else if (item.getTitle().equals("Delete all")) {

                        showDeleteAllConfirmationDialogue();

                    } else if (item.getTitle().equals("Backup")) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            //First checking if the app is already having the permission
                            if (isWriteStoragePermissionAllowed()) {

//                                SqliteBackupManager.exportDB(BirthdaysListActivity.this);
                                showBackupNotifyDialog();

                            } else {
                                //If the app has not the permission then asking for the permission
                                requestWriteStoragePermission();
                            }
                        } else {
//                            SqliteBackupManager.exportDB(BirthdaysListActivity.this);
                            showBackupNotifyDialog();

                        }


                    } else {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            //First checking if the app is already having the permission
                            if (isReadStoragePermissionAllowed()) {

//                                boolean acknowledgement = SqliteBackupManager.importDB(BirthdaysListActivity.this);
//
//                                if (acknowledgement) {
//
//                                    mListBirthdayBeans.clear();
//                                    setRecyclerViewData();
//                                    setTodaysRecyclerViewData();
//                                    Toast.makeText(BirthdaysListActivity.this, "Called", Toast.LENGTH_SHORT).show();
//                                }
                                showRestoreNotifyDialog();

                            } else {
                                //If the app has not the permission then asking for the permission
                                requestReadStoragePermission();
                            }

                        } else {
//                            SqliteBackupManager.importDB(BirthdaysListActivity.this);
//
//                            boolean acknowledgement = SqliteBackupManager.importDB(BirthdaysListActivity.this);
//
//                            if (acknowledgement) {
//
//                                mListBirthdayBeans.clear();
//                                setRecyclerViewData();
//                                setTodaysRecyclerViewData();
//                                Toast.makeText(BirthdaysListActivity.this, "Called", Toast.LENGTH_SHORT).show();
//                            }
                            showRestoreNotifyDialog();


                        }


                    }


                    return true;
                }
            });

            popup.show();//showing popup menu
        }

        if (v == mTableRowBsEdit) {
            Intent objectBirthdaysListToAddBirthdayIntent = new Intent(this, AddBirthdayActivity.class);
            objectBirthdaysListToAddBirthdayIntent.putExtra("CLICKED_VALUE", mRecyclerViewClickedPosition);
            objectBirthdaysListToAddBirthdayIntent.putExtra("TYPE", "update");
            startActivity(objectBirthdaysListToAddBirthdayIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }

        if (v == mTableRowBsDelete) {

            mObjectDataBaseHandler.deleteBirthday(mListBirthdayBeans.get(mRecyclerViewClickedPosition));
            mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
            mListBirthdayBeans.clear();
            setRecyclerViewData();
            setTodaysRecyclerViewData();
        }

        if (v == mTableRowBsCancel) {
            mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        if (v == mViewDimBackground) {
            mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }
    }

    @Override
    public void itemClicked(View view, int position) {

       /* Intent objectBirthdaysListToAddBirthdayIntent = new Intent(this, AddBirthdayActivity.class);
        objectBirthdaysListToAddBirthdayIntent.putExtra("CLICKED_VALUE", position);
        objectBirthdaysListToAddBirthdayIntent.putExtra("TYPE", "update");
        startActivity(objectBirthdaysListToAddBirthdayIntent);*/

        mRecyclerViewClickedPosition = position;
        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);


    }

    //We are calling this method to check the permission status
    private boolean isReadStoragePermissionAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //We are calling this method to check the permission status
    private boolean isWriteStoragePermissionAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestReadStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_CODE);
    }

    //Requesting permission
    private void requestWriteStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                boolean acknowledgement = SqliteBackupManager.importDB(BirthdaysListActivity.this);
//
//                if (acknowledgement) {
//
//                    mListBirthdayBeans.clear();
//                    setRecyclerViewData();
//                    setTodaysRecyclerViewData();
//                    Toast.makeText(this, "Called", Toast.LENGTH_SHORT).show();
//                }
                showRestoreNotifyDialog();

            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Restore failed", Toast.LENGTH_LONG).show();
            }
        }

        //Checking the request code of our request
        if (requestCode == WRITE_STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                SqliteBackupManager.exportDB(BirthdaysListActivity.this);
                showBackupNotifyDialog();

            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Backup failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showBackupNotifyDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BirthdaysListActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Backup data");

        // Setting Dialog Message
        alertDialog.setMessage("The current data will replace the last backup data");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                backupData();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });
        alertDialog.setCancelable(false);

        // Showing Alert Message
        alertDialog.show();
    }

    private void showRestoreNotifyDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BirthdaysListActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Restore data?");

        // Setting Dialog Message
        alertDialog.setMessage("All the current data will be replaced by the last backup data");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                restoreData();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        alertDialog.setCancelable(false);

        // Showing Alert Message
        alertDialog.show();
    }

    private void showAutostartDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BirthdaysListActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Enable Autostart");

        // Setting Dialog Message
        alertDialog.setMessage("In order to receive notifications you need to enable \"Autostart\" else you won't be able to receive " +
                "notifications.You can always enable Autostart in " +
                "Settings > Installed apps > Permissions > Autostart.");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                gotoAutostartActivity();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        alertDialog.setCancelable(false);

        // Showing Alert Message
        alertDialog.show();
    }

    private void backupData() {
        SqliteBackupManager.exportDB(BirthdaysListActivity.this);
    }

    private void restoreData() {

        boolean acknowledgement = SqliteBackupManager.importDB(BirthdaysListActivity.this);

        if (acknowledgement) {

            mListBirthdayBeans.clear();
            setRecyclerViewData();
            setTodaysRecyclerViewData();
        }
    }

    private void gotoAutostartActivity() {
        if (MANUFACTURER.equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
            //this will open auto start screen where user can enable permission for your app
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            startActivity(intent);
            finish();
        }
    }

    public void gifts(View view) {
        Intent intent = new Intent(this, GiftRecomendation.class);
        startActivity(intent);
    }

    public void viewevents(View view) {
        Intent intent = new Intent(this, SmsSchedulerActivity.class);
        startActivity(intent);
    }

    public void familytree(View view) {
        //     Toast.makeText(this, "Family Tree", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, FamilyTree.class);
        startActivity(intent);
    }

    public void addevents(View view) {
        Toast.makeText(this, "Add Events", Toast.LENGTH_SHORT).show();
    }
}
