package com.oneplus.mybatis.generat.config;

import java.util.Properties;

/**
 * 功能描述：自动化生成配置接口
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/12 Time：23:11
 */
public interface GeneratorConfigurer {

    /**
     * 代码生成包
     */
    public static final String GENERATOR_PACKAGE = "com.oneplus";

    /**
     * 生成代码存放位置
     */
    public static final String GENERATOR_LOCATION = "oneplus";

    /**
     * 所属系统项目
     */
    public static final String GENERATOR_PROJECT_NAME = "wms";

    /**
     * 过滤表名前缀
     */
    public static final String GENERATOR_TABLEPREFIX = "wms_";

    /**
     * 强类型转换，数据库float转换为BigDecimal，不配置转换为Double
     */
    public static final String GENERATOR_PRECISION = "high";

    /**
     * 配置文件名称
     */
    public static final String LOCAL_GENERATOR_PATH = "local-generat.properties";

    /**
     * Spring配置文件
     */
    public static final String SPRING_COFIG = "oneplus-spring-generator.xml";

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
