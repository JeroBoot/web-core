package com.jero.core.advice.dto;

/**
 * @Description 错误校验类
 *  * 封装了错误字段和错误消息
 *  * 用于处理通用的java注解校验产生的错误
 * @Author lixuetao
 * @Date 2020/3/24
 **/
public class ValidError {
    /**
     * 字段
     */
    private String field;

    /**
     * 错误信息
     */
    private String message;

    public ValidError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
