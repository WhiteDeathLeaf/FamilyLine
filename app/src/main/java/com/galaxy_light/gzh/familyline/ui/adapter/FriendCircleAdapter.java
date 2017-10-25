package com.galaxy_light.gzh.familyline.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.FriendCircleBean;

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
        helper.setText(R.id.tv_friend_circle_name, item.getName());
        helper.setText(R.id.tv_friend_circle_time, item.getTime());
        helper.setText(R.id.tv_friend_circle_content, item.getContent());
        if (item.getImages() != null && item.getImages().size() > 0) {
            LinearLayout parent = helper.getView(R.id.ll_content_parent);
            if (item.getImages().size() == 1) {
                ImageView imageView = new ImageView(mContext);
                Glide.with(mContext).load(item.getImages().get(0)).into(imageView);
                parent.addView(imageView);
            } else {
                for (String imageUrl : item.getImages()) {
                    // TODO: 2017-10-25 宫格展示
                }
            }
        }
    }
}
