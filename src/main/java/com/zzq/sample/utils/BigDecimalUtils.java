package com.zzq.sample.utils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Zhou Zhongqing
 * @ClassName BigDecimalUtils
 * @description: 数值计算工具类
 * @date 2022-11-10 17:17
 */


public class BigDecimalUtils {

    /**
     * 默认保留位数
     **/
    public static final Integer DEFAULT_SCALE = 2;


    /**
     * 保留4位数
     **/
    public static final Integer  FOUR_SCALE = 4;


    /**
     * 取整
     **/
    public static final Integer DEFAULT_ROUNDING = 0;
    /***
     * zhouzhongqing
     * 2021年5月14日15:27:45
     * 金额保留2位小数
     * */
    public static String format(BigDecimal money) {
        if (!ObjectUtils.isEmpty(money)) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            return decimalFormat.format(money);
        }
        return null;
    }


    /***
     * 保留两位小数，返回BigDecimal
     * */
    public static BigDecimal formatBigDecimal(BigDecimal money) {
        if (!ObjectUtils.isEmpty(money)) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            return new BigDecimal(decimalFormat.format(money));
        }
        return null;
    }

    /***
     * zhouzhongqing
     * 2021年5月14日15:27:45
     * 金额保留整数
     * */
    public static String formatRounding(BigDecimal money) {
        if (!ObjectUtils.isEmpty(money)) {
            DecimalFormat decimalFormat = new DecimalFormat("0");
            return decimalFormat.format(money);
        }
        return null;
    }


    /**
     * 乘法
     *
     * @param v1 需要四舍五入的数字
     * @param v2 需要四舍五入的数字
     * @return 四舍五入后的结果
     */
    public static BigDecimal multiply(BigDecimal v1, BigDecimal v2) {
        return v1.multiply(v2);
    }

    /**
     * 乘法
     *
     * @param v1 需要四舍五入的数字
     * @param v2 需要四舍五入的数字
     * @return 四舍五入后的结果
     */
    public static BigDecimal multiply(BigDecimal v1, Integer v2) {
        return v1.multiply(new BigDecimal(Integer.toString(v2)));
    }


    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(v1.doubleValue()));
        BigDecimal bd2 = new BigDecimal(Double.toString(v2.doubleValue()));
        return bd1.add(bd2);
    }

    /**
     * 相除,做除法的时候相对的麻烦一点,涉及的多一些
     * 提供精确的小数位四舍五入处理。
     *
     * @param v1    需要四舍五入的数字
     * @param v2    需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(Integer v1, Integer v2, int scale) {
        return round(new BigDecimal(Integer.toString(v1)),new BigDecimal(Integer.toString(v2)),scale);
    }

    /***
     * zzq
     * 2022年1月5日15:30:50
     * 向下取整
     * */
    public static BigDecimal roundDown(Integer v1, Integer v2) {
        BigDecimal bigDecimal = new BigDecimal(Integer.toString(v1));
        BigDecimal divisor = new BigDecimal(Integer.toString(v2));
        if (!ObjectUtils.isEmpty(divisor) && divisor.doubleValue() != 0.00) {
            return bigDecimal.divide(divisor, DEFAULT_SCALE, BigDecimal.ROUND_HALF_DOWN);
        }
        return new BigDecimal(0);
    }


    public static BigDecimal round(Integer v1, Integer v2) {
        return round(v1, v2, DEFAULT_SCALE);
    }

    public static BigDecimal round(BigDecimal v1, Integer v2, int scale) {
        return round(v1, new BigDecimal(Integer.toString(v2)), scale);
    }
    public static BigDecimal round(Integer v1, BigDecimal v2, int scale) {
        return round(new BigDecimal(Integer.toString(v1)), v2, scale);
    }

    public static BigDecimal round(BigDecimal v1, BigDecimal v2) {
        return round(v1, v2, DEFAULT_SCALE);
    }

    public static BigDecimal round(Double v1, Double v2) {
        return round(new BigDecimal(Double.toString(v1)), new BigDecimal(Double.toString(v2)), DEFAULT_SCALE);
    }

    public static BigDecimal round(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("此参数错误");
        }
        if (!ObjectUtils.isEmpty(v2) && v2.doubleValue() != 0.00) {
            return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
        }
        return new BigDecimal(0);
    }

    /***
     * zzq
     * 2021年9月18日10:17:06
     * 四舍五入
     * */
    public static Integer roundHalfUp(BigDecimal val){
        return val.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static void main(String[] args) {
        BigDecimal add = add(new BigDecimal(0), new BigDecimal(0.22));
        System.out.println(add);
        System.out.println(new BigDecimal(0).add( new BigDecimal(0.22)));
    }
}
