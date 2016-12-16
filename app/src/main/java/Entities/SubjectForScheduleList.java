package Entities;


public class SubjectForScheduleList {
    public int mId;
    public int mNumberOfSubject;
    public String mType;
    public String mRoom;
    public int mSubjectId;
    public String mShortTitle;
    public int mLecturerId;
    public String mSurname;
    public String mName;
    public String mPatronymic;

    public SubjectForScheduleList() {
    }

    public SubjectForScheduleList(int id, int NSubject, String type, String room, int subjectId, String shortTitle, int lecturerId,
                                  String surname, String name, String patronymic) {
        this.mId = id;
        this.mNumberOfSubject = NSubject;
        this.mType = type;
        this.mRoom = room;
        this.mSubjectId = subjectId;
        this.mShortTitle = shortTitle;
        this.mLecturerId = lecturerId;
        this.mSurname = surname;
        this.mName = name;
        this.mPatronymic = patronymic;
    }

    public SubjectForScheduleList(int NSubject, String type, String room, int subjectId, String shortTitle, int lecturerId,
                                  String surname, String name, String patronymic) {
        this.mNumberOfSubject = NSubject;
        this.mType = type;
        this.mRoom = room;
        this.mSubjectId = subjectId;
        this.mShortTitle = shortTitle;
        this.mLecturerId = lecturerId;
        this.mSurname = surname;
        this.mName = name;
        this.mPatronymic = patronymic;
    }

    public SubjectForScheduleList(int NSubject, String shortTitle) {
        this.mNumberOfSubject = NSubject;
        this.mShortTitle = shortTitle;
    }

    public int getmNumberOfSubject() {
        return mNumberOfSubject;
    }

    public int getmId() {
        return mId;
    }

    public int getmLecturerId() {
        return mLecturerId;
    }

    public int getmSubjectId() {
        return mSubjectId;
    }

    public String getmName() {
        return mName;
    }

    public String getmPatronymic() {
        return mPatronymic;
    }

    public String getmRoom() {
        return mRoom;
    }

    public String getmShortTitle() {
        return mShortTitle;
    }

    public String getmSurname() {
        return mSurname;
    }

    public String getmType() {
        return mType;
    }

    public void setmNumberOfSubject(int mNumberOfSubject) {
        this.mNumberOfSubject = mNumberOfSubject;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmLecturerId(int mLecturerId) {
        this.mLecturerId = mLecturerId;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmPatronymic(String mPatronymic) {
        this.mPatronymic = mPatronymic;
    }

    public void setmRoom(String mRoom) {
        this.mRoom = mRoom;
    }

    public void setmShortTitle(String mShortTitle) {

        this.mShortTitle = mShortTitle;
    }

    public void setmSubjectId(int mSubjectId) {
        this.mSubjectId = mSubjectId;
    }

    public void setmSurname(String mSurname) {
        this.mSurname = mSurname;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }
}