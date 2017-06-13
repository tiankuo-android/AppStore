package com.atguigu.tiankuo.appstore.shoppingcartfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.tiankuo.appstore.R;
import com.atguigu.tiankuo.appstore.base.BaseFragment;
import com.atguigu.tiankuo.appstore.homefragment.GoodsBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/6/11 0011.
 */

public class ShoppingCartFragment extends BaseFragment {
    @InjectView(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @InjectView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @InjectView(R.id.btn_check_out)
    Button btnCheckOut;
    @InjectView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @InjectView(R.id.cb_all)
    CheckBox cbAll;
    @InjectView(R.id.btn_delete)
    Button btnDelete;
    @InjectView(R.id.btn_collection)
    Button btnCollection;
    @InjectView(R.id.ll_delete)
    LinearLayout llDelete;
    @InjectView(R.id.iv_empty)
    ImageView ivEmpty;
    @InjectView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @InjectView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;
    ShoppingCartAdapter adapter;
    //编辑状态
    private static final int ACTION_EDIT = 1;
    //完成状态
    private static final int ACTION_COMPLETE = 2;
    List<GoodsBean> datas;
    boolean checked;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);
        ButterKnife.inject(this, view);

        //设置编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
//显示去结算布局
        llCheckAll.setVisibility(View.VISIBLE);

        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                int action = (int) v.getTag();
                //2.根据不同状态做不同的处理
                if (action == ACTION_EDIT) {
                    //切换完成状态
                    showDelete();
                } else {
                    //切换成编辑状态
                    hideDelete();
                }
            }
        });
        return view;
    }

    private void hideDelete() {
        //1.设置编辑
        tvShopcartEdit.setTag(ACTION_EDIT);
        //2.隐藏删除控件
        llDelete.setVisibility(View.GONE);
        //3.显示结算控件
        llCheckAll.setVisibility(View.VISIBLE);
        //4.设置文本为-编辑
        tvShopcartEdit.setText("编辑");
        //5.把所有的数据设置勾选择状态
        if (adapter != null) {
            adapter.checkAll_none(true);
            adapter.checkAll();
            adapter.showTotalPrice();
        }

    }

    private void showDelete() {
        //1.设置完成
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        //2.显示删除控件
        llDelete.setVisibility(View.VISIBLE);
        //3.隐藏结算控件
        llCheckAll.setVisibility(View.GONE);
        //4.设置文本为-完成
        tvShopcartEdit.setText("完成");
        //5.把所有的数据设置非选择状态
        if (adapter != null) {
            adapter.checkAll_none(false);
            adapter.checkAll();
            adapter.showTotalPrice();
        }

    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "购物车ui被绑定数据了");


    }

    @Override
    public void onResume() {
        super.onResume();
        List<GoodsBean> list = CartStorage.getInstance(mContext).getAllData();
        for (int i = 0; i < list.size(); i++) {
            GoodsBean goodsBean = list.get(i);
            Log.e("TAG", goodsBean.toString() + "tiankuo");
        }

        datas = CartStorage.getInstance(mContext).getAllData();
        if (datas != null && datas.size() > 0) {
            //有数据-隐藏
            llEmptyShopcart.setVisibility(View.GONE);
            //设置适配器
            adapter = new ShoppingCartAdapter(mContext, datas, checkboxAll, tvShopcartTotal, cbAll);
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

            //设置item的点击事件
            adapter.setOnItemClickListener(new ShoppingCartAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position) {
                    //得到数据
                    GoodsBean goodsBean = datas.get(position);
                    //取反
                    goodsBean.setChecked(!goodsBean.isChecked());
                    //刷新适配器
                    adapter.notifyItemChanged(position);
                    //校验全选
                    adapter.checkAll();
                    //显示总价格
                    adapter.showTotalPrice();

                }
            });
//默认校验全选
            adapter.checkAll();


        } else {
            //没有数据-显示没有数据的页面
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_shopcart_edit, R.id.checkbox_all, R.id.btn_check_out, R.id.cb_all, R.id.btn_delete, R.id.btn_collection, R.id.tv_empty_cart_tobuy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcart_edit:
                Toast.makeText(mContext, "编辑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkbox_all:
//                Toast.makeText(mContext, "结算的全选/反选", Toast.LENGTH_SHORT).show();
                checked = checkboxAll.isChecked();
                //全选、反选
                adapter.checkAll_none(checked);
                //刷新适配器
                adapter.notifyDataSetChanged();
                //显示总价格
                adapter.showTotalPrice();

                break;
            case R.id.btn_check_out:
                Toast.makeText(mContext, "结算", Toast.LENGTH_SHORT).show();

                break;
            case R.id.cb_all:
//                Toast.makeText(mContext, "删除的全选/反选", Toast.LENGTH_SHORT).show();
                checked = cbAll.isChecked();
                //全选、反选
                adapter.checkAll_none(checked);
                //刷新适配器
                adapter.notifyDataSetChanged();
                //显示总价格
                adapter.showTotalPrice();
                break;
            case R.id.btn_delete:
//                Toast.makeText(mContext, "删除", Toast.LENGTH_SHORT).show();
                adapter.deleteData();
                adapter.checkAll();
                showEempty();
                break;
            case R.id.btn_collection:
                Toast.makeText(mContext, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_empty_cart_tobuy:
                Toast.makeText(mContext, "去逛逛", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showEempty() {
        if(datas == null || datas.size() ==0){
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }
}
