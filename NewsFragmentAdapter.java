package com.tryndamere.lzm.alltesttwo.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


import com.tryndamere.lzm.alltesttwo.bean.UrlBean;
import com.tryndamere.lzm.alltesttwo.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;


public class NewsFragmentAdapter extends FragmentPagerAdapter {

    private List<UrlBean> list;
    String [] title = new String []{"本社介绍","履行职责","自身建设"};
    public NewsFragmentAdapter(FragmentManager fragmentManager,List<UrlBean> list) {
        super(fragmentManager);
        this.list = list;
    }


    @Override
    public Fragment getItem(int position) {

        NewsFragment newFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("channelId",list.get(position).getChannelId());
        bundle.putString("startNum",list.get(position).getStartNum());
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public int getCount() {
        return title.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
