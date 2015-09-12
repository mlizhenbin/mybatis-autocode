package com.oneplus.mybatis.generat.generator.impl;

import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * 功能描述：Domain代码生成器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/9/12 Time: 21:15
 */
public class DomainGenerator extends ServiceGenerator {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        super.initVelocityContext(velocityContext, generatorContext);
    }

    @Override
    protected PackageConfigType getPackageConfigType() {
        return PackageConfigType.domain;
    }
}
