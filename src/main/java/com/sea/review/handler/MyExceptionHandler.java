package com.sea.review.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler()
    @ResponseBody
    String handleException(Exception e) {
        e.printStackTrace();
        return "Exception Deal! " + e.getMessage();
    }
}
