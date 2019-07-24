#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.enums;

import lombok.*;

@Getter
@AllArgsConstructor
public enum BizEnums {

    private String name;
    private String value;
}
