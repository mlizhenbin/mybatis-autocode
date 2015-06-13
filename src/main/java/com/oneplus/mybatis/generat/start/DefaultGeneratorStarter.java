package com.oneplus.mybatis.generat.start;

import com.oneplus.mybatis.generat.config.GeneratorConfigurer;
import com.oneplus.mybatis.generat.config.GeneratorConfigurerFactory;
import com.oneplus.mybatis.generat.connect.Connector;
import com.oneplus.mybatis.generat.connect.MysqlConnector;
import com.oneplus.mybatis.generat.generator.Generator;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.impl.ControllerGenerator;
import com.oneplus.mybatis.generat.utils.FileUtils;
import com.oneplus.mybatis.generat.utils.GeneratorStringUtils;
import com.oneplus.mybatis.generat.utils.PropertiesUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 功能描述：
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/12 Time：23:41
 */
public class DefaultGeneratorStarter implements GeneratorStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGeneratorStarter.class);

    private static Properties properties;

    private static Connector connector;

    static {
        GeneratorConfigurer generatorConfigurer = GeneratorConfigurerFactory.getGeneratorConfigurer();
        properties = generatorConfigurer.getProperties();
        connector = new MysqlConnector(properties);
    }

    public void start() {
        try {
            generator();
        } catch (Exception e) {
            throw new RuntimeException("启动创建代码工具出现异常", e);
        }
    }

    protected void generator() {
        FileUtils.createPackageDirectory(properties);

        String primaryKey;
        List<String> tables;

        tables = PropertiesUtils.getTableList(properties);
        if (CollectionUtils.isEmpty(tables)) {
            throw new RuntimeException("配置代码生成表格为空.");
        }

        for (String tableName : tables) {
            try {
                Map<String, String> pkMap = connector.getPrimaryKey(tableName);
                primaryKey = pkMap.get("primaryKey");
            } catch (Exception e) {
                LOGGER.error(tableName + " 在数据库中不存在，请检查配置和数据库表结构.", e);
                return;
            }

            if (StringUtils.isBlank(primaryKey)) {
                LOGGER.error(tableName + " 表结构没有主键，请检查表结构，生成代码失败.");
            }

            String layerConfig = PropertiesUtils.getLayers(properties);
            String[] layers = StringUtils.split(layerConfig, ",");
            if (ArrayUtils.isEmpty(layers)) {
                LOGGER.error("读取配置文件分层结构为空，请检查配置是否按照逗号隔开.");
                return;
            }

            Generator controllerGenerator = new ControllerGenerator();
            controllerGenerator.generator(initBaseContext(tableName));

            LOGGER.info(tableName + " 生成代码成功.");
        }

        LOGGER.info("全部生成代码成功.");
    }

    /**
     * 初始化渲染模板基本参数上下文
     *
     * @param tableName
     * @return
     */
    protected GeneratorContext initBaseContext(String tableName) {
        Map<String, String> propMap = connector.getPrimaryKey(tableName);
        String upClassName = GeneratorStringUtils.firstUpperAndNoPrefix(tableName, properties);
        String lowClassName = GeneratorStringUtils.formatAndNoPrefix(tableName, properties);
        String packageName = PropertiesUtils.getPackage(properties);
        String primaryKeyType = propMap.get("primaryKeyType");
        String primaryKey = GeneratorStringUtils.firstUpperNoFormat(GeneratorStringUtils.format(propMap.get("primaryKey")));

        GeneratorContext context = new GeneratorContext(tableName, upClassName, lowClassName,
                packageName, primaryKeyType, primaryKey, properties);
        return context;
    }
}
