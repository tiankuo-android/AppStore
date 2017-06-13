package com.atguigu.tiankuo.appstore.shoppingcartfragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.tiankuo.appstore.R;
import com.atguigu.tiankuo.appstore.domain.Constants;
import com.atguigu.tiankuo.appstore.homefragment.GoodsBean;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/6/13 0013.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    private final List<GoodsBean> datas;
    private final Context mContext;
    private final CheckBox checkboxAll;
    private final TextView tvShopcartTotal;
    private final CheckBox checkboxDeleteAll;
    private LayoutInflater inflater;
    private static OnItemClickListener itemClickListener;


    public ShoppingCartAdapter(Context mContext, List<GoodsBean> datas, CheckBox checkboxAll, TextView tvShopcartTotal, CheckBox checkboxDeleteAll) {
        this.mContext = mContext;
        this.datas = datas;
        inflater = LayoutInflater.from(mContext);
        this.checkboxAll = checkboxAll;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxDeleteAll = checkboxDeleteAll;

        //显示总价格
        showTotalPrice();

    }

    void showTotalPrice() {
        tvShopcartTotal.setText(getTotalPrice() + "");
    }

    private double getTotalPrice() {
        double total = 0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isChecked())
                    total += Double.parseDouble(goodsBean.getCover_price()) * Double.parseDouble(goodsBean.getNumber() + "");
            }
        }
        return total;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_shop_cart, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //1.更加位置得到数据
        final GoodsBean goodsBean = datas.get(position);
        //2.绑定数据
        holder.cbGov.setChecked(goodsBean.isChecked());
        //图片
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(holder.ivGov);
        holder.tvDescGov.setText(goodsBean.getName());
        //设置价格
        holder.tvPriceGov.setText("￥" + goodsBean.getCover_price());
        holder.addSubView.setValue(goodsBean.getNumber());

        holder.addSubView.setMinValue(1);
        //设置库存
        holder.addSubView.setMaxValue(100);

        //设置商品数量
        holder.addSubView.setValue(goodsBean.getNumber());
        //设置点击增加减少按钮的监听
        holder.addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void numberChange(int value) {
                //对象设置数据
                goodsBean.setNumber(value);
                //更加数据
                CartStorage.getInstance(mContext).updateData(goodsBean);
                //显示总价格
                showTotalPrice();

            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isChecked()) {
                    checkboxAll.setChecked(false);
                    checkboxDeleteAll.setChecked(false);
                } else {
                    number++;
                }
            }

            if (datas.size() == number) {
                checkboxAll.setChecked(true);
                checkboxDeleteAll.setChecked(true);
            }
        } else {
            checkboxAll.setChecked(false);
            checkboxDeleteAll.setChecked(false);
        }

    }

    public void checkAll_none(boolean checked) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setChecked(checked);
                checkboxAll.setChecked(checked);
                notifyItemChanged(i);
            }
        } else {
            checkboxAll.setChecked(false);
        }

    }

    public void deleteData() {
        if(datas != null && datas.size() >0){
            for (int i=0;i<datas.size();i++){
                GoodsBean goodsBean = datas.get(i);
                if(goodsBean.isChecked()){
                    //1.内存中删除
                    datas.remove(goodsBean);
                    //2.本地也好保持
                    CartStorage.getInstance(mContext).deleteData(goodsBean);
                    //刷新数据
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.cb_gov)
        CheckBox cbGov;
        @InjectView(R.id.iv_gov)
        ImageView ivGov;
        @InjectView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @InjectView(R.id.tv_price_gov)
        TextView tvPriceGov;
        @InjectView(R.id.AddSubView)
        com.atguigu.tiankuo.appstore.shoppingcartfragment.AddSubView addSubView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClickListener(v, getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.itemClickListener = l;
    }
}
