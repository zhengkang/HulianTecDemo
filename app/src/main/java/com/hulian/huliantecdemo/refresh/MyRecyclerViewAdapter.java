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
import com.hulian.searchwifi.R;
import com.hulian.searchwifi.a.entity.CouponEntity;
import com.hulian.searchwifi.a.event.MallBus;
import com.hulian.searchwifi.a.event.MallTypeBus;
import com.hulian.searchwifi.a.ui.mall.CouponDetailsActivity;
import com.hulian.searchwifi.a.ui.mall.MyCouponActivity;
import com.hulian.searchwifi.a.utils.MDUtil;
import com.hulian.searchwifi.a.utils.MethodName;
import com.hulian.searchwifi.a.utils.MySP;
import com.hulian.searchwifi.a.utils.T;
import com.hulian.searchwifi.b.login.LoginActivity;
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
                    CouponDetailsActivity.naveToMain(context,couponEntityArrayList.get(position).getCoupon_id(),couponEntityArrayList.get(position).getCoupon_name(),"1");

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
        if(MySP.getLoginStatus(context)){
            postMessage(context,couponEntityArrayList.get(pos).getCoupon_id());
        }else{
            LoginActivity.GoLoginUI(context);
        }
        /*switch (view.getId()){
            case R.id.icon:
                CouponDetailsActivity.naveToMain(context,couponEntityArrayList.get(pos).getCoupon_id(),couponEntityArrayList.get(pos).getCoupon_name(),"1");
                break;
            case R.id.lijiduihuan:
                postMessage(context,couponEntityArrayList.get(pos).getCoupon_id());
                break;
        }*/
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
        Map<String, String> params = new HashMap<String, String>();
        params.put(MethodName.METHOD, MethodName.GETORDER);
        params.put(MethodName.SIGN, MDUtil.getSign(MethodName.GETORDER));
        params.put("user_id", MySP.getLoginId(context));
        params.put("coupon_id", id);
        MyOkHttp.get().post(context, MDUtil.gethttp(MethodName.GETORDER), params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject jsonObject) {
                Log.e("json", jsonObject.toString());
                if (jsonObject != null) {
                    if (jsonObject.optString("code").equals("200")) {
                        showduihuan2(viewtag,"兑换成功");
                        EventBus.getDefault().post(new MallTypeBus("1"));
                        EventBus.getDefault().post(new MallBus("1"));

                    } else {
                        showduihuan1(viewtag,jsonObject.optString("msg"));
                    }
                } else {
                    T.showShort(context,"json格式错误");
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                T.showShort(context,"网络连接失败");
            }
        });
    }

    public void showduihuan2(View v,String str) {
        View contentView;
        popupWindow1 = null;
        if (popupWindow1 == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(context);
            contentView = mLayoutInflater.inflate(R.layout.duihuachenggong2, null);
            popupWindow1 = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView negativeButton = (TextView) contentView.findViewById(R.id.negativeButton);
            TextView negativeButton2 = (TextView) contentView.findViewById(R.id.negativeButton2);

            TextView title=(TextView) contentView.findViewById(R.id.title);
            title.setText(str);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow1.dismiss();
                }
            });
            negativeButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow1.dismiss();
                    Intent intent=new Intent(context, MyCouponActivity.class);
                    context.startActivity(intent);
                }
            });
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow1.setBackgroundDrawable(cd);
        //产生背景变暗效果
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = 0.4f;
        ((Activity)context).getWindow().setAttributes(lp);
        popupWindow1.setOutsideTouchable(true);
        popupWindow1.setFocusable(true);
        popupWindow1.setAnimationStyle(R.style.AnimBottom);
        popupWindow1.showAtLocation((View) v.getParent(), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        //popupWindow1.update();
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp =((Activity)context).getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity)context).getWindow().setAttributes(lp);
            }
        });
    }

    public void showduihuan1(View v,String str) {
        View contentView;
        popupWindow1 = null;
        if (popupWindow1 == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(context);
            contentView = mLayoutInflater.inflate(R.layout.duihuachenggong, null);
            popupWindow1 = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView negativeButton = (TextView) contentView.findViewById(R.id.negativeButton);

            TextView title=(TextView) contentView.findViewById(R.id.title);
            title.setText(str);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow1.dismiss();
                }
            });
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow1.setBackgroundDrawable(cd);
        //产生背景变暗效果
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = 0.4f;
        ((Activity)context).getWindow().setAttributes(lp);
        popupWindow1.setOutsideTouchable(true);
        popupWindow1.setFocusable(true);
        popupWindow1.setAnimationStyle(R.style.AnimBottom);
        popupWindow1.showAtLocation((View) v.getParent(), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        //popupWindow1.update();
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp =((Activity)context).getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity)context).getWindow().setAttributes(lp);
            }
        });
    }

}


