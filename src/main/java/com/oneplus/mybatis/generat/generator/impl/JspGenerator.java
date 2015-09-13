package com.oneplus.mybatis.generat.generator.impl;

import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import org.apache.velocity.VelocityContext;

/**
 * 功能描述：JSP生成器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/9/13 Time: 13:03
 */
public class JspGenerator extends BaseGenerator {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        super.initVelocityContext(velocityContext, generatorContext);
    }

    @Override
    protected PackageConfigType getPackageConfigType() {
        return PackageConfigType.jsp;
    }

    @Override
    protected String getDescription() {
        return "";
    }
}
