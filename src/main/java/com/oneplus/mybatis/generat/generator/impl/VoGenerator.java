package com.oneplus.mybatis.generat.generator.impl;

import com.oneplus.mybatis.generat.generator.context.PackageConfigType;

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
    protected PackageConfigType getPackageConfigType() {
        return PackageConfigType.vo;
    }

    @Override
    protected String getDescription() {
        return "查询VO";
    }
}
