package com.oneplus.mybatis.generat.core.impl;

import com.oneplus.mybatis.generat.core.context.AutoCodeContext;
import com.oneplus.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.apache.velocity.VelocityContext;

/**
 * 功能描述：JSP生成器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/9/13 Time: 13:03
 */
public class JspGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, AutoCodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
    }

    @Override
    protected AutoCodeGeneratorType getPackageConfigType() {
        return AutoCodeGeneratorType.JSP;
    }

    @Override
    protected String getDescription() {
        return "";
    }
}
