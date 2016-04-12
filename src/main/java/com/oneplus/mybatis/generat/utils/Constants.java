package com.oneplus.mybatis.generat.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 功能描述：常用配置文件常量
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 16/4/12 Time: 14:13
 */
public enum Constants {

    PRIMARY_KEY_TYPE("primaryKeyType", "表主键类型"),

    PRIMARY_KEY("primaryKey", "表主键"),

    ORACLE_SCHEMA_NAME("oracle.schema.name", "oracle schema"),

    GENERATOR_DOMAIN("generator.domain", "Doamin后缀");

    private String type;

    private String desc;

    Constants(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Constants getByType(String typeCode) {
        if (StringUtils.isEmpty(typeCode)) {
            return null;
        }

        for (Constants type : Constants.values()) {
            if (type.getType().equals(typeCode)) {
                return type;
            }
        }
        return null;
    }
}
