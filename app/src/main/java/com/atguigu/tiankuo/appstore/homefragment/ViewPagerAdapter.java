package com.atguigu.tiankuo.appstore.homefragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atguigu.tiankuo.appstore.domain.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/6/12 0012.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private final Context mContext;
    private final List<HomeBean.ResultBean.ActInfoBean> datas;

    public ViewPagerAdapter(Context mContext, List<HomeBean.ResultBean.ActInfoBean> act_info) {
        this.mContext = mContext;
        this.datas = act_info;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        HomeBean.ResultBean.ActInfoBean actInfoBean = datas.get(position);
        //联网请求
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + actInfoBean.getIcon_url()).into(imageView);
        container.addView(imageView);

        //添加点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null) {
                    clickListener.onItemClick(position);
                }
            }
        });

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setOnItemOnClickListener(OnItemClickListener onItemClickListener) {
        this.clickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(int position);
    }

    private OnItemClickListener clickListener;

}
