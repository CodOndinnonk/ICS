package Entitys;


public class Subject {
    public int mId;
    public String mShort_title;
    public String mFull_title;
    public int mLecturer;

    public Subject(int id, String Short_title, String Full_title, int Lecturer) {
        this.mId = id;
        this.mShort_title = Short_title;
        this.mFull_title = Full_title;
        this.mLecturer = Lecturer;
    }

    public Subject(String Short_title, String Full_title, int Lecturer) {
        this.mShort_title = Short_title;
        this.mFull_title = Full_title;
        this.mLecturer = Lecturer;
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

    public int getmLecturer() {
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

    public void setmLecturer(int mLecturer) {
        this.mLecturer = mLecturer;
    }
}
