package com.atguigu.tiankuo.appstore.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.tiankuo.appstore.MainActivity;
import com.atguigu.tiankuo.appstore.R;
import com.atguigu.tiankuo.appstore.domain.Constants;
import com.atguigu.tiankuo.appstore.homefragment.domain.GoodsBean;
import com.atguigu.tiankuo.appstore.homefragment.adapter.HomeAdapter;
import com.atguigu.tiankuo.appstore.shoppingcartfragment.AddSubView;
import com.atguigu.tiankuo.appstore.shoppingcartfragment.CallCenterActivity;
import com.atguigu.tiankuo.appstore.shoppingcartfragment.CartStorage;
import com.bumptech.glide.Glide;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GoodsInfoActivity extends AppCompatActivity {

    @InjectView(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @InjectView(R.id.ib_good_info_more)
    ImageButton ibGoodInfoMore;
    @InjectView(R.id.iv_good_info_image)
    ImageView ivGoodInfoImage;
    @InjectView(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @InjectView(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @InjectView(R.id.tv_good_info_price)
    TextView tvGoodInfoPrice;
    @InjectView(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @InjectView(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    @InjectView(R.id.wb_good_info_more)
    WebView wbGoodInfoMore;
    @InjectView(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @InjectView(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @InjectView(R.id.tv_good_info_cart)
    TextView tvGoodInfoCart;
    @InjectView(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @InjectView(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;
    @InjectView(R.id.tv_more_share)
    TextView tvMoreShare;
    @InjectView(R.id.tv_more_search)
    TextView tvMoreSearch;
    @InjectView(R.id.tv_more_home)
    TextView tvMoreHome;
    @InjectView(R.id.btn_more)
    Button btnMore;
    @InjectView(R.id.ll_root)
    LinearLayout llRoot;

    private GoodsBean goodsBean;
    //缓存
    private GoodsBean tempGoodsBean;

    private  boolean isExist = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.inject(this);
        getData();
        setData();
    }

    private void setData() {
        //设置图片和名字和价格的数据
        String coverPrice = goodsBean.getCover_price();
        String figure = goodsBean.getFigure();
        String productId = goodsBean.getProduct_id();
        String name = goodsBean.getName();

        //设置图片
//        Glide.with(this).load(Constants.BASE_URL_IMAGE + figure).into(ivGoodInfoImage);
        Glide.with(this).load(Constants.BASE_URL_IMAGE + figure).placeholder(R.drawable.new_user_icon_background).error(R.drawable.new_user_icon_background).into(ivGoodInfoImage);
        tvGoodInfoName.setText(name);
        tvGoodInfoPrice.setText("￥" + coverPrice);


        //设置webView的数据
        setWebViewData(productId);
    }

    private void setWebViewData(String productId) {
        WebSettings webSettings = wbGoodInfoMore.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);

        //设置检索缓存的
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);

        //设置不跳转到系统的浏览器
        wbGoodInfoMore.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        wbGoodInfoMore.loadUrl("http://mp.weixin.qq.com/s/Cf3DrW2lnlb-w4wYaxOEZg");

    }

    private void getData() {
        goodsBean = (GoodsBean) getIntent().getSerializableExtra(HomeAdapter.GOODS_BEAN);
    }

    @OnClick({R.id.ib_good_info_back, R.id.ib_good_info_more, R.id.tv_good_info_callcenter, R.id.tv_good_info_collection, R.id.tv_good_info_cart, R.id.btn_good_info_addcart, R.id.tv_more_share, R.id.tv_more_search, R.id.tv_more_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;
            case R.id.ib_good_info_more:
                Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_callcenter:
//                Toast.makeText(this, "呼叫中心", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(GoodsInfoActivity.this, CallCenterActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_good_info_collection:
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_cart:
//                Toast.makeText(this, "进入购物车", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("checkId",R.id.rb_cart);
                startActivity(intent);

                break;
            case R.id.btn_good_info_addcart:
//                Toast.makeText(this, "添加到购物车", Toast.LENGTH_SHORT).show();
                CartStorage.getInstance(GoodsInfoActivity.this).addData(goodsBean);
                showPopwindow();
                break;
            case R.id.tv_more_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_home:
                Toast.makeText(this, "主页", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showPopwindow() {

        tempGoodsBean = CartStorage.getInstance(this).findDete(goodsBean.getProduct_id());
        if(tempGoodsBean == null){
            isExist = false;
            tempGoodsBean = goodsBean;
        }else {
            isExist  =true;
        }

        // 1 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow_add_product, null);

        // 2下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 3 参数设置
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);


        // 4 控件处理
        ImageView iv_goodinfo_photo = (ImageView) view.findViewById(R.id.iv_goodinfo_photo);
        TextView tv_goodinfo_name = (TextView) view.findViewById(R.id.tv_goodinfo_name);
        TextView tv_goodinfo_price = (TextView) view.findViewById(R.id.tv_goodinfo_price);
        AddSubView nas_goodinfo_num = (AddSubView) view.findViewById(R.id.nas_goodinfo_num);
        Button bt_goodinfo_cancel = (Button) view.findViewById(R.id.bt_goodinfo_cancel);
        Button bt_goodinfo_confim = (Button) view.findViewById(R.id.bt_goodinfo_confim);

        // 加载图片
        Glide.with(GoodsInfoActivity.this).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(iv_goodinfo_photo);

        // 名称
        tv_goodinfo_name.setText(tempGoodsBean.getName());
        // 显示价格
        tv_goodinfo_price.setText(tempGoodsBean.getCover_price());

        // 设置最大值和当前值
        nas_goodinfo_num.setMaxValue(100);//库存100

        nas_goodinfo_num.setValue(tempGoodsBean.getNumber());


        nas_goodinfo_num.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void numberChange(int value) {
                tempGoodsBean.setNumber(value);
            }
        });

        bt_goodinfo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                tempGoodsBean.setNumber(tempGoodsBean.getNumber());
            }
        });

        bt_goodinfo_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                if(isExist&& tempGoodsBean.getNumber()==1){
                    tempGoodsBean.setNumber(tempGoodsBean.getNumber()+1);
                }
                //添加购物车
                CartStorage.getInstance(GoodsInfoActivity.this).updateData(tempGoodsBean);
                Log.e("TAG", "66:" + goodsBean.toString());
                Toast.makeText(GoodsInfoActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            }
        });

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });

        // 5 在底部显示
        window.showAtLocation(GoodsInfoActivity.this.findViewById(R.id.ll_goods_root),
                Gravity.BOTTOM, 0, VirtualkeyboardHeight.getBottomStatusHeight(GoodsInfoActivity.this));

    }

    private static class VirtualkeyboardHeight {
        // 获取屏幕和底部的高度
        public static int getDpi(Context context) {

            int dpi = 0;
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();

            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, displayMetrics);
                dpi = displayMetrics.heightPixels;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return dpi;
        }

        // 获取底部虚拟键盘的高度
        public static int getBottomStatusHeight(Context context) {
            int totalHeight = getDpi(context);

            int contentHeight = getScreenHeight(context);

            return totalHeight - contentHeight;
        }

        // 获取屏幕的高度
        public static int getScreenHeight(Context context) {

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);

            return outMetrics.heightPixels;
        }

    }
}
