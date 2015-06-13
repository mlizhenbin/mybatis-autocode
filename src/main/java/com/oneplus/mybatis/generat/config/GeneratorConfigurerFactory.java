package com.oneplus.mybatis.generat.config;

/**
 * 功能描述：
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/12 Time：23:37
 */
public class GeneratorConfigurerFactory {

    /** 测试环境配置器 */
    private static GeneratorConfigurer generatorConfigurer;

    /**
     * 获取测试环境配置器
     *
     * @return 测试环境配置器
     */
    public synchronized static GeneratorConfigurer getGeneratorConfigurer() {
        if (null == generatorConfigurer) {
            generatorConfigurer = new DefaultGeneratorConfigurer();
        }
        return generatorConfigurer;
    }

}
