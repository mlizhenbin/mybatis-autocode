package com.oneplus.mybatis.generat.generator.context;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

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
public class GeneratorContext implements Serializable {
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

}
