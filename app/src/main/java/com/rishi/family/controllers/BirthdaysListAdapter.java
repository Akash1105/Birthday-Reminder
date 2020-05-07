package com.rishi.family.controllers;


import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rishi.family.R;
import com.rishi.family.listeners.RecyclerViewClickListener;
import com.rishi.family.model.BirthdayBeans;
import com.rishi.family.utilities.TodaysBirthdayManager;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class BirthdaysListAdapter extends RecyclerView.Adapter<BirthdaysListAdapter.MyViewHolder> {

    //Variables
    private int mYear, mMonth, mDayOfMonth;
    private List<BirthdayBeans> mListBirthdayBeans;

    //Objects
    private Calendar mObjectCalender;

    //Listeners
    private RecyclerViewClickListener mRecyclerViewClickListener = null;

    public BirthdaysListAdapter(List<BirthdayBeans> listBirthdayBeans) {
        this.mListBirthdayBeans = listBirthdayBeans;
    }

    public void setClickListener(RecyclerViewClickListener clicklistener) {
        this.mRecyclerViewClickListener = clicklistener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.birthdayslist_recyclerview_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BirthdayBeans objectBirthdayBeans = mListBirthdayBeans.get(position);
        if (objectBirthdayBeans.getmByteArrayImage().length != 0) {
//            holder.mImageViewEntryPicture.setImageBitmap(ImageUtils.getImage(objectBirthdayBeans.getmByteArrayImage()));
            holder.mTextViewCircularIcon.setText(objectBirthdayBeans.getmName().substring(0, 1).toUpperCase());

        } else {
//            holder.mImageViewEntryPicture.setBackgroundResource(R.mipmap.ic_launcher);
            holder.mTextViewCircularIcon.setText(objectBirthdayBeans.getmName().substring(0, 1).toUpperCase());

        }
        holder.mTextViewEntryName.setText(objectBirthdayBeans.getmName());
        holder.mTextViewEntryBirthday.setText(getDate(objectBirthdayBeans));
        if (TodaysBirthdayManager.ifIsBirthdayToday(objectBirthdayBeans)) {
            holder.mImageViewBirthdayLogo.setImageResource(R.mipmap.ic_launcher);
        }
        if (TodaysBirthdayManager.getExactAge(objectBirthdayBeans) < 1) {
            holder.mTextViewEntryAge.setText("Less than 1 year old");
        } else if (TodaysBirthdayManager.getExactAge(objectBirthdayBeans) == 1) {
            holder.mTextViewEntryAge.setText(TodaysBirthdayManager.getExactAge(objectBirthdayBeans) + " year old");

        } else {
            holder.mTextViewEntryAge.setText(TodaysBirthdayManager.getExactAge(objectBirthdayBeans) + " years old");

        }

    }

    @Override
    public int getItemCount() {
        return mListBirthdayBeans.size();
    }

    private String getDate(BirthdayBeans objectBirthdayBeans) {
        /*Creating the date which has to be displayed*/
        //Storing the values in class variables
        mYear = objectBirthdayBeans.getmYear();
        mMonth = objectBirthdayBeans.getmMonth();
        mDayOfMonth = objectBirthdayBeans.getmDayOfMonth();

        mObjectCalender = Calendar.getInstance();
        //Setting the calendar object's date to the date selected by the user
        mObjectCalender.set(Calendar.YEAR, mYear);
        mObjectCalender.set(Calendar.MONTH, mMonth);
        mObjectCalender.set(Calendar.DAY_OF_MONTH, mDayOfMonth);

        return "" + DateFormat.getDateInstance().format(mObjectCalender.getTime());

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageViewEntryPicture, mImageViewBirthdayLogo;
        public TextView mTextViewCircularIcon, mTextViewEntryName, mTextViewEntryBirthday, mTextViewEntryAge;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

//            mImageViewEntryPicture = (ImageView) view.findViewById(R.id.imageview_entrypicture);
            mTextViewCircularIcon = (TextView) view.findViewById(R.id.textview_circular_picture);
            mTextViewEntryName = (TextView) view.findViewById(R.id.textview_entryname);
            mTextViewEntryBirthday = (TextView) view.findViewById(R.id.textview_entrybirthday);
            mImageViewBirthdayLogo = (ImageView) view.findViewById(R.id.imageview_birthdaylogo);
            mTextViewEntryAge = (TextView) view.findViewById(R.id.textview_entryage);
        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewClickListener != null) {
                mRecyclerViewClickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }


}