package com.liuquan.liwushuo.tools;

import java.text.DecimalFormat;

/**
 * Created by PC on 2016/3/19.
 */
public class DecimalFormatTool {
    public static String format(int num){
        DecimalFormat df = new DecimalFormat("0.0");
        float num1 = (float)num;
        String format = df.format(num1);
        return format;
    }
    public static String format(float num){
        DecimalFormat df = new DecimalFormat("0.0");
        String format = df.format(num);
        return format;
    }
}
