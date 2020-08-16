package com.example.studyandroid.No7_SupportLibrary.NotificationCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studyandroid.R;

/*

[ Support Library ]
 : 안드로이드 스튜디오에서 라이브러리 형태로 제공하는 하위 호환성을 가진 안드로이드 프레임워크 API

 - 안드로이드 API 레벨이 올라가면서, 버전에 따라 이용할 수 있는 클래스나 메소드가 달라지게 된다.
 - 이러한 문제점을 일부 해결하기 위해, 지원 라이브러리를 통해 오래된 OS 버전에서도 신규 클래스나 메소드를 사용할 수 있는 기능을 제공한다.
 - ex) Fragment (API 11, Android 3.0 이상) | Fragment.requestPermissions (API 23, Android 6.0 이상)
       -> API 11 레벨보다 낮은 버전의 OS 에서 v4 Support Library 를 추가하면 Fragment 를 이용할 수 있게 된다.
 - 지원 라이브러리의 추가는 app/build.gradle 의 dependencies{} 내에 원하는 지원 라이브러리를 작성하면 된다.
 - 단, 모든 하위 버전에 대해 지원할 수 있는 것은 아니며 빈 메소드 내용을 리턴하는 경우도 있어 개발자 레퍼런스를 확인하여야 한다.
-------------------------------------------------------------------------------------------------------------------

[ v4 Support Library ]
 - API 4, Android 1.6 이상에서 사용 가능
 - Fragment, NotificationCompat, ViewPager, Loader 등의 기능 제공

[ v7 Support Library ]
 - API 7, Android 2.1 이상에서 사용 가능
 - AppCompat, Toolbar, CardView, RecyclerView 등의 기능 제공

[ Annotation Support Library ]
 - 어노테이션을 활용하여 코드에 정보를 부가한다.
 - 안드로이드 스튜디오에서 이 부가 정보를 이용하여 소스코드가 틀렸을 때 경고를 띄워 코딩에 도움을 줌

[ Design Support Library ]
 - 머터리얼 디자인을 하위호환성 있는 형태로 구현할 수 있는 기능 제공
 - NavigationDrawer, FloatingActionButton, SnackBar 등의 기능 제공
-------------------------------------------------------------------------------------------------------------------

[ v4 Support Library 중 NotificationCompat 을 이용한 예제 ]

 * Notification : 사용자에게 각종 정보를 알려주기 위해 사용

 - Notification 표시를 위해 설정해야할 것
    1. 알림에 표시할 작은 아이콘 선택
    2. 출력할 타이틀과 내용 설정
    3. 알림을 탭했을 때 이동할 경로 설정

 - Notification 사용방법
    1. NotificationCompat.Builder 로 설정추가
        * setSmallIcon() : 아이콘 설정
          setContentTitle() : 타이틀 설정
          setContentText() : 내용 설정
          setContentIntent() : 알림을 탭하면 실행될 PendingIntent 설정
    2. NotificationManagerCompat 에서 notify() 메소드 실행
        * notify() 의 매개변수
          첫번째 : NotificationID | 두번째 : NotificationCompat.Builder 를 build() 한 Notification
          이 때, NotificationID 가 같으면 알림이 덮어씌워지는 등 ID에 따른 조작을 할 수 있다.

    * API 26, Oreo 버전 이상부터는 NotificationChannel 을 이용해야 Notification 서비스를 이용할 수 있다.
      builder 설정 시, 두번째 매개변수로 채널 ID 를 함께 넘겨줌으로써 구현할 수 있다.

 - Action 사용
    * Action : 신속하게 PendingIntent 로 이동할 수 있게 Notification 에 표시되는 버튼

    1. Android 4.1 이상부터 이용 가능
    2. builder.addAction() 을 이용하여 추가
    3. 최대 3개까지 추가 가능

 - Heads-Up Notification 사용
    * Heads-Up Notification : Notification 을 전송함과 동시에 앱 위에 Notification 이 표시되는 기능

    1. Android 5.0 이상부터 이용 가능
    2. 중요도가 높은 알림을 전송할 때 사용
    3. 우선순위를 설정해야하며 단말이 진동하는 경우 표시됨

 */
