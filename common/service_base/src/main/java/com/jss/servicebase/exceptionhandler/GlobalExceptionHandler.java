package com.jss.servicebase.exceptionhandler;

import com.jss.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常的处理");
    }

    /**
     * 特点的异常
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException的处理");
    }

    /**
     * 自定义异常类
     * @param e
     * @return
     */
    @ExceptionHandler(CollegeException.class)
    @ResponseBody
    public R error(CollegeException e){
        e.printStackTrace();
        log.error(e.getMsg());
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
