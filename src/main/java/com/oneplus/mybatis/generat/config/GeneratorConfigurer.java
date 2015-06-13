package com.oneplus.mybatis.generat.config;

import java.util.Properties;

/**
 * 功能描述：数组元素验证工具类
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/12 Time：23:11
 */
public interface GeneratorConfigurer {

    public static final String GENERATOR_PACKAGE = "com.oneplus";

    public static final String GENERATOR_LOCATION = "/";

    public static final String GENERATOR_PROJECT_NAME = "wms";

    public static final String GENERATOR_TABLEPREFIX = "wms_";

    public static final String GENERATOR_PRECISION = "high";

    public static final String GENERATOR_LAYERS = "dao,mapper,service,controller,model,test,result";

    public static final String LOCAL_GENERATOR_PATH = "local-generat.properties";

    /**
     * 获取配置文件配置集
     *
     * @return 配置文件配置集
     */
    public Properties getProperties();

    /**
     * 初始化系统参数
     */
    public void initEnvParams();

}
