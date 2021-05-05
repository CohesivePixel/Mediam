package com.example.ben_e.mediam.fragments;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ben_e.mediam.Manifest;
import com.example.ben_e.mediam.R;
import com.example.ben_e.mediam.database.LogbookContract;
import com.example.ben_e.mediam.database.LogbookDbHelper;
import com.example.ben_e.mediam.models.MediaItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by ben-e on 8-10-17.
 */

public class InputFragment extends Fragment {
    private FragmentActivity listener;
    private LogbookDbHelper mDbHelper;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private String strDate;

    private String mediaContent;
    private String startTijdContent;
    private String eindTijdContent;
    private String meningContent;

    private static final int MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 0;

    // When the fragment instance is associated with an activity
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof Activity)
        {
            this.listener = (FragmentActivity) context;
        }
    }

    // When the fragment is being created
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mDbHelper = new LogbookDbHelper(listener);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        strDate = sdf.format(new Date());
    }

    // When the fragment creates it's view objects
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_input, parent, false);
    }

    // When a view is setup, do view lookups here and attach viewListeners here
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

    }

    // When the fragment is no longer connected to the activity
    @Override
    public void onDetach()
    {
        super.onDetach();
        // prevent memory leaks, set everything created in onViewCreated to null here
    }

    // After onCreate has been completed, search for views by ID here
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        final Button sendBtn = listener.findViewById(R.id.send_btn);

        final EditText inputMedia = listener.findViewById(R.id.input_media);
        final EditText inputStartTijd = listener.findViewById(R.id.input_starttijd);
        final EditText inputEindTijd = listener.findViewById(R.id.input_eindtijd);
        final EditText inputMening = listener.findViewById(R.id.input_mening);

        inputStartTijd.setInputType(InputType.TYPE_NULL);
        inputStartTijd.setFocusable(false);

        inputEindTijd.setInputType(InputType.TYPE_NULL);
        inputEindTijd.setFocusable(false);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaContent = inputMedia.getText().toString();
                startTijdContent = inputStartTijd.getText().toString();
                eindTijdContent = inputEindTijd.getText().toString();
                meningContent = inputMening.getText().toString();

                String ID;

                Boolean greenLight = true;


                if (mediaContent.matches(""))
                {
                    System.out.println("Media Empty!");
                    inputMedia.setError("This field is empty");
                    greenLight = false;
                }

                if (startTijdContent.matches(""))
                {
                    System.out.println("StartTijd Empty!");
                    inputStartTijd.setError("This field is empty");
                    greenLight = false;
                }


                if (eindTijdContent.matches(""))
                {
                    System.out.println("EindTijd Empty!");
                    inputEindTijd.setError("THis field is empty");
                    greenLight = false;
                }


                if (meningContent.matches(""))
                {
                    System.out.println("Mening Empty!");
                    inputMening.setError("This field is empty");
                    greenLight = false;
                }

                if (greenLight)
                {

                    // This is what writes to FireBase
                    writeNewMediaItem(mediaContent, startTijdContent,
                            eindTijdContent, meningContent);

                    if (ContextCompat.checkSelfPermission(listener,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(listener,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                            // Show explanation in here

                        } else {

                            // Define wether permission was given or not and put the
                            // result of the decision in an int
                            ActivityCompat.requestPermissions(listener,
                                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);

                        }

                    }

                    // This is what writes to the local DB
                    writeToDb(mediaContent, startTijdContent, eindTijdContent, meningContent);

                    inputMedia.setText("");
                    inputStartTijd.setText("");
                    inputEindTijd.setText("");
                    inputMening.setText("");

                    Toast.makeText(listener, "Data added succesfully!", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    //System.out.println("Data not sent!");
                    Toast.makeText(listener, "You have to fill in all the forms",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        inputStartTijd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(listener, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        inputStartTijd.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true); //Yes 24 hour time
                mTimePicker.show();
            }
        });


        inputEindTijd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(listener, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        inputEindTijd.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true); //Yes 24 hour time
                mTimePicker.show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    writeToDb(mediaContent, startTijdContent, eindTijdContent, meningContent);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private void writeNewMediaItem(String mediaType, String startTijd, String eindTijd, String mening) {

        MediaItem mediam = new MediaItem(mediaType, strDate, startTijd, eindTijd, mening);

        // Insert data into firebase
        mDatabase.push().setValue(mediam);
    }


    private void writeToDb(String mediaContent, String startTijdContent,
                           String eindTijdContent, String meningContent){

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LogbookContract.LogbookEntry.COLUMN_NAME_MEDIA, mediaContent);
        values.put(LogbookContract.LogbookEntry.COLUMN_NAME_DATE, strDate);
        values.put(LogbookContract.LogbookEntry.COLUMN_NAME_STARTTIJD, startTijdContent);
        values.put(LogbookContract.LogbookEntry.COLUMN_NAME_EINDTIJD, eindTijdContent);
        values.put(LogbookContract.LogbookEntry.COLUMN_NAME_MENING, meningContent);

        long newRowId = db.insert(LogbookContract.LogbookEntry.TABLE_NAME, null, values);
        System.out.println(newRowId);
    }
}
