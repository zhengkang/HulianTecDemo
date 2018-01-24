package com.hulian.huliantecdemo.hook.hookutils;

import android.content.Context;

/**
 * Created by wiky_zhang on 2018/1/24.
 * 创建一个钩子hook
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


}
