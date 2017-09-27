package com.galaxy_light.gzh.familyline.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
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
import com.galaxy_light.gzh.familyline.utils.PopupManager;

import java.util.List;

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
        AVIMMessageManager.registerMessageHandler(AVIMTextMessage.class, new MessageFragmentHandler());
        if (((MessageAdapter) (rvMessage.getAdapter())).getData().size() <= 0) {
            presenter.requestMessageData();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        AVIMMessageManager.unregisterMessageHandler(AVIMTextMessage.class, new MessageFragmentHandler());
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

    private BaseQuickAdapter.OnItemClickListener itemClickListener = (adapter, view, position) -> {
        MessageBean messageBean = (MessageBean) adapter.getData().get(position);
        UserBean userBean = new UserBean(messageBean.getImageUrl(), messageBean.getUsername(), messageBean.getId());
        MessageDetailActivity.openMessage(getContext(), userBean, messageBean.getId());
        ((HomeActivity) getActivity()).setCurrentPage(0);
        view.findViewById(R.id.iv_message_tip).setVisibility(View.GONE);
    };

    private BaseQuickAdapter.OnItemLongClickListener itemLongClickListener = (adapter, view, position) -> {
        PopupManager.getInstance().createMultiMenu(view, R.layout.popup_message_item, PopupManager.SIZE_WRAP, new int[]{R.id.tv_popup_delete, R.id.tv_popup_top}, v -> {
            switch (v.getId()) {
                case R.id.tv_popup_delete://删除
                    adapter.getData().remove(position);
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.tv_popup_top://置顶
                    MessageBean currentBean = (MessageBean) adapter.getData().get(position);
                    adapter.getData().remove(position);
                    adapter.getData().add(0, currentBean);
                    adapter.notifyDataSetChanged();
                    break;
            }
        });
        return true;
    };

    private class MessageFragmentHandler extends AVIMMessageHandler {

        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
            List<MessageBean> data = ((MessageAdapter) (rvMessage.getAdapter())).getData();
            String username = conversation.getName().replace(AVUser.getCurrentUser().getUsername(), "").replace("&", "");
            if (data.size() <= 0) {
                presenter.acceptNewMessage(message, conversation);
            } else {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getUsername().equals(username)) {
                        return;
                    }
                }
                presenter.acceptNewMessage(message, conversation);
            }
        }
    }
}
