package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.MessageBean;
import com.galaxy_light.gzh.familyline.ui.adapter.MessageDetailAdapter;
import com.galaxy_light.gzh.familyline.ui.presenter.MessageDetailPresenter;
import com.galaxy_light.gzh.familyline.ui.view.MessageDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MessageDetailActivity extends AppCompatActivity implements MessageDetailView {

    private static final String MESSAGE = "message";

    @BindView(R.id.tv_message_detail)
    TextView tvMessageDetail;
    @BindView(R.id.toolbar_message_detail)
    Toolbar toolbarMessageDetail;
    @BindView(R.id.rv_message_detail)
    RecyclerView rvMessageDetail;
    @BindView(R.id.tet_message_input)
    EditText tetMessageInput;
    @BindView(R.id.btn_message_input)
    Button btnMessageInput;
    @BindView(R.id.gv_more)
    GridView gvMore;
    @BindView(R.id.message_detail_bottom)
    RelativeLayout messageDetailBottom;
    @BindView(R.id.fl_emoji)
    FrameLayout flEmoji;
    @BindView(R.id.cb_at_input)
    CheckBox cbAtInput;
    @BindView(R.id.cb_et_input)
    CheckBox cbEtInput;
    @BindView(R.id.cb_more)
    CheckBox cbMore;
    @BindView(R.id.btn_message_send)
    Button btnMessageSend;

    private MessageBean messageBean;
    private String messageDetail;

    public static void fromMessage(Context context, MessageBean messageBean) {
        Intent intent = new Intent(context, MessageDetailActivity.class);
        intent.putExtra(MESSAGE, messageBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        messageBean = getIntent().getParcelableExtra(MESSAGE);
        initToolbar();
        initListener();
    }

    private void initToolbar() {
        setSupportActionBar(toolbarMessageDetail);
        if (getSupportActionBar() != null) {
            tvMessageDetail.setText(messageBean.getUsername());
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initListener() {
        tetMessageInput.addTextChangedListener(textWatcher);
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
            messageDetail = tetMessageInput.getText().toString();
            if (!TextUtils.isEmpty(messageDetail)) {
                btnMessageSend.setVisibility(View.VISIBLE);
                cbEtInput.setVisibility(View.GONE);
                cbMore.setVisibility(View.GONE);
            } else {
                btnMessageSend.setVisibility(View.GONE);
                cbEtInput.setVisibility(View.VISIBLE);
                cbMore.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        new MessageDetailPresenter(this).requestMeesageDetailData();
    }

    @OnCheckedChanged({R.id.cb_at_input, R.id.cb_et_input, R.id.cb_more})
    public void onCheck(CompoundButton compoundButton, boolean isCheck) {
        switch (compoundButton.getId()) {
            case R.id.cb_at_input:
                if (isCheck) {
                    tetMessageInput.setVisibility(View.GONE);
                    btnMessageInput.setVisibility(View.VISIBLE);
                    cbEtInput.setChecked(false);
                    cbMore.setChecked(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    tetMessageInput.setVisibility(View.VISIBLE);
                    btnMessageInput.setVisibility(View.GONE);
                }
                break;
            case R.id.cb_et_input:
                if (isCheck) {
                    flEmoji.setVisibility(View.VISIBLE);
                    cbAtInput.setChecked(false);
                    cbMore.setChecked(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    flEmoji.setVisibility(View.GONE);
                }
                break;
            case R.id.cb_more:
                if (isCheck) {
                    messageDetailBottom.setVisibility(View.VISIBLE);
                    cbAtInput.setChecked(false);
                    cbEtInput.setChecked(false);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    messageDetailBottom.setVisibility(View.GONE);
                }
                break;
        }
    }

    @OnClick(R.id.btn_message_send)
    public void onSend() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setDetailAdapter(MessageDetailAdapter adapter) {
        rvMessageDetail.setLayoutManager(new LinearLayoutManager(this));
        rvMessageDetail.setAdapter(adapter);
    }
}