package com.example.vakery.ics.Domain.Entities;



public class Subject {
    public int mId;
    public String mShortTitle;
    public String mFullTitle;
    public int mLecturerId;

    public Subject(){}

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmShortTitle(String mShortTitle) {
        this.mShortTitle = mShortTitle;
    }

    public void setmFullTitle(String mFullTitle) {
        this.mFullTitle = mFullTitle;
    }

    public void setmLecturerId(int mLecturerId) {
        this.mLecturerId = mLecturerId;
    }

    public int getmId() {
        return mId;
    }

    public String getmShortTitle() {
        return mShortTitle;
    }

    public String getmFullTitle() {
        return mFullTitle;
    }

    public int getmLecturerId() {
        return mLecturerId;
    }
}
