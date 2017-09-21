package com.galaxy_light.gzh.familyline.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.UserBean;

import java.util.List;

/**
 * 好友Adapter
 * Created by gzh on 2017-9-21.
 */

public class ContactAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {

    public ContactAdapter(@LayoutRes int layoutResId, @Nullable List<UserBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        //根据position获取首字母作为目录catalog
        String catalog = item.getFirstLetter();
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (helper.getLayoutPosition() == getPositionForSection(catalog)) {
            helper.setVisible(R.id.tv_contact_catalog, true);
            helper.setText(R.id.tv_contact_catalog, item.getFirstLetter().toUpperCase());
        } else {
            helper.getView(R.id.tv_contact_catalog).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.getImageUrl())) {
            //设置用户头像
            Glide.with(mContext).load(item.getImageUrl()).into((ImageView) helper.getView(R.id.iv_message_avatar));
        }else{
            helper.setImageResource(R.id.iv_contact_avatar,R.drawable.ic_launcher);
        }
        //设置用户名
        helper.setText(R.id.tv_contact_username, item.getUsername());
    }

    /**
     * 获取catalog首次出现位置
     * @param catalog 目录
     */
    private int getPositionForSection(String catalog) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = getData().get(i).getFirstLetter();
            if (catalog.equalsIgnoreCase(sortStr)) {
                return i;
            }
        }
        return -1;
    }
}
