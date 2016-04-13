package com.oneplus.mybatis.generat.starter.impl;

import com.oneplus.mybatis.generat.core.Generator;
import com.oneplus.mybatis.generat.core.context.AutoCodeContext;
import com.oneplus.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：自动化生成代码插件执行抽象实现
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/12 Time：23:41
 */
public class MySqlPluginGeneratorStarterImpl extends MysqlDefaultGeneratorStarterImpl {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MySqlPluginGeneratorStarterImpl.class);

    @Override
    protected void executeGenerator(Generator generator, AutoCodeContext generatorContext, AutoCodeGeneratorType configType) {
        generator.pluginGenerator(generatorContext, configType);
    }
}
