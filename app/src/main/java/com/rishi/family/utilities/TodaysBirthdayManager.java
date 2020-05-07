package com.rishi.family.utilities;

import android.util.Log;

import com.rishi.family.model.BirthdayBeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TodaysBirthdayManager {

    //Variables
    private static int mStoredYear, mStoredMonth, mStoredDayOfMonth, mCurrentYear, mCurrentMonth, mCurrentDayOfMonth;
    private static List<BirthdayBeans> mListTodaysBirthdayBeans;

    //Objects
    private static Calendar mObjectCalendarStoredDate, mObjectCalendarTodaysDate;


    public static List<BirthdayBeans> getTodaysBirthdaysList(List<BirthdayBeans> mListBirthdayBeans) {
        instantiateObjects();

        for (int i = 0; i < mListBirthdayBeans.size(); i++) {

            mStoredYear = mListBirthdayBeans.get(i).getmYear();
            mStoredMonth = mListBirthdayBeans.get(i).getmMonth();
            mStoredDayOfMonth = mListBirthdayBeans.get(i).getmDayOfMonth();


            mObjectCalendarStoredDate.set(Calendar.YEAR, mStoredYear);
            mObjectCalendarStoredDate.set(Calendar.MONTH, mStoredMonth);
            mObjectCalendarStoredDate.set(Calendar.DAY_OF_MONTH, mStoredDayOfMonth);

            if (mObjectCalendarTodaysDate.get(Calendar.DAY_OF_MONTH) == mObjectCalendarStoredDate.get(Calendar.DAY_OF_MONTH)) {

                mListTodaysBirthdayBeans.add(mListBirthdayBeans.get(i));
            }

        }
        return mListTodaysBirthdayBeans;
    }

    public static int getCountOfTodaysBirthdays(List<BirthdayBeans> mListBirthdayBeans) {

        instantiateObjects();

        for (int i = 0; i < mListBirthdayBeans.size(); i++) {

            mStoredYear = mListBirthdayBeans.get(i).getmYear();
            mStoredMonth = mListBirthdayBeans.get(i).getmMonth();
            mStoredDayOfMonth = mListBirthdayBeans.get(i).getmDayOfMonth();


            mObjectCalendarStoredDate.set(Calendar.YEAR, mStoredYear);
            mObjectCalendarStoredDate.set(Calendar.MONTH, mStoredMonth);
            mObjectCalendarStoredDate.set(Calendar.DAY_OF_MONTH, mStoredDayOfMonth);

            if (mObjectCalendarTodaysDate.get(Calendar.DAY_OF_MONTH) == mObjectCalendarStoredDate.get(Calendar.DAY_OF_MONTH)) {

                mListTodaysBirthdayBeans.add(mListBirthdayBeans.get(i));
            }

        }
        return mListTodaysBirthdayBeans.size();
    }

    public static boolean ifIsBirthdayToday(BirthdayBeans objectBirthdayBeans) {

        boolean isBirthdayToday = false;

        instantiateObjects();
        mStoredYear = objectBirthdayBeans.getmYear();
        mStoredMonth = objectBirthdayBeans.getmMonth();
        mStoredDayOfMonth = objectBirthdayBeans.getmDayOfMonth();


        mObjectCalendarStoredDate.set(Calendar.YEAR, mStoredYear);
        mObjectCalendarStoredDate.set(Calendar.MONTH, mStoredMonth);
        mObjectCalendarStoredDate.set(Calendar.DAY_OF_MONTH, mStoredDayOfMonth);

        //mObjectCalendarTodaysDate.equals(mObjectCalendarStoredDate)
        isBirthdayToday = mObjectCalendarTodaysDate.get(Calendar.DAY_OF_MONTH) == mObjectCalendarStoredDate.get(Calendar.DAY_OF_MONTH);

        return isBirthdayToday;
    }

    public static int getAge(BirthdayBeans objectBirthdayBeans) {

        int age;

        instantiateObjects();
        mStoredYear = objectBirthdayBeans.getmYear();
        mStoredMonth = objectBirthdayBeans.getmMonth();
        mStoredDayOfMonth = objectBirthdayBeans.getmDayOfMonth();


        mCurrentYear = mObjectCalendarTodaysDate.get(Calendar.YEAR);
        mCurrentMonth = 1 + mObjectCalendarTodaysDate.get(Calendar.MONTH);
        mCurrentDayOfMonth = mObjectCalendarTodaysDate.get(Calendar.DAY_OF_MONTH);


        age = mCurrentYear - mStoredYear;

        if (mStoredMonth == (mCurrentMonth + 1) && mStoredDayOfMonth > mCurrentDayOfMonth) {
            age--;
        }

        return age;

    }

    public static int getExactAge(BirthdayBeans objectBirthdayBeans) {

        int age;

        instantiateObjects();
        mStoredYear = objectBirthdayBeans.getmYear();
        mStoredMonth = objectBirthdayBeans.getmMonth();
        mStoredDayOfMonth = objectBirthdayBeans.getmDayOfMonth();


        mCurrentYear = mObjectCalendarTodaysDate.get(Calendar.YEAR);
        mCurrentMonth = mObjectCalendarTodaysDate.get(Calendar.MONTH);
        mCurrentDayOfMonth = mObjectCalendarTodaysDate.get(Calendar.DAY_OF_MONTH);

        age = mCurrentYear - mStoredYear;

        if (mStoredMonth == mCurrentMonth && mStoredDayOfMonth > mCurrentDayOfMonth) {
            age--;
        }
        if (mStoredMonth > mCurrentMonth) {
            age--;
        }

        return age;

    }

    public static void getDaysRemaining(BirthdayBeans objectBirthdayBeans) {

        instantiateObjects();
        mStoredYear = objectBirthdayBeans.getmYear();
        mStoredMonth = objectBirthdayBeans.getmMonth();
        mStoredDayOfMonth = objectBirthdayBeans.getmDayOfMonth();

        final Calendar c = Calendar.getInstance();
        c.set(mStoredYear, mStoredMonth, mStoredDayOfMonth);  //this is birthdate. set it to date you get from date picker
        // c.add(Calendar.DATE, value);
        final Calendar today = Calendar.getInstance();
        // Take your DOB Month and compare it to current
        // month
        final int BMonth = c.get(Calendar.MONTH);
        final int CMonth = today.get(Calendar.MONTH);

        final int BDate = c.get(Calendar.DAY_OF_MONTH);
        final int CDate = today.get(Calendar.DAY_OF_MONTH);

        c.set(Calendar.YEAR, today.get(Calendar.YEAR));
        c.set(Calendar.DAY_OF_WEEK,
                today.get(Calendar.DAY_OF_WEEK));
        if (BMonth < CMonth) {
            c.set(Calendar.YEAR,
                    today.get(Calendar.YEAR) + 1);
        }
        //added this condition so that it doesn't add in case birthdate is greater than current date . i.e. yet to come in this month.
        else if (BMonth == CMonth) {
            if (BDate < CDate) {
                c.set(Calendar.YEAR,
                        today.get(Calendar.YEAR) + 1);
            }
            if (BDate > CDate) {
                c.set(Calendar.YEAR,
                        today.get(Calendar.YEAR) - 1);
            }
        }
        // Result in millis
        final long millis = c.getTimeInMillis()
                - today.getTimeInMillis();
        // Convert to days
        final long days = millis / 86400000;
    }

    static void instantiateObjects() {
        mObjectCalendarTodaysDate = Calendar.getInstance();
        mObjectCalendarStoredDate = Calendar.getInstance();
        mListTodaysBirthdayBeans = new ArrayList<BirthdayBeans>();
    }

    public static void calculateAge(BirthdayBeans objectBirthdayBeans) {
        int years = 0;
        int months = 0;
        int days = 0;

        mStoredYear = objectBirthdayBeans.getmYear();
        mStoredMonth = objectBirthdayBeans.getmMonth();
        mStoredDayOfMonth = objectBirthdayBeans.getmDayOfMonth();

        final Calendar c = Calendar.getInstance();
        c.set(mStoredYear, mStoredMonth, mStoredDayOfMonth);  //this is birthdate. set it to date you get from date picker
        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(c.getTimeInMillis());
        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        //Get difference between months
        months = currMonth - birthMonth;
        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }
        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }
        Log.e("Years", "" + years);
        Log.e("Months", "" + months);
        Log.e("Days", "" + days);


    }
}
