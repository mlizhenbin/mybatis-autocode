package com.oneplus.mybatis.generat.start;

import com.oneplus.mybatis.generat.config.GeneratorConfigurer;
import com.oneplus.mybatis.generat.config.GeneratorConfigurerFactory;
import com.oneplus.mybatis.generat.connect.OracleConnector;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 功能描述：oracle默认实现
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 16/4/12 Time: 13:16
 */
public class OracleGeneratorStarter extends DefaultGeneratorStarter {

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
    protected boolean isLoop(PackageConfigType configType) {
        if (configType == PackageConfigType.manager) {
            return false;
        }
        return true;
    }
}
