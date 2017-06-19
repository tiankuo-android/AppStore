package com.atguigu.tiankuo.appstore.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.tiankuo.appstore.MainActivity;
import com.atguigu.tiankuo.appstore.R;
import com.atguigu.tiankuo.appstore.domain.Constants;
import com.atguigu.tiankuo.appstore.homefragment.adapter.GoodsListAdapter;
import com.atguigu.tiankuo.appstore.homefragment.domain.GoodsBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.atguigu.tiankuo.appstore.homefragment.adapter.HomeAdapter.GOODS_BEAN;

public class GoodsListActivity extends AppCompatActivity {

    @InjectView(R.id.ib_goods_list_back)
    ImageButton ibGoodsListBack;
    @InjectView(R.id.tv_goods_list_search)
    TextView tvGoodsListSearch;
    @InjectView(R.id.ib_goods_list_home)
    ImageButton ibGoodsListHome;
    @InjectView(R.id.tv_goods_list_sort)
    TextView tvGoodsListSort;
    @InjectView(R.id.tv_goods_list_price)
    TextView tvGoodsListPrice;
    @InjectView(R.id.iv_goods_list_arrow)
    ImageView ivGoodsListArrow;
    @InjectView(R.id.ll_goods_list_price)
    LinearLayout llGoodsListPrice;
    @InjectView(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    @InjectView(R.id.ll_goods_list_head)
    LinearLayout llGoodsListHead;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.dl_left)
    DrawerLayout dlLeft;

    private String[] urls = new String[]{
            Constants.CLOSE_STORE,
            Constants.GAME_STORE,
            Constants.COMIC_STORE,
            Constants.COSPLAY_STORE,
            Constants.GUFENG_STORE,
            Constants.STICK_STORE,
            Constants.WENJU_STORE,
            Constants.FOOD_STORE,
            Constants.SHOUSHI_STORE,
    };
    private GoodsListAdapter goodsListAdapter;
    private List<TypeListBean.ResultBean.PageDataBean> page_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.inject(this);
        getDataFromHome();
        initView();
    }

    private void initView() {
        //设置红色
        tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));
        //价格文字变成默认黑色
        tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
        tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));
    }

    private void getDataFromHome() {
        int position = getIntent().getIntExtra("position", -1);
        //根据位置取对应的url，得到对应数据
        getDataFromNet(urls[position]);
    }

    private void getDataFromNet(String url) {
        Log.e("TAG", "url==" + url);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new MyStringCallback());
    }

    private boolean isClick = false;

    @OnClick({R.id.ib_goods_list_back, R.id.tv_goods_list_search, R.id.ib_goods_list_home, R.id.tv_goods_list_sort, R.id.tv_goods_list_price, R.id.tv_goods_list_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_list_back:
                finish();
                break;
            case R.id.tv_goods_list_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_goods_list_home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("checkId", R.id.rb_home);
                startActivity(intent);
                break;
            case R.id.tv_goods_list_sort:
//                Toast.makeText(this, "综合搜索", Toast.LENGTH_SHORT).show();
                isClick = false;
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);
                //设置红色
                tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));
                //价格文字变成默认黑色
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));
                break;
            case R.id.tv_goods_list_price:
//                Toast.makeText(this, "价格搜索", Toast.LENGTH_SHORT).show();
                //设置红色
                tvGoodsListPrice.setTextColor(Color.parseColor("#ed4141"));
                //价格文字变成默认黑色
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));

                //                click_count ;
                isClick = !isClick;

                if (isClick) {
                    // 箭头向下红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_desc);
                } else {
                    // 箭头向上红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_asc);
                }
                break;
            case R.id.tv_goods_list_select:
//                Toast.makeText(this, "筛选搜索", Toast.LENGTH_SHORT).show();
                isClick = false;
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);
                //设置红色
                tvGoodsListSelect.setTextColor(Color.parseColor("#ed4141"));
                //价格文字变成默认黑色
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
                break;
        }
    }

    private class MyStringCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "请求失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e("TAG", "请求成功==");
            processData(response);
        }
    }

    private void processData(String json) {
        TypeListBean typeListBean = JSON.parseObject(json, TypeListBean.class);
        Log.e("TAG", "解析成功==" + typeListBean.getResult().getPage_data().get(0).getName());

        goodsListAdapter = new GoodsListAdapter(this, typeListBean.getResult().getPage_data());
        recyclerview.setAdapter(goodsListAdapter);


        //布局管理器
        recyclerview.setLayoutManager(new GridLayoutManager(GoodsListActivity.this, 2));

        //设置分割线
        recyclerview.addItemDecoration(new SpaceItemDecoration(10));

        //设置item的监听事件
        goodsListAdapter.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TypeListBean.ResultBean.PageDataBean dataBean) {
                //传递数据
                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setName(dataBean.getName());
                goodsBean.setCover_price(dataBean.getCover_price());
                goodsBean.setFigure(dataBean.getFigure());
                goodsBean.setProduct_id(dataBean.getProduct_id());
                Intent intent = new Intent(GoodsListActivity.this, GoodsInfoActivity.class);
                intent.putExtra(GOODS_BEAN, goodsBean);
                startActivity(intent);
            }
        });
    }
}
