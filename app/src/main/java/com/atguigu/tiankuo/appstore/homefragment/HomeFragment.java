package com.atguigu.tiankuo.appstore.homefragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.tiankuo.appstore.R;
import com.atguigu.tiankuo.appstore.base.BaseFragment;
import com.atguigu.tiankuo.appstore.domain.Constants;
import com.atguigu.tiankuo.appstore.homefragment.adapter.HomeAdapter;
import com.atguigu.tiankuo.appstore.homefragment.domain.HomeBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Request;

import static com.atguigu.tiankuo.appstore.R.id.ib_top;
import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/6/11 0011.
 */

public class HomeFragment extends BaseFragment {

    @InjectView(R.id.tv_search_home)
    TextView tvSearchHome;
    @InjectView(R.id.tv_message_home)
    TextView tvMessageHome;
    @InjectView(R.id.rv_home)
    RecyclerView rvHome;
    @InjectView(ib_top)
    ImageButton ibTop;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick({R.id.tv_search_home, R.id.tv_message_home, ib_top})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search_home:
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_message_home:
                Toast.makeText(mContext, "进入消息中心", Toast.LENGTH_SHORT).show();
                break;
            case ib_top:
                rvHome.scrollToPosition(0);
                break;
        }
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.HOME_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {

        }

        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            //当联网成功后会回调这里
            Log.e(TAG, "请求成功==");
            processData(response);
        }

    }

    private void processData(String json) {
        //解析数据
        HomeBean homeBean = JSON.parseObject(json, HomeBean.class);
        Log.e(TAG, "解析成功了==" + homeBean.getResult().getAct_info().get(0).getName());

        HomeAdapter adapter = new HomeAdapter(mContext, homeBean.getResult());
        rvHome.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);

        //设置滑动到哪个位置了的监听
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 3) {
                    ibTop.setVisibility(View.GONE);
                } else {
                    ibTop.setVisibility(View.VISIBLE);
                }
                //只能返回1

                return 1;
            }
        });
        //设置网格布局
        rvHome.setLayoutManager(manager);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}
