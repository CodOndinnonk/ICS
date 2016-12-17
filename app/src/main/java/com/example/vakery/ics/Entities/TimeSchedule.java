package com.example.vakery.ics.Entities;


public class TimeSchedule {
    public int mId;
    public int mSubjectNumber;
    public String mStart;
    public String mFinish;


    public TimeSchedule(){}


    public void setmSubjectNumber(int mSubjectNumber) {
        this.mSubjectNumber = mSubjectNumber;
    }

    public int getmSubjectNumber() {
        return mSubjectNumber;
    }

    public int getmId() {
        return mId;
    }

    public void setmStart(String mStart) {
        this.mStart = mStart;
    }

    public void setmFinish(String mFinish) {
        this.mFinish = mFinish;
    }

    public String getmFinish() {
        return mFinish;
    }

    public String getmStart() {
        return mStart;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }


}
