package com.galaxy_light.gzh.familyline.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.UserBean;

import java.util.List;

/**
 * 搜索Adapter
 * Created by gzh on 2017/9/19.
 */

public class SearchAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {

    public SearchAdapter(@LayoutRes int layoutResId, @Nullable List<UserBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        if (!TextUtils.isEmpty(item.getImageUrl())) {
            //设置用户头像
            Glide.with(mContext).load(item.getImageUrl()).into((ImageView) helper.getView(R.id.iv_search_avatar));
        } else {
            helper.setImageResource(R.id.iv_search_avatar, R.drawable.ic_launcher);
        }
        //设置用户名
        helper.setText(R.id.tv_search_username, item.getUsername());
    }
}
