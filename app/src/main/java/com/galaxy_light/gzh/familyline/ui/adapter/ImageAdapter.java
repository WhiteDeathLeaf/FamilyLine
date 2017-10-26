package com.galaxy_light.gzh.familyline.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.galaxy_light.gzh.familyline.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图片Adapter
 * Created by gzh on 2017/9/30.
 */

class ImageAdapter extends BaseAdapter {
    private List<String> images;

    ImageAdapter(List<String> images) {
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public String getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ViewGroup.LayoutParams params = holder.iv_image.getLayoutParams();
        params.width = parent.getWidth() / 3;
        params.height = parent.getWidth() / 3;
        holder.iv_image.setLayoutParams(params);
        Glide.with(parent.getContext()).load(images.get(position)).into(holder.iv_image);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_image)
        ImageView iv_image;

        ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }


}