package com.galaxy_light.gzh.familyline.ui.adapter;

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.MessageBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 消息Adapter
 * Created by gzh on 2017-9-19.
 */

public class MessageAdapter extends BaseItemDraggableAdapter<MessageBean, BaseViewHolder> {

    public MessageAdapter(int layoutResId, List<MessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        if (!TextUtils.isEmpty(item.getImageUrl())) {
            //设置用户头像
            Glide.with(mContext).load(item.getImageUrl()).into((CircleImageView) helper.getView(R.id.iv_message_avatar));
        }else{
            helper.setImageResource(R.id.iv_message_avatar,R.drawable.ic_launcher);
        }
        //设置用户名
        helper.setText(R.id.tv_message_username, item.getUsername());
        //设置最后一条消息
        helper.setText(R.id.tv_message_lastMessage, item.getLastMessage());
        //设置最后一条消息的时间
        helper.setText(R.id.tv_message_lastTime, item.getLastTime());
    }
}
