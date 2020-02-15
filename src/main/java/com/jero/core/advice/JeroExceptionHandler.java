package com.jero.core.advice;

import com.jero.common.constant.Code;
import com.jero.common.exception.JeroBaseException;
import com.jero.http.ResponseMessage;
import com.jero.http.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常统一处理
 *
 * @author zer0
 * @version 1.0
 */
@ControllerAdvice
public class JeroExceptionHandler {

    private final Log log = LogFactory.getLog(JeroExceptionHandler.class);

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseMessage handlerException(Exception exception) {
        Throwable deepestException = deepestExcepetion(exception);
        log.error("全局异常处理捕获："+ deepestException);
        return Result.error(Code.ERROR.getCode(), "程序异常，请重试。如果重复出现请联系管理员处理！");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(JeroBaseException.class)
    @ResponseBody
    public ResponseMessage handlerJeroBaseException(JeroBaseException exception){
        log.warn(exception);
        return Result.error(exception.getErrorCode(), exception.getMessage());
    }

    /**
     * 获取引发异常的源头
     * @param e
     * @return 抛出的异常
     */
    private Throwable deepestExcepetion(Throwable e){
        if (e == null){
            return new NullPointerException("异常信息不能为空");
        }

        Throwable tmp = e;
        int breakPoint = 0;
        while (tmp.getCause() != null){
            if (tmp.equals(tmp.getCause())){
                break;
            }
            tmp = tmp.getCause();
            breakPoint++;
            if (breakPoint > 1000){
                break;
            }
        }
        return tmp;
    }
}
