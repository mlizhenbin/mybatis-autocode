package com.oneplus.mybatis.generat.start;

import com.oneplus.mybatis.generat.config.GeneratorConfigurer;
import com.oneplus.mybatis.generat.config.GeneratorConfigurerFactory;
import com.oneplus.mybatis.generat.connect.Connector;
import com.oneplus.mybatis.generat.connect.MysqlConnector;
import com.oneplus.mybatis.generat.generator.Generator;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import com.oneplus.mybatis.generat.utils.Constants;
import com.oneplus.mybatis.generat.utils.GeneratorFileUtils;
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
 * 功能描述：自动化生成代码默认执行实现
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
        } catch (Exception e) {
            throw new RuntimeException("启动创建代码工具出现异常", e);
        }
    }

    /**
     * 自动化创建代码文件
     */
    protected void generator() {
        LOGGER.info("代码生成工具，开始自动生成代码...");

        // 创建目录
        GeneratorFileUtils.createPackageDirectory(properties);
        List<String> tables = PropertiesUtils.getTableList(properties);
        if (CollectionUtils.isEmpty(tables)) {
            throw new RuntimeException("配置代码生成数据库表为空.");
        }

        // 自动化生成文件
        for (String tableName : tables) {
            try {
                Map<String, String> pkMap = connector.getPrimaryKey(tableName);
                if (StringUtils.isBlank(pkMap.get(Constants.PRIMARY_KEY.getType()))) {
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

            for (PackageConfigType configType : PackageConfigType.values()) {
                if (isLoop(configType)) {
                    Generator generator = (Generator) context.getBean(GENERATOR_FACADE);
                    GeneratorContext generatorContext = assemblyContext(tableName);
                    executeGenerator(generator, generatorContext, configType);
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
    protected boolean isLoop(PackageConfigType configType) {
        if (configType == PackageConfigType.MANAGER) {
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
    protected void executeGenerator(Generator generator, GeneratorContext generatorContext, PackageConfigType configType) {
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
    protected GeneratorContext assemblyContext(String tableName) {
        Map<String, String> propMap = connector.getPrimaryKey(tableName);
        GeneratorContext context = new GeneratorContext(tableName, propMap, properties);
        context.addAttribute(Constants.JDBC_CONNECTOR, connector);
        context.addAttribute(Constants.CONFIG_PROPERTIES, properties);
        context.addAttribute(Constants.DOMAIN, properties.get(Constants.DOMAIN.getType()));
        context.addAttribute(Constants.NORMAL_PRIMARY_KEY,
                GeneratorStringUtils.format(propMap.get(Constants.PRIMARY_KEY.getType())));
        context.addAttribute(Constants.COL_ALL_UPPERCASE_PRIMARY_KEY,
                StringUtils.upperCase(propMap.get(Constants.PRIMARY_KEY.getType())));
        context.addAttribute(Constants.ORACLE_SCHEMA,
                properties.get(Constants.ORACLE_SCHEMA.getType()));
        return context;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
