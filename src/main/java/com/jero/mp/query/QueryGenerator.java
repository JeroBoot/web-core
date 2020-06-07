package com.jero.mp.query;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description Mp的QueryWrapper生成器。可根据前端传来的参数、查询条件
 * 自动封装为Mp的QueryWrapper生成器
 *
 * 高级查询条件
 * [
 *     {
 *         "rule": "gt",
 *         "type": "input",
 *         "val": "方法",
 *         "field": "realname"
 *     },
 *     {
 *         "rule": "eq",
 *         "type": "select",
 *         "dictCode": "sex",
 *         "val": "1",
 *         "field": "sex"
 *     }
 * ]
 *
 * 排序字段：order_field
 * 排序形式：order
 * 显示字段：field
 * 高级查询字段key：advancedQueryParam
 * 高级查询拼接形式key：advancedQueryType，值为and或者or
 *
 * @Author lixuetao
 * @Date 2020/6/7
 **/
@Slf4j
public class QueryGenerator {
}
