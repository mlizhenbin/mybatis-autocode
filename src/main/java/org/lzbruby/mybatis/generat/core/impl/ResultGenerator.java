package org.lzbruby.mybatis.generat.core.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.lzbruby.mybatis.generat.core.connect.Connector;
import org.lzbruby.mybatis.generat.core.context.AutoCodeContext;
import org.lzbruby.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.lzbruby.mybatis.generat.config.AutoCodeConstantsType;
import org.lzbruby.mybatis.generat.utils.PropertiesUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 功能描述：ResultGenerator
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
 * Date: 15/6/13 Time：00:51
 */
public class ResultGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, AutoCodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
        velocityContext.put(AutoCodeConstantsType.SERIAL_VERSION_UID.getDesc(), String.valueOf(UUID.randomUUID().getLeastSignificantBits()));

        String tableName = cxt.getTableName();
        Connector connector = (Connector) cxt.getAttribute(AutoCodeConstantsType.JDBC_CONNECTOR);
        Map<String, String> columnNameTypeMap = connector.mapColumnNameType(tableName);
        Map<String, String> columnRemarkMap = connector.mapColumnRemark(tableName);

        List<String> allUpCaseCols = Lists.newArrayList();
        Map<String, String> remarkMap = Maps.newHashMap();
        for (String col : columnNameTypeMap.keySet()) {
            String upCaseCol = StringUtils.upperCase(col);
            allUpCaseCols.add(upCaseCol);
            remarkMap.put(upCaseCol, columnRemarkMap.get(col));
        }

        String noPrefixTableName = StringUtils.upperCase(tableName.toLowerCase().replaceFirst(PropertiesUtils.getTablePrefix(cxt.getProperties()), ""));
        velocityContext.put(AutoCodeConstantsType.NO_PREFIX_TABLE_NAME.getDesc(), noPrefixTableName);
        velocityContext.put(AutoCodeConstantsType.ALL_UP_CASE_COLS.getDesc(), allUpCaseCols);
        velocityContext.put(AutoCodeConstantsType.REMARK_MAP.getDesc(), remarkMap);
    }

    @Override
    protected AutoCodeGeneratorType getPackageConfigType() {
        return AutoCodeGeneratorType.result;
    }

    @Override
    protected String getDescription() {
        return "结果ErrorCode";
    }
}
