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
import com.galaxy_light.gzh.familyline.ui.presenter.MessagePresenter;
import com.galaxy_light.gzh.familyline.ui.view.MessageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by gzh on 2017-9-19.
 */

public class MessageFragment extends Fragment implements MessageView{
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    Unbinder unbinder;

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
        rvMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        new MessagePresenter(this).requestMessageData(rvMessage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
