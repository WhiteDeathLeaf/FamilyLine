package com.galaxy_light.gzh.familyline.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.galaxy_light.gzh.familyline.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 定位列表Adapter
 * Created by gzh on 2017-11-7.
 */

public class LocationListAdapter extends BaseAdapter {
    private List<String> list;

    public LocationListAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_location.setText(list.get(position));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_location_list)
        TextView tv_location;

        ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }
}
