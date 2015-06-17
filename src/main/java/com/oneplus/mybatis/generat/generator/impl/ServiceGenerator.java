package com.oneplus.mybatis.generat.generator.impl;

import com.google.common.collect.Lists;
import com.oneplus.mybatis.generat.config.GeneratorConfigurer;
import com.oneplus.mybatis.generat.connect.Connector;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import com.oneplus.mybatis.generat.utils.GeneratorStringUtils;
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

        Map<String, String> colMap = connector.getColumnNameTypeMap(tableName);
        Map<String, String> columnRemarkMap = connector.getColumnRemarkMap(tableName);
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
        velocityContext.put("methods", generateGetAndSetMethods(colMap));
        velocityContext.put("fields", generateFields(colMap, columnRemarkMap));
        velocityContext.put("importSets", importSets);
        velocityContext.put("convertDomains", getCovertDomainFields(colMap));
        velocityContext.put("converts", getCovertFields(colMap));
    }

    protected List<String> getCovertDomainFields(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        List<String> converts = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String field = GeneratorStringUtils.format(key);
            sb.append(velocityContext.get("lowClassName")).append(GeneratorConfigurer.GENERATOR_DOMAIN)
                    .append(".set" + GeneratorStringUtils.firstUpperNoFormat(field) + "(")
                    .append(velocityContext.get("lowClassName")).append(".get" + GeneratorStringUtils.firstUpperNoFormat(field) + "())");
            converts.add(sb.toString());
        }
        return converts;
    }

    protected List<String> getCovertFields(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        List<String> converts = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String field = GeneratorStringUtils.format(key);
            sb.append(velocityContext.get("lowClassName")).append(".set" + GeneratorStringUtils.firstUpperNoFormat(field) + "(")
                    .append(velocityContext.get("lowClassName")).append(GeneratorConfigurer.GENERATOR_DOMAIN)
                    .append(".get" + GeneratorStringUtils.firstUpperNoFormat(field) + "())");
            converts.add(sb.toString());
        }
        return converts;
    }

    @Override
    protected PackageConfigType getPackageConfigType() {
        return PackageConfigType.service;
    }
}
