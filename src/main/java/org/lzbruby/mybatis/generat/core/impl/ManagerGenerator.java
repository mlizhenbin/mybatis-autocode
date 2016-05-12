package org.lzbruby.mybatis.generat.core.impl;

import org.lzbruby.mybatis.generat.core.context.AutoCodeContext;
import org.lzbruby.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.apache.velocity.VelocityContext;

/**
 * 功能描述：Manager层代码生成
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
 * Date: 15/6/13 Time：00:51
 */
public class ManagerGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, AutoCodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
    }

    @Override
    protected AutoCodeGeneratorType getPackageConfigType() {
        return AutoCodeGeneratorType.MANAGER;
    }

    @Override
    protected String getDescription() {
        return " Manager";
    }
}
