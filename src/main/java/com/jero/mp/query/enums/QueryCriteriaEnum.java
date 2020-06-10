package com.jero.mp.query.enums;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jero.common.utils.StringUtils;
import lombok.Getter;

/**
 * 查询条件枚举
 */
@Getter
public enum QueryCriteriaEnum {

    EQ("=","eq","等于"){
        public void assemblyQuery(QueryWrapper<?> queryWrapper, String fieldName, Object resultValue){
            queryWrapper.eq(fieldName, resultValue);
        }
    },
    GT(">","gt","大于"){
        public void assemblyQuery(QueryWrapper<?> queryWrapper, String fieldName, Object resultValue){
            queryWrapper.gt(fieldName, resultValue);
        }
    },
    GE(">=","ge","大于等于"){
        public void assemblyQuery(QueryWrapper<?> queryWrapper, String fieldName, Object resultValue){
            queryWrapper.ge(fieldName, resultValue);
        }
    },
    LT("<","lt","小于"){
        public void assemblyQuery(QueryWrapper<?> queryWrapper, String fieldName, Object resultValue){
            queryWrapper.lt(fieldName, resultValue);
        }
    },
    LE("<=","le","小于等于"){
        public void assemblyQuery(QueryWrapper<?> queryWrapper, String fieldName, Object resultValue){
            queryWrapper.le(fieldName, resultValue);
        }
    },
    NE("!=","ne","不等于"){
        public void assemblyQuery(QueryWrapper<?> queryWrapper, String fieldName, Object resultValue){
            queryWrapper.ne(fieldName, resultValue);
        }
    },
    IN("IN","in","包含"){
        public void assemblyQuery(QueryWrapper<?> queryWrapper, String fieldName, Object resultValue){
            if (resultValue instanceof String){
                queryWrapper.in(fieldName, (Object[]) resultValue.toString().split(","));
            } else if (resultValue instanceof String[]){
                queryWrapper.in(fieldName, (Object[]) resultValue);
            } else {
                queryWrapper.in(fieldName, resultValue);
            }
        }
    },
    LIKE("LIKE","like","全模糊"){
        public void assemblyQuery(QueryWrapper<?> queryWrapper, String fieldName, Object resultValue){
            queryWrapper.like(fieldName, resultValue);
        }
    },
    LEFT_LIKE("LEFT_LIKE","left_like","左模糊"){
        public void assemblyQuery(QueryWrapper<?> queryWrapper, String fieldName, Object resultValue){
            queryWrapper.likeLeft(fieldName, resultValue);
        }
    },
    RIGHT_LIKE("RIGHT_LIKE","right_like","右模糊"){
        public void assemblyQuery(QueryWrapper<?> queryWrapper, String fieldName, Object resultValue){
            queryWrapper.likeRight(fieldName, resultValue);
        }
    },
    SQL_RULES("USE_SQL_RULES","ext","自定义SQL片段"){
        public void assemblyQuery(QueryWrapper<?> queryWrapper, String fieldName, Object resultValue){
            // TODO
        }
    };

    /**
     * 针对每个枚举封装对象
     * @return
     */
    public abstract void assemblyQuery(QueryWrapper<?> queryWrapper, String fieldName, Object resultValue);

    private String code;

    private String condition;

    private String msg;

    QueryCriteriaEnum(String code, String condition, String msg){
        this.code = code;
        this.condition = condition;
        this.msg = msg;
    }

    public static String getConditionByCode(String code){
        if (StringUtils.isEmail(code)){
            return StringUtils.EMPTY;
        }

        for (QueryCriteriaEnum enums: QueryCriteriaEnum.values()){
            if (enums.getCode().equals(code)){
                return enums.getCondition();
            }
        }

        return StringUtils.EMPTY;
    }

    public static QueryCriteriaEnum getByCode(String code){
        if (StringUtils.isEmail(code)){
            return null;
        }

        for (QueryCriteriaEnum enums: QueryCriteriaEnum.values()){
            if (enums.getCode().equals(code)){
                return enums;
            }
        }

        return null;
    }

}
