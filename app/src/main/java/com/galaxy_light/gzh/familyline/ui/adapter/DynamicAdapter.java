package com.galaxy_light.gzh.familyline.ui.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.galaxy_light.gzh.familyline.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 动态Adapter
 * Created by gzh on 2017-10-24.
 */

public class DynamicAdapter extends BaseAdapter {
    private SparseArray<String> array;

    public DynamicAdapter(SparseArray<String> array) {
        this.array = array;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dynamic, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_icon.setImageResource(array.keyAt(position));
        holder.tv_name.setText(array.valueAt(position));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_dynamic_icon)
        CircleImageView iv_icon;
        @BindView(R.id.tv_dynamic_name)
        TextView tv_name;

        ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }
}
