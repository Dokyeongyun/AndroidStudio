package com.github.advanced_android.newgithubrepo.view;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.advanced_android.newgithubrepo.R;
import com.github.advanced_android.newgithubrepo.contract.RepositoryListViewContract;
import com.github.advanced_android.newgithubrepo.databinding.ActivityRepositoryListBinding;
import com.github.advanced_android.newgithubrepo.model.GitHubService;
import com.github.advanced_android.newgithubrepo.viewmodel.RepositoryListViewModel;
import com.google.android.material.snackbar.Snackbar;

/*

[ MVVM 설계기법과 MVP 설계기법 ]

[ MVVM 설계기법으로 구현하기 ]

  1. model, view, viewModel, contract 패키지 생성 (contract 에는 view 와 viewModel 이 구현할 인터페이스를 정의)
  2. MVP 기법에서 Presenter 의 역할을 ViewModel 이 수행하고, ViewModel 에서는 데이터바인딩을 활용함
  3. app/build.gradle 에 Data Binding 내용 작성
  4. 흐름 파악
      -> RepositoryListActivity.java - RepositoryListViewModel.java - activity_repository_list.xml : 데이터 바인딩
          - activity_repository_list.xml 파일 내에 RecyclerView 는 content_repository_list.xml 에 정의
          - content_repository_list.xml 의 RecyclerView 타입은 repo_item.xml 에서 정의
      -> RepositoryItemViewModel.java - repo_item_xml : 데이터 바인딩
      -> DetailActivity.java - DetailViewModel.java - activity_detail.xml : 데이터 바인딩

      ex) 앱의 초기 화면, RepositoryListActivity (바인딩 객체) 의 View 인 activity_repository_list.xml (View) 에서
          프로그래밍 언어 스피너의 Item이 선택되면, RepositoryListViewModel (ViewModel) 의 메소드를 호출한다.
            android:onItemSelected="@{viewModel::onLanguageSpinnerItemSelected}"
          이제 ViewModel 에서는 선택된 언어로 loadRepositories() 로 API 에 접근하여 데이터를 가져온다.
          그 후 RepositoryListViewContract 인터페이스의 showRepositories() 메소드를 재정의한 객체를 이용하여 RecyclerView 를 갱신한다.


 */


/**
 * 리포지토리 목록을 표시하는 액티비티
 * MVVM의 뷰 역할을 한다
 */
public class RepositoryListActivity extends AppCompatActivity implements RepositoryListViewContract {

    private Spinner languageSpinner;
    private CoordinatorLayout coordinatorLayout;

    private RepositoryAdapter repositoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRepositoryListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_repository_list);
        final GitHubService gitHubService = ((NewGitHubReposApplication) getApplication()).getGitHubService();
        binding.setViewModel(new RepositoryListViewModel(this, gitHubService));

        // 뷰를 셋업
        setupViews();
    }

    /**
     * 목록 등 화면 요소를 만든다
     */
    private void setupViews() {
        // 툴바 설정
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Recycler View
        RecyclerView recyclerView = findViewById(R.id.recycler_repos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repositoryAdapter = new RepositoryAdapter(this, this);
        recyclerView.setAdapter(repositoryAdapter);

        // SnackBar 표시에서 이용한다
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        // Spinner
        languageSpinner = findViewById(R.id.language_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.addAll("java", "objective-c", "swift", "groovy", "python", "ruby", "c");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
    }

    // =====RepositoryListViewContract 구현=====
    // 여기서 Presenter로부터 지시를 받아 뷰의 변경 등을 한다

    @Override
    public void startDetailActivity(String full_name) {
        DetailActivity.start(this, full_name);
    }

    @Override
    public void showRepositories(GitHubService.Repositories repositories) {
        repositoryAdapter.setItemsAndRefresh(repositories.items);
    }

    @Override
    public void showError() {
        Snackbar.make(coordinatorLayout, "읽을 수 없습니다", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
