package org.lzbruby.mybatis.generat.core.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.lzbruby.mybatis.generat.config.AutoCodeConstantsType;
import org.lzbruby.mybatis.generat.core.context.AutoCodeContext;
import org.lzbruby.mybatis.generat.core.context.AutoCodeGeneratorType;

/**
 * 功能描述：Controller生成器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
 * Date: 15/6/13 Time：00:51
 */
public class ControllerGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, AutoCodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
        String description = (String) velocityContext.get("classTitle");
        String replace = StringUtils.replace(description, "{classDescription}",
                velocityContext.get(AutoCodeConstantsType.UP_CLASS_NAME.getDesc()) + "控制器");
        velocityContext.put(AutoCodeConstantsType.CLASS_TITLE.getDesc(), replace);
    }

    @Override
    protected AutoCodeGeneratorType getPackageConfigType() {
        return AutoCodeGeneratorType.CONTROLLER;
    }

    @Override
    protected String getDescription() {
        return "控制器";
    }

}
