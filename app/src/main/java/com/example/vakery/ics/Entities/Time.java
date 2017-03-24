package com.example.vakery.ics.Entities;

public class Time {
    public int mId;
    public int mNumberOfSubject;
    public String mTimeStart;
    public String mTimeEnd;

    public Time(){}

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmNumberOfSubject(int mNumberOfSubject) {
        this.mNumberOfSubject = mNumberOfSubject;
    }

    public void setmTimeStart(String mTimeStart) {
        this.mTimeStart = mTimeStart;
    }

    public void setmTimeEnd(String mTimeEnd) {
        this.mTimeEnd = mTimeEnd;
    }

    public int getmId() {
        return mId;
    }

    public int getmNumberOfSubject() {
        return mNumberOfSubject;
    }

    public String getmTimeStart() {
        return mTimeStart;
    }

    public String getmTimeEnd() {
        return mTimeEnd;
    }
}
