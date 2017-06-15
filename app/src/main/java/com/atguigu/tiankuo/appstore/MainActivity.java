package com.atguigu.tiankuo.appstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.tiankuo.appstore.base.BaseFragment;
import com.atguigu.tiankuo.appstore.communityfragment.CommunityFragment;
import com.atguigu.tiankuo.appstore.homefragment.HomeFragment;
import com.atguigu.tiankuo.appstore.shoppingcartfragment.ShoppingCartFragment;
import com.atguigu.tiankuo.appstore.typefragment.TypeFragment;
import com.atguigu.tiankuo.appstore.userfragment.UserFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.frameLayout)
    FrameLayout frameLayout;

    @InjectView(R.id.rg_main)
    RadioGroup rgMain;

    private int position;
    private ArrayList<BaseFragment> fragments;
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initFragment();
        initListener();
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                }

                //根据位置切换到对应的Fragment
                BaseFragment baseFragment = getFragment(position);
                switchFragment(baseFragment);
            }
        });

        //默认选中首页--注意默认选中要放在后边
        rgMain.check(R.id.rb_home);
    }

    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    private void switchFragment(Fragment currentFragment) {
        if (tempFragment != currentFragment) {
            //得到FragmentMager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //如果没有添加就添加
            if (!currentFragment.isAdded()) {
                //缓存的隐藏
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }
                //添加
                ft.add(R.id.frameLayout, currentFragment);
            } else {
                //缓存的隐藏
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }
                //显示
                ft.show(currentFragment);
            }
            //事务提交
            ft.commit();
            //把当前的赋值成缓存的
            tempFragment = currentFragment;

        }
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int id = intent.getIntExtra("checkedid",R.id.rb_home);
        switch (id){
            case R.id.rb_home:
                rgMain.check(R.id.rb_home);
                break;
            case R.id.rb_cart:
                rgMain.check(R.id.rb_cart);
                break;
        }
    }

    private void defaultFragment(BaseFragment fragment) {
        tempFragment = fragment;
        //1.得到FragmentManger
        FragmentManager fm = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ta = fm.beginTransaction();
        //3.添加
        ta.add(R.id.frameLayout, tempFragment);
        //4.提交
        ta.commit();
    }

}
