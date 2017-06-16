package com.atguigu.tiankuo.appstore.typefragment.fragment;

import android.view.View;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.atguigu.tiankuo.appstore.R;
import com.atguigu.tiankuo.appstore.base.BaseFragment;
import com.atguigu.tiankuo.appstore.domain.Constants;
import com.atguigu.tiankuo.appstore.typefragment.domain.TagBean;
import com.atguigu.tiankuo.appstore.typefragment.adapter.TagGridViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * 作者：田阔
 * 邮箱：1226147264@qq.com
 * Created by Administrator on 2017/6/15 0015.
 */

public class TagFragment extends BaseFragment {


    @InjectView(R.id.gv_tag)
    GridView gvTag;
    private TagGridViewAdapter adapter;

    private List<TagBean.ResultBean> result;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.TAG_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response);
                        adapter = new TagGridViewAdapter(mContext, result);
                        gvTag.setAdapter(adapter);
                    }
                    break;
                case 101:
                    break;
            }
        }
    }

    /**
     * 解析json数据
     *
     * @param json
     */
    private void processData(String json) {
        TagBean tagBean = JSON.parseObject(json, TagBean.class);
        result = tagBean.getResult();
    }

}
