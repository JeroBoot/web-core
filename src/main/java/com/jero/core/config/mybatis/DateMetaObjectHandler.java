package com.jero.core.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jero.filter.HttpCacheFilter;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description 自动填充类
 * @Author lixuetao
 * @Date 2020/5/15
 **/
@Component
public class DateMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            Date now = new Date();
            setFieldValByName("createTime", now, metaObject);
        } catch (Exception e) {
            LOGGER.error("自动插入创建时间失败", e);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
