package com.oneplus.mybatis.generat.core.impl;

import com.oneplus.mybatis.generat.core.connect.Connector;
import com.oneplus.mybatis.generat.core.context.AutoCodeContext;
import com.oneplus.mybatis.generat.core.context.AutoCodeGeneratorType;
import com.oneplus.mybatis.generat.config.AutoCodeConstantsType;
import org.apache.velocity.VelocityContext;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 功能描述：Model代码生成
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:51
 */
public class ModelGenerator extends AbstractGeneratorImpl {

    @Override
    public void initVelocityContext(VelocityContext velocityContext, AutoCodeContext cxt) {
        super.initVelocityContext(velocityContext, cxt);
        velocityContext.put("SerialVersionUID", String.valueOf(UUID.randomUUID().getLeastSignificantBits()));

        String tableName = cxt.getTableName();
        Connector connector = (Connector) cxt.getAttribute(AutoCodeConstantsType.JDBC_CONNECTOR);

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
        velocityContext.put("methods", generateGetAndSetMethods(colMap));
        velocityContext.put("fields", generateFields(colMap, columnRemarkMap));
        velocityContext.put("importSets", importSets);
    }

    @Override
    protected AutoCodeGeneratorType getPackageConfigType() {
        return AutoCodeGeneratorType.model;
    }

    @Override
    protected String getDescription() {
        return " Model";
    }
}
