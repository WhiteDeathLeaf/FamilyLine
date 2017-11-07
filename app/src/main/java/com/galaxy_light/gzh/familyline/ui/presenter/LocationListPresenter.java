package com.galaxy_light.gzh.familyline.ui.presenter;

import com.baidu.location.Poi;
import com.galaxy_light.gzh.familyline.ui.adapter.LocationListAdapter;
import com.galaxy_light.gzh.familyline.ui.view.LocationListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 定位列表Presenter
 * Created by gzh on 2017/10/31.
 */

public class LocationListPresenter {
    private LocationListView locationListView;
    private LocationListAdapter adapter;

    public LocationListPresenter(LocationListView locationListView) {
        this.locationListView = locationListView;
    }

    public void loadLocationData(List<Poi> list, String proCity, String cityDis, String disStr) {
        List<String> address = new ArrayList<>();
        address.add(proCity);
        address.add(cityDis);
        address.add(disStr);
        for (Poi poi : list) {
            address.add(poi.getName());
        }
        if (adapter == null) {
            adapter = new LocationListAdapter(address);
            locationListView.setAdapter(adapter);
        }
    }
}
