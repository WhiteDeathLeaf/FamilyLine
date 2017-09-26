package com.galaxy_light.gzh.familyline.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.custom.view.IndexBar;
import com.galaxy_light.gzh.familyline.custom.view.LoadingDialog;
import com.galaxy_light.gzh.familyline.model.bean.UserBean;
import com.galaxy_light.gzh.familyline.ui.activity.ContactDetailActivity;
import com.galaxy_light.gzh.familyline.ui.activity.HomeActivity;
import com.galaxy_light.gzh.familyline.ui.adapter.ContactAdapter;
import com.galaxy_light.gzh.familyline.ui.presenter.ContactPresenter;
import com.galaxy_light.gzh.familyline.ui.view.ContactView;
import com.galaxy_light.gzh.familyline.utils.PopupManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 好友Fragment
 * Created by gzh on 2017-9-20.
 */

public class ContactFragment extends Fragment implements ContactView {

    @BindView(R.id.rv_contact)
    RecyclerView rvContact;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    Unbinder unbinder;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_contact, container, false);
        unbinder = ButterKnife.bind(this, view);
        new ContactPresenter(this).requestContactData();
        return view;
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
    public void setAdapter(final ContactAdapter adapter) {
        adapter.setOnItemChildClickListener(childClickListener);
        adapter.setOnItemChildLongClickListener(childLongClickListener);
        rvContact.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContact.setAdapter(adapter);
        indexBar.setOnStrSelectCallBack((index, selectStr) -> {
            for (int i = 0; i < adapter.getData().size(); i++) {
                if (selectStr.equalsIgnoreCase(adapter.getData().get(i).getFirstLetter())) {
                    rvContact.scrollToPosition(i); // 移动到首字母出现的位置
                    return;
                }
            }
        });
    }

    private BaseQuickAdapter.OnItemChildClickListener childClickListener = (adapter, view, position) -> {
        ContactDetailActivity.fromContact(getContext(), (UserBean) adapter.getData().get(position), ((UserBean) adapter.getData().get(position)).getId());
        ((HomeActivity) getActivity()).setCurrentPage(1);
    };
    private BaseQuickAdapter.OnItemChildLongClickListener childLongClickListener = (adapter, view, position) -> {
        PopupManager.getInstance().createEasyMenu(view, new String[]{"备注", "标签"}, v -> {
            switch (v.getId()) {
                case R.id.tv_popup_easy_1:
                    Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_popup_easy_2:
                    Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
        return true;
    };
}
