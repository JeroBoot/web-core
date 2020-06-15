package com.jero.mp.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.CaseFormat;
import com.jero.common.utils.ConvertUtils;
import com.jero.common.utils.StringUtils;
import com.jero.mp.query.enums.ClassTypeEnum;
import com.jero.mp.query.enums.QueryCriteriaEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.formula.functions.T;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
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
    private static final String ORDER_TYPE_ASC = "ASC";

    private static final String FIELD = "field"; //前端要求显示字段

    private static final String COMMA = ",";


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
        //通过反射获取字段名和字段类型
        PropertyDescriptor propertyDescriptor[] = PropertyUtils.getPropertyDescriptors(entity);

        /*
         * select字段组
         * TODO 处理要查询的字段
         */
        for (int i = 0; i < propertyDescriptor.length; i++){
            String fieldName = propertyDescriptor[i].getName();
            String fieldType = propertyDescriptor[i].getPropertyType().toString();

            try {
                if (isFilterField(fieldName) || !PropertyUtils.isReadable(entity, fieldName)){
                    continue;
                }

                //处理区间查询的情况，带_begin和_end代表是区间查询
                if (paramMap != null && paramMap.containsKey(fieldName + BEGIN)){
                    // TODO 测试前端传来的值是否接收为map
                    String beginValue = paramMap.get(fieldName + BEGIN)[0].trim();
                    addQueryByCriteria(queryWrapper, fieldName, fieldType, beginValue, QueryCriteriaEnum.GE);
                }
                if (paramMap != null && paramMap.containsKey(fieldName + END)){
                    String endValue = paramMap.get(fieldName + END)[0].trim();
                    addQueryByCriteria(queryWrapper, fieldName, fieldType, endValue, QueryCriteriaEnum.LE);
                }

                //从对象中取出字段值
                Object value = PropertyUtils.getSimpleProperty(entity, fieldName);
                QueryCriteriaEnum criteria = convertToCriteria(value);
                handlerCriteria(queryWrapper, fieldName, criteria, value);

            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }

        //排序逻辑
        doFieldOrder(queryWrapper, paramMap);
        //高级查询

    }

    /**
     * 过滤不拼接到查询条件里的字段
     * @param name
     * @return
     */
    private static boolean isFilterField(String name){
        String[] filterName = new String[]{"class", "id", "ids", "page", "sort", "rows"};
        for (int i = 0; i < filterName.length; i++) {
            if (filterName[i].equals(name)){
                return true;
            }
        }
        return false;
    }

    /**
     * 添加查询规则
     * @param queryWrapper
     * @param fieldName 字段名
     * @param fieldType 字段类型
     * @param fieldValue 字段值
     * @param queryCriteriaEnum 查询条件枚举
     */
    private static void addQueryByCriteria(QueryWrapper<?> queryWrapper,
                                       String fieldName,
                                       String fieldType,
                                       String fieldValue,
                                       QueryCriteriaEnum queryCriteriaEnum){

        if (StringUtils.isEmpty(fieldValue)){
            return;
        }

        Object resultValue = handlerValueToObj(fieldType, fieldValue);
        handlerCriteria(queryWrapper, fieldName, queryCriteriaEnum, resultValue);
    }

    /**
     * 将param中取得的值转换为对应的对象
     * @param fieldType 字段的class类型
     * @param fieldValue 字段的值
     * @return
     */
    private static Object handlerValueToObj(String fieldType, String fieldValue){
        Object resultValue = fieldValue;
        //遍历所有类型，进行数据转换
        for (ClassTypeEnum classTypeEnum : ClassTypeEnum.values()){
            if (classTypeEnum.getValue().equals(fieldType)){
                resultValue = classTypeEnum.get(fieldValue);
                log.debug("转换类型为{}，原始数据为{}，转换得到的值为{}", fieldType, fieldValue, resultValue);
                break;
            }
        }
        return resultValue;
    }

    /**
     * 将结果值和查询条件封装后加入queryWrapper中
     * @param queryWrapper
     * @param fieldName
     * @param queryCriteriaEnum
     * @param resultValue
     */
    private static void handlerCriteria(QueryWrapper<?> queryWrapper,
                                        String fieldName,
                                        QueryCriteriaEnum queryCriteriaEnum,
                                        Object resultValue){

        if (resultValue == null || queryCriteriaEnum == null){
            return;
        }

        fieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);

        log.info("--查询规则-->{} {} {}", fieldName, queryCriteriaEnum.getCode(), resultValue);
        //遍历所有条件，筛选出符合条件的加入QueryWrapper
        for (QueryCriteriaEnum queryCriteria: QueryCriteriaEnum.values()){
            if (queryCriteria.getCode().equals(queryCriteriaEnum.getCode())){
                queryCriteria.assemblyQuery(queryWrapper, fieldName, resultValue);
                log.debug("匹配到查询规则-->{} {} {}", fieldName, queryCriteria.getCode(), resultValue);
                break;
            }
        }
    }

    /**
     * 根据所传的值 转化成对应的比较方式
     * String 转换为like
     * 带逗号的String转为 in
     * @param value
     * @return
     */
    private static QueryCriteriaEnum convertToCriteria(Object value){
        if (value == null){
            return null;
        }

        String val = (value+"").toString().trim();
        if (val.contains(COMMA)){
            return QueryCriteriaEnum.IN;
        } else {
            return QueryCriteriaEnum.LIKE;
        }
    }

    private static void doFieldOrder(QueryWrapper<?> queryWrapper, Map<String, String[]> parameterMap){

        String orderFiled = null;
        String orderType= null;
        if (parameterMap != null && parameterMap.containsKey(ORDER_FIELD)){
            orderFiled = parameterMap.get(ORDER_FIELD)[0].trim();
        }
        if (parameterMap != null && parameterMap.containsKey(ORDER_TYPE)){
            orderType = parameterMap.get(ORDER_TYPE)[0].trim();
        }
        log.debug("排序规则为字段{} {}顺序排列", orderFiled, orderType);

        if (StringUtils.isNotEmpty(orderFiled)){
            //TODO 检查Sql注入
            if (StringUtils.isNotEmpty(orderType) && orderType.toUpperCase().indexOf(ORDER_TYPE_ASC) >= 0){
                queryWrapper.orderByAsc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderFiled));
            } else {
                queryWrapper.orderByDesc(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderFiled));
            }
        }
    }

}
