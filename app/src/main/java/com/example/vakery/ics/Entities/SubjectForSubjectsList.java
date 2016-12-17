package com.example.vakery.ics.Entities;


public class SubjectForSubjectsList {
    public int mId;
    public String mTitle;
    public String mType;
    public int mLecturerId;
    public String mSemesters;
    public String mSurname;
    public String mName;
    public String mPatronymic;
    public String mInfo;


    public SubjectForSubjectsList() {
    }


    public String getmInfo() {
        return mInfo;
    }

    public void setmInfo(String mInfo) {
        this.mInfo = mInfo;
    }

    public String getmSemesters() {
        return mSemesters;
    }

    public void setmSemesters(String mSemesters) {
        this.mSemesters = mSemesters;
    }

    public int getmId() {
        return mId;
    }

    public int getmLecturerId() {
        return mLecturerId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public void setmLecturerId(int mLecturerId) {
        this.mLecturerId = mLecturerId;
    }

    public String getmSurname() {
        return mSurname;
    }

    public void setmSurname(String mSurname) {
        this.mSurname = mSurname;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPatronymic() {
        return mPatronymic;
    }

    public void setmPatronymic(String mPatronymic) {
        this.mPatronymic = mPatronymic;
    }
}