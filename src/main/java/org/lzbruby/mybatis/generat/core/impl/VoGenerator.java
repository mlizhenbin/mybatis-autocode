package org.lzbruby.mybatis.generat.core.impl;

import org.lzbruby.mybatis.generat.core.context.AutoCodeGeneratorType;

/**
 * 功能描述：VO生成器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
 * Date: 15/9/12 Time: 21:48
 */
public class VoGenerator extends ModelGenerator {

    @Override
    protected AutoCodeGeneratorType getPackageConfigType() {
        return AutoCodeGeneratorType.VO;
    }

    @Override
    protected String getDescription() {
        return "查询VO";
    }
}
