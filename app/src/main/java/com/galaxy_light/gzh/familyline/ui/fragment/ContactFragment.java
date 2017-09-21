package com.galaxy_light.gzh.familyline.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.custom.view.IndexBar;
import com.galaxy_light.gzh.familyline.custom.view.LoadingDialog;
import com.galaxy_light.gzh.familyline.ui.adapter.ContactAdapter;
import com.galaxy_light.gzh.familyline.ui.presenter.ContactPresenter;
import com.galaxy_light.gzh.familyline.ui.view.ContactView;

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
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new ContactPresenter(this).requestContactData();
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
        rvContact.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContact.setAdapter(adapter);
        indexBar.setOnStrSelectCallBack(new IndexBar.IndexSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                for (int i = 0; i < adapter.getData().size(); i++) {
                    if (selectStr.equalsIgnoreCase(adapter.getData().get(i).getFirstLetter())) {
                        rvContact.scrollToPosition(i); // 移动到首字母出现的位置
                        return;
                    }
                }
            }
        });
    }
}
