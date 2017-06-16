package com.atguigu.tiankuo.appstore.communityfragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atguigu.tiankuo.appstore.communityfragment.fragment.HotPostFragment;
import com.atguigu.tiankuo.appstore.communityfragment.fragment.NewPostFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/6/16 0016.
 */

public class CommunityViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private String[] titles = new String[]{"新帖", "热帖"};

    public CommunityViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments() {
        fragmentList = new ArrayList<>();

        NewPostFragment newPostFragment = new NewPostFragment();
        fragmentList.add(newPostFragment);

        HotPostFragment hotPostFragment = new HotPostFragment();
        fragmentList.add(hotPostFragment);

    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];

    }
}
