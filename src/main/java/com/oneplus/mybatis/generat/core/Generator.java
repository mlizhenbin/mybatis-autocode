package com.oneplus.mybatis.generat.core;

import com.oneplus.mybatis.generat.core.context.AutoCodeContext;
import com.oneplus.mybatis.generat.core.context.AutoCodeGeneratorType;

/**
 * 功能描述：读取配置自动化生成代码接口
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:22
 */
public interface Generator {

    /**
     * 读取配置生成文件
     *
     * @param context
     * @param configType
     */
    public void defaultGenerator(AutoCodeContext context, AutoCodeGeneratorType configType);

    /**
     * 插件配置生成文件
     *
     * @param context
     * @param configType
     */
    public void pluginGenerator(AutoCodeContext context, AutoCodeGeneratorType configType);
}
