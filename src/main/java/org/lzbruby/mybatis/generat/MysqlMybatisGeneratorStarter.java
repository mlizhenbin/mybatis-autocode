package org.lzbruby.mybatis.generat;

import org.lzbruby.mybatis.generat.starter.GeneratorStarter;
import org.lzbruby.mybatis.generat.starter.impl.MysqlDefaultGeneratorStarterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：自动化生成代码启动入口
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
 * Date: 15/6/12 Time：23:42
 */
public class MysqlMybatisGeneratorStarter {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlMybatisGeneratorStarter.class);

    public static void main(String[] args) {
        GeneratorStarter starter = new MysqlDefaultGeneratorStarterImpl();
        starter.start();
        LOGGER.info("auto plugin Generator code finish...");
    }
}
