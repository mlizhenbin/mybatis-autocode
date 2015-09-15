package com.oneplus.mybatis.generat.generator.impl;

import com.google.common.collect.Lists;
import com.oneplus.mybatis.generat.connect.Connector;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import com.oneplus.mybatis.generat.utils.GeneratorStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import java.util.*;

/**
 * 功能描述：Service代码生成
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:51
 */
public class ServiceGenerator extends BaseGenerator {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext) {
        super.initVelocityContext(velocityContext, generatorContext);
        velocityContext.put("SerialVersionUID", String.valueOf(UUID.randomUUID().getLeastSignificantBits()));

        String tableName = generatorContext.getTableName();
        Connector connector = (Connector) generatorContext.getAttribute("connector");

        Map<String, String> colMap = connector.mapColumnNameType(tableName);
        Map<String, String> columnRemarkMap = connector.mapColumnRemark(tableName);
        Set<String> keySet = colMap.keySet();
        Set<String> importSets = new HashSet<String>();
        for (String key : keySet) {
            String value = colMap.get(key);
            if ("BigDecimal".equals(value) && !importSets.contains("BigDecimal")) {
                importSets.add("import java.math.BigDecimal;\n");
            } else if ("Date".equals(value) && !importSets.contains("Date")) {
                importSets.add("import java.util.Date;\n");
            } else if ("Timestamp".equals(value) && !importSets.contains("Timestamp")) {
                importSets.add("import java.sql.Timestamp;\n");
            }
        }

        Properties properties = generatorContext.getProperties();

        velocityContext.put("methods", generateGetAndSetMethods(colMap));
        velocityContext.put("fields", generateFields(colMap, columnRemarkMap));
        velocityContext.put("importSets", importSets);
        velocityContext.put("convertDomains", getCovertDomainFields(colMap, properties));
        velocityContext.put("converts", getCovertFields(colMap, properties));
        velocityContext.put("utils", getUtilFields(colMap, columnRemarkMap));
    }

    protected List<String> getCovertDomainFields(Map<String, String> map, Properties properties) {
        Set<String> keySet = map.keySet();
        List<String> converts = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String field = GeneratorStringUtils.format(key);
            sb.append(velocityContext.get("lowClassName")).append(properties.get("generator.domain"))
                    .append(".set" + GeneratorStringUtils.firstUpperNoFormat(field) + "(")
                    .append(velocityContext.get("lowClassName")).append(".get" + GeneratorStringUtils.firstUpperNoFormat(field) + "())");
            converts.add(sb.toString());
        }
        return converts;
    }

    protected List<String> getCovertFields(Map<String, String> colMap, Properties properties) {
        Set<String> keySet = colMap.keySet();
        List<String> converts = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String field = GeneratorStringUtils.format(key);
            sb.append(velocityContext.get("lowClassName")).append(".set" + GeneratorStringUtils.firstUpperNoFormat(field) + "(")
                    .append(velocityContext.get("lowClassName")).append(properties.get("generator.domain"))
                    .append(".get" + GeneratorStringUtils.firstUpperNoFormat(field) + "())");
            converts.add(sb.toString());
        }
        return converts;
    }

    protected List<String> getUtilFields(Map<String, String> colMap, Map<String, String> columnRemarkMap) {
        Set<String> keySet = colMap.keySet();
        List<String> utils = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String field = GeneratorStringUtils.format(key);
            String colType = colMap.get(key);
            if (StringUtils.equals(colType, "String")) {
                sb.append("\tif (StringUtils.isBlank(").append(velocityContext.get("lowClassName"))
                        .append(".get").append(GeneratorStringUtils.firstUpperNoFormat(field)).append("())) {\n");
            } else {
                sb.append("\tif (").append(velocityContext.get("lowClassName")).append(".get")
                        .append(GeneratorStringUtils.firstUpperNoFormat(field)).append("() == null) {\n");
            }
            String remark = columnRemarkMap.get(key);
            sb.append("\t\t\tLOGGER.warn(\"").append(field).append(remark).append("为空, ").append(velocityContext.get("lowClassName"))
                    .append("=\" + ").append(velocityContext.get("lowClassName")).append(");\n");
            sb.append("\t\t\t").append("throw new ").append(velocityContext.get("upClassName")).append("Exception(")
                    .append(velocityContext.get("upClassName")).append("Result.").append(StringUtils.upperCase(key)).append("_NULL);\n");
            sb.append("\t\t}\n");
            utils.add(sb.toString());
        }
        return utils;
    }

    @Override
    protected PackageConfigType getPackageConfigType() {
        return PackageConfigType.service;
    }

    @Override
    protected String getDescription() {
        return " Service";
    }
}
