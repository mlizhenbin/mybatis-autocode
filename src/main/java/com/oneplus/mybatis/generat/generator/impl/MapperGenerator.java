package com.oneplus.mybatis.generat.generator.impl;

import com.google.common.collect.Lists;
import com.oneplus.mybatis.generat.connect.Connector;
import com.oneplus.mybatis.generat.generator.Generator;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import com.oneplus.mybatis.generat.utils.GeneratorStringUtils;
import org.apache.velocity.VelocityContext;

import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:51
 */
public class MapperGenerator extends BaseGenerator {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        super.initVelocityContext(velocityContext, generatorContext);

        String tableName = generatorContext.getTableName();
        Connector connector = (Connector) generatorContext.getAttribute("connector");
        Map<String, String> nameTypeMap = connector.getFormatedColumnNameTypeMap(tableName);

        List<String> resultMapColumns = Lists.newArrayList();
        List<String> whereConditions = Lists.newArrayList();
        List<String> columns = Lists.newArrayList();
        List<String> insertConditions = Lists.newArrayList();
        List<String> updateConditions = Lists.newArrayList();
        for (String col : nameTypeMap.keySet()) {
            String field = GeneratorStringUtils.format(col);

            String tmpCol = tableName + "." + col;
            columns.add(tmpCol);

            StringBuilder cloumBf = new StringBuilder();
            cloumBf.append("<result property=\"").append(field).append("\" column=\"").append(tmpCol).append("\"/>");
            resultMapColumns.add(cloumBf.toString());

            if (nameTypeMap.get(col).equals("Date")) {
                StringBuilder conditionBfs = new StringBuilder();
                conditionBfs.append("<if test=\"").append(field).append(" != null\">\n")
                        .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" &gt;= #{").append("dynamicFileds_startTime").append("}\n")
                        .append("\t\t\t</if>");
                whereConditions.add(conditionBfs.toString());
                StringBuilder conditionBfe = new StringBuilder();
                conditionBfe.append("<if test=\"").append(field).append(" != null\">\n")
                        .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" &lt; #{").append("dynamicFileds_endTime").append("}\n")
                        .append("\t\t\t</if>");
                whereConditions.add(conditionBfe.toString());
            } else {
                StringBuilder conditionBf = new StringBuilder();
                conditionBf.append("<if test=\"").append(field).append(" != null\">\n")
                        .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" = #{").append(field).append("}\n")
                        .append("\t\t\t</if>");
                whereConditions.add(conditionBf.toString());
            }

            if (col.startsWith("gmt")) {
                insertConditions.add("now(),");
            } else {
                StringBuilder conditionBf = new StringBuilder();
                conditionBf.append("#{").append(field).append("},");
                insertConditions.add(conditionBf.toString());
            }

            StringBuilder upBf = new StringBuilder();
            upBf.append("<if test=\"").append(field).append(" != null\">\n")
                    .append("\t\t\t\t").append(tableName).append(".").append(col).append(" = #{").append(field).append("},\n")
                    .append("\t\t\t</if>");
            updateConditions.add(upBf.toString());
        }

        velocityContext.put("resultMapColumns", resultMapColumns);
        velocityContext.put("whereConditions", whereConditions);
        velocityContext.put("insertConditions", insertConditions);
        velocityContext.put("updateConditions", updateConditions);
        velocityContext.put("columns", columns);
        velocityContext.put("columnPrimaryKey", generatorContext.getAttribute("columnPrimaryKey"));
        velocityContext.put("normalPrimaryKey", generatorContext.getAttribute("normalPrimaryKey"));
    }

    @Override
    protected PackageConfigType getPackageConfigType() {
        return PackageConfigType.mapper;
    }
}
