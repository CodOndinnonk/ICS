package Entitys;


public class Marks {
    public int mId;
    public int mChapter1;
    public int mChapter2;

    public Marks(int id, int Chapter1, int Chapter2) {
        this.mId = id;
        this.mChapter1 = Chapter1;
        this.mChapter2 = Chapter2;
    }

    public Marks(int Chapter1, int Chapter2) {
        this.mChapter1 = Chapter1;
        this.mChapter2 = Chapter2;
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
