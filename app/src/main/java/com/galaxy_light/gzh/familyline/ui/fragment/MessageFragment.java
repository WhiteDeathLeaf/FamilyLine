package com.galaxy_light.gzh.familyline.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.custom.view.LoadingDialog;
import com.galaxy_light.gzh.familyline.model.bean.MessageBean;
import com.galaxy_light.gzh.familyline.ui.activity.MessageDetailActivity;
import com.galaxy_light.gzh.familyline.ui.adapter.MessageAdapter;
import com.galaxy_light.gzh.familyline.ui.presenter.MessagePresenter;
import com.galaxy_light.gzh.familyline.ui.view.MessageView;
import com.galaxy_light.gzh.familyline.utils.PopupManager;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new MessagePresenter(this).requestMessageData();
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

    private BaseQuickAdapter.OnItemClickListener itemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            MessageDetailActivity.fromMessage(getContext(), (MessageBean) adapter.getData().get(position));
        }
    };

    private BaseQuickAdapter.OnItemLongClickListener itemLongClickListener = new BaseQuickAdapter.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final BaseQuickAdapter adapter, View view, final int position) {
            PopupManager.getInstance().createMultiMenu(view, R.layout.popup_message_item, PopupManager.SIZE_WRAP, new int[]{R.id.tv_popup_delete, R.id.tv_popup_top}, new PopupManager.PopupChildListener() {
                @Override
                public void onChildClick(View v) {
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
                }
            });
            return true;
        }
    };
}
