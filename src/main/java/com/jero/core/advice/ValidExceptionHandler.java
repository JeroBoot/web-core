package com.jero.core.advice;

import com.jero.common.constant.Code;
import com.jero.http.ResponseMessage;
import com.jero.http.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 校验异常统一处理
 *
 * @author zer0
 * @version 1.0
 */
@Order(1)
@ControllerAdvice
public class ValidExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidExceptionHandler.class);

    /**
     * 处理方法参数无效异常
     *
     * @param exception
     * @return
     **/
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseMessage<List<String>> handleMethodArgument(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<String> validErrorList = adapterValidError(result);
        return Result.error(Code.VALID_ERROR, validErrorList);
    }

    /**
     * 处理参数绑定异常
     *
     * @param exception
     * @return
     **/
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseMessage<List<String>> handleConstraint(BindException  exception) {
        BindingResult result = exception.getBindingResult();
        List<String> validErrorList = adapterValidError(result);
        return Result.error(Code.VALID_ERROR, validErrorList);
    }

    /**
     * 处理约束违反异常
     *
     * @param exception
     * @return
     **/
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseMessage<List<String>> handleConstraint(ConstraintViolationException exception) {
        List<String> validErrorList = new ArrayList<>();
        Set<ConstraintViolation<?>> violationSet = exception.getConstraintViolations();
        for (ConstraintViolation violation : violationSet) {
            validErrorList.add(violation.getMessage());
            LOGGER.warn("param valid error: obj[{}], filed[{}], message[{}]", violation.getRootBeanClass(),
                    violation.getPropertyPath(), violation.getMessage());
        }
        return Result.error(Code.VALID_ERROR, validErrorList);
    }

    /**
     * 封装错误异常
     *
     * @param result
     * @return
     */
    private List<String> adapterValidError(BindingResult result){
        List<String> validErrorList = new ArrayList<String>();
        for (FieldError fieldError : result.getFieldErrors()) {
            validErrorList.add(fieldError.getDefaultMessage());
            LOGGER.warn("valid error: obj[{}], filed[{}], message[{}]",
                    fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return validErrorList;
    }

}

