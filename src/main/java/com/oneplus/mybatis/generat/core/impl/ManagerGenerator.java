package com.oneplus.mybatis.generat.core.impl;

import com.oneplus.mybatis.generat.core.context.AutoCodeContext;
import com.oneplus.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.apache.velocity.VelocityContext;

/**
 * 功能描述：Manager层代码生成
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
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
