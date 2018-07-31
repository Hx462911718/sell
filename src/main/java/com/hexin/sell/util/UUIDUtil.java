package com.hexin.sell.util;

import java.util.UUID;

/**
 * uuid生成工具类
 * @author hexin
 * @createDate 2018年07月27日 11:55:00
 */
public class UUIDUtil {

        public static String getUUID(){
            return UUID.randomUUID().toString().replace("-",""
            );
        }


}
