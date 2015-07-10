package me.whiteworld.zlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import me.whiteworld.zlist.model.Info;

/**
 * Created by whiteworld on 2015/3/26.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private int count = 1;
    private List<Info> infoList;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return infoList.get(position).getName();
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int cnt) {
        count = cnt;
    }

    public void setInfoList(List<Info> infoList) {
        this.infoList = infoList;
        count = infoList.size();
    }

    @Override
    public Fragment getItem(int position) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(Consts.URL_KEY, infoList.get(position).getUrl());
        fragment.setArguments(args);
        return fragment;
    }
}