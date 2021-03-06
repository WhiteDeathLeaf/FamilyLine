package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.UserBean;
import com.galaxy_light.gzh.familyline.ui.adapter.EmojiPagerAdapter;
import com.galaxy_light.gzh.familyline.ui.adapter.MessageDetailAdapter;
import com.galaxy_light.gzh.familyline.ui.presenter.MessageDetailPresenter;
import com.galaxy_light.gzh.familyline.ui.view.MessageDetailView;
import com.galaxy_light.gzh.familyline.utils.ContentUtil;
import com.galaxy_light.gzh.familyline.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTouch;

public class MessageDetailActivity extends AppCompatActivity implements MessageDetailView {

    private static final String USER = "user";
    private static final String USER_ID = "user_id";
    private static final String CONVERSATION_ID = "conversation_ID";

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
    @BindView(R.id.vp_emoji)
    ViewPager vpEmoji;

    private UserBean userBean;
    private String user_id;
    private String conversationID;
    private String messageDetail;
    private MessageDetailPresenter presenter;
    private MessageDetailHandler detailHandler;

    public static void openMessage(Context context, UserBean userBean, String id, String conversationID) {
        Intent intent = new Intent(context, MessageDetailActivity.class);
        intent.putExtra(USER, userBean);
        intent.putExtra(USER_ID, id);
        intent.putExtra(CONVERSATION_ID, conversationID);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        userBean = getIntent().getParcelableExtra(USER);
        user_id = getIntent().getStringExtra(USER_ID);
        conversationID = getIntent().getStringExtra(CONVERSATION_ID);
        initToolbar();
        initListener();
        presenter = new MessageDetailPresenter(this, userBean.getImageUrl());
        detailHandler = new MessageDetailHandler();
        presenter.requestMessageDetailData(userBean.getUsername(), conversationID);
        presenter.initEmoji(this);
    }

    private void initToolbar() {
        setSupportActionBar(toolbarMessageDetail);
        if (getSupportActionBar() != null) {
            tvMessageDetail.setText(userBean.getUsername());
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
        AVIMMessageManager.registerMessageHandler(AVIMTextMessage.class, detailHandler);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AVIMMessageManager.unregisterMessageHandler(AVIMTextMessage.class, detailHandler);
    }

    @OnCheckedChanged({R.id.cb_at_input, R.id.cb_et_input, R.id.cb_more})
    public void onCheck(CompoundButton compoundButton, boolean isCheck) {
        switch (compoundButton.getId()) {
            case R.id.cb_at_input:
                if (tetMessageInput.isFocused()) {
                    tetMessageInput.clearFocus();
                    cbAtInput.setChecked(true);
                    tetMessageInput.setVisibility(View.GONE);
                    btnMessageInput.setVisibility(View.VISIBLE);
                    if (getCurrentFocus() != null) {
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    return;
                }
                if (isCheck) {
                    tetMessageInput.setVisibility(View.GONE);
                    btnMessageInput.setVisibility(View.VISIBLE);
                    cbEtInput.setChecked(false);
                    cbMore.setChecked(false);
                    if (getCurrentFocus() != null) {
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                } else {
                    tetMessageInput.setVisibility(View.VISIBLE);
                    btnMessageInput.setVisibility(View.GONE);
                    tetMessageInput.requestFocus();
                    InputMethodManager m=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (m != null) {
                        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    moveToLast(presenter.getLastPosition());
                }
                break;
            case R.id.cb_et_input:
                if (isCheck) {
                    flEmoji.setVisibility(View.VISIBLE);
                    cbAtInput.setChecked(false);
                    cbMore.setChecked(false);
                    if (getCurrentFocus() != null) {
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                } else {
                    flEmoji.setVisibility(View.GONE);
                    InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (m != null) {
                        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                tetMessageInput.requestFocus();
                moveToLast(presenter.getLastPosition());
                break;
            case R.id.cb_more:
                if (isCheck) {
                    messageDetailBottom.setVisibility(View.VISIBLE);
                    cbAtInput.setChecked(false);
                    cbEtInput.setChecked(false);
                    if (getCurrentFocus() != null) {
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    moveToLast(presenter.getLastPosition());
                } else {
                    messageDetailBottom.setVisibility(View.GONE);
                }
                break;
        }
    }

    @OnClick(R.id.btn_message_send)
    public void onSend() {
        presenter.sendMessage(userBean.getUsername(), user_id, messageDetail);
        Intent intent = new Intent();
        intent.putExtra("conversationId", conversationID);
        intent.putExtra("lastMessage", messageDetail);
        intent.putExtra("lastTime", DateUtil.formatDate(System.currentTimeMillis()));
        setResult(1000, intent);
        tetMessageInput.setText(null);
    }

    @OnClick(R.id.tet_message_input)
    public void input() {
        if (cbAtInput.isChecked() || cbEtInput.isChecked() || cbMore.isChecked()) {
            if (getCurrentFocus() != null) {
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @OnTouch(R.id.rv_message_detail)
    public boolean onTouch() {
        tetMessageInput.clearFocus();
        messageDetailBottom.setVisibility(View.GONE);
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;//让事件继续传递;
    }

    @OnFocusChange(R.id.tet_message_input)
    public void onFocusChanged(boolean isChange) {
        if (!isChange) {
            cbAtInput.setChecked(false);
            cbEtInput.setChecked(false);
            cbMore.setChecked(false);
        }
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

    @Override
    public void setEmojiAdapter(EmojiPagerAdapter adapter) {
        adapter.setOnEmojiClickListener(emoji -> {
            if ("del".equals(emoji)) {
                //表示点击的是删除按钮
                KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL);
                tetMessageInput.onKeyDown(KeyEvent.KEYCODE_DEL, event);
            } else {
                tetMessageInput.append(emoji);
            }
        });
        vpEmoji.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToLast(int position) {
        rvMessageDetail.scrollToPosition(position);
    }

    private class MessageDetailHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
            if (message.getConversationId().equals(conversationID)) {
                presenter.acceptMessage(ContentUtil.subContent(message.getContent()));
            }
        }
    }
}
