package com.galaxy_light.gzh.familyline.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我的Fragment
 * Created by gzh on 2017-9-20.
 */

public class MineFragment extends Fragment {
    @BindView(R.id.iv_mine_avatar)
    ImageView ivMineAvatar;
    @BindView(R.id.tv_mine_username)
    TextView tvMineUsername;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvMineUsername.setText(FamilyLineUser.getCurrentUser().getUsername());
        Glide.with(getContext()).load(((FamilyLineUser) (FamilyLineUser.getCurrentUser())).getAvatar().getUrl()).into(ivMineAvatar);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_mine_avatar)
    public void onViewClicked() {
    }
}
