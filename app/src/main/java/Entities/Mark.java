package Entities;


public class Mark {
    public int mId;
    public int mSubjectId;
    public String mSubjectName;
    public int mChapter1;
    public int mChapter2;

    public Mark(){
    }

    public Mark(int id, int subject, String subjectName, int Chapter1, int Chapter2) {
        this.mId = id;
        this.mSubjectId = subject;
        this.mSubjectName = mSubjectName;
        this.mChapter1 = Chapter1;
        this.mChapter2 = Chapter2;
    }

    public Mark(int subject, String subjectName, int Chapter1, int Chapter2) {
        this.mSubjectId = subject;
        this.mSubjectName = mSubjectName;
        this.mChapter1 = Chapter1;
        this.mChapter2 = Chapter2;
    }

    public int getmSubjectId() {
        return mSubjectId;
    }

    public void setmSubjectId(int mSubjectId) {
        this.mSubjectId = mSubjectId;
    }

    public String getmSubjectName() {
        return mSubjectName;
    }

    public void setmSubjectName(String mSubjectName) {
        this.mSubjectName = mSubjectName;
    }

    public int getmId() {
        return mId;
    }

    public int getmChapter1() {
        return mChapter1;
    }

    public int getmChapter2() {
        return mChapter2;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmChapter1(int mChapter1) {
        this.mChapter1 = mChapter1;
    }

    public void setmChapter2(int mChapter2) {
        this.mChapter2 = mChapter2;
    }
}
