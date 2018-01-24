package com.hulian.huliantecdemo.hook.hookutils;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by wiky_zhang on 2018/1/24.
 * 创建一个钩子hook
 *
 */

public class HookActivityManagerServiceUtils {

    private Class<?> proxyActivity;//需要过packagemanager检查的代理activity

    private Context context;//上下文

    /**
     * 创建构造函数
     * @param proxyActivity
     * @param context
     */
    public HookActivityManagerServiceUtils(Class<?> proxyActivity, Context context) {
        this.proxyActivity = proxyActivity;
        this.context = context;
    }

    public void hookAms() throws Exception{

       Class<?> cl= Class.forName("android.app.ActivityManagerNative");
        Field d_field=cl.getDeclaredField("gDefault");
        d_field.setAccessible(true);
        Object d_value=d_field.get(null);//静态方法，获取gDefault的变量值
        //反射singleton
       Class<?> sclass= Class.forName("android.util.Singleton");
        Field s_field=sclass.getDeclaredField("mInstance");
        s_field.setAccessible(true);
        //这个就是系统的IActivityManager的对象
        Object s_value=s_field.get(d_value);




    }
}
