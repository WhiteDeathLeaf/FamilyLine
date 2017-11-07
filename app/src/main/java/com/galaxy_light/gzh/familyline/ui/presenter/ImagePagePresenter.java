package com.galaxy_light.gzh.familyline.ui.presenter;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.galaxy_light.gzh.familyline.ui.adapter.ImagePagerAdapter;
import com.galaxy_light.gzh.familyline.ui.view.ImagePageView;
import com.galaxy_light.gzh.familyline.utils.DensityUtil;

import java.util.ArrayList;

/**
 * 图片页Presenter
 * Created by gzh on 2017-10-30.
 */

public class ImagePagePresenter {
    private ImagePageView imagePageView;
    private ImagePagerAdapter adapter;

    public ImagePagePresenter(ImagePageView imagePageView) {
        this.imagePageView = imagePageView;
    }

    public ArrayList<ImageView> initData(Context context, ArrayList<String> list, LinearLayout layout) {
        ArrayList<ImageView> views = new ArrayList<>();
        ArrayList<ImageView> indicators = new ArrayList<>();
        for (String url : list) {
            ImageView imageView1 = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5));
            params.setMarginEnd(DensityUtil.dip2px(context, 2));
            imageView1.setLayoutParams(params);
            imageView1.setBackgroundColor(Color.WHITE);
            imageView1.setAlpha(0.3f);
            layout.addView(imageView1);
            indicators.add(imageView1);
            ImageView imageView = new ImageView(context);
            Glide.with(context).load(url).into(imageView);
            imageView.setOnClickListener(v -> imagePageView.pageClick());
            views.add(imageView);
        }
        if (adapter == null) {
            adapter = new ImagePagerAdapter(views);
            imagePageView.setAdapter(adapter);
        }
        return indicators;
    }

}
