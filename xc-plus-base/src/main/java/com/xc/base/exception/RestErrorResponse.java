package com.xc.base.exception;

import java.io.Serializable;

/**
 * 错误响应参数包装
 *
 * @author LJ
 * @create 2023/2/5
 */
public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

}
