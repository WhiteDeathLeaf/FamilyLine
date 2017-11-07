package com.galaxy_light.gzh.familyline.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.ui.adapter.ImagePagerAdapter;
import com.galaxy_light.gzh.familyline.ui.presenter.ImagePagePresenter;
import com.galaxy_light.gzh.familyline.ui.view.ImagePageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagePageActivity extends AppCompatActivity implements ImagePageView {

    @BindView(R.id.vp_image)
    ViewPager vpImage;
    @BindView(R.id.ll_image_indicator)
    LinearLayout llImageIndicator;
    private ArrayList<ImageView> indicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_page);
        ButterKnife.bind(this);
        int index = getIntent().getIntExtra("index", 0);
        ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra("imageUrls");
        ImagePagePresenter imagePagePresenter = new ImagePagePresenter(this);
        indicators = imagePagePresenter.initData(this, list, llImageIndicator);
        vpImage.addOnPageChangeListener(pageChangeListener);
        vpImage.setCurrentItem(index, false);
        indicators.get(index).setAlpha(1.0f);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < indicators.size(); i++) {
                if (i == position) {
                    indicators.get(i).setAlpha(1.0f);
                } else {
                    indicators.get(i).setAlpha(0.3f);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void setAdapter(ImagePagerAdapter adapter) {
        vpImage.setAdapter(adapter);
    }

    @Override
    public void pageClick() {
        finish();
    }
}
