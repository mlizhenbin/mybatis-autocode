package com.oneplus.mybatis.generat.generator.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oneplus.mybatis.generat.connect.Connector;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 功能描述：
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:51
 */
public class ResultGenerator extends BaseGenerator {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        super.initVelocityContext(velocityContext, generatorContext);
        velocityContext.put("SerialVersionUID", String.valueOf(UUID.randomUUID().getLeastSignificantBits()));

        String tableName = generatorContext.getTableName();
        Connector connector = (Connector) generatorContext.getAttribute("connector");
        Map<String, String> columnNameTypeMap = connector.mapColumnNameType(tableName);
        Map<String, String> columnRemarkMap = connector.mapColumnRemark(tableName);

        List<String> allUpCaseCols = Lists.newArrayList();
        Map<String, String> remarkMap = Maps.newHashMap();
        for (String col : columnNameTypeMap.keySet()) {
            String upCaseCol = StringUtils.upperCase(col);
            allUpCaseCols.add(upCaseCol);
            remarkMap.put(upCaseCol, columnRemarkMap.get(col));
        }
        velocityContext.put("allUpCaseCols", allUpCaseCols);
        velocityContext.put("remarkMap", remarkMap);
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
