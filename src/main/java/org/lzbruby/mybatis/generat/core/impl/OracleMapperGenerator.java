package org.lzbruby.mybatis.generat.core.impl;

import org.lzbruby.mybatis.generat.config.AutoCodeConstantsType;
import org.lzbruby.mybatis.generat.core.context.AutoCodeContext;
import org.lzbruby.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：OracleMapperGenerator
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
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
