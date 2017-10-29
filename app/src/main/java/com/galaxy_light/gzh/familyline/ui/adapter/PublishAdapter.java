package com.galaxy_light.gzh.familyline.ui.adapter;

import android.graphics.Bitmap;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.galaxy_light.gzh.familyline.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发表Adapter
 * Created by gzh on 2017-10-29.
 */

public class PublishAdapter extends BaseAdapter {
    private List<Bitmap> list = new ArrayList<>();
    public static final int TYPE_ADD = 0;
    public static final int TYPE_IMAGE = 1;

    @IntDef({TYPE_ADD, TYPE_IMAGE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public void addImage(Bitmap bitmap) {
        if (bitmap != null) {
            list.add(bitmap);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return Math.min(list.size() + 1, 9);
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) return TYPE_ADD;
        return TYPE_IMAGE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case TYPE_ADD:
                AddViewHolder addViewHolder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_add, parent, false);
                    addViewHolder = new AddViewHolder(convertView);
                    convertView.setTag(addViewHolder);
                } else {
                    addViewHolder = (AddViewHolder) convertView.getTag();
                }
                if (position == 9) {
                    addViewHolder.ib_image_add.setVisibility(View.GONE);
                } else {
                    addViewHolder.ib_image_add.setVisibility(View.VISIBLE);
                }
                ViewGroup.LayoutParams params = addViewHolder.ib_image_add.getLayoutParams();
                params.width = parent.getWidth() / 5;
                params.height = parent.getWidth() / 5;
                addViewHolder.ib_image_add.setLayoutParams(params);
                addViewHolder.ib_image_add.setOnClickListener(view -> listener.addClick());
                break;
            case TYPE_IMAGE:
                ImageViewHolder imageViewHolder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
                    imageViewHolder = new ImageViewHolder(convertView);
                    convertView.setTag(imageViewHolder);
                } else {
                    imageViewHolder = (ImageViewHolder) convertView.getTag();
                }
                ViewGroup.LayoutParams layoutParams = imageViewHolder.iv_image.getLayoutParams();
                layoutParams.width = parent.getWidth() / 5;
                layoutParams.height = parent.getWidth() / 5;
                imageViewHolder.iv_image.setLayoutParams(layoutParams);
                imageViewHolder.iv_image.setImageBitmap(list.get(position));
                imageViewHolder.iv_image.setOnClickListener(view -> listener.imageClick(list.get(position)));
                break;
        }
        return convertView;
    }

    static class ImageViewHolder {
        @BindView(R.id.iv_image)
        ImageView iv_image;

        ImageViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }

    static class AddViewHolder {
        @BindView(R.id.ib_image_add)
        ImageButton ib_image_add;

        AddViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }

    private ImageListener listener;

    public interface ImageListener {
        void imageClick(Bitmap bitmap);

        void addClick();
    }

    public void setImageListener(ImageListener listener) {
        this.listener = listener;
    }
}