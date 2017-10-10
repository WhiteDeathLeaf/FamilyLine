package com.galaxy_light.gzh.familyline.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.galaxy_light.gzh.familyline.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 表情Adapter
 * Created by gzh on 2017/9/30.
 */

class EmojiAdapter extends BaseAdapter {
    private Context context;
    private String[] emojis;

    EmojiAdapter(Context context, String[] eachPageEmojis) {
        this.context = context;
        this.emojis = eachPageEmojis;
    }

    @Override
    public int getCount() {
        return null == emojis ? 0 : emojis.length;
    }

    @Override
    public String getItem(int position) {
        return null == emojis ? "" : emojis[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.item_emoji, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 27) {
            //第28个显示删除按钮
            holder.emojiTv.setBackgroundResource(R.drawable.ic_emojis_delete);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) holder.emojiTv.getLayoutParams();
            lp.bottomMargin = (int) context.getResources().getDimension(R.dimen.dp_12);
        } else {
            holder.emojiTv.setText(getItem(position));
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_emoji)
        TextView emojiTv;

        ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }


}