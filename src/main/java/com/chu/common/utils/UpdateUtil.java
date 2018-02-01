package com.chu.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * 更新操作工具类
 * 用来实体对象中的null赋值
 *
 * @author zhurongzeng
 * @create 2018-01-30 16:56
 **/
@Slf4j
public class UpdateUtil {
    /**
     * 将空值的属性从目标实体类中复制到源实体类中
     *
     * @param src    : 要将属性中的空值覆盖的对象(源实体类)
     * @param target :从数据库根据id查询出来的目标对象
     */
    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullProperties(src));
    }

    /**
     * 将为空的properties给找出来,然后返回出来
     *
     * @param src
     * @return
     */
    private static String[] getNullProperties(Object src) {
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> emptyName = new HashSet<>();
        for (PropertyDescriptor p : pds) {
            try {
                Object srcValue = srcBean.getPropertyValue(p.getName());
                if (srcValue == null) emptyName.add(p.getName());
            } catch (Exception e) {
                log.error("获取属性{}失败！", p.getName());
            }
        }
        String[] result = new String[emptyName.size()];
        return emptyName.toArray(result);
    }
}
