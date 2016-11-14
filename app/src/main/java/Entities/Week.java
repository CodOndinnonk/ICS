package Entities;

public class Week {
    public int mId;
    public int mKind_of_week;
    public int mDay_of_week;
    public int mId_of_subject;
    public String mType_of_subject;
    public String mRoom_number;

    public Week(int id, int Kind_of_week, int Day_of_week, int Id_of_subject, String Type_of_subject, String Room_number) {
        this.mId = id;
        this.mKind_of_week = Kind_of_week;
        this.mDay_of_week = Day_of_week;
        this.mId_of_subject = Id_of_subject;
        this.mType_of_subject = Type_of_subject;
        this.mRoom_number = Room_number;
    }

    public Week(int Kind_of_week, int Day_of_week, int Id_of_subject, String Type_of_subject, String Room_number) {
        this.mKind_of_week = Kind_of_week;
        this.mDay_of_week = Day_of_week;
        this.mId_of_subject = Id_of_subject;
        this.mType_of_subject = Type_of_subject;
        this.mRoom_number = Room_number;
    }

    public int getmId() {
        return mId;
    }

    public int getmKind_of_week() {
        return mKind_of_week;
    }

    public int getmDay_of_week() {
        return mDay_of_week;
    }

    public int getmId_of_subject() {
        return mId_of_subject;
    }

    public String getmType_of_subject() {
        return mType_of_subject;
    }

    public String getmRoom_number() {
        return mRoom_number;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmDay_of_week(int mDay_of_week) {
        this.mDay_of_week = mDay_of_week;
    }

    public void setmId_of_subject(int mId_of_subject) {
        this.mId_of_subject = mId_of_subject;
    }

    public void setmKind_of_week(int mKind_of_week) {
        this.mKind_of_week = mKind_of_week;
    }

    public void setmRoom_number(String mRoom_number) {
        this.mRoom_number = mRoom_number;
    }

    public void setmType_of_subject(String mType_of_subject) {
        this.mType_of_subject = mType_of_subject;
    }

}
