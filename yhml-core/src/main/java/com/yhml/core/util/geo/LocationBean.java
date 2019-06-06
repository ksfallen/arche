package com.yhml.core.util.geo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Jfeng
 * @date: 2019-06-05
 */
@Getter
@Setter
@AllArgsConstructor
public class LocationBean {
    public static final double MINLAT = -90;
    public static final double MAXLAT = 90;
    public static final double MINLNG = -180;
    public static final double MAXLNG = 180;
    private double lat;//纬度[-90,90]
    private double lng;//经度[-180,180]
}
