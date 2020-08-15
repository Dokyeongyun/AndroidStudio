package com.example.studyandroid.No4_ContentProvider.WordOfToday2;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public interface WordOfTodayContract {

    //public static final String AUTHORITY = "com.example.studyandroid.No4_ContentProvider.WordOfToday2";
    public static final String AUTHORITY = "com.example.studyandroid.wordoftoday2";
    public static final String TABLE_NAME = "WordOfToday";
    public static Uri CONTENT_URI = Uri.parse(ContentResolver.SCHEME_CONTENT+"://"+AUTHORITY+"/"+TABLE_NAME);

    public static final String MIME_TYPE_DIR = "vnd.android.cursor.dir/" + AUTHORITY +"."+TABLE_NAME;
    public static final String MIME_TYPE_ITEM = "vnd.android.cursor.item/" + AUTHORITY +"."+TABLE_NAME;

    public interface WordOfTodayColumns extends BaseColumns{
        public static final String NAME = "name";
        public static final String WORDS = "words";
        public static final String DATE = "date";
    }
}
