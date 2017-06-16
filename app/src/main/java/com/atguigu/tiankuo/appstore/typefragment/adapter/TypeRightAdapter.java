package com.atguigu.tiankuo.appstore.typefragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.tiankuo.appstore.R;
import com.atguigu.tiankuo.appstore.app.GoodsInfoActivity;
import com.atguigu.tiankuo.appstore.domain.Constants;
import com.atguigu.tiankuo.appstore.homefragment.GoodsBean;
import com.atguigu.tiankuo.appstore.homefragment.HomeAdapter;
import com.atguigu.tiankuo.appstore.typefragment.domain.TypeBean;
import com.atguigu.tiankuo.appstore.utils.DensityUtil;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/6/16 0016.
 */

public class TypeRightAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private final LayoutInflater layoutInflater;

    /**
     * 接搜数据
     */
    private List<TypeBean.ResultBean.HotProductListBean> hot_product_list;
    private List<TypeBean.ResultBean.ChildBean> hsl_hot_right;
    //热卖推荐
    private static final int HOT = 0;
    //常用分类
    private static final int ORDINARY = 1;
    //当前类型
    private int currentType = HOT;


    public TypeRightAdapter(Context mContext, List<TypeBean.ResultBean> result) {
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
        hsl_hot_right = result.get(0).getChild();
        hot_product_list = result.get(0).getHot_product_list();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HOT) {
            return new HotViewHolder(mContext, layoutInflater.inflate(R.layout.item_hot_right, null));
        } else if (viewType == ORDINARY) {
            return new OrdinaryHolder(mContext, layoutInflater.inflate(R.layout.item_common_right, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOT) {
            HotViewHolder viewHolder = (HotViewHolder) holder;
            //设置数据
            viewHolder.setData(hot_product_list);
        } else if (getItemViewType(position) == ORDINARY) {
            OrdinaryHolder viewHolder = (OrdinaryHolder) holder;
            int realPosition = position - 1;
            viewHolder.setData(hsl_hot_right.get(realPosition), realPosition);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == HOT) {
            currentType = HOT;
        } else {
            currentType = ORDINARY;
        }
        return currentType;

    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        @InjectView(R.id.ll_hot_right)
        LinearLayout llHotRight;
        @InjectView(R.id.hsl_hot_right)
        HorizontalScrollView hslHotRight;

        public HotViewHolder(Context mContext, View inflate) {
            super(inflate);
            this.mContext = mContext;
            ButterKnife.inject(this, inflate);
        }

        public void setData(final List<TypeBean.ResultBean.HotProductListBean> hot_product_list) {
            for (int i = 0; i < hot_product_list.size(); i++) {

                //得到数据
                TypeBean.ResultBean.HotProductListBean listBean = hot_product_list.get(i);
                 //线性布局
                LinearLayout myLinear = new LinearLayout(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
                params.setMargins(DensityUtil.dip2px(mContext, 5), 0, DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 20));
                myLinear.setOrientation(LinearLayout.VERTICAL);
                myLinear.setGravity(Gravity.CENTER);
                myLinear.setTag(i);
                //图片
                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 80), DensityUtil.dip2px(mContext, 80));
                ivParams.setMargins(0, 0, 0, DensityUtil.dip2px(mContext, 10));
                //请求图片
                Glide.with(mContext).load(Constants.BASE_URL_IMAGE + listBean.getFigure()).into(imageView);
                myLinear.addView(imageView, ivParams);
                //文字
                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.parseColor("#ed3f3f"));
                textView.setText("￥" + listBean.getCover_price());
                myLinear.addView(textView, tvParams);
                llHotRight.addView(myLinear, params);
                //设置点击事件
                myLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) v.getTag();
                        String cover_price = hot_product_list.get(position).getCover_price();
                        String name = hot_product_list.get(position).getName();
                        String figure = hot_product_list.get(position).getFigure();
                        String product_id = hot_product_list.get(position).getProduct_id();
                        GoodsBean goodsBean = new GoodsBean();
                        goodsBean.setProduct_id(product_id);
                        goodsBean.setFigure(figure);
                        goodsBean.setCover_price(cover_price);
                        goodsBean.setName(name);

                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                        intent.putExtra(HomeAdapter.GOODS_BEAN, goodsBean);
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }

    class OrdinaryHolder extends RecyclerView.ViewHolder {
        private final Context mContext;

        @InjectView(R.id.iv_ordinary_right)
        ImageView ivOrdinaryRight;
        @InjectView(R.id.tv_ordinary_right)
        TextView tvOrdinaryRight;
        @InjectView(R.id.llRoot)
        LinearLayout llRoot;

        public OrdinaryHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.inject(this, itemView);
        }

        public void setData(TypeBean.ResultBean.ChildBean childBean, final int realPosition) {
            Log.e("TAG", realPosition + "-----");
            //设置图片
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE + childBean.getPic()).placeholder(R.drawable.new_img_loading_2)
                    .error(R.drawable.new_img_loading_2).into(ivOrdinaryRight);

            //设置名称
            tvOrdinaryRight.setText(childBean.getName());

            //设置点击事件
            llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "realPostion==" + realPosition, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
