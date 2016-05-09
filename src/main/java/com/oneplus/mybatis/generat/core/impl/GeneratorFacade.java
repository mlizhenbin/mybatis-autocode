package com.oneplus.mybatis.generat.core.impl;

import com.google.common.collect.Maps;
import com.oneplus.mybatis.generat.core.Generator;
import com.oneplus.mybatis.generat.core.context.AutoCodeContext;
import com.oneplus.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.apache.commons.collections.MapUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * 功能描述：分发生成代码适配器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/14 Time：16:36
 */
public class GeneratorFacade implements Generator {

    private Map<AutoCodeGeneratorType, Generator> generatorMap = Maps.newHashMap();

    public void defaultGenerator(AutoCodeContext context, AutoCodeGeneratorType configType) {
        getGenerator(configType).defaultGenerator(context, configType);
    }

    public void pluginGenerator(AutoCodeContext context, AutoCodeGeneratorType configType) {
        getGenerator(configType).pluginGenerator(context, configType);
    }

    public void setGeneratorMap(Map<String, Generator> map) {
        if (MapUtils.isNotEmpty(map)) {
            Iterator<Map.Entry<String, Generator>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Generator> entry = iterator.next();
                String key = entry.getKey();
                AutoCodeGeneratorType configType = AutoCodeGeneratorType.getByType(key);
                if (null == configType) {
                    throw new RuntimeException("根据key找不到生成代码的Generator，key = " + key);
                }
                this.generatorMap.put(configType, entry.getValue());
            }
        }
    }

    public Generator getGenerator(AutoCodeGeneratorType configType) {
        return generatorMap.get(configType);
    }
}
