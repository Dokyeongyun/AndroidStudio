package com.github.advanced_android.newgithubrepo;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

// [ MVVM 설계기법과 MVP 설계기법의 전반적인 내용 ]
/*

[ MVVM 설계기법과 MVP 설계기법 ]

 *필요성 : 안드로이드에서 액티비티 클래스는 하나의 화면을 대표한다.
          이 화면을 표현하기 위한 로직, View 이벤트 수신, View 에 데이터 반영 등의 작업을
          모두 액티비티에서 하게되면 하나의 액티비티에 너무 많은 코드가 작성되게 된다.
          이렇게되면 유지보수 등의 작업에서 많은 시간과 비용이 소요되게 된다.

          이를 해결하기 위한 설계기법으로 MVP, MVVM 을 알아본다.

 * 효과 :  MVP, MVVM 등의 설계기법은 역할에 따라 코드를 분리하게 되어
          앱의 유지보수, 변경 등의 작업에서 효과적인 작업을 보장해준다.
          이에 따른 생산성, 관리 효율이 높아지며 변경에 강한 코드를 작성할 수 있게 해준다.

 * 개념 :  1. MVP (Model View Presenter)
            - 사용자 인터페이스를 구축할 때 이용하는 설계 기법
            - 모델, 뷰, 프레젠터의 세 가지 요소로 역할을 분담
                모델 : 데이터, 비즈니스 로직, DB, API 관련 내용 포함 (UI 로직 제외)
                뷰 : 데이터 표시
                프레젠터 : 모델과 뷰 사이의 인터페이스, 뷰에서 발생한 이벤트를 수신하여 이에 대응하는 처리를 위헤 모델에 접근
            - 장점 : 모델과 뷰 사이의 의존관계가 없어짐, 코드관리 효율 향상
            - 단점 : 프레젠터 내에 뷰와 모델간의 인터페이스를 정의해야 함 -> 코드가 길어질 수 있으며 난도가 높음
          2. MVVM (Model View ViewModel)
            - View 와 결합하는 부분의 코드를 줄일 수 있는 Data Binding 을 전제로 한 설계기법
            - 모델, 뷰, 뷰모델의 세 가지 요소로 역할을 분담
                모델 : 데이터, 비즈니스 로직 포함
                뷰 : 데이터 표시, 뷰모델이 모델에서 가져온 데이터를 반영하여 표시
                     뷰모델에 있는 데이터가 Data Binding 을 통해 자동적으로 뷰에 반영 -> 따로 구현할 필요 없음
                뷰모델 : 뷰의 상태, UI 관련 로직, 뷰의 이벤트 수신하여 모델의 데이터 접근 -> Data Binding 으로 뷰의 상태 갱신
            - 장점 : 역할의 분담을 통해 액티비티의 크기 감소, Data Binding 을 통해 모델에서 가져온 데이터를 뷰에 반영하는 로직 작성 필요 없음
            - 단점 : Data Binding 처리는 블랙박스화되어 있어 가독성이 낮고 디버그가 어려움

 */

// [ MVVM, MVP 설계기법을 적용해보기 전에 Flat 한 구현방법으로 구현해보자 ]
/*

[ MVVM, MVP 설계기법을 적용하지 않은 Flat 한 구현방법 ]

  1. 어떤 앱을 만들 것인가
    -> GitHub 의 API 를 이용하여 새로 만들어진 주목받는 오픈소스 프로젝트 리포지토리를 추출하는 앱
  2. 앱에 포함될 화면과 기능
    -> A. GitHub API 에 접근하여 사용자가 지정한 언어의 프로젝트 리포지토리 목록을 불러오기
    -> B. 사용자가 원하는대로 언어를 변경할 수 있고 변경 시 목록을 갱신
    -> C. 리포지토리 목록을 탭하면 상세화면으로 이동
    -> D. 상세화면 - 선택된 리포지토리의 데이터를 API 로 가져와 표시함
  3. 구현방법
    -> MVP, MVVM 설계기법을 이용하지 않고 Flat 하게 구현한다.
    -> 사용되는 라이브러리
       - 지원 라이브러리 (AppCompat, CardView, Design)
       - 이미지 로딩 (Glide)
       - API 접근 (Retrofit)
       - 비동기처리 (RxJAVA)

 */

/*

  * 리포지토리 목록을 표시하는 액티비티
    - 앱의 초기 시작화면
    - 리포지토리 목록과 특정 언어 선택기능, 앱의 제목을 나타내는 화면을 모두 포함
    - 리포지토리 목록에 대한 내용은 RecyclerView 를 이용 -> 이를 위해 Adapter 를 정의함 ( RepositoryAdapter )
    - 사용자가 프로그래밍 언어를 선택하는 기능은 Spinner 를 통해 구현
    - 목록을 불러오는 동안 표시할 ProgressBar 를 구현
 */
