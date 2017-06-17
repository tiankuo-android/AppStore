package com.atguigu.tiankuo.appstore.communityfragment.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.tiankuo.appstore.base.BaseFragment;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/6/16 0016.
 */
public class HotPostFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(23);
        return textView;
    }

    @Override
    public void initData() {
        textView.setText("热帖内容");
    }
}
