package com.galaxy_light.gzh.familyline.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.ui.adapter.DynamicAdapter;
import com.galaxy_light.gzh.familyline.ui.presenter.DynamicPresenter;
import com.galaxy_light.gzh.familyline.ui.view.DynamicView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 动态Fragment
 * Created by gzh on 2017-9-20.
 */

public class DynamicFragment extends Fragment implements DynamicView {
    @BindView(R.id.gv_dynamic)
    GridView gvDynamic;
    Unbinder unbinder;
    private DynamicPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_dynamic, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new DynamicPresenter(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setAdapter(DynamicAdapter adapter) {
        gvDynamic.setAdapter(adapter);
        gvDynamic.setOnItemClickListener((parent, view, position, id) -> presenter.open(getActivity(), position));
    }
}
