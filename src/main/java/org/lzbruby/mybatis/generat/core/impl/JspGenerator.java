package org.lzbruby.mybatis.generat.core.impl;

import com.google.common.collect.Lists;
import org.lzbruby.mybatis.generat.config.AutoCodeConstantsType;
import org.lzbruby.mybatis.generat.core.connect.Connector;
import org.lzbruby.mybatis.generat.core.context.AutoCodeContext;
import org.lzbruby.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.apache.velocity.VelocityContext;
import org.lzbruby.mybatis.generat.utils.GeneratorStringUtils;

import java.util.List;
import java.util.Map;

/**
 * 功能描述：JSP生成器
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
 * Date: 15/9/13 Time: 13:03
 */
public class JspGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, AutoCodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);

        // 获取所有的属性
        String tableName = cxt.getTableName();
        Connector connector = (Connector) cxt.getAttribute(AutoCodeConstantsType.JDBC_CONNECTOR);
        Map<String, String> columnNameTypeMap = connector.mapColumnNameType(tableName);

        List<String> fields= Lists.newArrayList();
        for (String col : columnNameTypeMap.keySet()) {
            String field = GeneratorStringUtils.format(col);
            fields.add(field);
        }
        velocityContext.put(AutoCodeConstantsType.FIELDS.getDesc(), fields);
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
