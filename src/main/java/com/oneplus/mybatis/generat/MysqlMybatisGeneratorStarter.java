package com.oneplus.mybatis.generat;

import com.oneplus.mybatis.generat.start.GeneratorStarter;
import com.oneplus.mybatis.generat.start.MysqlDefaultGeneratorStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：自动化生成代码启动入口
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/12 Time：23:42
 */
public class MysqlMybatisGeneratorStarter {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlMybatisGeneratorStarter.class);

    public static void main(String[] args) {
        GeneratorStarter starter = new MysqlDefaultGeneratorStarter();
        starter.start();
        LOGGER.info("auto plugin Generator code finish...");
    }
}
