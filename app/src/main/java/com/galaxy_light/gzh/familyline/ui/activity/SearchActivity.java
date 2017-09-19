package com.galaxy_light.gzh.familyline.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.ui.presenter.SearchPresenter;
import com.galaxy_light.gzh.familyline.ui.view.SearchView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements SearchView {

    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.toolbar_search)
    Toolbar toolbarSearch;
    @BindView(R.id.tet_search)
    EditText tetSearch;
    @BindView(R.id.ib_search)
    ImageButton ibSearch;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initToolbar();
        initListener(textWatcher);
    }

    private void initToolbar() {
        setSupportActionBar(toolbarSearch);
        if (getSupportActionBar() != null) {
            tvSearch.setText(getString(R.string.search));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initListener(TextWatcher textWatcher) {
        tetSearch.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            username = tetSearch.getText().toString();
        }
    };

    @OnClick(R.id.ib_search)
    public void onSearch() {
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "没有搜索内容哦", Toast.LENGTH_SHORT).show();
            return;
        }
        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        new SearchPresenter(this).requestSearchData(rvSearch);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
