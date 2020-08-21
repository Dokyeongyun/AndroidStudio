package com.example.studyandroid.No9_Gradle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studyandroid.BuildConfig;
import com.example.studyandroid.GreatFeature;
import com.example.studyandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * [ Gradle ]
 * : 안드로이드 스튜디오의 빌드 시스템.
 *   - Android Gradle 플러그인을 이용하면 Build Type, Product Flavors 를 이용할 수 있어
 *     빌드에 따라 변경사항을 추가하는 등의 유연성 높은 빌드 커스터마이징이 가능하다.
 *   - Groovy 라는 단순한 스크립트 언어를 사용한다.
 *   - 빌드 중 프로그램을 조작할 수 있다.
 *
*/
/**
 * [ Gradle _ 특징 ]
 *  1. Groovy 라는 스크립트 언어로 기술할 수 있다 ( Groovy 는 JAVA 의 문법과 아주 비슷하다. )
 *     -> Groovy 를 몰라도 자바와 같은 방식으로 Gradle 을 다룰 수 있으며, 더 간단히 작성할 수도 있다.
 *     ex) * JAVA 문법처럼 작성하는 Groovy Code
 *            public class Example {
 *                public static void main(String[] args) {
 *                    System.out.println("Hello");
 *                }
 *            }
 *         * Groovy Script 로 간결하게 작성한 Code
 *            println 'Hello'
 *  2. 다른 환경에서 똑같이 실행할 수 있다.
 *     -> GradleWrapper 라는 메커니즘을 통해 사용 시 설정된 버전으로 자동으로 설정할 수 있다.
 *     -> 프로젝트팀 간 또는 서버에서 같은 버전의 Gradle 로 빌드를 실행할 수 있다.
 *  3. Dependencies 를 작성할 수 있다.
 *     -> 단 한 줄의 코드만으로 라이브러리 등을 추가할 수 있다.
*/
/**
 * [ Gradle _ Gradle PlugIn 의 특징 ]
 *  1. 앱에 특화된 설정을 할 수 있다.
 *     -> 앱의 버전, 빌드에 이용할 툴의 버전을 지정할 수 있다.
 *     -> Build Variant 로 제품 특성에 따른 앱을 빌드할 수 있다. (무료 or 유료, Debug version or Release Version )
 *  2. 안드로이드 스튜디오와의 결합
*/
/**
 * [ Gradle _ 안드로이드의 Build 파일 ]
 *   1. 전체 프로젝트의 build.gradle ( Project: StudyAndroid )
 *     * buildscript 블록 : 빌드 시 Android gradle 플러그인을 사용하기 위한 설정 포함
 *         - repository 블록 : Android gradle 플러그인이 포함되어 있는 리포지토리 지정
 *         - dependencies 블록 : 빌드 시 사용되는 Android gradle 플러그인을 의존관계로써 작성
 *     * allprojects 블록 : 모든 프로젝트에서 볼 수 있게 하고자 하는 리포지토리를 작성
 *
 *   2. 모듈의 build.gradle ( Module: app )
 *     * android 블록 : 빌드 시 이용할 설정 작성
 *         - compileSdkVersion : 컴파일 시 사용할 SDK 버전 설정 ( .java 파일을 컴파일하여 class 파일로 만들 때 이용됨 )
 *         - buildToolsVersion : 빌드 툴 버전 설정 ( apk 파일을 만들 때 사용되는 빌드 툴 버전 설정 )
 *         - signingConfigs 블록 : 서명 설정 ( keyStore 파일의 경로, 패스워드 등 설정, keyStore 파일 : 디지털 서명에 필요한 인증서 저장 )
 *         - defaultConfig 블록 : 앱의 설정에 관련된 내용 작성
 *            -- applicationId : 앱의 패키지명
 *            -- minSdkVersion : 최소 SDK 버전 설정 ( 앱을 설치할 수 있는 최소 API 레벨을 지정 )
 *            -- targetSdkVersion : 타겟 SDK 버전 설정
 *            -- versionCode, versionName, testInstrumentationRunner : 버전 설정 ( 버전코드는 숫자로, 버전네임은 문자열로 작성 )
 *         - buildTypes 블록
 *     * dependencies 블록 : 의존관계 작성 ( 라이브러리 등 )
 *         - implementation fileTree(dir: 'libs', include: ['*.jar']) : fileTree 중 libs 라는 폴더, .jar 확장자를 가진 파일을 의존관계로써 정의
 *         - Android 내에 포함된 지원 라이브러리 등을 Maven 리포지토리에서 가져와 사용함
 *         - 외부 라이브러리 또한 이곳에 작성함
 *
 *  3. 프로젝트의 settings.gradle
 *     * Gradle 에는 settings.gradle 파일이 꼭 있어야 하며, 어떤 모듈이 포함되는 지 작성한다.
 *
 *  4. gradle-wrapper.properties
 *     * 앱을 빌드할 때 필요한 Gradle 이 설치되어 있지 않아도 Gradle Binary 를 다운로드하여 설정해준다.
 *     * 이로 인해, 서버에 Gradle 이 설치되어 있지 않아도 실행이 가능하며,
 *     * 프로젝트팀 간에 모두 같은 빌드 환경에서 작업이 가능하다.
 *
 *  5. proguard-rules.pro
 *     * 프로가드는 빌드 시 코드를 난독화 또는 최적화하는 도구
 *     * 빌드 시 사용하지 않는 메소드 제거
 *     * 코드의 난독화를 하면 디컴파일을 통해 apk 로부터 소스코드를 읽는 행위를 막는다.
 *     * 난독화하지 않을 클래스를 별도로 지정할 수도 있다.
 *
 *  6. gradle.properties
 *     * 이 파일에 작성한 내용을 build.gradle 에서 참조한다.
 *     * ex) gradle.properties 에 변수처럼 작성한 test = TEST 를
 *           build.gradle(app) 에서 println test 라고 작성하면 빌드 시 해당 내용이 출력된다.
 *
 *  7. local.properties
 *     * SDK 위치가 작성됨
 *     * 이 파일의 SDK 위치보다, Android Studio 의 설정이 우선시된다.
*/
/**
 * [ Gradle _ Task ]
 *  - Gradle 이 프로젝트를 빌드할 때, Task 가 빌드 처리를 수행한다.
 *  - 빌드를 수행하면, Android Gradle 플러그인이 정의하는 assembleDebug 라는 Task 를 실행
 *  - 각 태스크는 의존관계를 설정할 수 있는데, 다음은 build 태스크가 configure 태스크에 의존하는 것을 정의한다.
 *       build.dependOn(configure)
 *   - 이처럼 안드로이드 앱의 빌드는 assembleDebug 태스크에 의존하는 태스크가 차례대로 실행되는 것
 */
