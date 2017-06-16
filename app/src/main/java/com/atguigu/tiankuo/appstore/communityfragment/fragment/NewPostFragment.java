package com.atguigu.tiankuo.appstore.communityfragment.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.tiankuo.appstore.R;
import com.atguigu.tiankuo.appstore.base.BaseFragment;
import com.atguigu.tiankuo.appstore.communityfragment.adapter.NewPostListViewAdapter;
import com.atguigu.tiankuo.appstore.communityfragment.domain.NewPostBean;
import com.atguigu.tiankuo.appstore.domain.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/6/16 0016.
 */
public class NewPostFragment extends BaseFragment {

    @InjectView(R.id.lv_new_post)
    ListView lvNewPost;
    private List<NewPostBean.ResultBean> result;


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_new_post, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.NEW_POST_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private class MyStringCallback extends Callback {

        @Override
        public void onBefore(Request request, int id) {
            super.onBefore(request, id);
        }

        @Override
        public void onAfter(int id) {
            super.onAfter(id);
        }

        @Override
        public Object parseNetworkResponse(Response response, int id) throws Exception {
            return null;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(Object response, int id) {
            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response);
                    }
                    break;
                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void processData(Object response) {
        NewPostBean newPostBean = JSON.parseObject((String) response, NewPostBean.class);
        result = newPostBean.getResult();

        if (response != null) {
            processData(response);
            NewPostListViewAdapter adapter = new NewPostListViewAdapter(mContext, result);
            lvNewPost.setAdapter(adapter);
        }

    }
}
