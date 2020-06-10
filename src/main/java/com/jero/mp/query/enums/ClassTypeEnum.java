package com.jero.mp.query.enums;

import com.jero.common.utils.ConvertUtils;
import com.jero.common.utils.StringUtils;
import lombok.Getter;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Java的类型枚举
 */
@Getter
public enum ClassTypeEnum {

    INTEGER("Integer", "class java.lang.Integer"){
        public Object get(String value){
            return Integer.parseInt(value);
        }
    },
    BIG_DECIMAL("BigDecimal", "class java.math.BigDecimal"){
        public Object get(String value){
            return new BigDecimal(value);
        }
    },
    SHORT("Short", "class java.lang.Short"){
        public Object get(String value){
            return Short.parseShort(value);
        }
    },
    LONG("Long", "class java.lang.Long"){
        public Object get(String value){
            return Long.parseLong(value);
        }
    },
    FLOAT("Float", "class java.lang.Float"){
        public Object get(String value){
            return Float.parseFloat(value);
        }
    },
    DOUBLE("Double", "class java.lang.Double"){
        public Object get(String value){
            return Double.parseDouble(value);
        }
    },
    LOCAL_DATE_TIME("LocalDateTime", "class java.time.LocalDateTime"){
        public Object get(String value){
            try {
                return ConvertUtils.strToLocalDateTime(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    },
    LOCAL_DATE("LocalDate", "class java.time.LocalDate"){
        public Object get(String value){
            try {
                return ConvertUtils.strToLocalDate(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    },
    DATE("Date", "class java.util.Date"){
        public Object get(String value){
            try {
                return ConvertUtils.strToDate(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    /**
     * 将传入的值根据类型转换为对应的对象
     * @param value
     * @return
     */
    public abstract Object get(String value);

    private String code;
    private String value;

    ClassTypeEnum(String code, String value){
        this.code = code;
        this.value = value;
    }

    public static String getCodeByValue(String code){
        if (StringUtils.isEmail(code)){
            return StringUtils.EMPTY;
        }

        for (ClassTypeEnum enums: ClassTypeEnum.values()){
            if (enums.getCode().equals(code)){
                return enums.getValue();
            }
        }

        return StringUtils.EMPTY;
    }

}
