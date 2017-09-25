package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUtils;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.custom.view.LoadingDialog;
import com.galaxy_light.gzh.familyline.ui.presenter.RegisterPresenter;
import com.galaxy_light.gzh.familyline.ui.view.RegisterView;
import com.galaxy_light.gzh.familyline.utils.RegexUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    @BindView(R.id.toolbar_register)
    Toolbar toolbarRegister;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tet_register_username)
    EditText tetRegisterUsername;
    @BindView(R.id.tet_register_password)
    EditText tetRegisterPassword;
    @BindView(R.id.tet_register_password_sure)
    EditText tetRegisterPasswordSure;
    @BindView(R.id.tet_register_email)
    EditText tetRegisterEmail;
    @BindView(R.id.btn_register_soon)
    Button btnRegisterSoon;

    private String username;
    private String password;
    private String passwordSure;
    private String email;
    private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initToolbar();
        initListener(textWatcher);
    }

    private void initToolbar() {
        setSupportActionBar(toolbarRegister);
        if (getSupportActionBar() != null) {
            tvRegister.setText(getString(R.string.register));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initListener(TextWatcher textWatcher) {
        tetRegisterUsername.addTextChangedListener(textWatcher);
        tetRegisterPassword.addTextChangedListener(textWatcher);
        tetRegisterPasswordSure.addTextChangedListener(textWatcher);
        tetRegisterEmail.addTextChangedListener(textWatcher);
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
            username = tetRegisterUsername.getText().toString();
            password = tetRegisterPassword.getText().toString();
            passwordSure = tetRegisterPasswordSure.getText().toString();
            email = tetRegisterEmail.getText().toString();
            boolean canRegister = !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordSure) || TextUtils.isEmpty(email));
            btnRegisterSoon.setEnabled(canRegister);
        }
    };

    @OnCheckedChanged(R.id.cb_pwd_see)
    public void onPwdSee(boolean isChecked) {
        if (isChecked) {
            tetRegisterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            tetRegisterPasswordSure.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            tetRegisterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            tetRegisterPasswordSure.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @OnClick(R.id.btn_register_soon)
    public void onRegister() {
        if (RegexUtil.verifyUsername(username) != RegexUtil.VERIFY_SUCCESS) {
            Toast.makeText(this, R.string.username_rules, Toast.LENGTH_SHORT).show();
            tetRegisterUsername.setText(null);
            return;
        }
        if (RegexUtil.verifyPassword(password) != RegexUtil.VERIFY_SUCCESS) {
            Toast.makeText(this, R.string.password_rules, Toast.LENGTH_SHORT).show();
            tetRegisterPassword.setText(null);
            tetRegisterPasswordSure.setText(null);
            return;
        }
        if (!password.equals(passwordSure)) {
            Toast.makeText(this, R.string.equal_pwd, Toast.LENGTH_SHORT).show();
            tetRegisterPassword.setText(null);
            tetRegisterPasswordSure.setText(null);
            return;
        }
        if (!AVUtils.checkEmailAddress(email)) {
            Toast.makeText(this, R.string.email_rules, Toast.LENGTH_SHORT).show();
            tetRegisterEmail.setText(null);
            return;
        }
        new RegisterPresenter(this).register(username, password, email);
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
        loadingDialog = new LoadingDialog();
        loadingDialog.show(getSupportFragmentManager(), "loadingDialog");
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void registerSuccess() {
        startActivity(new Intent(this, HomeActivity.class));
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(WelcomeActivity.ACTION_WELCOME));
        finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
