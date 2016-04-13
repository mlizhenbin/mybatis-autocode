package com.oneplus.mybatis.generat.starter.impl;

import com.oneplus.mybatis.generat.config.GeneratorConfigurer;
import com.oneplus.mybatis.generat.config.GeneratorConfigurerFactory;
import com.oneplus.mybatis.generat.core.connect.OracleConnector;
import com.oneplus.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 功能描述：oracle默认实现
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 16/4/12 Time: 13:16
 */
public class OracleGeneratorStarterImpl extends MysqlDefaultGeneratorStarterImpl {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OracleGeneratorStarterImpl.class);

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
