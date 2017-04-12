package com.example.vakery.ics.Entities;


public class Schedule {
    //на веб части называется Week
    public int mId;
    public int mKindOfWeek;
    public int mDayOfWeek;
    public int mNumberOfSubject;
    public int mSubjectId;
    public String mTypeOfSubject;
    public String mRoomNumber;
    public int mGroupId;

    public Schedule(){}

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmKindOfWeek(int mKindOdWeek) {
        this.mKindOfWeek = mKindOdWeek;
    }

    public void setmDayOfWeek(int mDayOfWeek) {
        this.mDayOfWeek = mDayOfWeek;
    }

    public void setmNumberOfSubject(int mNumberOfSubject) {
        this.mNumberOfSubject = mNumberOfSubject;
    }

    public void setmSubjectId(int mSubjectId) {
        this.mSubjectId = mSubjectId;
    }

    public void setmTypeOfSubject(String mTypeOfSubject) {
        this.mTypeOfSubject = mTypeOfSubject;
    }

    public void setmRoomNumber(String mRoomNumber) {
        this.mRoomNumber = mRoomNumber;
    }

    public void setmGroupId(int mGroupId) {
        this.mGroupId = mGroupId;
    }

    public int getmId() {
        return mId;
    }

    public int getmKindOfWeek() {
        return mKindOfWeek;
    }

    public int getmDayOfWeek() {
        return mDayOfWeek;
    }

    public int getmNumberOfSubject() {
        return mNumberOfSubject;
    }

    public int getmSubjectId() {
        return mSubjectId;
    }

    public String getmTypeOfSubject() {
        return mTypeOfSubject;
    }

    public String getmRoomNumber() {
        return mRoomNumber;
    }

    public int getmGroupId() {
        return mGroupId;
    }
}
