package com.hulian.huliantecdemo.refresh;

import java.security.MessageDigest;

/**
 * MD5加密
 * Created by zkagang on 2016/5/10.
 */
public class MDUtil {

    /**
     * 加密算法
     * @param需要加密的字符串
     * @return
     */
    public final static String MD5(String s) {

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSign(String method_name){
        return MD5(MethodName.APPKEY+method_name+MethodName.PRIVATEKEY);
    }

    public static String gethttp(String method_name){
        return "http://web6.mingtaikeji.cn/Api/"+method_name+"?";
    }
}
