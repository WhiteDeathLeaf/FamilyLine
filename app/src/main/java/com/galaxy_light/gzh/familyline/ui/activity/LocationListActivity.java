package com.galaxy_light.gzh.familyline.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.ui.adapter.LocationListAdapter;
import com.galaxy_light.gzh.familyline.ui.presenter.LocationListPresenter;
import com.galaxy_light.gzh.familyline.ui.view.LocationListView;
import com.galaxy_light.gzh.familyline.utils.MapUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class LocationListActivity extends AppCompatActivity implements LocationListView, EasyPermissions.PermissionCallbacks {

    private static final int RC_LOCATION = 3;
    @BindView(R.id.mv_locationList)
    MapView mvLocationList;
    @BindView(R.id.tv_locationList)
    TextView tvLocationList;
    @BindView(R.id.toolbar_locationList)
    Toolbar toolbarLocationList;
    @BindView(R.id.lv_locationList)
    ListView lvLocationList;
    private BaiduMap map;
    private boolean isFirstLoc = true;
    private LocationClient locationClient;
    private LocationListPresenter locationListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        ButterKnife.bind(this);
        locationListPresenter = new LocationListPresenter(this);
        initToolbar();
        requiresLocationPermission();
    }

    private void initToolbar() {
        setSupportActionBar(toolbarLocationList);
        if (getSupportActionBar() != null) {
            tvLocationList.setText(R.string.location_list);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initMap() {
        mvLocationList.removeViewAt(1);
        mvLocationList.showScaleControl(false);
        mvLocationList.showZoomControls(false);
        map = mvLocationList.getMap();
        map.setMyLocationEnabled(true);
    }

    private void initLocation() {
        locationClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd0911");
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationPoiList(true);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new MyLocationListener());
        locationClient.start();
    }

    @AfterPermissionGranted(RC_LOCATION)
    private void requiresLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            initMap();
            initLocation();
        } else {
            EasyPermissions.requestPermissions(this, "位置", RC_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            Toast.makeText(this, "位置", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setAdapter(LocationListAdapter adapter) {
        lvLocationList.setAdapter(adapter);
        lvLocationList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent();
            intent.putExtra("location", (String) parent.getItemAtPosition(position));
            setResult(3000, intent);
            finish();
        });
    }

    private class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String proCity = bdLocation.getProvince() + bdLocation.getCity();
            String cityDis = bdLocation.getCity() + bdLocation.getDistrict();
            String disStr = bdLocation.getDistrict() + bdLocation.getStreet();
            double latitude = bdLocation.getLatitude();
            double longitude = bdLocation.getLongitude();
            LatLng latLng = MapUtil.bdLatLag(longitude, latitude);
            MyLocationData locData = new MyLocationData.Builder()
                    .latitude(latLng.latitude).longitude(latLng.longitude)
                    .accuracy(100f).build();
            map.setMyLocationData(locData);
            locationListPresenter.loadLocationData(bdLocation.getPoiList(), proCity, cityDis, disStr);
            if (isFirstLoc) {
                isFirstLoc = false;
                MapStatus mapStatus = new MapStatus.Builder().target(latLng).zoom(18.0f).build();
                map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        locationClient.stop();
        map.setMyLocationEnabled(false);
        mvLocationList.onDestroy();
        super.onDestroy();
    }
}
