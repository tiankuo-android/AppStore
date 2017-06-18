package com.atguigu.tiankuo.appstore.userfragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.tiankuo.appstore.R;
import com.atguigu.tiankuo.appstore.base.BaseFragment;
import com.hankkin.gradationscroll.GradationScrollView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/6/11 0011.
 */

public class UserFragment extends BaseFragment implements GradationScrollView.ScrollViewListener {

    @InjectView(R.id.ib_user_icon_avator)
    ImageButton ibUserIconAvator;
    @InjectView(R.id.tv_username)
    TextView tvUsername;
    @InjectView(R.id.rl_header)
    RelativeLayout rlHeader;
    @InjectView(R.id.tv_all_order)
    TextView tvAllOrder;
    @InjectView(R.id.tv_user_pay)
    TextView tvUserPay;
    @InjectView(R.id.tv_user_receive)
    TextView tvUserReceive;
    @InjectView(R.id.tv_user_finish)
    TextView tvUserFinish;
    @InjectView(R.id.tv_user_drawback)
    TextView tvUserDrawback;
    @InjectView(R.id.tv_user_location)
    TextView tvUserLocation;
    @InjectView(R.id.tv_user_collect)
    TextView tvUserCollect;
    @InjectView(R.id.tv_user_coupon)
    TextView tvUserCoupon;
    @InjectView(R.id.tv_user_score)
    TextView tvUserScore;
    @InjectView(R.id.tv_user_prize)
    TextView tvUserPrize;
    @InjectView(R.id.tv_user_ticket)
    TextView tvUserTicket;
    @InjectView(R.id.tv_user_invitation)
    TextView tvUserInvitation;
    @InjectView(R.id.tv_user_callcenter)
    TextView tvUserCallcenter;
    @InjectView(R.id.tv_user_feedback)
    TextView tvUserFeedback;
    @InjectView(R.id.ll_root)
    LinearLayout llRoot;
    @InjectView(R.id.scrollview)
    GradationScrollView scrollview;
    @InjectView(R.id.tv_usercenter)
    TextView tvUsercenter;
    @InjectView(R.id.ib_user_setting)
    ImageButton ibUserSetting;
    @InjectView(R.id.ib_user_message)
    ImageButton ibUserMessage;
    private int height;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        initListeners();
    }

    private void initListeners() {
        tvUsercenter.setBackgroundColor(Color.argb((int) 0, 255,0,0));
        ViewTreeObserver vto = rlHeader.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rlHeader.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                //得到头部相对布局的高度
                height = rlHeader.getHeight();
                //监听ScrollView滑动监听
                scrollview.setScrollViewListener(UserFragment.this);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_username, R.id.ib_user_setting, R.id.ib_user_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_username:
//                Toast.makeText(mContext, "登录/注册", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_user_setting:
                Toast.makeText(mContext, "用户设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_user_message:
                Toast.makeText(mContext, "用户消息", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {

        if (y <= 0) {
            //设置标题的背景颜色  -透明
            tvUsercenter.setBackgroundColor(Color.argb((int) 0, 255,0,0));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            //滑动距离 ： 总距离 = 改变的透明度 ： 总透明度
            //改变的透明度 = (滑动距离 ：总距离) *总透明度

            tvUsercenter.setTextColor(Color.argb((int) alpha, 255,255,255));
            tvUsercenter.setBackgroundColor(Color.argb((int) alpha, 255,0,0));
        } else {
            //滑动到banner下面设置普通颜色 - 非透明
            tvUsercenter.setBackgroundColor(Color.argb((int) 255, 255,0,0));
        }
    }
}
