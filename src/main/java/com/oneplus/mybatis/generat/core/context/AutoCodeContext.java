package com.oneplus.mybatis.generat.core.context;

import com.google.common.collect.Maps;
import com.oneplus.mybatis.generat.config.AutoCodeConstantsType;
import com.oneplus.mybatis.generat.utils.GeneratorStringUtils;
import com.oneplus.mybatis.generat.utils.PropertiesUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

/**
 * 功能描述：Velocity渲染模板配置上下文信息
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:58
 */
public class AutoCodeContext implements Serializable {
    private static final long serialVersionUID = -8244453504134436716L;

    /**
     * 配置文件配置
     */
    private Properties properties;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 类的名称
     */
    private String upClassName;

    /**
     * 按照JAVA规范，类对应的变量小写
     */
    private String lowClassName;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 主键类型
     */
    private String primaryKeyType;

    /**
     * 主键
     */
    private String primaryKey;

    /**
     * 上下文参数
     */
    private Map<AutoCodeConstantsType, Object> attributes = Maps.newHashMap();

    /**
     * 构造生成代码上下文
     *
     * @param tableName  表名称
     * @param propMap    JDBC数据map
     * @param properties 配置
     */
    public AutoCodeContext(String tableName, Map<String, String> propMap, Properties properties) {
        this.tableName = tableName;
        this.upClassName = GeneratorStringUtils.firstUpperAndNoPrefix(tableName, properties);
        this.lowClassName = GeneratorStringUtils.formatAndNoPrefix(tableName, properties);
        this.packageName = PropertiesUtils.getPackage(properties);
        this.primaryKeyType = propMap.get(AutoCodeConstantsType.PRIMARY_KEY_TYPE.getType());
        this.primaryKey = GeneratorStringUtils.firstUpperNoFormat(GeneratorStringUtils.format(propMap.get(AutoCodeConstantsType.PRIMARY_KEY.getType())));
        this.properties = properties;
    }

    public Object getAttribute(AutoCodeConstantsType key) {
        if (key == null) {
            return null;
        }
        return this.attributes.get(key);
    }

    public void addAttribute(AutoCodeConstantsType key, Object value) {
        if (key == null) {
            return;
        }
        this.attributes.put(key, value);
    }

    public Properties getProperties() {
        return properties;
    }

    public String getTableName() {
        return tableName;
    }

    public String getUpClassName() {
        return upClassName;
    }

    public String getLowClassName() {
        return lowClassName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getPrimaryKeyType() {
        return primaryKeyType;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }
}
