package org.lzbruby.mybatis.generat.config;

import java.util.Properties;

/**
 * 功能描述：自动化生成配置接口
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
 * Date: 15/6/12 Time：23:11
 */
public interface GeneratorConfigurer {

    /**
     * 代码生成包
     */
    public static final String GENERATOR_PACKAGE = "org.lzbruby";

    /**
     * 生成代码存放位置
     */
    public static final String GENERATOR_LOCATION = "lzbruby";

    /**
     * 所属系统项目
     */
    public static final String GENERATOR_PROJECT_NAME = "lzbruby";

    /**
     * 过滤表名前缀
     */
    public static final String GENERATOR_TABLEPREFIX = "";

    /**
     * 强类型转换，数据库float转换为BigDecimal，不配置转换为Double
     */
    public static final String GENERATOR_PRECISION = "high";

    /**
     * domain后缀名称
     */
    public static final String GENERATOR_DOMAIN = "DTO";

    /**
     * 默认配置文件名称
     */
    public static final String LOCAL_GENERATOR_PATH = "autocode-generator.properties";

    /**
     * project下配置的内容
     */
    public static final String CONFIG_GENERATOR_PATH = "generator.properties";

    /**
     * Spring配置文件
     */
    public static final String SPRING_CONFIG = "autocode-generator.xml";

    /**
     * JAVA文件生成的所在包文件
     */
    public static final String JAVA_SRC = "java";

    /**
     * oracle schema
     */
    public static final String ORACLE_SCHEMA = "APPS";

    /**
     * 获取配置文件配置集
     *
     * @return 配置文件配置集
     */
    public Properties getProperties();

    /**
     * 初始化默认参数
     */
    public void initConfigParams();

}
