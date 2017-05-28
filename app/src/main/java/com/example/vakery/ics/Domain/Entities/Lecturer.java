package com.example.vakery.ics.Domain.Entities;

public class Lecturer {
    public int mId;
    public String mPhoto;
    public String mSurname;
    public String mName;
    public String mPatronymic;
    public String mContacts;
    public int mIsICS;


    public Lecturer(){
    }


    public Lecturer(int id, String Photo) {
        this.mId = id;
        this.mPhoto = Photo;
    }


    public int getmId() {
        return mId;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public String getmSurname() {
        return mSurname;
    }

    public String getmName() {
        return mName;
    }

    public String getmPatronymic() {
        return mPatronymic;
    }

    public String getmContacts() {
        return mContacts;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public void setmSurname(String mSurname) {
        this.mSurname = mSurname;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmPatronymic(String mPatronymic) {
        this.mPatronymic = mPatronymic;
    }

    public void setmContacts(String mContacts) {
        this.mContacts = mContacts;
    }

    public int getmIsICS() {
        return mIsICS;
    }

    public void setmIsICS(int mIsICS) {
        this.mIsICS = mIsICS;
    }
}
