package com.mustr.utils;

import cn.hutool.core.util.IdUtil;

/**
 * id生成工具
 * @author chenxj
 * @Date 2021-6-7
 *
 */
public class IdGenerateUtil {

    /**
     * 生成一个雪花算法的唯一id
     * @return
     */
    public static long generateId() {
        return IdUtil.getSnowflake(1, 1).nextId();
    }
    
}
