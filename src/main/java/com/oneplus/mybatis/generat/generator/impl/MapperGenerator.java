package com.oneplus.mybatis.generat.generator.impl;

import com.google.common.collect.Lists;
import com.oneplus.mybatis.generat.connect.Connector;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import com.oneplus.mybatis.generat.utils.Constants;
import com.oneplus.mybatis.generat.utils.GeneratorStringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 功能描述：Mapper.xml代码生成
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:51
 */
public class MapperGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext cxt) {
        super.initVelocityContext(velocityContext, cxt);

        String tableName = cxt.getTableName();
        Connector connector = (Connector) cxt.getAttribute(Constants.JDBC_CONNECTOR);
        Map<String, String> columnNameTypeMap = connector.mapColumnNameType(tableName);
        List<String> allIndexs = connector.listAllIndex(tableName);

        List<String> resultMapColumns = Lists.newArrayList();
        List<String> whereConditions = Lists.newArrayList();
        List<String> columns = Lists.newArrayList();
        List<String> insertValueConditions = Lists.newArrayList();
        List<String> insertColsConditions = Lists.newArrayList();
        List<String> updateConditions = Lists.newArrayList();

        String pk = (String) velocityContext.get(Constants.PRIMARY_KEY.getDesc());
        for (String col : columnNameTypeMap.keySet()) {
            String field = GeneratorStringUtils.format(col);
            columns.add(tableName + "." + col);
            StringBuilder cloumBf = new StringBuilder();
            cloumBf.append("<result property=\"").append(field).append("\" column=\"").append(col).append("\"/>");
            resultMapColumns.add(cloumBf.toString());

            String defaultFieldStr = "<if test=\"" + field + "!=null and ''!=" + field + "\">\n";
            String defaultFieldStrs = "<if test=\"" + field + "s" + "!=null and ''!=" + field + "s" + "\">\n";
            if (columnNameTypeMap.get(col).equals("Date")) {
                StringBuilder conditionBfs = new StringBuilder();
                conditionBfs.append(defaultFieldStr)
                        .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" &gt;= #{").append(Constants.DYNAMIC_FILEDS.getDesc()).append(col).append("}\n")
                        .append("\t\t\t</if>");
                whereConditions.add(conditionBfs.toString());
                StringBuilder conditionBfe = new StringBuilder();
                conditionBfe.append(defaultFieldStr)
                        .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" &lt; #{").append(Constants.DYNAMIC_FILEDS.getDesc()).append(col).append("}\n")
                        .append("\t\t\t</if>");
                whereConditions.add(conditionBfe.toString());
            } else {
                if (columnNameTypeMap.get(col).equals("Long") || columnNameTypeMap.get(col).equals("Integer")) {
                    defaultFieldStr = "<if test=\"" + field + "!=null\">\n";
                }
                StringBuilder conditionBf = new StringBuilder();
                conditionBf.append(defaultFieldStr)
                        .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" = #{").append(field).append("}\n")
                        .append("\t\t\t</if>");
                whereConditions.add(conditionBf.toString());


                boolean isKey = false;
                if (!CollectionUtils.isEmpty(allIndexs)) {
                    for (String allIndex : allIndexs) {
                        if (StringUtils.contains(allIndex, col)) {
                            isKey = true;
                            break;
                        }
                    }
                }

                if (columnNameTypeMap.get(col).equals("Long") || columnNameTypeMap.get(col).equals("Integer")) {
                    defaultFieldStrs = "<if test=\"" + field + "s" + "!=null\">\n";
                }
                if ((StringUtils.equals(field, pk) || isKey) && !StringUtils.equals(field, "id")) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(defaultFieldStrs)
                            .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" IN\n")
                            .append("\t\t\t\t<foreach collection=\"").append(field).append("s\" item=\"").append(field).append("\" open=\"(\" close=\")\" separator=\",\">\n")
                            .append("\t\t\t\t\t").append("#{").append(field).append("}\n")
                            .append("\t\t\t\t</foreach>\n")
                            .append("\t\t\t</if>");
                    whereConditions.add(builder.toString());
                }
            }

            if (col.startsWith("gmt") || StringUtils.equals(field, "createTime") || StringUtils.equals(field, "modifyTime")) {
                insertValueConditions.add("now(),");
                insertColsConditions.add(col + ", ");
            } else {
                StringBuilder conditionValueBf = new StringBuilder();
                conditionValueBf.append(defaultFieldStr)
                        .append("\t\t\t\t").append("#{").append(field).append("},\n")
                        .append("\t\t\t</if>");
                insertValueConditions.add(conditionValueBf.toString());

                StringBuilder conditionColBf = new StringBuilder();
                conditionColBf.append(defaultFieldStr)
                        .append("\t\t\t\t").append(col).append(",\n")
                        .append("\t\t\t</if>");
                insertColsConditions.add(conditionColBf.toString());
            }

            String[] updateFilters = {pk, "createTime"};
            if (!ArrayUtils.contains(updateFilters, field)) {
                StringBuilder upBf = new StringBuilder();
                if (StringUtils.equals(field, "modifyTime")) {
                    upBf.append(tableName).append(".").append(col).append(" = NOW(),");
                } else {
                    upBf.append(defaultFieldStr)
                            .append("\t\t\t\t").append(tableName).append(".").append(col).append(" = #{").append(field).append("},\n")
                            .append("\t\t\t</if>");
                }
                updateConditions.add(upBf.toString());
            }
        }

        velocityContext.put(Constants.RESULT_MAP_COLUMNS.getDesc(), resultMapColumns);
        velocityContext.put(Constants.WHERE_CONDITIONS.getDesc(), whereConditions);
        velocityContext.put(Constants.INSERT_VALUE_CONDITIONS.getDesc(), insertValueConditions);
        velocityContext.put(Constants.INSERT_COLS_CONDITIONS.getDesc(), insertColsConditions);
        velocityContext.put(Constants.UPDATE_CONDITIONS.getDesc(), updateConditions);
        for (int i = 0; i < columns.size() - 1; i++) {
            String tempCol = columns.get(i) + ",";
            columns.set(i, tempCol);
        }
        velocityContext.put(Constants.COLUMNS.getDesc(), columns);
    }

    @Override
    protected PackageConfigType getPackageConfigType() {
        return PackageConfigType.MAPPER;
    }

    @Override
    protected String getDescription() {
        return " Dao Mapper";
    }


}
