package com.yhml.core.util.geo;

import java.util.*;

import com.yhml.core.util.Arith;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author: Jfeng
 * @date: 2019-06-05
 */
@NoArgsConstructor
public class Geohash {
    private static final double EARTH_RADIUS = 6371000;//赤道半径(单位m)

    private static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
            'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static Map<Character, Integer> CHARSMAP;

    static {
        CHARSMAP = new HashMap();
        for (int i = 0; i < CHARS.length; i++) {
            CHARSMAP.put(CHARS[i], i);
        }
    }


    /**
     * 1 2500km 2 630km 3 78km 4 30km
     * 5 2.4km  6 610m; 7 76m  8 19m
     */
    @Getter
    private int hashLength = 12; //经纬度转化为geohash长度
    private int latLength = 30; //纬度转化为二进制长度
    private int lngLength = 30; //经度转化为二进制长度
    private double minLat;//每格纬度的单位大小
    private double minLng;//每个经度的倒下
    private LocationBean location;


    public Geohash(double lat, double lng) {
        location = new LocationBean(lat, lng);
        setMinLatLng();
    }

    public static void main(String[] args) {
        // geohash.org/wtm7yp690586
        double lat = 30.230446;
        double lng = 120.149918;

        // wtq2yx8yfp3s
        double lat2 = 29.705483;
        double lng2 = 121.575825;

        System.out.println("lat = " + lat);
        System.out.println("lng = " + lng);

        Geohash g = new Geohash(lat, lng);
        System.out.println("当前坐标：" + g.getGeoHash());

        LocationBean location = g.getLocation(g.getGeoHash());
        System.out.println("geohash to point: " + location.getLat() + "," + location.getLng());

        double distance = Geohash.distance(lat, lng, lat2, lng2);
        System.out.println("两点相距：" + distance + " 米");

        // System.out.println(JsonUtil.toJsonString(g.getGeoHashBase32For9()));
    }


    /**
     * 基于googleMap中的算法得到两经纬度之间的距离
     * 计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下
     *
     * @param lat1 第一点的纬度
     * @param lng1 第一点的精度
     * @param lat2 第二点的纬度
     * @param lng2 第二点的精度
     * @return 返回的距离，单位m
     **/
    private static double distance(double lat1, double lng1, double lat2, double lng2) {
        return Math.round(calculateDistance(lat1, lng1, lat2, lng2));
    }

