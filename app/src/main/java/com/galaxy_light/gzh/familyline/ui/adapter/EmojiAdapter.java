package com.galaxy_light.gzh.familyline.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.galaxy_light.gzh.familyline.R;

import java.util.List;

/**
 * 表情Adapter
 * Created by gzh on 2017-9-30.
 */

public class EmojiAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    public EmojiAdapter(@LayoutRes int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        Glide.with(mContext).load(item).into((ImageView) helper.getView(R.id.iv_emoji));
        helper.addOnClickListener(R.id.iv_emoji);
    }
}
