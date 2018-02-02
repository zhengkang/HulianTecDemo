package com.hulian.huliantecdemo.refresh;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulian.huliantecdemo.BaseActivity;
import com.hulian.huliantecdemo.R;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

public class RefeshActivity extends BaseActivity {
    @Bind(R.id.tablayout)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager fragmentContainer;
    @Bind(R.id.banner_guide_content)
    BGABanner bannerGuideContent;
    @Bind(R.id.search)
    ImageView search;


    private List<Fragment> fragmentList = new ArrayList<>();
    private static List<String> tab = new ArrayList<String>();
    private TabAdapter tabsAdapter;

    private ArrayList<String> bannerList = new ArrayList<String>();//轮播图图片
    private ArrayList<String> bannerList2 = new ArrayList<String>();//轮播图文字

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_refesh);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() {

            public void run() {
                getctype(RefeshActivity.this);
                getbanner();
            }
        }, 100);
    }


    public void getbanner() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(MethodName.METHOD, "Banner100");
        params.put(MethodName.SIGN, MDUtil.getSign("Banner100"));
        params.put("type", "2");
        MyOkHttp.get().post(this, MDUtil.gethttp("Banner100"), params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject jsonObject) {
                Log.e("json", jsonObject.toString());
                if (jsonObject != null) {
                    if (jsonObject.optString("code").equals("200")) {
                        for (int i = 0; i < jsonObject.optJSONArray("data").length(); i++) {
                            bannerList2.add("");
                            bannerList.add(jsonObject.optJSONArray("data").optJSONObject(i).optString("img_url"));
                        }

                        if (bannerList.size() > 1) {
                            bannerGuideContent.setAutoPlayAble(true);
                        } else {
                            bannerGuideContent.setAutoPlayAble(false);
                        }
                        bannerGuideContent.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                            @Override
                            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                                Glide.with(RefeshActivity.this).load(model).into(itemView);
                            }
                        });

                        bannerGuideContent.setData(bannerList, bannerList2);
                        bannerGuideContent.setDelegate(new BGABanner.Delegate<ImageView, String>() {
                            @Override
                            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                            }
                        });

                    } else {
                    }
                } else {

                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }


    /**
     * 网络请求
     *
     * @param context
     * @param
     */
    public void getctype(Context context) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(MethodName.METHOD, "getcoupontype100");
        params.put(MethodName.SIGN, MDUtil.getSign("getcoupontype100"));
        MyOkHttp.get().post(context, MDUtil.gethttp("getcoupontype100"), params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject jsonObject) {
                Log.e("json", jsonObject.toString());
                if (jsonObject != null) {
                    if (jsonObject.optString("code").equals("200")) {
                        for (int i = 0; i < jsonObject.optJSONArray("data").length(); i++) {
                            tab.add(jsonObject.optJSONArray("data").optJSONObject(i).optString("c_type_name"));
                            FragmentOne mallItemFragment = new FragmentOne();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", jsonObject.optJSONArray("data").optJSONObject(i).optString("c_type_id"));
                            mallItemFragment.setArguments(bundle);
                            fragmentList.add(mallItemFragment);
                        }
                        //适配数据
                        setData();
                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }

    public void setData() {
        tabsAdapter = new TabAdapter(getSupportFragmentManager(), fragmentList);
        tabsAdapter.setTabTitle(tab);
        //给ViewPager设置适配器
        fragmentContainer.setAdapter(tabsAdapter);
        fragmentContainer.setOffscreenPageLimit(tab.size());
        //将TabLayout和ViewPager关联起来。
        tabs.setupWithViewPager(fragmentContainer);
        //设置可以滑动
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        //setIndicator(getActivity(),tabs,50,50);
        reflex(tabs);
    }


    public void reflex(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = dip2px(tabLayout.getContext(), 10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static int dip2px(Context context, float dipValue) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
