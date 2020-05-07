package com.rishi.family.views.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment {

    //VAariables
    int currentYear, currentMonth, currentDay;

    //Objects
    Calendar objectCalendar;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Getting Calendar Object
        objectCalendar = Calendar.getInstance();

        //Getting the current Year,Month,Date
        currentYear = objectCalendar.get(Calendar.YEAR);
        currentMonth = objectCalendar.get(Calendar.MONTH);
        currentDay = objectCalendar.get(Calendar.DAY_OF_MONTH);

        //Returning the DatePickerDialog with all the required values set after the date is selected by the user
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), currentYear, currentMonth, currentDay);
    }
}
