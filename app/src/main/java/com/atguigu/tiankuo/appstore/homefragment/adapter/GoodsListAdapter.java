package com.atguigu.tiankuo.appstore.homefragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.tiankuo.appstore.R;
import com.atguigu.tiankuo.appstore.app.GoodsListActivity;
import com.atguigu.tiankuo.appstore.app.TypeListBean;
import com.atguigu.tiankuo.appstore.domain.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.atguigu.tiankuo.appstore.R.id.iv_hot;
import static com.atguigu.tiankuo.appstore.R.id.tv_name;
import static com.atguigu.tiankuo.appstore.R.id.tv_price;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/6/19 0019.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> {

    private final List<TypeListBean.ResultBean.PageDataBean> datas;
    private final GoodsListActivity mContext;
    private OnItemClickListener onItemClickListener;

    public GoodsListAdapter(GoodsListActivity mContext, List<TypeListBean.ResultBean.PageDataBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_goods_list, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //根据位置得到对应的数据
        TypeListBean.ResultBean.PageDataBean pageDataBean = datas.get(position);

        //绑定数据
        holder.tvName.setText(pageDataBean.getName());
        holder.tvPrice.setText("￥" + pageDataBean.getCover_price());

        //设置图片
        String imageUrl = Constants.BASE_URL_IMAGE + pageDataBean.getFigure();
        //加载图片
        Glide.with(mContext).load(imageUrl).placeholder(R.drawable.new_img_loading_2).error(R.drawable.new_img_loading_2).into(holder.ivHot);

    }



    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(iv_hot)
        ImageView ivHot;
        @InjectView(tv_name)
        TextView tvName;
        @InjectView(tv_price)
        TextView tvPrice;
        private GoodsListAdapter.OnItemClickListener onItemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            //设置item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(datas.get(getLayoutPosition()));
                    }
                }
            });
        }
    }
        public interface OnItemClickListener {
            public void onItemClick(TypeListBean.ResultBean.PageDataBean dataBean);
        }

        public void setOnItemClickListener(OnItemClickListener l) {
            this.onItemClickListener = l;
        }
    }
