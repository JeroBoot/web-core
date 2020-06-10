package com.jero.mp.query.enums;

import com.jero.common.utils.StringUtils;
import lombok.Getter;

@Getter
public enum QueryCriteriaEnum {

    GT(">","gt","大于"),
    GE(">=","ge","大于等于"),
    LT("<","lt","小于"),
    LE("<=","le","小于等于"),
    EQ("=","eq","等于"),
    NE("!=","ne","不等于"),
    IN("IN","in","包含"),
    LIKE("LIKE","like","全模糊"),
    LEFT_LIKE("LEFT_LIKE","left_like","左模糊"),
    RIGHT_LIKE("RIGHT_LIKE","right_like","右模糊"),
    SQL_RULES("USE_SQL_RULES","ext","自定义SQL片段");

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
