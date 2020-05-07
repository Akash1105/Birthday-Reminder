package com.rishi.family.model;


public class BirthdayBeans {

    private int mId;
    private byte[] mByteArrayImage;
    private String mName;
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;


    public BirthdayBeans() {

    }

    public BirthdayBeans(int mId, byte[] mByteArrayImage, String mName, int mYear, int mMonth, int mDayOfMonth) {
        this.mId = mId;
        this.mByteArrayImage = mByteArrayImage;
        this.mName = mName;
        this.mYear = mYear;
        this.mMonth = mMonth;
        this.mDayOfMonth = mDayOfMonth;
    }

    public BirthdayBeans(byte[] mByteArrayImage, String mName, int mYear, int mMonth, int mDayOfMonth) {
        this.mByteArrayImage = mByteArrayImage;
        this.mName = mName;
        this.mYear = mYear;
        this.mMonth = mMonth;
        this.mDayOfMonth = mDayOfMonth;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public byte[] getmByteArrayImage() {
        return mByteArrayImage;
    }

    public void setmByteArrayImage(byte[] mByteArrayImage) {
        this.mByteArrayImage = mByteArrayImage;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public int getmMonth() {
        return mMonth;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public int getmDayOfMonth() {
        return mDayOfMonth;
    }

    public void setmDayOfMonth(int mDayOfMonth) {
        this.mDayOfMonth = mDayOfMonth;
    }
}
