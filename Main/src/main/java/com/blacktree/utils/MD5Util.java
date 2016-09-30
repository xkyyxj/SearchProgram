package com.blacktree.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangqchf on 2016/9/30.
 */
public class MD5Util {

    /**
     * 将输入字符串转换成MD5的字节序列
     * 将MD5字节序列用16进制的形式展现出来
     *
     * @param input 输入字符串
     *
     * @return 返回16进制的MD5序列
     */
    public static String toMD5HexString(String input){
        String result = null;
        try {
            byte[] temp;
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input.getBytes());
            temp = messageDigest.digest();
            char[] tempStr = new char[16 * 2];
            byte tempByte;
            int tempChar;
            for(int i = 0;i < temp.length;i++){
                tempByte = temp[i];
                tempChar = tempByte >>> 4 & 0xf;
                tempStr[i * 2] = (char)(tempChar > 9 ? tempChar + 87 : tempChar + 48);
                tempChar = tempByte & 0xf;
                tempStr[i * 2 + 1] = (char)(tempChar > 9 ? tempChar + 87 : tempChar + 48);
            }
            result = new String(tempStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

}
