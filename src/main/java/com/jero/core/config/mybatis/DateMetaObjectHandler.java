package com.jero.core.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description 自动填充类
 * @Author lixuetao
 * @Date 2020/5/15
 **/
@Component
public class DateMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        setFieldValByName("createTime", now, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
