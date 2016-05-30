package org.lzbruby.mybatis.generat.core.impl;

import com.google.common.collect.Lists;
import org.lzbruby.mybatis.generat.core.connect.Connector;
import org.lzbruby.mybatis.generat.core.context.AutoCodeContext;
import org.lzbruby.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.lzbruby.mybatis.generat.config.AutoCodeConstantsType;
import org.lzbruby.mybatis.generat.utils.GeneratorStringUtils;
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
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
 * Date: 15/6/13 Time：00:51
 */
public class MapperGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, AutoCodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);

        String tableName = cxt.getTableName();
        Connector connector = (Connector) cxt.getAttribute(AutoCodeConstantsType.JDBC_CONNECTOR);
        Map<String, String> columnNameTypeMap = connector.mapColumnNameType(tableName);
        List<String> allIndexs = connector.listAllIndex(tableName);

        List<String> resultMapColumns = Lists.newArrayList();
        List<String> whereConditions = Lists.newArrayList();
        List<String> columns = Lists.newArrayList();
        List<String> insertValueConditions = Lists.newArrayList();
        List<String> insertColsConditions = Lists.newArrayList();
        List<String> updateConditions = Lists.newArrayList();

        String pk = (String) velocityContext.get(AutoCodeConstantsType.PRIMARY_KEY.getDesc());
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
                        .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" &gt;= #{").append(AutoCodeConstantsType.DYNAMIC_FILEDS.getDesc()).append(col).append("}\n")
                        .append("\t\t\t</if>");
                whereConditions.add(conditionBfs.toString());
                StringBuilder conditionBfe = new StringBuilder();
                conditionBfe.append(defaultFieldStr)
                        .append("\t\t\t\tAND ").append(tableName).append(".").append(col).append(" &lt; #{").append(AutoCodeConstantsType.DYNAMIC_FILEDS.getDesc()).append(col).append("}\n")
                        .append("\t\t\t</if>");
                whereConditions.add(conditionBfe.toString());
            } else {
                // 非String类型的,使用null
                String colShowType = columnNameTypeMap.get(col);
                if (!StringUtils.equals(colShowType, "String")) {
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

                if (!StringUtils.equals(colShowType, "String")) {
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

        velocityContext.put(AutoCodeConstantsType.RESULT_MAP_COLUMNS.getDesc(), resultMapColumns);
        velocityContext.put(AutoCodeConstantsType.WHERE_CONDITIONS.getDesc(), whereConditions);
        velocityContext.put(AutoCodeConstantsType.INSERT_VALUE_CONDITIONS.getDesc(), insertValueConditions);
        velocityContext.put(AutoCodeConstantsType.INSERT_COLS_CONDITIONS.getDesc(), insertColsConditions);
        velocityContext.put(AutoCodeConstantsType.UPDATE_CONDITIONS.getDesc(), updateConditions);
        for (int i = 0; i < columns.size() - 1; i++) {
            String tempCol = columns.get(i) + ",";
            columns.set(i, tempCol);
        }
        velocityContext.put(AutoCodeConstantsType.COLUMNS.getDesc(), columns);
    }

    @Override
    protected AutoCodeGeneratorType getPackageConfigType() {
        return AutoCodeGeneratorType.MAPPER;
    }

    @Override
    protected String getDescription() {
        return " Dao Mapper";
    }


}
