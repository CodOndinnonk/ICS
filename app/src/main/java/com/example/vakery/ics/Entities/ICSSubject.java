package com.example.vakery.ics.Entities;


public class ICSSubject {
    public int mid;
    public String mTitle;
    public int mLecturerId;
    public String mSemesters;
    public String mKind;
    public String mExtraInfo;

    public ICSSubject() {
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmLecturerId(int mLecturerId) {
        this.mLecturerId = mLecturerId;
    }

    public void setmSemesters(String mSemesters) {
        this.mSemesters = mSemesters;
    }

    public void setmKind(String mKind) {
        this.mKind = mKind;
    }

    public void setmExtraInfo(String mExtraInfo) {
        this.mExtraInfo = mExtraInfo;
    }

    public int getMid() {
        return mid;
    }

    public String getmTitle() {
        return mTitle;
    }

    public int getmLecturerId() {
        return mLecturerId;
    }

    public String getmSemesters() {
        return mSemesters;
    }

    public String getmKind() {
        return mKind;
    }

    public String getmExtraInfo() {
        return mExtraInfo;
    }
}
