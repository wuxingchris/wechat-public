package com.wechat.utils;

import java.util.Arrays;

/**
 * Created by wuxing on 2016/9/13.
 */
public class WechatUtil {
    private static final String token = "wuxing";

    public static boolean checkSignature(String signature, String timestamp, String nonce){
        String[] array = new String[]{token, timestamp, nonce};
        Arrays.sort(array);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < array.length; i ++){
            stringBuffer.append(array[i]);
        }
        String encryptContent = SHA1Util.SHA1(stringBuffer.toString());
        return encryptContent.equals(signature);
    }
}
