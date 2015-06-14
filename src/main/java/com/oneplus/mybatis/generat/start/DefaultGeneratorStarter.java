package com.oneplus.mybatis.generat.start;

import com.oneplus.mybatis.generat.config.GeneratorConfigurer;
import com.oneplus.mybatis.generat.config.GeneratorConfigurerFactory;
import com.oneplus.mybatis.generat.connect.Connector;
import com.oneplus.mybatis.generat.connect.MysqlConnector;
import com.oneplus.mybatis.generat.generator.Generator;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import com.oneplus.mybatis.generat.utils.FileUtils;
import com.oneplus.mybatis.generat.utils.GeneratorStringUtils;
import com.oneplus.mybatis.generat.utils.PropertiesUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGeneratorStarter.class);

    private static Properties properties;

    private static Connector connector;

    static {
        GeneratorConfigurer generatorConfigurer = GeneratorConfigurerFactory.getGeneratorConfigurer();
        properties = generatorConfigurer.getProperties();
        generatorConfigurer.initConfigParams();
        connector = new MysqlConnector(properties);
    }

    /**
     * 执行生成方法
     */
    public void start() {
        try {
            generator();
            LOGGER.info("全部生成代码成功.");
        } catch (Exception e) {
            throw new RuntimeException("启动创建代码工具出现异常", e);
        }
    }

    /**
     * 自动化创建代码文件
     */
    protected void generator() {
        LOGGER.info("代码生成工具，开始生成代码...");

        // 创建目录
        FileUtils.createPackageDirectory(properties);
        List<String> tables = PropertiesUtils.getTableList(properties);
        if (CollectionUtils.isEmpty(tables)) {
            throw new RuntimeException("配置代码生成表格为空.");
        }

        // 自动化生成文件
        for (String tableName : tables) {
            try {
                Map<String, String> pkMap = connector.getPrimaryKey(tableName);
                if (StringUtils.isBlank(pkMap.get("primaryKey"))) {
                    throw new RuntimeException(tableName + " 表结构没有主键，请检查表结构，生成代码失败.");
                }
            } catch (Exception e) {
                throw new RuntimeException(tableName + " 在数据库中不存在，请检查配置和数据库表结构.", e);
            }

            String layerConfig = PropertiesUtils.getLayers(properties);
            String[] layers = StringUtils.split(layerConfig, ",");
            if (ArrayUtils.isEmpty(layers)) {
                LOGGER.error("读取配置文件分层结构为空，请检查配置是否按照逗号隔开.");
                return;
            }

            ApplicationContext context = new ClassPathXmlApplicationContext(GeneratorConfigurer.SPRING_COFIG);
            for (PackageConfigType configType : PackageConfigType.values()) {
                Generator generator = (Generator) context.getBean("generatorFacade");
                generator.generator(initBaseContext(tableName), configType);
            }
            LOGGER.info(tableName + " 生成代码成功.");
        }
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
        String columnPrimaryKey = propMap.get("primaryKey");
        String normalPrimaryKey = GeneratorStringUtils.format(propMap.get("primaryKey"));
        GeneratorContext context = new GeneratorContext(tableName, upClassName, lowClassName,
                packageName, primaryKeyType, primaryKey, properties);
        context.addAttribute("connector", connector);
        context.addAttribute("properties", properties);
        context.addAttribute("columnPrimaryKey", columnPrimaryKey);
        context.addAttribute("normalPrimaryKey", normalPrimaryKey);
        return context;
    }

}
