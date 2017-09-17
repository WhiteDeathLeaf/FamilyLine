package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.ui.presenter.LoginPresenter;
import com.galaxy_light.gzh.familyline.ui.view.LoginView;
import com.galaxy_light.gzh.familyline.utils.RegexUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.toolbar_login)
    Toolbar toolbarLogin;
    @BindView(R.id.tet_login_username)
    EditText tetLoginUsername;
    @BindView(R.id.tet_login_password)
    EditText tetLoginPassword;
    @BindView(R.id.btn_login_soon)
    Button btnLoginSoon;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initToolbar();
        initListener(textWatcher);
    }

    private void initToolbar() {
        setSupportActionBar(toolbarLogin);
        if (getSupportActionBar() != null) {
            tvLogin.setText(getString(R.string.login));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initListener(TextWatcher textWatcher) {
        tetLoginUsername.addTextChangedListener(textWatcher);
        tetLoginPassword.addTextChangedListener(textWatcher);
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
            username = tetLoginUsername.getText().toString();
            password = tetLoginPassword.getText().toString();
            boolean canRegister = !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password));
            btnLoginSoon.setEnabled(canRegister);
        }
    };

    @OnClick(R.id.btn_login_soon)
    public void onLogin() {
        if (RegexUtil.verifyUsername(username) != RegexUtil.VERIFY_SUCCESS) {
            Toast.makeText(this, R.string.username_rules, Toast.LENGTH_SHORT).show();
            tetLoginUsername.setText(null);
            return;
        }
        if (RegexUtil.verifyPassword(password) != RegexUtil.VERIFY_SUCCESS) {
            Toast.makeText(this, R.string.password_rules, Toast.LENGTH_SHORT).show();
            tetLoginPassword.setText(null);
            return;
        }
        new LoginPresenter(this).login(username, password);
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

    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
