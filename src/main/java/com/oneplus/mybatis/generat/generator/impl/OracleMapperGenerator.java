package com.oneplus.mybatis.generat.generator.impl;

import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import com.oneplus.mybatis.generat.utils.ConstantsType;
import org.apache.velocity.VelocityContext;

/**
 * 功能描述：OracleMapperGenerator
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 16/4/12 Time: 12:43
 */
public class OracleMapperGenerator extends MapperGenerator {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
        velocityContext.put(ConstantsType.ORACLE_SCHEMA.getDesc(), cxt.getAttribute(ConstantsType.ORACLE_SCHEMA));
    }

    @Override
    protected PackageConfigType getPackageConfigType() {
        return PackageConfigType.ORACLE_MAPPER;
    }
}
