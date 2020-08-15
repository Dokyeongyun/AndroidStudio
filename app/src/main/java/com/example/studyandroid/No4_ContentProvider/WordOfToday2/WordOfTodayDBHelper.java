package com.example.studyandroid.No4_ContentProvider.WordOfToday2;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import static android.provider.BaseColumns._ID;
import static com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfTodayContract.TABLE_NAME;
import static com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfTodayContract.WordOfTodayColumns.NAME;
import static com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfTodayContract.WordOfTodayColumns.DATE;
import static com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfTodayContract.WordOfTodayColumns.WORDS;

public class WordOfTodayDBHelper extends SQLiteOpenHelper {

    private static final String TAG = WordOfToday.class.getSimpleName();
    public static final String DB_NAME = "WordOfToday.db";
    public static final int DB_VERSION = 1;

    private static final String SQL_CREATE_TABLE =
            String.format("CREATE TABLE %s (\n", TABLE_NAME) +
                    String.format("%s INTEGER PRIMARY KEY AUTOINCREMENT,\n", _ID) +
                    String.format("%s TEXT,\n", NAME) +
                    String.format("%s TEXT,\n", WORDS) +
                    String.format("%s TEXT);", DATE);

    private static final String[] SQL_INSERT_INITIAL_DATA = {
            String.format("INSERT INTO %s (%s, %s, %s)" + "VALUES('김김김', '오늘은 무엇을 할까?', '20200814')", TABLE_NAME, NAME, WORDS, DATE),
            String.format("INSERT INTO %s (%s, %s, %s)" + "VALUES('박박박', '안드로이드 스튜디오를 공부하자', '20200811')", TABLE_NAME, NAME, WORDS, DATE),
            String.format("INSERT INTO %s (%s, %s, %s)" + "VALUES('도도도', '알고리즘을 공부하자', '20200812')", TABLE_NAME, NAME, WORDS, DATE),
            String.format("INSERT INTO %s (%s, %s, %s)" + "VALUES('지지지', '취업을 위해 열심히 공부해야해', '20200810')", TABLE_NAME, NAME, WORDS, DATE),
            String.format("INSERT INTO %s (%s, %s, %s)" + "VALUES('이이이', '밥은 언제 먹을까?', '20200813')", TABLE_NAME, NAME, WORDS, DATE),
            String.format("INSERT INTO %s (%s, %s, %s)" + "VALUES('송송송', '정보처리기사 공부를 하자', '20200815')", TABLE_NAME, NAME, WORDS, DATE)
    };

    public WordOfTodayDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler){
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();

        try{
            execSQL(db, SQL_CREATE_TABLE);
            Log.i("CREATE_TABLE", "CREATE_TABLE");
            for( String sql : SQL_INSERT_INITIAL_DATA){
                execSQL(db, sql);
                Log.i("INSERT_DATA", "INSERT_DATA");
            }
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void execSQL(SQLiteDatabase db, String sql){
        db.execSQL(sql);
    }

}
