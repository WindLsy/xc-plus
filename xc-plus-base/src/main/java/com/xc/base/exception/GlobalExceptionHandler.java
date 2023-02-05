package com.xc.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理类型
 *
 * @author LJ
 * @create 2023/2/5
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //处理XueChengPlusException异常  此类异常是程序员主动抛出的，可预知异常
    @ExceptionHandler(XueChengPlusException.class)//此方法捕获XueChengPlusException异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//状态码返回500
    public RestErrorResponse doXueChengPlusException(XueChengPlusException e){

        log.error("捕获异常：{}",e.getErrMessage());
        e.printStackTrace();

        String errMessage = e.getErrMessage();

        return new RestErrorResponse(errMessage);
    }

    //捕获不可预知异常 Exception
    @ExceptionHandler(Exception.class)//此方法捕获Exception异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//状态码返回500
    public RestErrorResponse doException(Exception e){

        log.error("捕获异常：{}",e.getMessage());
        e.printStackTrace();

        return new RestErrorResponse(CommonError.UNKOWN_ERROR.getErrMessage());
    }

    /**
     * 使用的JSR303校验，不符合的时候捕获异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)//此方法捕获MethodArgumentNotValidException异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//状态码返回500
    public RestErrorResponse doMethodArgumentNotValidException(MethodArgumentNotValidException e){

        BindingResult bindingResult = e.getBindingResult();
        //校验的错误信息
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        //收集错误
        StringBuffer errors = new StringBuffer();
        fieldErrors.forEach(error->{
            errors.append(error.getDefaultMessage()).append(",");
        });

        return new RestErrorResponse(errors.toString());
    }


}
