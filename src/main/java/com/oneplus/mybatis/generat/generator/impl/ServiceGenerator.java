package com.oneplus.mybatis.generat.generator.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oneplus.mybatis.generat.connect.Connector;
import com.oneplus.mybatis.generat.generator.context.GeneratorContext;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import com.oneplus.mybatis.generat.utils.ConstantsType;
import com.oneplus.mybatis.generat.utils.GeneratorStringUtils;
import com.oneplus.mybatis.generat.utils.PropertiesUtils;
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
public class ServiceGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, GeneratorContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
        velocityContext.put(ConstantsType.SERIAL_VERSION_UID.getDesc(), String.valueOf(UUID.randomUUID().getLeastSignificantBits()));

        String tableName = cxt.getTableName();
        Connector connector = (Connector) cxt.getAttribute(ConstantsType.JDBC_CONNECTOR);

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

        Properties properties = cxt.getProperties();

        velocityContext.put(ConstantsType.METHODS.getDesc(), generateGetAndSetMethods(colMap));
        velocityContext.put(ConstantsType.FIELDS.getDesc(), generateFields(colMap, columnRemarkMap));
        velocityContext.put(ConstantsType.IMPORT_SETS.getDesc(), importSets);
        velocityContext.put(ConstantsType.CONVERT_DOMAINS.getDesc(), getCovertDomainFields(colMap, properties));
        velocityContext.put(ConstantsType.CONVERTS.getDesc(), getCovertFields(colMap, properties));
        String noPrefixTableName = StringUtils.upperCase(tableName.toLowerCase()
                .replaceFirst(PropertiesUtils.getTablePrefix(cxt.getProperties()), ""));
        velocityContext.put(ConstantsType.NO_PREFIX_TABLE_NAME.getDesc(), noPrefixTableName);
        velocityContext.put(ConstantsType.ADD_UTILS.getDesc(), getUtilFields(colMap, columnRemarkMap, noPrefixTableName));

        Map<String, String> checkUpdateMap = Maps.newHashMap();
        String primaryKey = (String) cxt.getAttribute(ConstantsType.PRIMARY_KEY);
        for (String col : colMap.keySet()) {
            if (StringUtils.equals(col, primaryKey)) {
                checkUpdateMap.put(col, colMap.get(col));
            }
        }
        velocityContext.put(ConstantsType.UPT_UTILS.getDesc(), getUtilFields(checkUpdateMap, columnRemarkMap, noPrefixTableName));
    }

    /**
     * domain模板
     *
     * @param map
     * @param properties
     * @return
     */
    protected List<String> getCovertDomainFields(Map<String, String> map, Properties properties) {
        Set<String> keySet = map.keySet();
        List<String> converts = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String field = GeneratorStringUtils.format(key);
            sb.append(velocityContext.get(ConstantsType.LOW_CLASS_NAME.getDesc()))
                    .append(properties.get(ConstantsType.DOMAIN.getType()))
                    .append(".set" + GeneratorStringUtils.firstUpperNoFormat(field) + "(")
                    .append(velocityContext.get(ConstantsType.LOW_CLASS_NAME.getDesc()))
                    .append(".get" + GeneratorStringUtils.firstUpperNoFormat(field) + "())");
            converts.add(sb.toString());
        }
        return converts;
    }

    /**
     * 转换类
     *
     * @param colMap
     * @param properties
     * @return
     */
    protected List<String> getCovertFields(Map<String, String> colMap, Properties properties) {
        Set<String> keySet = colMap.keySet();
        List<String> converts = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String field = GeneratorStringUtils.format(key);
            sb.append(velocityContext.get(ConstantsType.LOW_CLASS_NAME.getDesc()))
                    .append(".set" + GeneratorStringUtils.firstUpperNoFormat(field) + "(")
                    .append(velocityContext.get(ConstantsType.LOW_CLASS_NAME.getDesc()))
                    .append(properties.get(ConstantsType.DOMAIN.getType()))
                    .append(".get" + GeneratorStringUtils.firstUpperNoFormat(field) + "())");
            converts.add(sb.toString());
        }
        return converts;
    }

    /**
     * 组装工具类模板
     *
     * @param colMap
     * @param columnRemarkMap
     * @param noPrefixTableName
     * @return
     */
    protected List<String> getUtilFields(Map<String, String> colMap, Map<String, String> columnRemarkMap, String noPrefixTableName) {
        Set<String> keySet = colMap.keySet();
        List<String> utils = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String field = GeneratorStringUtils.format(key);
            String colType = colMap.get(key);
            if (StringUtils.equals(colType, "String")) {
                sb.append("\tif (StringUtils.isBlank(")
                        .append(velocityContext.get(ConstantsType.LOW_CLASS_NAME.getDesc()))
                        .append(".get")
                        .append(GeneratorStringUtils.firstUpperNoFormat(field))
                        .append("())) {\n");
            } else {
                sb.append("\tif (")
                        .append(velocityContext.get(ConstantsType.LOW_CLASS_NAME.getDesc()))
                        .append(".get")
                        .append(GeneratorStringUtils.firstUpperNoFormat(field))
                        .append("() == null) {\n");
            }
            String remark = columnRemarkMap.get(key);
            sb.append("\t\t\tLOGGER.warn(\"")
                    .append(field)
                    .append(remark)
                    .append("为空, ")
                    .append(velocityContext.get(ConstantsType.LOW_CLASS_NAME.getDesc()))
                    .append("=\" + ")
                    .append(velocityContext.get(ConstantsType.LOW_CLASS_NAME.getDesc()))
                    .append(");\n");
            sb.append("\t\t\t")
                    .append("throw new ")
                    .append(velocityContext.get(ConstantsType.UP_CLASS_NAME.getDesc()))
                    .append("Exception(")
                    .append(velocityContext.get(ConstantsType.UP_CLASS_NAME.getDesc()))
                    .append("Result.")
                    .append(noPrefixTableName)
                    .append("_")
                    .append(StringUtils.upperCase(key))
                    .append("_NULL);\n");
            sb.append("\t\t}\n");
            utils.add(sb.toString());
        }
        return utils;
    }

    @Override
    protected PackageConfigType getPackageConfigType() {
        return PackageConfigType.SERVICE;
    }

    @Override
    protected String getDescription() {
        return " Service";
    }
}
