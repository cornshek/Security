package com.pd.util;

public class IncrementallyNumber {
    public static void main(String[] args) {
        int number = 0;
        StringBuffer numberStr = new StringBuffer("0000");
        for (int i = 0; i < 100; i++) {
            numberStr.append(i);
            System.out.println(numberStr);
        }

    }
}
