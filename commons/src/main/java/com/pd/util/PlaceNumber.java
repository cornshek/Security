package com.pd.util;

import java.util.Random;

/**
 * @author GTY
 * 随机生成一个指定长度的字符串，字母+数字组成
 */
public class PlaceNumber {
    /**
     * java生成随机数字和字母组合10位数
     * @param length：生成数字的长度
     * @return
     */
    public static String getRandomNickname(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
