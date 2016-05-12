package org.lzbruby.mybatis.generat;

import org.lzbruby.mybatis.generat.starter.GeneratorStarter;
import org.lzbruby.mybatis.generat.starter.impl.OracleDefaultGeneratorStarterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：OracleMybatisGeneratorStarter
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
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