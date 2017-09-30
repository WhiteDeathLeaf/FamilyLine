package com.galaxy_light.gzh.familyline.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 表情PagerAdapter
 * Created by PC on 2017/10/1.
 */

public class EmojiPagerAdapter extends PagerAdapter {
    private Context context;
    private String[] emojis;//216个表情字符
    private List<View> pages;//展示的页面
    private OnEmojiClickListener emojiClickListener;//表情点击监听接口

    public EmojiPagerAdapter(Context context, String[] emojis) {
        this.context = context;
        this.emojis = emojis;
        this.pages = getPagerList();
    }

    @Override
    public int getCount() {
        return null == pages ? 0 : pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = pages.get(position);
        if (null != view) {
            container.addView(view);
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 获取所有表情GridView页面的集合
     *
     * @return
     */
    public List<View> getPagerList() {
        List<View> pagers = null;
        String[] eachPageEmojis;
        if (null != emojis && emojis.length > 0) {
            pagers = new ArrayList<>();
            int pageCount = emojis.length / 27;//共8页表情
            for (int i = 0; i < pageCount; i++) {
                GridView gridView = new GridView(context);
                gridView.setNumColumns(7);
                gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                gridView.setCacheColorHint(Color.TRANSPARENT);
                gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
                gridView.setGravity(Gravity.CENTER);
                eachPageEmojis = new String[28];
                //总共216个表情字符,索引变化为:0-26,27-53,54-80,81-107,108-134,135-161,162-188,189-215
                System.arraycopy(emojis, i * 27, eachPageEmojis, 0, 27);
                eachPageEmojis[27] = "del";//第28是删除按钮,用特殊字符串表示
                gridView.setAdapter(new EmojiAdapter(context, eachPageEmojis));
                //点击表情监听
                gridView.setOnItemClickListener((parent, view, position, id) -> {
                    //获取选中的表情字符
                    String emoji = (String) parent.getAdapter().getItem(position);
                    if (null != emojiClickListener) {
                        emojiClickListener.onClick(emoji);
                    }
                });
                pagers.add(gridView);
            }
        }
        return pagers;
    }

    /**
     * 表情点击监听器
     */
    public interface OnEmojiClickListener {
        void onClick(String emoji);
    }

    public void setOnEmojiClickListener(OnEmojiClickListener l) {
        this.emojiClickListener = l;
    }
}