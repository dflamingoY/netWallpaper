package com.mingo.baselibrary.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ProjectName: QiuTan
 * @Package: com.gqj.qiutan.Utils
 * @ClassName: MD5
 * @Description: 描述 ：
 * @Author: 唐杰
 * @CreateDate: 2020/4/9 17:37
 * @UpdateUser: 唐杰
 * @UpdateDate: 2020/4/9 17:37
 * @UpdateRemark: 更新说明
 * @Version: 3.1.0
 */
public class MD5 {
    public static String getMD5(String plainText, int length) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");//获取MD5实例
            md.update(plainText.getBytes());//此处传入要加密的byte类型值
            byte[] digest = md.digest();//此处得到的是md5加密后的byte类型值
            int i;
            StringBuilder sb = new StringBuilder();
            for (int offset = 0; offset < digest.length; offset++) {
                i = digest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    sb.append(0);
                sb.append(Integer.toHexString(i));//通过Integer.toHexString方法把值变为16进制
            }
            return sb.toString().substring(0, length);//从下标0开始，length目的是截取多少长度的值
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
