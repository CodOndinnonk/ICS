package Entitys;


public class SubjectForList {
    public int mId;
    public String mShort_title;
    public String mFull_title;
    public String mLecturer;
    public String mType;
    public String mRoom;

    public SubjectForList(){}

    public SubjectForList(int id, String Short_title, String Full_title, String Lecturer, String type, String room) {
        this.mId = id;
        this.mShort_title = Short_title;
        this.mFull_title = Full_title;
        this.mLecturer = Lecturer;
        this.mType = type;
        this.mRoom = room;
    }

    public SubjectForList(String Short_title, String Full_title, String Lecturer, String type, String room) {
        this.mShort_title = Short_title;
        this.mFull_title = Full_title;
        this.mLecturer = Lecturer;
        this.mType = type;
        this.mRoom = room;
    }

    public int getmId() {
        return mId;
    }

    public String getmShort_title() {
        return mShort_title;
    }

    public String getmFull_title() {
        return mFull_title;
    }

    public String getmLecturer() {
        return mLecturer;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmShort_title(String mShort_title) {
        this.mShort_title = mShort_title;
    }

    public void setmFull_title(String mFull_title) {
        this.mFull_title = mFull_title;
    }

    public void setmLecturer(String mLecturer) {
        this.mLecturer = mLecturer;
    }

    public String getmRoom() {
        return mRoom;
    }

    public String getmType() {
        return mType;
    }

    public void setmRoom(String mRoom) {
        this.mRoom = mRoom;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }
}