public class RepositoryListActivity extends AppCompatActivity implements RepositoryAdapter.OnRepositoryItemClickListener {

    private Spinner languageSpinner;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;

    private RepositoryAdapter repositoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_list);
        setupViews();
    }

    /**
     * 목록 등 화면 요소를 만든다
     */
    private void setupViews() {
        // 툴바 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Recycler View
        RecyclerView recyclerView = findViewById(R.id.recycler_repos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // LayoutManager 를 LinearLayout 으로 설정
        repositoryAdapter = new RepositoryAdapter(this, this); // Adapter 생성
        recyclerView.setAdapter(repositoryAdapter); // Adapter 를 RepositoryAdapter 로 설정

        // ProgressBar
        progressBar = findViewById(R.id.progress_bar);

        // SnackBar 표시에 이용한다
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        // Spinner
        languageSpinner = findViewById(R.id.language_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item); // Spinner 에 표현될 양식결정 및 Adapter 생성
        adapter.addAll("java", "objective-c", "swift", "groovy", "python", "ruby", "c"); // Spinner 에 포함될 내용 설정
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // DropDown 되었을 때 표현될 View 설정
        languageSpinner.setAdapter(adapter); // Spinner 에 표현될 데이터가 담긴 Adapter 설정
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // 아이템클릭 리스너 정의
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택시 뿐만 아니라 처음에도 호출된다
                String language = (String) languageSpinner.getItemAtPosition(position); // 선택한 위치의 언어를 language 변수에 저장
                loadRepositories(language); // 선택한 언어를 매개변수로 loadRepositories() 메소드 호출
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }


    /**
     * 지난 1주일간 만들어진 라이브러리의 인기순으로 가져온다
     *
     * @param language 가져올 프로그래밍 언어
     *                 <p>
     *                 API 에 접근하여 리포지토리 목록 데이터를 가져옴
     *                 1. 진행표시줄 표시
     *                 2. 1주일 전 날짜 구하기
     *                 3. 1주일 전 날짜와 매개변수로 주어진 laguage 를 이용해 통신 라이브러리인 Retrofit 으로 API 접근
     *                 4. API 응답을 onNext() 로 수신
     *                 5. 진행표시줄 제거
     *                 6. 수신한 데이터를 설정 후 RecyclerView 갱신
     */
    private void loadRepositories(String language) {

        // 데이터를 가져오는 동안 ProgressBar 표시
        progressBar.setVisibility(View.VISIBLE);

        // 현재 날짜를 얻어온 후 일주일 전의 날짜를 문자열로 저장함
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        String text = DateFormat.format("yyyy-MM-dd", calendar).toString();

        // Retrofit 을 이용해 서버에 액세스
        final NewGitHubReposApplication application = (NewGitHubReposApplication) getApplication();
        // 특정 날짜와 매개변수로 주어진 언어에 대한 데이터를 요청으로 전달
        // listRepos() 메소드의 매개변수로 query 내용을 입력 후 액세스의 결과를 observable 변수에 저장
        Observable<GitHubService.Repositories> observable = application.getGitHubService().listRepos("language:" + language + " " + "created:>" + text);
        // 입출력(IO)용 스레드로 통신하고, 메인스레드에서 결과를 수신하게 함
        // observable 에 subscribe() 를 호출하면 API 접근을 수행함
        // onNext() 메소드는 API 접근이 완료되었을 때 RxJAVA 의 메커니즘으로 호출된다.
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GitHubService.Repositories>() {
            @Override
            public void onNext(GitHubService.Repositories repositories) {
                // 데이터 수신이 완료되었으므로 ProgressBar 숨기기
                progressBar.setVisibility(View.GONE);
                // 수신한 데이터를 RecyclerView 에 설정한 후 갱신
                repositoryAdapter.setItemsAndRefresh(repositories.items);
            }

            @Override
            public void onError(Throwable e) {
                // 통신 실패 시 호출
                // 스낵바를 표시(아래에 표시되는 바)
                Snackbar.make(coordinatorLayout, "읽어올 수 없습니다.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onCompleted() {
                // 아무것도 하지 않는다
            }
        });
    }

    /**
     * 상세화면을 표시한다
     *
     * @see RepositoryAdapter.OnRepositoryItemClickListener#onRepositoryItemClick
     */
    @Override
    public void onRepositoryItemClick(GitHubService.RepositoryItem item) {
        DetailActivity.start(this, item.full_name);
    }
}
