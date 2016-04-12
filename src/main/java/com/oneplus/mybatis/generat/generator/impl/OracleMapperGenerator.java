package com.oneplus.mybatis.generat.generator.impl;

import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import org.apache.velocity.VelocityContext;

/**
 * 功能描述：
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 16/4/12 Time: 12:43
 */
public class OracleMapperGenerator extends MapperGenerator {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        super.initVelocityContext(velocityContext, generatorContext);
        velocityContext.put("oracleSchemaName", generatorContext.getAttribute("oracleSchemaName"));
    }

    @Override
    protected PackageConfigType getPackageConfigType() {
        return PackageConfigType.oracle_mapper;
    }
}
