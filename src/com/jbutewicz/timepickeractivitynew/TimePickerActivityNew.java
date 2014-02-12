package com.jbutewicz.timepickeractivitynew;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimePickerActivityNew extends Activity implements TimePickerDialog.OnTimeSetListener {

    private static final String ARG_HOUR = "hour";
    private static final String ARG_MINUTE = "minute";

    public static class TimePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Get arguments passed by the calling activity (hour and minute)
            Bundle args = getArguments();
            final Calendar c = Calendar.getInstance();
            int hour = args.getInt(ARG_HOUR, c.get(Calendar.HOUR_OF_DAY));
            int minute = args.getInt(ARG_MINUTE, c.get(Calendar.MINUTE));

            // Create a new instance of TimePickerDialog and return it
            // (the listener of the time picker events is the calling activity)
            return new TimePickerDialog(getActivity(),
                    (TimePickerDialog.OnTimeSetListener) getActivity(),
                    hour, minute, DateFormat.is24HourFormat(getActivity()));
        }


    }

    private TextView mTimeDisplay;
    private Button mPickTime;

    private int mHour;
    private int mMinute;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // capture our View elements
        mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
        mPickTime = (Button) findViewById(R.id.pickTime);

        // add a click listener to the button
        mPickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                Bundle args = new Bundle();
                args.putInt(ARG_HOUR, mHour);
                args.putInt(ARG_MINUTE, mMinute);
                timePicker.setArguments(args);
                timePicker.show(getFragmentManager(), "timePicker");
            }
        });

        // get the current time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // display the current date
        updateDisplay();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        updateDisplay();
    }

    // updates the time we display in the TextView
    private void updateDisplay() {
        mTimeDisplay.setText(new StringBuilder().append(pad(mHour)).append(":")
                .append(pad(mMinute)));
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

}