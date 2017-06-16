package com.atguigu.tiankuo.appstore.typefragment.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.tiankuo.appstore.R;
import com.atguigu.tiankuo.appstore.base.BaseFragment;
import com.atguigu.tiankuo.appstore.domain.Constants;
import com.atguigu.tiankuo.appstore.typefragment.adapter.TypeLeftAdapter;
import com.atguigu.tiankuo.appstore.typefragment.adapter.TypeRightAdapter;
import com.atguigu.tiankuo.appstore.typefragment.domain.TypeBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

import static com.atguigu.tiankuo.appstore.R.id.lv_left;


/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/6/15 0015.
 */

public class ListFragment extends BaseFragment {


    @InjectView(lv_left)
    ListView lvLeft;
    @InjectView(R.id.rv_right)
    RecyclerView rvRight;

    private TypeLeftAdapter adapter;
    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品",
            "办公文具", "数码周边", "游戏专区"};
    private static final String TAG = ListFragment.class.getSimpleName();
    private String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
            Constants.DIGIT_URL, Constants.GAME_URL};
    private List<TypeBean.ResultBean> result;
    private boolean isFirst  =true;
    private TypeLeftAdapter leftAdapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_left, null);
        ButterKnife.inject(this, view);
        return view;

    }

    @Override
    public void initData() {
        super.initData();
        adapter = new TypeLeftAdapter(mContext, titles);
        lvLeft.setAdapter(adapter);
        //设置监听点击ListView的item的点击事件，并且点击的时候变效果
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.记录点击的位置
                adapter.setChangeSelected(position);
                getDataFromNet(urls[position]);
                //2.适配器刷新
                adapter.notifyDataSetChanged();//getView
                if(position != 0){
                    isFirst = false;
                }else{
                    isFirst = true;
                }
            }
        });
//        getDataFromNet(urls[0]);
    }

    private void getDataFromNet(String url) {
        System.out.println("url==" + url);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new MyStringCallback());
            }

    class MyStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e(TAG, "请求成功失败==" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "请求成功==" + response);
            if (response != null) {
                if (isFirst) {
                    leftAdapter = new TypeLeftAdapter(mContext,titles);
                    lvLeft.setAdapter(leftAdapter);
                }
            }
            processData(response);
        }
    }


    private void processData(String json) {
        TypeBean typeBean = JSON.parseObject(json, TypeBean.class);
        result = typeBean.getResult();
        Log.e("TAG", "解析成功==" + typeBean.getResult().get(0).getName());

        if (result != null && result.size() > 0) {

            //有数据，设置RecyclerView的适配器
            TypeRightAdapter rightAdapter = new TypeRightAdapter(mContext,result);
            rvRight.setAdapter(rightAdapter);
            //设置三列
            GridLayoutManager manager = new GridLayoutManager(mContext,3);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    if(position==0){
                        return 3;
                    }else{
                        return 1;
                    }
                }
            });
            rvRight.setLayoutManager(manager);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
