package com.jero.mp.query.enums;

import com.jero.common.utils.EnumUtils;
import com.jero.common.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum ClassTypeEnum {

    INTEGER("Integer", "class java.lang.Integer"),
    BIG_DECIMAL("BigDecimal", "class java.math.BigDecimal"),
    SHORT("Short", "class java.lang.Short"),
    LONG("Long", "class java.lang.Long"),
    FLOAT("Float", "class java.lang.Float"),
    DOUBLE("Double", "class java.lang.Double"),
    LOCAL_DATE_TIME("LocalDateTime", "class java.time.LocalDateTime"),
    DATE("Date", "class java.util.Date");

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
