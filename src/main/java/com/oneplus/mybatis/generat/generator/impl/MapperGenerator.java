package com.oneplus.mybatis.generat.generator.impl;

import com.google.common.collect.Lists;
import com.oneplus.mybatis.generat.connect.Connector;
import com.oneplus.mybatis.generat.generator.Generator;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import com.oneplus.mybatis.generat.utils.GeneratorStringUtils;
import org.apache.velocity.VelocityContext;

import java.util.List;

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

        Connector connector = (Connector) generatorContext.getAttribute("connector");
        List<String> columnList = connector.getColumnNameList(generatorContext.getTableName());

        List<String> resultMapColumns = Lists.newArrayList();
        List<String> whereConditions = Lists.newArrayList();
        List<String> columns = Lists.newArrayList();

        String tableName = generatorContext.getTableName();
        for (String col : columnList) {
            String field = GeneratorStringUtils.format(col);

            String tmpCol = tableName + "." + col;
            columns.add(tmpCol);

            StringBuilder cloumBf = new StringBuilder();
            cloumBf.append("<result property=\"").append(field).append("\" column=\"").append(tmpCol).append("\"/>");
            resultMapColumns.add(cloumBf.toString());

            StringBuilder conditionBf = new StringBuilder();
            conditionBf.append("<if test=\"").append(field).append(" != null\">\n")
                    .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" = #{").append(field).append("}\n")
                    .append("\t\t\t</if>");
            whereConditions.add(conditionBf.toString());
        }

        velocityContext.put("resultMapColumns", resultMapColumns);
        velocityContext.put("whereConditions", whereConditions);
        velocityContext.put("columns", columns);
    }

    @Override
    protected PackageConfigType getPackageConfigType() {
        return PackageConfigType.mapper;
    }
}
