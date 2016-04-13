package com.oneplus.mybatis.generat;

import com.oneplus.mybatis.generat.starter.GeneratorStarter;
import com.oneplus.mybatis.generat.starter.impl.OracleDefaultGeneratorStarterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：OracleMybatisGeneratorStarter
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 16/4/12 Time: 11:31
 */
public class OracleMybatisGeneratorStarter {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OracleMybatisGeneratorStarter.class);

    public static void main(String[] args) {
        GeneratorStarter starter = new OracleDefaultGeneratorStarterImpl();
        starter.start();
        LOGGER.info("auto plugin Generator code finish...");
    }
}