package com.galaxy_light.gzh.familyline.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.custom.view.LoadingDialog;
import com.galaxy_light.gzh.familyline.model.bean.MessageBean;
import com.galaxy_light.gzh.familyline.model.bean.UserBean;
import com.galaxy_light.gzh.familyline.ui.activity.HomeActivity;
import com.galaxy_light.gzh.familyline.ui.activity.MessageDetailActivity;
import com.galaxy_light.gzh.familyline.ui.adapter.MessageAdapter;
import com.galaxy_light.gzh.familyline.ui.presenter.MessagePresenter;
import com.galaxy_light.gzh.familyline.ui.view.MessageView;
import com.galaxy_light.gzh.familyline.utils.NotifyManager;
import com.galaxy_light.gzh.familyline.utils.PopupManager;
import com.galaxy_light.gzh.familyline.utils.PrefManager;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 消息Fragment
 * Created by gzh on 2017-9-19.
 */

public class MessageFragment extends Fragment implements MessageView {
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    Unbinder unbinder;
    private LoadingDialog loadingDialog;
    private MessagePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new MessagePresenter(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        AVIMMessageManager.registerMessageHandler(AVIMTextMessage.class, new MessageFragmentHandler(presenter,getContext()));
//        if (((MessageAdapter) (rvMessage.getAdapter())).getData().size() <= 0) {
            presenter.requestMessageData();
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
        AVIMMessageManager.unregisterMessageHandler(AVIMTextMessage.class, new MessageFragmentHandler(presenter,getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {
        loadingDialog = new LoadingDialog();
        loadingDialog.show(getFragmentManager(), "loadingDialog");
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void setAdapter(MessageAdapter adapter) {
        adapter.setOnItemClickListener(itemClickListener);
        adapter.setOnItemLongClickListener(itemLongClickListener);
        rvMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMessage.setAdapter(adapter);
    }

    @Override
    public void messageTip(MessageAdapter adapter, int position) {
        View tipView = adapter.getViewByPosition(rvMessage, position, R.id.iv_message_tip);
        if (tipView != null) {
            tipView.setVisibility(View.VISIBLE);
        }
    }

    private BaseQuickAdapter.OnItemClickListener itemClickListener = (adapter, view, position) -> {
        MessageBean messageBean = (MessageBean) adapter.getData().get(position);
        UserBean userBean = new UserBean(messageBean.getImageUrl(), messageBean.getUsername(), messageBean.getId());
//        MessageDetailActivity.openMessage(getContext(), userBean, messageBean.getId(), messageBean.getConversationID());
        Intent intent = new Intent(getContext(), MessageDetailActivity.class);
        intent.putExtra("user", userBean);
        intent.putExtra("user_id", userBean.getId());
        intent.putExtra("conversation_ID", messageBean.getConversationID());
        startActivityForResult(intent, 100);
        ((HomeActivity) getActivity()).setCurrentPage(0);
        view.findViewById(R.id.iv_message_tip).setVisibility(View.GONE);
        NotifyManager.getInstance(getContext()).clear();
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 1000) {
            presenter.refresh(data.getStringExtra("conversationId"), data.getStringExtra("lastMessage"), data.getStringExtra("lastTime"));
        }
    }

    @SuppressWarnings("unchecked")
    private BaseQuickAdapter.OnItemLongClickListener itemLongClickListener = (adapter, view, position) -> {
        PopupManager.getInstance().createMultiMenu(view, R.layout.popup_message_item, PopupManager.SIZE_WRAP, true, new int[]{R.id.tv_popup_delete, R.id.tv_popup_top}, v -> {
            MessageBean currentBean = (MessageBean) adapter.getData().get(position);
            switch (v.getId()) {
                case R.id.tv_popup_delete://删除
                    PrefManager.removeConversationId(currentBean.getUsername());
                    adapter.getData().remove(position);
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.tv_popup_top://置顶
                    adapter.getData().remove(position);
                    adapter.getData().add(0, currentBean);
                    adapter.notifyDataSetChanged();
                    break;
            }
        });
        return true;
    };

    private static class MessageFragmentHandler extends AVIMMessageHandler {

        private MessagePresenter presenter;
        private WeakReference<Context> contextWrf;

        MessageFragmentHandler(MessagePresenter presenter, Context context) {
            this.presenter = presenter;
            this.contextWrf = new WeakReference<>(context);
        }

        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
            presenter.acceptNewMessage(contextWrf.get(), message, conversation);
        }
    }
}
