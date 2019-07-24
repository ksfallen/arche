#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\' )
package ${package}.api.exception;

import org.apache.commons.lang3.StringUtils;

import lombok.*;

@Getter
@AllArgsConstructor
public enum ErrorMessge {
    SUCCESS("0", "success"),
    ERROR_SYS("-1", "system error"),

    ERROR_DB("1000", "db error"),
    SERVICE_NOT_AVAILABLE("1001", "service not available"),
    ERROR_REPEAT_SUBMIT("1002", "重复提交"),
    ERROR_ARGS("1003", "参数错误"),
    MISS_PARAMETER("1004", "Miss Parameter"),
    ILLEGAL_PARAMETER("1005", "Illegal Parameter")

    ;

    public static String getMessage(String code) {
        for(ErrorMessge temp: ErrorMessge.values()){
            if(StringUtils.equals(code, temp.code)){
                return temp.getMsg();
            }
        }

        return "";
    }

    private String code;
    private String msg;
}
