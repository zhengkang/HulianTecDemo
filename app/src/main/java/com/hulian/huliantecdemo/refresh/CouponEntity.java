package com.hulian.huliantecdemo.refresh;

/**
 * 优惠券
 * Created by Administrator on 2017/12/1.
 */

public class CouponEntity {

    private String order_id;
    private String coupon_id;
    private String howmuch;
    private String coupon_name;
    private String coupon_img;
    private String order_state;


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getHowmuch() {
        return howmuch;
    }

    public void setHowmuch(String howmuch) {
        this.howmuch = howmuch;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getCoupon_img() {
        return coupon_img;
    }

    public void setCoupon_img(String coupon_img) {
        this.coupon_img = coupon_img;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    @Override
    public String toString() {
        return "CouponEntity{" +
                "order_id='" + order_id + '\'' +
                ", coupon_id='" + coupon_id + '\'' +
                ", howmuch='" + howmuch + '\'' +
                ", coupon_name='" + coupon_name + '\'' +
                ", coupon_img='" + coupon_img + '\'' +
                ", order_state='" + order_state + '\'' +
                '}';
    }
}
