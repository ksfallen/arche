package com.yhml.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.Converter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class BeanUtil extends BeanUtils {

    private static final ConcurrentHashMap<String, BeanCopier> cache = new ConcurrentHashMap<>();

    /**
     * 对象属性拷贝, 属性类别不同也可以 copy
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        T bean = null;

        if (source == null) {
            return bean;
        }

        return copy(source, BeanUtils.instantiateClass(targetClass));
    }

    public static <T> T copy(Object source, T target) {
        BeanCopier copier = buildCopier(source, target.getClass(), false);
        copier.copy(source, target, null);
        return target;
    }

    public static <T> T copy(Object source, T target, Converter converter) {
        BeanCopier copier = buildCopier(source, target.getClass(), true);
        copier.copy(source, target, converter);
        return target;
    }

    /**
     * 对象属性拷贝 忽略 src 中的 null 值
     *
     * @param target
     */
    public static <T> T copyPropertiesIgnoreNull(Object src, T target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
        return target;
    }

    /**
     * map 转 bean
     */
    public static <T> T toBean(Map<String, ?> map, Class<T> clazz) {
        return copyWithJson(map, clazz);
    }


    /**
     * 将对象装换为map
     */
    public static Map<String, Object> toMap(Object bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean == null) {
            return map;
        }

        if (bean instanceof Map) {
            return (Map<String, Object>) bean;
        }

        return copyWithJson(bean, map.getClass());

        // try {
        //     BeanMap.create(bean).forEach((k, v) -> {
        //         if (v != null) {
        //             map.put(k.toString(), v);
        //         }
        //     });
        // } catch (Exception e) {
        //     log.error(e.getMessage(), e);
        // }

        // return map;
    }

    public static Map<String, String> toStringMap(Object bean) {
        Map<String, String> map = new HashMap<>();

        if (bean == null) {
            return map;
        }

        if (bean instanceof Map) {
            return (Map<String, String>) bean;
        }

        try {
            BeanMap.create(bean).forEach((k, v) -> {
                if (v != null) {
                    map.put(k.toString(), v.toString());
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return map;
    }

    public static MultiValueMap<String, String> toMultiValueMap(Object bean) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        if (bean == null) {
            return map;
        }

        if (bean instanceof MultiValueMap) {
            return (MultiValueMap<String, String>) bean;
        }

        return copyWithJson(bean, map.getClass());
        //
        //
        // if (bean instanceof Map) {
        //     Map temp = (Map) bean;
        //     temp.forEach((k, v) -> {
        //         if (v != null) {
        //             map.add(k.toString(), v.toString());
        //         }
        //     });
        //
        //     return map;
        // }
        //
        // BeanMap.create(bean).forEach((k, v) -> {
        //     if (v != null) {
        //         map.add(k.toString(), v.toString());
        //     }
        // });
        //
        // return map;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) {
        List<T> list = new ArrayList<>();

        if (CollectionUtils.isEmpty(maps)) {
            return list;
        }

        maps.forEach(m -> list.add(toBean(m, clazz)));

        return list;
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.
     * 如 public BookManager extends GenricManager<Book>
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.
     * 如 public BookManager extends GenricManager<Book>
     */
    public static Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException {
        return getGenricType(clazz.getGenericSuperclass(), index);
    }

    public static Class getGenricType(Type genType) {
        return getGenricType(genType, 0);
    }

    /**
     * 获取泛型的 class 类型
     *
     * @param genType
     * @param index
     * @return
     */
    public static Class getGenricType(Type genType, int index) {
        if (genType == null) {
            return null;
        }

        if (index < 0) {
            throw new IllegalArgumentException("index:" + index);
        }

        //得到泛型里的class类型对象
        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            if (index >= params.length) {
                throw new IndexOutOfBoundsException("length:" + params.length + ", index:" + index);
            }

            if (params[index] instanceof Class) {
                return (Class) params[index];
            }
        }

        return null;
    }

    private static <T> T copyWithJson(Object source, Class<T> clazz) {
        String json = JsonUtil.toJsonString(source);
        return JsonUtil.parseObject(json, clazz);
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        return emptyNames.toArray(new String[0]);
    }

    private static <S, T> BeanCopier buildCopier(S source, Class<T> target, boolean useConverter) {
        String baseKey = generateKey(source.getClass(), target);
        BeanCopier copier;
        if (!cache.containsKey(baseKey)) {
            copier = BeanCopier.create(source.getClass(), target, useConverter);
            cache.put(baseKey, copier);
        } else {
            copier = cache.get(baseKey);
        }

        return copier;
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString().trim() + "-" + class2.getSimpleName();
    }
}