    private static double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double dLat = toRadians(lat2 - lat1);
        double dLon = toRadians(lng2 - lng1);
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS * c;
    }

    /**
     * 弧度
     *
     * @param d
     * @return
     */
    private static double toRadians(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 设置经纬度的最小单位
     */
    private void setMinLatLng() {
        minLat = LocationBean.MAXLAT - LocationBean.MINLAT;
        for (int i = 0; i < latLength; i++) {
            minLat /= 2.0;
        }
        minLng = LocationBean.MAXLNG - LocationBean.MINLNG;
        for (int i = 0; i < lngLength; i++) {
            minLng /= 2.0;
        }
    }

    /**
     * 设置经纬度转化为geohash长度
     *
     * @param length
     * @return
     */
    public boolean sethashLength(int length) {
        if (length < 1) {
            return false;
        }
        hashLength = length;
        latLength = (length * 5) / 2;
        if (length % 2 == 0) {
            lngLength = latLength;
        } else {
            lngLength = latLength + 1;
        }
        setMinLatLng();
        return true;
    }

    /**
     * 获取经纬度的base32字符串
     *
     * @return
     */
    public String getGeoHash() {
        return getGeoHashBase32(location.getLat(), location.getLng());
    }

    /**
     * 获取经纬度的base32字符串
     *
     * @param lat
     * @param lng
     * @return
     */
    private String getGeoHashBase32(double lat, double lng) {
        boolean[] bools = getGeoBinary(lat, lng);
        if (bools == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bools.length; i = i + 5) {
            boolean[] base32 = new boolean[5];
            System.arraycopy(bools, i, base32, 0, 5);
            char cha = getBase32Char(base32);
            if (' ' == cha) {
                return null;
            }
            sb.append(cha);
        }
        return sb.toString();
    }

    /**
     * 获取坐标的geo二进制字符串
     *
     * @param lat
     * @param lng
     * @return
     */
    private boolean[] getGeoBinary(double lat, double lng) {
        boolean[] latArray = getHashArray(lat, LocationBean.MINLAT, LocationBean.MAXLAT, latLength);
        boolean[] lngArray = getHashArray(lng, LocationBean.MINLNG, LocationBean.MAXLNG, lngLength);
        return merge(latArray, lngArray);
    }

    /**
     * 将数字转化为geohash二进制字符串
     *
     * @param value
     * @param min
     * @param max
     * @param length
     * @return
     */
    private boolean[] getHashArray(double value, double min, double max, int length) {
        if (value < min || value > max) {
            return null;
        }
        if (length < 1) {
            return null;
        }
        boolean[] result = new boolean[length];
        for (int i = 0; i < length; i++) {
            double mid = (min + max) / 2.0;
            if (value > mid) {
                result[i] = true;
                min = mid;
            } else {
                result[i] = false;
                max = mid;
            }
        }
        return result;
    }

    /**
     * 合并经纬度二进制
     *
     * @param latArray
     * @param lngArray
     * @return
     */
    private boolean[] merge(boolean[] latArray, boolean[] lngArray) {
        if (latArray == null || lngArray == null) {
            return null;
        }
        boolean[] result = new boolean[lngArray.length + latArray.length];
        Arrays.fill(result, false);
        for (int i = 0; i < lngArray.length; i++) {
            result[2 * i] = lngArray[i];
        }
        for (int i = 0; i < latArray.length; i++) {
            result[2 * i + 1] = latArray[i];
        }
        return result;
    }

    /**
     * 将五位二进制转化为base32
     *
     * @param base32
     * @return
     */
    private char getBase32Char(boolean[] base32) {
        if (base32 == null || base32.length != 5) {
            return ' ';
        }
        int num = 0;
        for (boolean bool : base32) {
            num <<= 1;
            if (bool) {
                num += 1;
            }
        }
        return CHARS[num % CHARS.length];
    }

    /**
     * 求所在坐标点及周围点组成的九个
     *
     * @return
     */
    public List<String> getGeoHashBase32For9() {
        double leftLat = location.getLat() - minLat;
        double rightLat = location.getLat() + minLat;
        double upLng = location.getLng() - minLng;
        double downLng = location.getLng() + minLng;
        List<String> base32For9 = new ArrayList<>();
        //左侧从上到下 3个
        String leftUp = getGeoHashBase32(leftLat, upLng);
        if (!(leftUp == null || "".equals(leftUp))) {
            base32For9.add(leftUp);
        }
        String leftMid = getGeoHashBase32(leftLat, location.getLng());
        if (!(leftMid == null || "".equals(leftMid))) {
            base32For9.add(leftMid);
        }
        String leftDown = getGeoHashBase32(leftLat, downLng);
        if (!(leftDown == null || "".equals(leftDown))) {
            base32For9.add(leftDown);
        }
        //中间从上到下 3个
        String midUp = getGeoHashBase32(location.getLat(), upLng);
        if (!(midUp == null || "".equals(midUp))) {
            base32For9.add(midUp);
        }
        String midMid = getGeoHashBase32(location.getLat(), location.getLng());
        if (!(midMid == null || "".equals(midMid))) {
            base32For9.add(midMid);
        }
        String midDown = getGeoHashBase32(location.getLat(), downLng);
        if (!(midDown == null || "".equals(midDown))) {
            base32For9.add(midDown);
        }
        //右侧从上到下 3个
        String rightUp = getGeoHashBase32(rightLat, upLng);
        if (!(rightUp == null || "".equals(rightUp))) {
            base32For9.add(rightUp);
        }
        String rightMid = getGeoHashBase32(rightLat, location.getLng());
        if (!(rightMid == null || "".equals(rightMid))) {
            base32For9.add(rightMid);
        }
        String rightDown = getGeoHashBase32(rightLat, downLng);
        if (!(rightDown == null || "".equals(rightDown))) {
            base32For9.add(rightDown);
        }
        return base32For9;
    }

    /**
     * 将数值转化为二进制字符串
     *
     * @param i
     * @return
     */
    private String getBase32BinaryString(int i) {
        if (i < 0 || i > 31) {
            return null;
        }
        String str = Integer.toBinaryString(i + 32);
        return str.substring(1);
    }

    /**
     * 将GeoHash转化为二进制字符串
     *
     * @param geoHash
     * @return
     */
    private String getGeoHashBinaryString(String geoHash) {
        if (geoHash == null || "".equals(geoHash)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < geoHash.length(); i++) {
            char c = geoHash.charAt(i);
            if (CHARSMAP.containsKey(c)) {
                String cStr = getBase32BinaryString(CHARSMAP.get(c));
                if (cStr != null) {
                    sb.append(cStr);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 返回geoHash 对应的坐标
     *
     * @param geoHash
     * @return
     */
    public LocationBean getLocation(String geoHash) {
        String geoHashBinaryStr = getGeoHashBinaryString(geoHash);
        if (geoHashBinaryStr == null) {
            return null;
        }
        StringBuilder lat = new StringBuilder();
        StringBuilder lng = new StringBuilder();
        for (int i = 0; i < geoHashBinaryStr.length(); i++) {
            if (i % 2 != 0) {
                lat.append(geoHashBinaryStr.charAt(i));
            } else {
                lng.append(geoHashBinaryStr.charAt(i));
            }
        }
        double latValue = getGeoHashMid(lat.toString(), LocationBean.MINLAT, LocationBean.MAXLAT);
        double lngValue = getGeoHashMid(lng.toString(), LocationBean.MINLNG, LocationBean.MAXLNG);

        latValue = Arith.round(latValue, 6);
        lngValue = Arith.round(lngValue, 6);

        LocationBean location = new LocationBean(latValue, lngValue);
        return location;
    }

    /**
     * 返回二进制对应的中间值
     *
     * @param binaryStr
     * @param min
     * @param max
     * @return
     */
    private double getGeoHashMid(String binaryStr, double min, double max) {
        if (binaryStr == null || binaryStr.length() < 1) {
            return (min + max) / 2.0;
        }
        if (binaryStr.charAt(0) == '1') {
            return getGeoHashMid(binaryStr.substring(1), (min + max) / 2.0, max);
        } else {
            return getGeoHashMid(binaryStr.substring(1), min, (min + max) / 2.0);
        }
    }

}
