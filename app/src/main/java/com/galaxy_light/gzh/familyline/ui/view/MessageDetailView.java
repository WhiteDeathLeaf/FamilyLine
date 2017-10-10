package com.galaxy_light.gzh.familyline.ui.view;

import com.galaxy_light.gzh.familyline.ui.adapter.EmojiPagerAdapter;
import com.galaxy_light.gzh.familyline.ui.adapter.MessageDetailAdapter;

/**
 * 消息详情View
 * Created by gzh on 2017-9-22.
 */

public interface MessageDetailView {
    void setDetailAdapter(MessageDetailAdapter adapter);

    void setEmojiAdapter(EmojiPagerAdapter adapter);

    void showMessage(String message);

    void moveToLast(int position);
}
