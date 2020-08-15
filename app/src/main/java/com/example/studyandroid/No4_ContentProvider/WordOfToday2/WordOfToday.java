package com.example.studyandroid.No4_ContentProvider.WordOfToday2;

import android.os.Parcel;
import android.os.Parcelable;

public class WordOfToday implements Parcelable {

    long _id;
    String name;
    String words;
    int date;

    public WordOfToday() {
    }

    public WordOfToday(long id, String name, String words, int date) {
        this._id = id;
        this.name = name;
        this.words = words;
        this.date = date;
    }

    protected WordOfToday(Parcel in) {
        _id = in.readLong();
        name = in.readString();
        words = in.readString();
        date = in.readInt();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(name);
        dest.writeString(words);
        dest.writeInt(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WordOfToday> CREATOR = new Creator<WordOfToday>() {
        @Override
        public WordOfToday createFromParcel(Parcel in) {
            return new WordOfToday(in);
        }

        @Override
        public WordOfToday[] newArray(int size) {
            return new WordOfToday[size];
        }
    };

    @Override
    public String toString() {
        return "WordOfToday _id=" + _id + ", name=" + name + ", words=" + words + ", date=" + date;
    }
}
