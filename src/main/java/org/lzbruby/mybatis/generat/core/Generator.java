package org.lzbruby.mybatis.generat.core;

import org.lzbruby.mybatis.generat.core.context.AutoCodeContext;
import org.lzbruby.mybatis.generat.core.context.AutoCodeGeneratorType;

/**
 * 功能描述：读取配置自动化生成代码接口
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
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
