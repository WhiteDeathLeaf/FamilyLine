package com.galaxy_light.gzh.familyline.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.custom.view.ImageGridView;
import com.galaxy_light.gzh.familyline.model.bean.FriendCircleBean;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 亲友圈Adapter
 * Created by gzh on 2017-10-25.
 */

public class FriendCircleAdapter extends BaseQuickAdapter<FriendCircleBean, BaseViewHolder> {
    public FriendCircleAdapter(@LayoutRes int layoutResId, @Nullable List<FriendCircleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendCircleBean item) {
        if (!TextUtils.isEmpty(item.getAvatar())) {
            //设置用户头像
            Glide.with(mContext).load(item.getAvatar()).into((CircleImageView) helper.getView(R.id.iv_friend_circle_avatar));
        } else {
            helper.setImageResource(R.id.iv_friend_circle_avatar, R.drawable.ic_launcher);
        }
        //设置用户名
        helper.setText(R.id.tv_friend_circle_name, item.getName());
        //设置发布时间
        helper.setText(R.id.tv_friend_circle_time, item.getTime());
        //设置文本内容
        helper.setText(R.id.tv_friend_circle_content, item.getContent());
        //设置图片内容
        if (item.getImages() != null && item.getImages().size() > 0) {
            LinearLayout parent = helper.getView(R.id.ll_content_parent);
            if (item.getImages().size() == 1) {//只有一张图片
                ImageView imageView = new ImageView(mContext);
                Glide.with(mContext).load(item.getImages().get(0)).into(imageView);
                parent.addView(imageView);
            } else {
                ImageGridView gridView = new ImageGridView(mContext);
                gridView.setNumColumns(3);
                gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                gridView.setCacheColorHint(Color.TRANSPARENT);
                gridView.setGravity(Gravity.CENTER);
                gridView.setAdapter(new ImageAdapter(item.getImages()));
                parent.addView(gridView);
                gridView.setOnItemClickListener((parent1, view, position, id) -> listener.photoClick(view.findViewById(R.id.iv_image), item.getImages()));
            }
        }
    }

    private PhotoListener listener;

    public interface PhotoListener {
        void photoClick(ImageView imageView, ArrayList<String> imageUrls);
    }

    public void setPhotoListener(PhotoListener listener) {
        this.listener = listener;
    }
}
