package org.lzbruby.mybatis.generat.starter.impl;

import org.lzbruby.mybatis.generat.config.GeneratorConfigurer;
import org.lzbruby.mybatis.generat.config.GeneratorConfigurerFactory;
import org.lzbruby.mybatis.generat.core.connect.OracleConnector;
import org.lzbruby.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 功能描述：Oracle自动化生成代码实现
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
 * Date: 16/4/12 Time: 11:09
 */
public class OraclePluginGeneratorStarterImpl extends MySqlPluginGeneratorStarterImpl {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OraclePluginGeneratorStarterImpl.class);

    /**
     * 重新初始化上线文信息
     */
    @Override
    public void buildConnector() {
        GeneratorConfigurer generatorConfigurer = GeneratorConfigurerFactory.getGeneratorConfigurer();
        properties = generatorConfigurer.getProperties();
        generatorConfigurer.initConfigParams();
        context = new ClassPathXmlApplicationContext(GeneratorConfigurer.SPRING_CONFIG);
        setConnector(new OracleConnector(properties));
        setProperties(properties);
        setContext(context);
    }

    @Override
    protected boolean isLoop(AutoCodeGeneratorType configType) {
        if (configType == AutoCodeGeneratorType.MAPPER) {
            return false;
        }
        return true;
    }
}
