package com.hexin.sell.util;

import java.util.Random;

/**
 * 订单号生成
 * @author hexin
 * @createDate 2018年07月30日 16:51:00
 */
public class KeyUtil {
    /**
     * 生成唯一主键
     * @return
     */
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000)+100000;
        return  System.currentTimeMillis()+String.valueOf(number);
    }

    public static long generateOrderNo(){
        long currentTime =System.currentTimeMillis();
        return currentTime+new Random().nextInt(100);
    }
}
