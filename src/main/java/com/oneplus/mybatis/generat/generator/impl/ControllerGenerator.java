package com.oneplus.mybatis.generat.generator.impl;

import com.oneplus.mybatis.generat.generator.Generator;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.GeneratorTemplate;
import com.oneplus.mybatis.generat.utils.FileUtils;
import com.oneplus.mybatis.generat.utils.GeneratorStringUtils;
import org.apache.velocity.VelocityContext;

import java.util.Properties;

/**
 * 功能描述：
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:51
 */
public class ControllerGenerator extends BaseGenerator implements Generator {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        super.initVelocityContext(velocityContext, generatorContext);
    }

    @Override
    public String initTemplateName() {
        return  GeneratorTemplate.TL_CONTROLLER;
    }

    @Override
    public String initPath(GeneratorContext generatorContext) {
        Properties properties = generatorContext.getProperties();
        String tableName = generatorContext.getTableName();
        return FileUtils.getPackageDirectory("controller", properties)
                + GeneratorStringUtils.firstUpperAndNoPrefix(tableName, properties)
                + "Controller.java";
    }

    public void generator(GeneratorContext context) {
        write(context);
    }
}
