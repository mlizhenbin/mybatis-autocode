package com.oneplus.mybatis.generat.generator.context;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

/**
 * 功能描述：
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:58
 */
public class GeneratorContext implements Serializable {
    private static final long serialVersionUID = -8244453504134436716L;

    private Properties properties;

    private String tableName;

    private String upClassName;

    private String lowClassName;

    private String packageName;

    private String primaryKeyType;

    private String primaryKey;

    private Map<String, Object> attributes = Maps.newHashMap();

    public GeneratorContext(String tableName, String upClassName, String lowClassName, String packageName,
                            String primaryKeyType, String primaryKey, Properties properties) {
        this.tableName = tableName;
        this.upClassName = upClassName;
        this.lowClassName = lowClassName;
        this.packageName = packageName;
        this.primaryKeyType = primaryKeyType;
        this.primaryKey = primaryKey;
        this.properties = properties;
    }

    public Object getAttribute(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return this.attributes.get(key);
    }

    public void addAttribute(String key, Object value) {
        if (StringUtils.isBlank(key)) {
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

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
