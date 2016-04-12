package com.oneplus.mybatis.generat.generator;

import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;

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
    public void defaultGenerator(GeneratorContext context, PackageConfigType configType);

    /**
     * 插件配置生成文件
     *
     * @param context
     * @param configType
     */
    public void pluginGenerator(GeneratorContext context, PackageConfigType configType);
}
