package com.galaxy_light.gzh.familyline.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.MessageDetailBean;

import java.util.List;

/**
 * Created by gzh on 2017-9-22.
 */

public class MessageDetailAdapter extends BaseMultiItemQuickAdapter<MessageDetailBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MessageDetailAdapter(List<MessageDetailBean> data) {
        super(data);
        addItemType(MessageDetailBean.OTHER, R.layout.item_message_detail_other);
        addItemType(MessageDetailBean.MINE, R.layout.item_message_detail_mine);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageDetailBean item) {
        switch (helper.getItemViewType()) {
            case MessageDetailBean.OTHER:
                helper.setText(R.id.tv_content_other,item.getMessageContent());
                break;
            case MessageDetailBean.MINE:
                helper.setText(R.id.tv_content_mine,item.getMessageContent());
                break;
        }
    }
}
