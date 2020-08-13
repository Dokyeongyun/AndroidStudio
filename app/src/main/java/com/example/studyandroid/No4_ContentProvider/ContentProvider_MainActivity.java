package com.example.studyandroid.No4_ContentProvider;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// MediaStore 에는 Image, Audio, Video 등의 인터페이스가 오버라이드 되어 있어 static 임포트를 통해 코드를 간결하게 한다.
import static android.provider.MediaStore.Images.ImageColumns;
import static android.provider.MediaStore.Images.Media;

import com.example.studyandroid.R;

import java.io.IOException;
import java.util.Calendar;

/*

[ ContentProvider ]

 : 앱 사이에서 각종 데이터를 공유할 수 있게 해주는 컴포넌트.
  - 예) 안드로이드 표준 시스템의 연락처, 이미지, 동영상 등의 데이터를 보관하는 MediaStore 등
  - 데이터를 검색, 추가, 갱신, 삭제할 수 있고 SQLite 등의 DB를 염두에 두고 설계됨
-------------------------------------------------------------------------------------

[ ContentProvider 로부터 데이터를 읽어오는 법 ]
  - 해당 ContentProvider 의 경로(URI)를 알아야 한다.
  - 이 경로는 접근할 대상 앱에서 정의한다.
  - URI 는 authority 라고도 하며 ContentProvider 를 직접 만들 때에는 Manifest 에 명시해야한다.

  1. ContentResolver 를 통해 데이터를 읽음 -> getContentResolver()
  2. ContentResolver 에 URI 를 전달하여 ContentProvider 에 접근
  3. ContentResolver.query() 를 이용해 데이터에 대한 참조를 가지는 Cursor 를 가져옴
  4. Cursor 로부터 원하는 데이터 컬럼의 인덱스를 획득
  5. 해당 인덱스에 있는 데이터를 가져옴
  6. 데이터의 이용을 마친 후 Cursor 를 해제함

   * query() 메소드 사용법
     Cursor query(Uri uri, String[] projection, String[] selection, String[] selectionArgs, String sortOrder)
      - [ projection : SELECT | selection : WHERE | selectionArgs : PreparedStatement | sortOrder : ORDER BY ]
      - 여기서 반환값인 Cursor 는 데이터에 접근하는 포인터. (관계형 DB 테이블에서 어떤 행, 열에 대한 데이터를 가리키는지를 나타냄)
-------------------------------------------------------------------------------------

[ MediaStore 에서 이미지 가져오기 ]
  - ContentProvider 에 접근할 때 다른 앱이 이용할 수 있도록 하기 위해 상수가 이미 정의되어 있다.
  - Uri 는 보통 CONTENT_URI, EXTERNAL_CONTENT_URI 와 같은 상수명으로 정의되어 있다.


 */
public class ContentProvider_MainActivity extends AppCompatActivity {

    private static final int GET_GALLERY_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider__main);


        // 갤러리에서 직접 이미지를 선택하는 방법
/*
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
*/

        // 가장 최근의 이미지를 가리키는 커서를 가져옴
        Cursor cursor = getImage();

        // moveToFirst() 메소드로 커서를 가장 앞으로 이동시킴
        // 이 값이 true 라면, 커서가 데이터를 가지고 있다는 의미이므로 데이터가 없을 때는 실행되지 않음
        // 데이터를 가져오는 방법 -> 1. 가져올 컬럼의 인덱스 획득 | 2. 해당 컬럼의 데이터를 가져옴
        if (cursor.moveToFirst()) {

            Toast.makeText(this, "커서가 데이터를 가지고 있습니다.", Toast.LENGTH_SHORT).show();
            // 1. 각 컬럼의 열 인덱스를 획득 (ID, TITLE, DATE_TAKEN)
            int idColNum = cursor.getColumnIndexOrThrow(ImageColumns._ID);
            int titleColNum = cursor.getColumnIndexOrThrow(ImageColumns.TITLE);
            int dateTakenColNum = cursor.getColumnIndexOrThrow(ImageColumns.DATE_TAKEN);

            // 2. 인덱스를 이용하여 Cursor 로부터 데이터를 획득
            long id = cursor.getLong(idColNum);
            String title = cursor.getString(titleColNum);
            long dateTaken = cursor.getLong(dateTakenColNum);

            Uri imageUri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id);

            // 3. 데이터를 View 로 설정
            TextView textView = findViewById(R.id.imageInfo);
            ImageView image = findViewById(R.id.image);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateTaken);
            String text = DateFormat.format("yyyy/MM/dd(E) kk:mm:ss", calendar).toString();
            textView.setText("제목: " + title + " 촬영일시: " + text);
            image.setImageURI(imageUri);
        }

        // 커서의 사용을 마친 후에는 ContentProvider 가 가지고 있는 데이터의 참조를 없애기 위해 커서를 닫음
        cursor.close();
    }


    // Media 에 접근하여 가장 최근의 Image 를 가리키는 query 문을 수행한후 해당 주소를 가리키는 커서를 반환하는 메소드
    private Cursor getImage() {
        ContentResolver cr = getContentResolver();

        // 미디어에 접근하기 위한 Uri 를 가져옴 (EXTERNAL_CONTENT_URI 라는 상수명으로 정의되어 있음)
        Uri queryUri = Media.EXTERNAL_CONTENT_URI;

        // 쿼리문 작성 - projection (사진의 ID, 제목, 취득일자를 SELECT | 상수명으로 정의되어 있음)
        String[] projection = new String[]{ImageColumns._ID, ImageColumns.DATE_TAKEN,};

        // 쿼리문 작성 - sortOrder (취득일자 내림차순으로 정렬함)
        String sortOrder = ImageColumns.DATE_TAKEN + " DESC";

        queryUri = queryUri.buildUpon().appendQueryParameter("limit", "1").build();

        // 쿼리문 작성 - 1개만 가져옴
        return cr.query(queryUri, projection, null, null, sortOrder);
    }


    // 이미지를 선택했을 때 실행됨
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            ImageView image = findViewById(R.id.image);

            Uri selectedImageUri = data.getData();
            image.setImageURI(selectedImageUri);
        }
    }
}
