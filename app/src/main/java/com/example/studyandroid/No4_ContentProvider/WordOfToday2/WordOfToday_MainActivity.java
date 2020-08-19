package com.example.studyandroid.No4_ContentProvider.WordOfToday2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studyandroid.R;

import static android.provider.BaseColumns._ID;
import static com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfTodayContract.WordOfTodayColumns.DATE;
import static com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfTodayContract.WordOfTodayColumns.NAME;
import static com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfTodayContract.WordOfTodayColumns.WORDS;



/*

[ ContentProvider ]
 - ContentProvider 를 직접 작성하여 오늘의 한마디를 출력하는 기능을 구현한다.
----------------------------------------------------------------------

 1. ContentProvider 를 생성하고 추상메소드를 구현한다. -> ( WordOfTodayProvider.java )
  - onCreate() : 초기화
  - getType() : 매개변수로 전달된 URI 에 대응하는 MIME 타입 반환
  - insert() : 레코드 추가
  - query() : 레코드 검색, 취득
  - update() : 레코드 갱신
  - delete()  : 레코드 삭제
  - 위 추상메소드에서 DB 의 데이터 수정권한을 얻기 위해 SQLiteOpenHelper.getWritableDatabase() 메소드를 사용한다.
  - insert(), update() 의 매개변수 중 ContentValues 는 ContentProvider 에 데이터를 추가, 갱신 할 때 다루는 데이터 구조이다
        키와 값의 쌍으로 구성되고 내부적으로 HashMap<String, Object> 를 Wrapping 한 것이다.
        이 ContentValues 에서 값을 가져올 때에는 getAsInteger(), getAsString() 등의 메소드를 사용한다.
  - ContentProvider 의 데이터가 변경되었을 때 이 변화를 ContentObserver 에게 통지하는 notifyChange() 메소드를 활용한다.
  - ContentObserver 클래스를 사용하기 위해 onChange() 추상메소드를 구현한다.
 2. '오늘의 한마디' 앱에 필요한 DB 데이터 구조를 정의한다. -> ( WordOfToday.java )
  - _id INTEGER PRIMARY KEY AUTOINCREMENT,
  - name TEXT, words TEXT, date TEXT

 3. '오늘의 한마디' 앱이 다른 앱에 공개할 정보를 인터페이스로 구현하여 공개한다. -> ( WordOfTodayContract.java )
  - ContentProvider 에 접근하기 위한 URI -> CONTENT_URI
  - ContentProvider 에서 제공할 데이터 구조 -> WordOfTodayColumns
  - ContentProvider 에서 제공할 데이터의 MIME 타입

 4. CursorAdapter 에 DB의 데이터를 입력 후 listView 의 Adapter 로 지정하여 출력한다.

 */
public class WordOfToday_MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = WordOfToday_MainActivity.class.getSimpleName();
    private static final int LOADER_ID = 1;

    private static final String[] PROJECTIONS = new String[]{
            _ID, NAME, WORDS, DATE,
    };

    private CursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        if (BuildConfig.DEBUG) {
            Context context = getApplicationContext();

            Stetho.initializeWithDefaults(this);
           // initialize(newInitializerBuilder(context).enableDumpapp(defaultDumperPluginsProvider(context)).build());
        }*/

        setContentView(R.layout.activity_word_of_today__main);

        mAdapter = new CursorAdapter(getApplicationContext(), null, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return View.inflate(context, R.layout.word_of_today_list_item, null);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME));
                String words = cursor.getString(cursor.getColumnIndexOrThrow(WORDS));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DATE));

                TextView nameText = view.findViewById(R.id.nameText);
                TextView wordsText = view.findViewById(R.id.wordsText);
                TextView dateText = view.findViewById(R.id.dateText);

                date = date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6);

                nameText.setText(name);
                wordsText.setText(words);
                dateText.setText(date);
            }
        };
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
        //getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoaderManager.getInstance(this).restartLoader(LOADER_ID, null, this);
        //getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(WordOfToday_MainActivity.this, WordOfTodayContract.CONTENT_URI, PROJECTIONS, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(data!=null){
            data.setNotificationUri(getContentResolver(), WordOfTodayContract.CONTENT_URI);
            mAdapter.swapCursor(data);
            mAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(this, "Cursor가 null 입니다..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
