package com.jero.mp.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;

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
 * 排序字段：orderField
 * 排序形式：orderType
 * 显示字段：field
 * 高级查询字段key：advancedQueryParam
 * 高级查询拼接形式key：advancedQueryType，值为and或者or
 *
 * @Author lixuetao
 * @Date 2020/6/7
 **/
@Slf4j
public class QueryGenerator {

    private static final String BEGIN = "_begin"; //区间查询起始条件
    private static final String END = "_end"; //区间查询结束条件

    private static final String ADVANCED_QUERY_PARAM = "advancedQueryParam"; //高级查询key
    private static final String ADVANCED_QUERY_TYPE = "advancedQueryType"; //高级查询类型key，值为and或者or

    private static final String ORDER_FIELD = "orderField"; //排序字段
    private static final String ORDER_TYPE = "orderType"; //排序类型，值为ASC或者DESC

    private static final String FIELD = "field"; //前端要求显示字段


    /**
     * 根据前端查询的参数，初始化封装QueryWrapper
     * @param entity 查询的实体
     * @param paramMap 查询的各类参数
     * @return 初始化封装QueryWrapper
     */
    public static QueryWrapper<T> initQueryWrapper(T entity, Map<String, String[]> paramMap){
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        assemblyQueryCriteria(queryWrapper, entity, paramMap);
        return queryWrapper;
    }

    /**
     * 将传入的queryWrapper添加查询条件，包含select字段、查询条件、排序字段等
     * @param queryWrapper
     * @param entity
     * @param paramMap
     */
    private static void assemblyQueryCriteria(QueryWrapper<?> queryWrapper, Object entity, Map<String, String[]> paramMap){
        //select字段组装

        //简单查询

        //排序逻辑

        //高级查询
    }

}
