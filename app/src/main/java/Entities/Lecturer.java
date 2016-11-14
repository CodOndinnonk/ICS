package Entities;

public class Lecturer {
    public int mId;
    public String mPhoto;
    public String mSurname;
    public String mName;
    public String mPatronymic;
    public String mContacts;

    public Lecturer(int id, String Photo, String Surname, String Name, String Patronymic, String Contacts){
        this.mId = id;
        this.mPhoto = Photo;
        this.mSurname = Surname;
        this.mName = Name;
        this.mPatronymic = Patronymic;
        this.mContacts = Contacts;
    }

    public Lecturer(String Photo, String Surname, String Name, String Patronymic, String Contacts){
        this.mPhoto = Photo;
        this.mSurname = Surname;
        this.mName = Name;
        this.mPatronymic = Patronymic;
        this.mContacts = Contacts;
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
}
