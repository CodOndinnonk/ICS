package com.example.vakery.ics.Entities;



public class Notification {
    public int mId;
    public int mSenderId;
    public String mContent;
    public int mISRead;

    public Notification(){}

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmSenderId(int mSenderId) {
        this.mSenderId = mSenderId;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public void setmISRead(int mISRead) {
        this.mISRead = mISRead;
    }

    public int getmId() {
        return mId;
    }

    public int getmSenderId() {
        return mSenderId;
    }

    public String getmContent() {
        return mContent;
    }

    public int getmISRead() {
        return mISRead;
    }
}
