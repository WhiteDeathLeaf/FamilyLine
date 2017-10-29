package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.ui.adapter.PublishAdapter;
import com.galaxy_light.gzh.familyline.ui.presenter.PublishPresenter;
import com.galaxy_light.gzh.familyline.ui.view.PublishView;
import com.galaxy_light.gzh.familyline.utils.ImageUtil;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishActivity extends AppCompatActivity implements PublishView {

    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.toolbar_publish)
    Toolbar toolbarPublish;
    @BindView(R.id.tet_publish)
    EditText tetPublish;
    @BindView(R.id.gv_publish)
    GridView gvPublish;
    @BindView(R.id.btn_publish)
    Button btnPublish;
    private PublishPresenter publishPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        publishPresenter = new PublishPresenter(this);
        initToolbar();
        publishPresenter.addImage(null);
    }

    private void initToolbar() {
        setSupportActionBar(toolbarPublish);
        if (getSupportActionBar() != null) {
            tvPublish.setText(R.string.publish_content);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @OnClick({R.id.tv_location, R.id.btn_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                break;
            case R.id.btn_publish:
                break;
        }
    }

    @Override
    public void setAdapter(PublishAdapter adapter) {
        adapter.setImageListener(imageListener);
        gvPublish.setAdapter(adapter);
    }

    private PublishAdapter.ImageListener imageListener = new PublishAdapter.ImageListener() {
        @Override
        public void imageClick(Bitmap bitmap) {

        }

        @Override
        public void addClick() {
            toGallery();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 400:
                if (data!= null) {
                    UCrop.of(data.getData(), Uri.fromFile(new File(getCacheDir(), System.currentTimeMillis() + ".png")))
                            .start(this);
                }
                break;
            case UCrop.REQUEST_CROP:
                if (UCrop.getOutput(data) != null) {
                    Bitmap bitmap = ImageUtil.convertBitmap(UCrop.getOutput(data).getPath(), 1080, 1920);
                    publishPresenter.addImage(bitmap);
                }
                break;
            case UCrop.RESULT_ERROR:
                Toast.makeText(this, "错误", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void toGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 400);
    }
}
