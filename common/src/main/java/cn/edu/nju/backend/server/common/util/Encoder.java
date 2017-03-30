package cn.edu.nju.backend.server.common.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhongyq on 17/3/8.
 */
public class Encoder {

    public static String encoderByMd5(String str) {
        String newstr = str;
        try {
            //确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return newstr;
    }
}
