package com.hulian.huliantecdemo.refresh;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hulian.huliantecdemo.R;
import com.hulian.huliantecdemo.view.refresh.DividerGridItemDecoration;
import com.hulian.huliantecdemo.view.refresh.SwipyRefreshLayout;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentOne extends Fragment implements SwipyRefreshLayout.OnRefreshListener {

    private SwipyRefreshLayout swiperefreshlayout;
    private RecyclerView mRecyclerView;

    public FragmentOne(){}


    private int page = 1;

    private View view;

    private ArrayList<CouponEntity> couponEntityArrayList = new ArrayList<CouponEntity>();//商品列表

    private String c_type_id;

    private MyRecyclerViewAdapter myRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        swiperefreshlayout=(SwipyRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setOnRefreshListener(this);


        EventBus.getDefault().register(this);


        c_type_id = getArguments().getString("id");





        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        //mRecyclerView.setNestedScrollingEnabled(false);

        myRecyclerViewAdapter=new MyRecyclerViewAdapter(getActivity(), couponEntityArrayList);

        mRecyclerView.setAdapter(myRecyclerViewAdapter);

        parseJsonOne(getActivity());

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBusInformBus

    }


    /**
     * 网络请求
     *
     * @param context
     * @param
     */
    public void parseJsonOne(Context context) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("method", MethodName.SHOP100);
        params.put("sign", MDUtil.getSign(MethodName.SHOP100));
        params.put("user_id", "1");
        params.put("page", page + "");
        params.put("c_type_id", c_type_id);
        MyOkHttp.get().post(context, MDUtil.gethttp(MethodName.SHOP100), params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject jsonObject) {
                Log.e("json", jsonObject.toString());
                try {
                    swiperefreshlayout.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (jsonObject != null) {
                    if (jsonObject.optString("code").equals("200")) {
                        if (page == 1) {
                            couponEntityArrayList.clear();
                        }
                        JSONArray coupon_array = jsonObject.optJSONObject("data").optJSONArray("list");
                        for (int i = 0; i < coupon_array.length(); i++) {//
                            JSONObject coupon_ject = coupon_array.optJSONObject(i);
                            CouponEntity couponEntity = new CouponEntity();
                            couponEntity.setCoupon_id(coupon_ject.optString("coupon_id"));
                            couponEntity.setCoupon_name(coupon_ject.optString("coupon_name"));
                            couponEntity.setCoupon_img(coupon_ject.optString("coupon_img"));
                            couponEntity.setHowmuch(coupon_ject.optString("howmuch"));
                            couponEntityArrayList.add(couponEntity);
                        }
                        if(coupon_array.length()>0){
                            page++;
                        }
                        myRecyclerViewAdapter.refresh(couponEntityArrayList);
                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                try {
                    swiperefreshlayout.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }




    @Override
    public void onRefresh(int index) {
        page = 1;
        parseJsonOne(getActivity());
    }

    @Override
    public void onLoad(int index) {
        parseJsonOne(getActivity());
    }
}