public class NotificationCompat_MainActivity extends AppCompatActivity {

    public static final int NOT_SET = -100;
    private Spinner visibilitySpinner;
    private Spinner prioritiesSpinner;

    final String[] VISIBILITIES = {
            "설정하지 않는다",
            "VISIBILITY_PUBLIC",
            "VISIBILITY_PRIVATE",
            "VISIBILITY_SECRET",
    };
    final int[] VISIBILITIES_INT = {
            NOT_SET,
            NotificationCompat.VISIBILITY_PUBLIC,
            NotificationCompat.VISIBILITY_PRIVATE,
            NotificationCompat.VISIBILITY_SECRET,
    };

    final String[] PRIORITIES = {
            "설정하지 않는다",
            "PRIORITY_MAX",
            "PRIORITY_HIGH",
            "PRIORITY_DEFAULT",
            "PRIORITY_LOW",
            "PRIORITY_MIN",
    };

    final int[] PRIORITIES_INT = {
            NOT_SET,
            NotificationCompat.PRIORITY_MAX,
            NotificationCompat.PRIORITY_HIGH,
            NotificationCompat.PRIORITY_DEFAULT,
            NotificationCompat.PRIORITY_LOW,
            NotificationCompat.PRIORITY_MIN,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_compat__main);

        visibilitySpinner = findViewById(R.id.visibility);
        prioritiesSpinner = findViewById(R.id.priority);

        visibilitySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, VISIBILITIES));
        prioritiesSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, PRIORITIES));

    }

    public void build(View view) {
        Toast.makeText(this, "build", Toast.LENGTH_SHORT).show();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Notification_Channel_ID");


        // 알림 아이콘 설정
        builder.setSmallIcon(R.drawable.ic_stat_name);

        // 타이틀 표시
        final CheckBox contentTitle = findViewById(R.id.contentTitle);
        if (contentTitle.isChecked()) {
            builder.setContentTitle("ContentTitle");
        }

        // 내용 표시
        final CheckBox contentText = findViewById(R.id.contentText);
        if (contentText.isChecked()) {
            builder.setContentText("ContentText");
        }

        // 클릭했을 때의 인텐트 설정
        final CheckBox contentIntentCheckBox = findViewById(R.id.content_intent);
        if (contentIntentCheckBox.isChecked()) {
            final Intent launchIntent = new Intent(this, NotificationCompat_MainActivity.class);
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        }

        // 통지를 가로 슬라이드로 취소할 수 없게 한다
        final CheckBox onGoingCheckBox = findViewById(R.id.on_going);
        if (onGoingCheckBox.isChecked()) {
            builder.setOngoing(true);
        }

        // Action
        final CheckBox action1CheckBox = findViewById(R.id.action1);
        if (action1CheckBox.isChecked()) {
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(this, NotificationCompat_MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            builder.addAction(new NotificationCompat.Action(R.drawable.ic_stat_name, "ActionText1", pendingIntent));
        }
        final CheckBox action2CheckBox = findViewById(R.id.action2);
        if (action2CheckBox.isChecked()) {
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, new Intent(this, NotificationCompat_MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            builder.addAction(R.drawable.ic_stat_name, "ActionText2", pendingIntent);
        }
        final CheckBox action3CheckBox = findViewById(R.id.action3);
        if (action3CheckBox.isChecked()) {
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 3, new Intent(this, NotificationCompat_MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            builder.addAction(R.drawable.ic_stat_name, "ActionText3", pendingIntent);
        }

        // Android Wear에서 Action을 표시한다
        final CheckBox extendCheckBox = findViewById(R.id.extend);
        if (extendCheckBox.isChecked()) {
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 3, new Intent(this, NotificationCompat_MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            builder.extend(new NotificationCompat.WearableExtender().addAction(new NotificationCompat.Action(R.drawable.ic_stat_name, action3CheckBox.getText(), pendingIntent)));
        }

        // AutoCancel을 설정한다(클릭했을 때 자동으로 비표시가 된다)
        final RadioGroup autoCancelRadioGroup = (RadioGroup) findViewById(R.id.autocancel);
        if (autoCancelRadioGroup.getCheckedRadioButtonId() == R.id.autocancel_true) {
            builder.setAutoCancel(true);
        }

        // Ticker를 설정한다
        final CheckBox tickerCheckBox = findViewById(R.id.ticker);
        if (tickerCheckBox.isChecked()) {
            builder.setTicker("ticker");
        }

        // Progress를 설정한다
        final CheckBox progressCheckBox = findViewById(R.id.progress);
        if (progressCheckBox.isChecked()) {
            builder.setProgress(100, 60, false);
        }

        // BigPictureStyle를 설정한다
        final CheckBox bigPictureStyleCheckBox = findViewById(R.id.big_picture_style);
        if (bigPictureStyleCheckBox.isChecked()) {
            builder.setStyle(
                    new NotificationCompat.BigPictureStyle()
                            .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.ic_stat_name))
                            .setBigContentTitle("BigContentTitle")
                            .setSummaryText("SummaryText"));
        }

        // InboxStyle를 설정한다
        final CheckBox inboxStyleCheckBox = findViewById(R.id.inbox_style);
        if (inboxStyleCheckBox.isChecked()) {
            builder.setStyle(
                    new NotificationCompat.InboxStyle()
                            .addLine("line1")
                            .addLine("line2")
                            .addLine("line3")
                            .addLine("line4")
                            .addLine("line5")
                            .addLine("line6")
                            .addLine("line7")
                            .setBigContentTitle("BigContentTitle")
                            .setSummaryText("SummaryText"));
        }

        // BigTextStyle를 설정한다
        final CheckBox bigTextCheckBox = findViewById(R.id.big_text_style);
        if (bigTextCheckBox.isChecked()) {
            builder.setStyle(
                    new NotificationCompat.BigTextStyle()
                            .bigText("BigText")
                            .setBigContentTitle("BigContentTitle")
                            .setSummaryText("SummaryText"));
        }

        // Color를 설정한다
        final CheckBox colorCheckBox = findViewById(R.id.color);
        if (colorCheckBox.isChecked()) {
            builder.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        // VISIBILITIES를 설정(잠금화면에서 표시 여부)
        final int selectedVisibilitiesPosition = visibilitySpinner.getSelectedItemPosition();
        final int visibilityInt = VISIBILITIES_INT[selectedVisibilitiesPosition];
        if (visibilityInt != NOT_SET) {
            builder.setVisibility(visibilityInt);
        }

        // 통지 우선도 설정(위에 표시 여부)
        final int selectedPrioritySelectedPosition = prioritiesSpinner.getSelectedItemPosition();
        final int priorityInt = PRIORITIES_INT[selectedPrioritySelectedPosition];
        if (priorityInt != NOT_SET) {
            builder.setPriority(priorityInt);
        }

        // 단말기를 진동시킬지 여부
        final CheckBox vibrationCheckBox = findViewById(R.id.vibration);
        if (vibrationCheckBox.isChecked()) {
            builder.setVibrate(new long[]{0, 1000, 250, 1000});
        }

        // fullScreenIntent를 설정할지 여부
        final CheckBox fullScreenIntentCheckBox = findViewById(R.id.full_screen_intent);
        if (fullScreenIntentCheckBox.isChecked()) {
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(this, NotificationCompat_MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setFullScreenIntent(pendingIntent, true);
        }


        NotificationChannel channel = new NotificationChannel("Notification_Channel_ID", "Notification Channel", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Oreo Version 이상 -> Notification Channel 이용해야 함");

        // 노티피케이션 채널을 시스템에 등록
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);

        notificationManager.notify(1, builder.build()); // 고유숫자로 노티피케이션 동작시킴

        //NotificationManagerCompat.from(this).notify(1, builder.build());

    }
}
