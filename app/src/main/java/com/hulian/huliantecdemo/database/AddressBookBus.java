package com.hulian.huliantecdemo.database;

/**
 * Created by Administrator on 2017/12/26.
 */

public class AddressBookBus {
    private String mMsg;
    public AddressBookBus(String msg) {
        mMsg = msg;
    }
    public String getMsg(){
        return mMsg;
    }
}
