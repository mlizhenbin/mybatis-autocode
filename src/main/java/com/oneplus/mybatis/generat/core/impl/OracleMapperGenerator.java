package com.oneplus.mybatis.generat.core.impl;

import com.oneplus.mybatis.generat.config.AutoCodeConstantsType;
import com.oneplus.mybatis.generat.core.context.AutoCodeContext;
import com.oneplus.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：OracleMapperGenerator
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 16/4/12 Time: 12:43
 */
public class OracleMapperGenerator extends MapperGenerator {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OracleMapperGenerator.class);

    @Override
    public void initVelocityContext(VelocityContext velocityContext, AutoCodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
        velocityContext.put(AutoCodeConstantsType.ORACLE_SCHEMA.getDesc(), cxt.getAttribute(AutoCodeConstantsType.ORACLE_SCHEMA));
    }

    @Override
    protected AutoCodeGeneratorType getPackageConfigType() {
        return AutoCodeGeneratorType.ORACLE_MAPPER;
    }
}
