package com.hulian.huliantecdemo.refresh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulian.huliantecdemo.R;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 晓勇 on 2015/7/12 0012.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private View viewtag;


    private ArrayList<CouponEntity> couponEntityArrayList;//商品列表

    /**
     * 弹窗
     */
    private PopupWindow popupWindow1;

    public MyRecyclerViewAdapter(Context context, ArrayList<CouponEntity> couponEntityArrayList) {
        this.couponEntityArrayList = couponEntityArrayList;
        this.context = context;
    }

    public void refresh(ArrayList<CouponEntity> couponEntityArrayList){
        this.couponEntityArrayList = couponEntityArrayList;
        notifyDataSetChanged();
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


            holder.name.setText(couponEntityArrayList.get(position).getCoupon_name());
            holder.jifen.setText(couponEntityArrayList.get(position).getHowmuch());
            Glide.with(context).load(couponEntityArrayList.get(position).getCoupon_img()).into(holder.icon);
            //holder.icon.setTag(position);

            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //CouponDetailsActivity.naveToMain(context,couponEntityArrayList.get(position).getCoupon_id(),couponEntityArrayList.get(position).getCoupon_name(),"1");

                }
            });

            holder.lijiduihuan.setTag(position);
            viewtag=holder.lijiduihuan;
            holder.lijiduihuan.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return couponEntityArrayList.size();
    }

    @Override
    public void onClick(View view) {
        int pos= (int) view.getTag();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon;
        TextView jifen;
        TextView lijiduihuan;
        public MyViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
            jifen = (TextView) itemView.findViewById(R.id.jifen);
            lijiduihuan = (TextView) itemView.findViewById(R.id.lijiduihuan);

        }
    }


    /**
     * 网络请求
     */
    public void postMessage(final Context context, String id){

    }

    public void showduihuan2(View v,String str) {

    }

    public void showduihuan1(View v,String str) {

    }

}


