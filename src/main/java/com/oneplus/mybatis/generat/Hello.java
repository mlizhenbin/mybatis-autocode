package com.oneplus.mybatis.generat;

import com.oneplus.mybatis.generat.start.GeneratorStarter;
import com.oneplus.mybatis.generat.start.OracleGeneratorStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 16/4/12 Time: 11:31
 */
public class Hello {

    /** sl4j */
    private static final Logger LOGGER = LoggerFactory.getLogger(Hello.class);

    public static void main(String[] args) {
        GeneratorStarter starter = new OracleGeneratorStarter();
        starter.start();
        LOGGER.info("auto plugin Generator code finish...");
    }
}
