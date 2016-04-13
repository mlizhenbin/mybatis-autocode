package com.oneplus.mybatis.generat.core.impl;

import com.oneplus.mybatis.generat.core.context.AutoCodeGeneratorType;

/**
 * 功能描述：VO生成器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/9/12 Time: 21:48
 */
public class VoGenerator extends ModelGenerator {

    @Override
    protected AutoCodeGeneratorType getPackageConfigType() {
        return AutoCodeGeneratorType.VO;
    }

    @Override
    protected String getDescription() {
        return "查询VO";
    }
}
