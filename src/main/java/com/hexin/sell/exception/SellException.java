package com.hexin.sell.exception;

import com.hexin.sell.enums.ResultEnum;

/**
 * 异常类
 * @author hexin
 * @createDate 2018年07月30日 16:30:00
 */
public class SellException extends  RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();

    }

    public SellException(Integer code,String message){
        super(message);
        this.code = code;

    }
}
