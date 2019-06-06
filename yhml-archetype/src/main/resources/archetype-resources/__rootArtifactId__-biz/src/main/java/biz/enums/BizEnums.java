import lombok.AllArgsConstructor;
import lombok.Getter;

#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' ) package ${package}.biz.enums;

@Getter
@AllArgsConstructor
public enum BizEnums {

    private String name;
    private String value;
}
