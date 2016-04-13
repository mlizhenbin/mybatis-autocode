package com.oneplus.mybatis.generat.core.impl;

import com.oneplus.mybatis.generat.core.context.AutoCodeContext;
import com.oneplus.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.apache.velocity.VelocityContext;

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
    public void initVelocityContext(VelocityContext velocityContext, AutoCodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
    }

    @Override
    protected AutoCodeGeneratorType getPackageConfigType() {
        return AutoCodeGeneratorType.DOMAIN;
    }

    @Override
    protected String getDescription() {
        return "DO领域对象";
    }
}
