package com.galaxy_light.gzh.familyline.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;
import com.galaxy_light.gzh.familyline.ui.activity.HomeActivity;
import com.galaxy_light.gzh.familyline.ui.presenter.MinePresenter;
import com.galaxy_light.gzh.familyline.ui.view.MineView;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的Fragment
 * Created by gzh on 2017-9-20.
 */

public class MineFragment extends Fragment implements MineView {

    @BindView(R.id.iv_mine_avatar)
    CircleImageView ivMineAvatar;
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
        toGallery();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 200:
                UCrop.of(data.getData(), Uri.fromFile(new File(getContext().getCacheDir(), System.currentTimeMillis() + ".png")))
                        .start(getContext(), this);
                break;
            case UCrop.REQUEST_CROP:
                new MinePresenter(this).uploadAvatar(UCrop.getOutput(data));
                ((HomeActivity) getActivity()).setCurrentPage(3);
                break;
            case UCrop.RESULT_ERROR:
                Throwable cropError = UCrop.getError(data);
                break;
        }
    }

    private void toGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 200);
    }

    @Override
    public void updateAvatar(String url) {
        Glide.with(getContext()).load(url).into(ivMineAvatar);
    }
}