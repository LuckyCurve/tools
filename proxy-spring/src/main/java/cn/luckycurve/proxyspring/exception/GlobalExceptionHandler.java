package cn.luckycurve.proxyspring.exception;

import com.google.common.base.Throwables;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author LuckyCurve
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String exceptionHandle(Exception e) {
        return Throwables.getStackTraceAsString(e);
    }
}
