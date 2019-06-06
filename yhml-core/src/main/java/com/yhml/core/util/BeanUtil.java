package com.yhml.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.alibaba.fastjson.serializer.SerializerFeature;

import lombok.extern.slf4j.Slf4j;

/**
 * User: Jfeng Date: 2017/3/14
 */
@Slf4j
public class BeanUtil extends BeanUtils {

    /**
     * map 转 bean
     */
    public static <T> T toBean(Map<String, ?> map, Class<T> clazz) {
        // T bean = null;
        // try {
        //     bean = instantiateClass(clazz);
        //     BeanMap.create(bean).putAll(map);
        // } catch (BeansException e) {
        //     log.error(e.getMessage());
        //     return null;
        // }
        //
        // return bean;
        return copyWithJson(map, clazz);
    }

    /**
     * 对象属性拷贝 忽略 src 中的 null 值
     *
     * @param src
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * 对象属性拷贝, 属性类别不同也可以 copy
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        T bean = null;

        if (source == null) {
            return bean;
        }

        return copyWithJson(source, targetClass);
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

    /**
     * 打印类的基本类型
     *
     * @param clazz
     * @return
     */
    public static <T> T instance(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T instance = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType() == String.class) {
                field.set(instance, "");
            }
            if (field.getType() == Integer.class) {
                field.set(instance, 0);
            }
            if (field.getType() == Double.class) {
                field.set(instance, 0.0);
            }

            System.out.println(field.getName() + ":" + field.get(instance));
        }

        String string = JsonUtil.toJsonString(instance, SerializerFeature.PrettyFormat);
        System.out.println(string);

        return instance;
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
}
