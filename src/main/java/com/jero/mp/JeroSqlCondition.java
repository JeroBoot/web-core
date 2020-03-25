package com.jero.mp;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * mybatis plus Table Field sql构造条件
 * @date 2020-03-23
 */
public class JeroSqlCondition extends SqlCondition {

    public static final String GT = "%s&gt;#{%s}";
    public static final String LT = "%s&lt;#{%s}";


}
