package com.example.studyandroid.No4_ContentProvider.WordOfToday2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static android.provider.BaseColumns._ID;
import static com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfTodayContract.AUTHORITY;
import static com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfTodayContract.MIME_TYPE_DIR;
import static com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfTodayContract.MIME_TYPE_ITEM;
import static com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfTodayContract.TABLE_NAME;
import static com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfTodayDBHelper.DB_NAME;
import static com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfTodayDBHelper.DB_VERSION;

/*
 구현해야할 추상메소드 6개를 직접 작성한다.
 */
public class WordOfTodayProvider extends ContentProvider {

    private static final String TAG = WordOfToday.class.getSimpleName();
    private static final UriMatcher sUriMatcher;

    private static final int ROW_DIR = 1;
    private static final int ROW_ITEM = 2;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, ROW_DIR);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", ROW_ITEM);
    }

    private WordOfTodayDBHelper mDBHelper;

    public WordOfTodayProvider() {
    }

    @Override
    public boolean onCreate() {

        // mDBHelper 초기화
        mDBHelper = new WordOfTodayDBHelper(getContext(), DB_NAME, null, DB_VERSION, new DatabaseErrorHandler() {
            @Override
            public void onCorruption(SQLiteDatabase dbObj) {
                String path = dbObj.getPath();
                getContext().deleteFile(path);
            }
        });
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;
        switch (sUriMatcher.match(uri)) {
            // ROW_DIR : 매칭 결과가 여러 개
            // ROW_ITEM : 매칭 결과가 1개 -> 특정 ID
            case ROW_DIR:
                synchronized (mDBHelper) {
                    SQLiteDatabase db = mDBHelper.getWritableDatabase();
                    cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                }
                return cursor;
            case ROW_ITEM:
                synchronized (mDBHelper) {
                    long id = ContentUris.parseId(uri);
                    SQLiteDatabase db = mDBHelper.getWritableDatabase();
                    cursor = db.query(TABLE_NAME, projection, _ID, new String[]{Long.toString(id)}, null, null, null);
                }
                break;
            default:
                throw new IllegalArgumentException("URI를 확인해주세요.");
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri resultUri = null;
        switch (sUriMatcher.match(uri)) {
            case ROW_DIR:
                synchronized (mDBHelper) {
                    SQLiteDatabase db = mDBHelper.getWritableDatabase();

                    // insert() 의 결과 값은 삽입이 성공적이면 마지막 row ID, 실패했다면 -1 이다.
                    long lastId = db.insert(TABLE_NAME, null, values);
                    resultUri = ContentUris.withAppendedId(uri, lastId);

                    // 변경 통지
                    getContext().getContentResolver().notifyChange(resultUri, null);
                }
                break;
            default:
                throw new IllegalArgumentException("URI를 확인해주세요.");
        }
        return resultUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (sUriMatcher.match(uri)) {
            case ROW_ITEM:
                int id = (int) ContentUris.parseId(uri);
                synchronized (mDBHelper) {
                    SQLiteDatabase db = mDBHelper.getWritableDatabase();
                    count = db.update(TABLE_NAME, values, _ID + "=?", new String[]{Long.toString(id)});

                    if (count > 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                }
                break;
            case ROW_DIR:
                synchronized (mDBHelper) {
                    SQLiteDatabase db = mDBHelper.getWritableDatabase();
                    count = db.update(TABLE_NAME, values, selection, selectionArgs);

                    if (count > 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                }
            default:
                throw new IllegalArgumentException("URI를 확인해주세요.");
        }
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (sUriMatcher.match(uri)) {
            case ROW_ITEM:
                int id = (int) ContentUris.parseId(uri);
                synchronized (mDBHelper) {
                    SQLiteDatabase db = mDBHelper.getWritableDatabase();
                    count = db.delete(TABLE_NAME, _ID + "=?", new String[]{Long.toString(id)});
                    if (count > 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                }
                break;
            case ROW_DIR:
                SQLiteDatabase db = mDBHelper.getWritableDatabase();
                count = db.delete(TABLE_NAME, selection, selectionArgs);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
            default:
                throw new IllegalArgumentException("URI를 확인해주세요.");
        }
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case ROW_DIR:
                return MIME_TYPE_DIR;
            case ROW_ITEM:
                return MIME_TYPE_ITEM;
            default:
                return null;
        }

    }

}