/**
 * [ Gradle _ 프로젝트 커스터마이징 ]
 *   : 유료버전과 무료버전 앱이 있다고 할 때, 각 버전 별로 기능이 달라 코드를 수정해야한다.
 *     이를 효과적으로 처리하기 위해 프로젝트를 커스터마이징하여 수행한다.
 *     build Type 과 Product Flavors 를 이용한다.
 *
 *   1. build Type
 *      - 대표적으로 debug 와 release 빌드 타입이 있음
 *      - debug : apk 에 디버그 서명 / release : apk 에 릴리즈 서명
 *      - 일반적으로 debug 를 이용해 디버그를 수행하고, release 를 이용해 Google Play 에 릴리즈함.
 *      - build.gradle(app) 의 android 블록 내의 buildTypes 블록에 내용을 작성
 *          * 이 실습에서는 build Type 에 따라 debug 버전, release 버전, stage 버전을 생성하였고,
 *            release 버전은 서명을 설정하고 프로가드를 활성화 하였다.
 *            또, stage 버전일 때, 앱 내에서 이용하는 URL 의 주소를 테스트용으로 설정할 수 있게 설정하였다.
 *              ( stage/res/values/strings.xml 파일 내에 api 주소 정의 )
 *   2. Product Flavors
 *      - Product Flavors 는 debug, release 버전 등의 빌드타입이 아닌,
 *        구현 자체를 바꾸거나 별도의 apk 를 만들고 싶을 때 이용
 *      - ex) 무료버전 vs 유료버전
 *          * 이 실습에서는 프로덕트 플레이버를 이용해 무료버전과 유료버전을 만들어 본다.
 *            1. 프로덕트 플레이버 선언 -> build.gradle(app) 파일 내에 productFlavors 블록 작성
 *            2. 무료버전 클래스 생성 -> src/free/java/com/example/studyandroid/GreatFeature.java
 *            3. 유료버전 클래스 생성 -> src/pro/java/com/example/studyandroid/GreatFeature.java
 *            4. 구현에서 각각 호출 -> src/main/java/com/example/studyandroid/Gradle_MainActivity.java
 *                                    [ new GreatFeature().doIt(Gradle_MainActivity.this); ]
 *
 *     * Build Type 과 Product Flavors 를 모두 정의하게 되면,
 *       Build Type 과 Product Flavors 를 조합하여 Build Variants 가 형성되게 된다.
 *          예를들어, Build Type : debug, release, stage
 *                   Product Flavors : free, pro
 *                   ==> Build Variants : freeDebug, freeRelease, freeStage, proDebug, proRelease, proStage
 *       이 때, build.gradle(app) 와 Product Flavors 블록에서 동일한 프로퍼티를 작성한다면,
 *          Product Flavors 의 프로퍼티가 덮어쓴다.
 *
 */


public class Gradle_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradle__main);
        final TextView packageNameTextView = findViewById(R.id.package_name);
        final TextView versionNameTextView = findViewById(R.id.version_name);
        Toolbar toolbar = findViewById(R.id.toolbar);

        packageNameTextView.setText(BuildConfig.APPLICATION_ID);
        versionNameTextView.setText(BuildConfig.VERSION_NAME);
        //setSupportActionBar(toolbar);

        Toast.makeText(this, getString(R.string.api_url)+"에 액세스", Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GreatFeature().doIt(Gradle_MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
