package org.lzbruby.mybatis.generat.starter.impl;

import org.lzbruby.mybatis.generat.config.GeneratorConfigurer;
import org.lzbruby.mybatis.generat.config.GeneratorConfigurerFactory;
import org.lzbruby.mybatis.generat.core.connect.Connector;
import org.lzbruby.mybatis.generat.core.connect.MysqlConnector;
import org.lzbruby.mybatis.generat.core.Generator;
import org.lzbruby.mybatis.generat.core.context.AutoCodeContext;
import org.lzbruby.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.lzbruby.mybatis.generat.config.AutoCodeConstantsType;
import org.lzbruby.mybatis.generat.starter.GeneratorStarter;
import org.lzbruby.mybatis.generat.utils.GeneratorFileUtils;
import org.lzbruby.mybatis.generat.utils.GeneratorStringUtils;
import org.lzbruby.mybatis.generat.utils.PropertiesUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 功能描述：自动化生成代码默认执行实现
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
 * Date: 15/6/12 Time：23:41
 */
public class MysqlDefaultGeneratorStarterImpl implements GeneratorStarter {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlDefaultGeneratorStarterImpl.class);

    /**
     * 读取配置
     */
    protected Properties properties;

    /**
     * 读取数据连接
     */
    protected Connector connector;

    /**
     * 上下文
     */
    protected ApplicationContext context;

    /**
     * 生成代码转发器名称
     */
    private static final String GENERATOR_FACADE = "generatorFacade";

    /**
     * 执行生成方法
     */
    public void start() {
        try {
            buildConnector();
            generator();
            connector.closeConnection();
        } catch (Exception e) {
            throw new RuntimeException("启动创建代码工具出现异常", e);
        }
    }

    /**
     * 自动化创建代码文件
     */
    protected void generator() {
        LOGGER.info("代码生成工具，开始自动生成代码...");

        // 获取配置layers
        String layerConfig = PropertiesUtils.getLayers(properties);
        String[] layers = StringUtils.split(layerConfig, ",");

        // 创建目录
        GeneratorFileUtils.createPackageDirectory(properties, layers);
        List<String> tables = PropertiesUtils.getTableList(properties);
        if (CollectionUtils.isEmpty(tables)) {
            throw new RuntimeException("配置代码生成数据库表为空.");
        }

        // 自动化生成文件
        for (String tableName : tables) {
            try {
                Map<String, String> pkMap = connector.getPrimaryKey(tableName);
                if (StringUtils.isBlank(pkMap.get(AutoCodeConstantsType.PRIMARY_KEY.getType()))) {
                    throw new RuntimeException(tableName + " 表结构没有主键，请检查表结构，生成代码失败.");
                }
            } catch (Exception e) {
                throw new RuntimeException(tableName + " 在数据库中不存在，请检查配置和数据库表结构.", e);
            }

            if (ArrayUtils.isEmpty(layers)) {
                LOGGER.error("读取配置文件分层结构为空，请检查配置是否按照逗号隔开.");
                return;
            }

            for (AutoCodeGeneratorType configType : AutoCodeGeneratorType.values()) {
                if (ArrayUtils.contains(layers, configType.getType())) {
                    if (isLoop(configType)) {
                        Generator generator = (Generator) context.getBean(GENERATOR_FACADE);
                        AutoCodeContext generatorContext = assemblyContext(tableName);
                        executeGenerator(generator, generatorContext, configType);
                    }
                }
            }
        }
    }

    /**
     * 控制区分数据库生成代码层次, 是否可以继续循环
     *
     * @param configType
     * @return
     */
    protected boolean isLoop(AutoCodeGeneratorType configType) {
        if (configType == AutoCodeGeneratorType.ORACLE_MAPPER) {
            return false;
        }
        return true;
    }

    /**
     * 调用创建模板的方式
     *
     * @param generator
     * @param generatorContext
     * @param configType
     */
    protected void executeGenerator(Generator generator, AutoCodeContext generatorContext, AutoCodeGeneratorType configType) {
        generator.defaultGenerator(generatorContext, configType);
    }

    /**
     * 构建生成器内容参数
     */
    protected void buildConnector() {
        GeneratorConfigurer generatorConfigurer = GeneratorConfigurerFactory.getGeneratorConfigurer();
        properties = generatorConfigurer.getProperties();
        generatorConfigurer.initConfigParams();
        context = new ClassPathXmlApplicationContext(GeneratorConfigurer.SPRING_CONFIG);
        setConnector(new MysqlConnector(properties));
        setProperties(properties);
        setContext(context);
    }

    /**
     * 初始化渲染模板基本参数上下文
     *
     * @param tableName
     * @return
     */
    protected AutoCodeContext assemblyContext(String tableName) {
        Map<String, String> propMap = connector.getPrimaryKey(tableName);
        AutoCodeContext context = new AutoCodeContext(tableName, propMap, properties);
        context.addAttribute(AutoCodeConstantsType.JDBC_CONNECTOR, connector);
        context.addAttribute(AutoCodeConstantsType.CONFIG_PROPERTIES, properties);
        context.addAttribute(AutoCodeConstantsType.DOMAIN, properties.get(AutoCodeConstantsType.DOMAIN.getType()));
        context.addAttribute(AutoCodeConstantsType.NORMAL_PRIMARY_KEY,
                GeneratorStringUtils.format(propMap.get(AutoCodeConstantsType.PRIMARY_KEY.getType())));
        context.addAttribute(AutoCodeConstantsType.COL_ALL_UPPERCASE_PRIMARY_KEY,
                StringUtils.upperCase(propMap.get(AutoCodeConstantsType.PRIMARY_KEY.getType())));
        context.addAttribute(AutoCodeConstantsType.COL_NORMAL_PRIMARY_KEY, propMap.get(AutoCodeConstantsType.PRIMARY_KEY.getType()));
        context.addAttribute(AutoCodeConstantsType.ORACLE_SCHEMA,
                properties.get(AutoCodeConstantsType.ORACLE_SCHEMA.getType()));
        context.addAttribute(AutoCodeConstantsType.CLASS_TITLE_EMAIL_SUFFIX, properties.getProperty(AutoCodeConstantsType.CLASS_TITLE_EMAIL_SUFFIX.getType()));
        context.addAttribute(AutoCodeConstantsType.CLASS_TITLE_ORG, properties.getProperty(AutoCodeConstantsType.CLASS_TITLE_ORG.getType()));
        return context;
    }

    /**
     *
     * @param connector
     */
    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    /**
     *
     * @return
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     *
     * @param properties
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     *
     * @return
     */
    public ApplicationContext getContext() {
        return context;
    }

    /**
     *
     * @param context
     */
    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
