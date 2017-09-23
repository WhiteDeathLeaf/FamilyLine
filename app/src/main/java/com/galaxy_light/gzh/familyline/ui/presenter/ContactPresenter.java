package com.galaxy_light.gzh.familyline.ui.presenter;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.UserBean;
import com.galaxy_light.gzh.familyline.ui.adapter.ContactAdapter;
import com.galaxy_light.gzh.familyline.ui.view.ContactView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 好友Presenter
 * Created by gzh on 2017-9-21.
 */

public class ContactPresenter {
    private ContactView contactView;
    private List<UserBean> userBeen;
    private ContactAdapter adapter;

    public ContactPresenter(ContactView contactView) {
        this.contactView = contactView;
    }

    public void requestContactData() {
        contactView.showLoading();
        if (userBeen == null) {
            userBeen = new ArrayList<>();
        }
        userBeen.add(new UserBean("","张三",""));
        userBeen.add(new UserBean("","李四",""));
        userBeen.add(new UserBean("","王二",""));
        userBeen.add(new UserBean("","波波",""));
        userBeen.add(new UserBean("","阿旺",""));
        userBeen.add(new UserBean("","方方",""));
        userBeen.add(new UserBean("","大娃",""));
        userBeen.add(new UserBean("","二娃",""));
        userBeen.add(new UserBean("","三娃",""));
        userBeen.add(new UserBean("","四娃",""));
        userBeen.add(new UserBean("","五娃",""));
        userBeen.add(new UserBean("","六娃",""));
        userBeen.add(new UserBean("","七娃",""));
        Collections.sort(userBeen);
        if (adapter==null){
            adapter=new ContactAdapter(R.layout.item_home_contact,userBeen);
            contactView.setAdapter(adapter);
        }
        contactView.hideLoading();
    }

}
