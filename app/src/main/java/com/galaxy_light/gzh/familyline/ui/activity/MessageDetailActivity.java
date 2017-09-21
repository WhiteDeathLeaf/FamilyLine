package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.MessageBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MessageDetailActivity extends AppCompatActivity {
    private static final String MESSAGE = "message";
    @BindView(R.id.tv_message_detail)
    TextView tvMessageDetail;
    @BindView(R.id.toolbar_message_detail)
    Toolbar toolbarMessageDetail;
    @BindView(R.id.rv_message_detail)
    RecyclerView rvMessageDetail;
    @BindView(R.id.tet_message_input)
    EditText tetMessageInput;
    @BindView(R.id.ib_more)
    ImageButton ibMore;
    private MessageBean messageBean;

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

        }
    };

    @OnCheckedChanged({R.id.cb_at_input, R.id.cb_et_input})
    public void onCheck(CompoundButton compoundButton, boolean isCheck) {
        switch (compoundButton.getId()) {
            case R.id.cb_at_input:

                break;
            case R.id.cb_et_input:

                break;
        }
    }

    @OnClick(R.id.ib_more)
    public void onMore() {

    }
}
