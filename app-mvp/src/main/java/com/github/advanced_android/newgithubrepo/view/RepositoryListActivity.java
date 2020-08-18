package com.github.advanced_android.newgithubrepo.view;

import android.content.Context;
import android.os.Bundle;
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

import com.github.advanced_android.newgithubrepo.R;
import com.github.advanced_android.newgithubrepo.contract.RepositoryListContract;
import com.github.advanced_android.newgithubrepo.model.GitHubService;
import com.github.advanced_android.newgithubrepo.presenter.RepositoryListPresenter;
import com.google.android.material.snackbar.Snackbar;
/*

[ MVVM 설계기법과 MVP 설계기법 ]

[ MVP 설계기법으로 구현하기 ]

  1. model, view, presenter, contract 패키지를 생성 (여기서 contract 패키지는 뷰와 프레젠터가 구현해야 할 인터페이스가 정의됨)
  2. 흐름 파악
    - View 에서 Spinner 를 선택하는 등의 이벤트가 발생 -> Presenter 로 통지
    - Presenter 는 View 의 선택 이벤트가 요청하는 바에 따라 Model 에 접근하여 데이터를 가져오며 이를 View 에 반영
    - 이 때, View 와 Presenter 의 통신 인터페이스는 contract 패키지에 정의 ( RepositoryListContract )
        View 에서는 RepositoryListContract.View 인터페이스를,
        Presenter 에서는 RepositoryListContract.UserActions 인터페이스를 구현해야 한다.
  3. View (RepositoryListActivity, DetailActivity) 에서는 Spinner, ProgressBar, CoordinatorLayout, RecyclerView 등
        사용자 인터페이스를 표시하는 역할에 집중하며, API 에 접근하여 데이터를 가져오는 등의 기능은 전혀 수행하지 않는다.
        다만, Presenter 에게 필요한 요청을 알림으로써 이를 Presenter 에게 온전히 위임한다.
  4. Presenter 에서는 View 로부터의 요청에 대해 API 에 직접 접근하여 데이터를 가져온 후 View 를 조작하는 등의 역할에 집중한다.
---------------------------------------------------------------------------------------------------------------------

   * RepositoryListActivity 에서는 Presenter 의 실체인 RepositoryListContract.UserActions 로써 Presenter 에 요청을 전송하고,
     RepositoryListPresenter 에서는 View 의 실체인 RepositoryListContract.View 를 이용하여 View 조작을 수행한다 *
---------------------------------------------------------------------------------------------------------------------


 */

/**
 * 리포지토리 목록을 표시하는 Activity
 * MVP의 View 역할을 가진다
 * 뷰 표시와 프레젠터 접근 관련 내용만 기술함 ( API 접근의 내용은 없음 )
 * Flat 하게 구현했던 코드와 달리, loadRepositories(), onRepositoryItemClick() 등
 * API 에 접근하여 데이터를 가져와야 하는 메소드를 액티비티에 직접 기술하지 않고 Presenter 에게 알려주기만 하면 된다.
 * 이로 인해, 액티비티 내의 코드가 간결해지며 Model 과의 의존관계가 없어진다.
 */
public class RepositoryListActivity extends AppCompatActivity implements RepositoryAdapter.OnRepositoryItemClickListener, RepositoryListContract.View {

    private Spinner languageSpinner;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;

    private RepositoryAdapter repositoryAdapter;

    private RepositoryListContract.UserActions repositoryListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_list);
        setupViews();

        // ① Presenter의 인스턴스를 생성
        final GitHubService gitHubService = ((NewGitHubReposApplication) getApplication()).getGitHubService();
        repositoryListPresenter = new RepositoryListPresenter(this, gitHubService);
    }

    /**
     * 목록 등의 화면 요소를 만든다
     */
    private void setupViews() {
        // 툴바 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Recycler View
        RecyclerView recyclerView = findViewById(R.id.recycler_repos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repositoryAdapter = new RepositoryAdapter(this, this);
        recyclerView.setAdapter(repositoryAdapter);

        // ProgressBar
        progressBar = findViewById(R.id.progress_bar);

        // SnackBar 표시로 이용한다
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        // Spinner
        languageSpinner = findViewById(R.id.language_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.addAll("java", "objective-c", "swift", "groovy", "python", "ruby", "c");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  스피너의 선택 내용이 바뀌면 호출된다
                String language = (String) languageSpinner.getItemAtPosition(position);
                // ② Presenter에 프로그래밍 언어를 선택했다고 알린다
                // 선택된 언어와 날짜를 바탕으로 API 에 접근하는 loadRepositories() 메소드를 직접 호출하는 것이 아니라
                // 프레젠터에게 프로그래밍 언어 선택을 알리기만 한다.
                // 프레젠터는 이 신호를 받고 Model, 즉 API 에 접근하여 데이터를 가져온 후 갱신한다.
                // 이에 따라, loadRepositories() 메소드의 내용 또한 이 액티비티에 기술하지 않아도 된다.
                repositoryListPresenter.selectLanguage(language);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    /**
     * RecyclerView에서 클릭됐다
     *
     * @see RepositoryAdapter.OnRepositoryItemClickListener#onRepositoryItemClickListener
     */
    @Override
    public void onRepositoryItemClick(GitHubService.RepositoryItem item) {
        // Presenter 에게 알리기만 할 뿐 API 에 대한 직접 접근은 하지 않는다.
        repositoryListPresenter.selectRepositoryItem(item);
    }


    // =====RepositoryListContract.View 구현=====
    // 이곳에서 Presenter로부터 지시를 받아 View의 변경 등을 한다

    @Override
    public void startDetailActivity(String full_name) {
        DetailActivity.start(this, full_name);
    }


    @Override
    public String getSelectedLanguage() {
        return (String) languageSpinner.getSelectedItem();
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRepositories(GitHubService.Repositories repositories) {
        // ③ 리포지토리 목록을 Adapter에 설정한다
        repositoryAdapter.setItemsAndRefresh(repositories.items);
    }

    @Override
    public void showError() {
        Snackbar.make(coordinatorLayout, "읽을 수 없습니다", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
