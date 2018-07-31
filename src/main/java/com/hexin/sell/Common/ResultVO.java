package com.hexin.sell.Common;


import lombok.Data;

import java.io.Serializable;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL) //为null的属性不返回到前端， 也就是类转json 属性为NULL的不参加序列化
public class ResultVO<T> implements Serializable{
    private static final long serialVersionUID = 4176441568338187710L;//实现序列化
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
//    private String msg = "";//赋予初始值

    /**
     * 返回的具体内容
     */
    private T data;

}
