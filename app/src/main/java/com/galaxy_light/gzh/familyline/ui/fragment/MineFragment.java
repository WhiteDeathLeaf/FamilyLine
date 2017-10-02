package com.galaxy_light.gzh.familyline.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;
import com.galaxy_light.gzh.familyline.ui.activity.HomeActivity;
import com.galaxy_light.gzh.familyline.ui.activity.WelcomeActivity;
import com.galaxy_light.gzh.familyline.ui.presenter.MinePresenter;
import com.galaxy_light.gzh.familyline.ui.view.MineView;
import com.galaxy_light.gzh.familyline.utils.PopupManager;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 我的Fragment
 * Created by gzh on 2017-9-20.
 */

public class MineFragment extends Fragment implements MineView, EasyPermissions.PermissionCallbacks {

    private static final int RC_CAMERA = 2;
    @BindView(R.id.iv_mine_avatar)
    CircleImageView ivMineAvatar;
    @BindView(R.id.tv_mine_username)
    TextView tvMineUsername;
    Unbinder unbinder;
    @BindView(R.id.ll_parent)
    LinearLayout llParent;

    private MinePresenter presenter;
    private String filePath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        FamilyLineUser user = AVUser.getCurrentUser(FamilyLineUser.class);
        presenter = new MinePresenter(this);
        tvMineUsername.setText(user.getUsername());
        presenter.initAvatar(user.getAvatar().getUrl());
        return view;
    }

    @AfterPermissionGranted(RC_CAMERA)
    private void methodRequiresCameraPermission() {
        String perm = Manifest.permission.CAMERA;
        if (EasyPermissions.hasPermissions(getContext(), perm)) {
            filePath = toCamera();
        } else {
            EasyPermissions.requestPermissions(this, "相机",
                    RC_CAMERA, perm);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(getContext(), "Returned from app settings to MainActivity with the following permissions:\n" +
                    "        \\n\\nCamera: %s\n" , Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_mine_avatar)
    public void updateAvatar() {
        PopupManager.getInstance().createMultiMenu(llParent, R.layout.popup_pic_item, PopupManager.SIZE_FULL_WIDTH, false,
                new int[]{R.id.btn_camera, R.id.btn_gallery, R.id.btn_cancel}, v -> {
                    switch (v.getId()) {
                        case R.id.btn_camera:
                            methodRequiresCameraPermission();
                            break;
                        case R.id.btn_gallery:
                            toGallery();
                            break;
                        case R.id.btn_cancel:
                            //dismiss
                            break;
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((HomeActivity) getActivity()).setCurrentPage(3);
        switch (requestCode) {
            case 200:
                if (data != null) {
                    UCrop.of(data.getData(), Uri.fromFile(new File(getContext().getCacheDir(), System.currentTimeMillis() + ".png")))
                            .start(getContext(), this);
                }
                break;
            case 300:
                if (new File(filePath).exists()) {
                    UCrop.of(Uri.fromFile(new File(filePath)), Uri.fromFile(new File(getContext().getCacheDir(), System.currentTimeMillis() + ".png")))
                            .start(getContext(), this);
                }
                break;
            case UCrop.REQUEST_CROP:
                if (UCrop.getOutput(data) != null) {
                    presenter.uploadAvatar(UCrop.getOutput(data));
                }
                break;
            case UCrop.RESULT_ERROR:
                Toast.makeText(getContext(), "错误", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void toGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 200);
    }

    public String toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FamilyLinePic";
        String fileName = System.currentTimeMillis() + ".png";
        File dir = new File(path);
        File file = new File(dir, fileName);
        if (!dir.exists()) {
            dir.mkdir();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, 300);
        return path + "/" + fileName;
    }

    @Override
    public void updateAvatar(String url) {
        Glide.with(getContext()).load(url).into(ivMineAvatar);
    }

    @Override
    public void logoutSuccess() {
        startActivity(new Intent(getContext(), WelcomeActivity.class));
        getActivity().finish();
    }

    @OnClick(R.id.btn_mine_logout)
    public void logout() {
        presenter.logout();
    }
}