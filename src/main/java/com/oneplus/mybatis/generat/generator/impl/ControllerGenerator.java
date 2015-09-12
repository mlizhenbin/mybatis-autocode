package com.oneplus.mybatis.generat.generator.impl;

import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

/**
 * 功能描述：Controller生成器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:51
 */
public class ControllerGenerator extends BaseGenerator {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        super.initVelocityContext(velocityContext, generatorContext);
        String description = (String) velocityContext.get("classTitle");
        String replace = StringUtils.replace(description, "{classDescription}", velocityContext.get("upClassName") + "控制器");
        velocityContext.put("classTitle", replace);
    }

    @Override
    protected PackageConfigType getPackageConfigType() {
        return PackageConfigType.controller;
    }

    @Override
    protected String getDescription() {
        return "控制器";
    }

}
