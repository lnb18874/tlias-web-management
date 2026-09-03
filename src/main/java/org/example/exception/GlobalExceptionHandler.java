package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result ex(Exception e){
        log.error("程序出错了 ",e);
//        e.printStackTrace();//打印堆栈中的异常信息
        String message = e.getMessage();
        if (message != null && message.contains("Duplicate entry")) {
            // 例如: Duplicate entry '13800138000' for key 'emp.idx_emp_phone'
            String errMsg = message.substring(message.indexOf("Duplicate entry"));
            String[] arr = errMsg.split("'");
            if (arr.length > 1) {
                return Result.error(arr[1] + "已存在");
            }
        }
        return Result.error("对不起，操作失败，请联系管理员");
    }
}
