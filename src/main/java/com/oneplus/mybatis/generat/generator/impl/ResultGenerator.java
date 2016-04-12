package com.oneplus.mybatis.generat.generator.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oneplus.mybatis.generat.connect.Connector;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import com.oneplus.mybatis.generat.utils.ConstantsType;
import com.oneplus.mybatis.generat.utils.PropertiesUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 功能描述：ResultGenerator
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:51
 */
public class ResultGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
        velocityContext.put(ConstantsType.SERIAL_VERSION_UID.getDesc(), String.valueOf(UUID.randomUUID().getLeastSignificantBits()));

        String tableName = cxt.getTableName();
        Connector connector = (Connector) cxt.getAttribute(ConstantsType.JDBC_CONNECTOR);
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
        velocityContext.put(ConstantsType.NO_PREFIX_TABLE_NAME.getDesc(), noPrefixTableName);
        velocityContext.put(ConstantsType.ALL_UP_CASE_COLS.getDesc(), allUpCaseCols);
        velocityContext.put(ConstantsType.REMARK_MAP.getDesc(), remarkMap);
    }

    @Override
    protected PackageConfigType getPackageConfigType() {
        return PackageConfigType.result;
    }

    @Override
    protected String getDescription() {
        return "结果Result";
    }
}
