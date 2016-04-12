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

    /**
     * 数据库连接池
     */
    JDBC_CONNECTOR("JDBC_CONNECTOR", "JDBC_CONNECTOR"),

    /**
     * 配置文件配置
     */
    CONFIG_PROPERTIES("CONFIG_PROPERTIES", "CONFIG_PROPERTIES"),

    /**
     * daomain
     */
    DOMAIN("generator.domain", "domain"),

    /**
     * 数据库表主键类型
     */
    PRIMARY_KEY_TYPE("primaryKeyType", "primaryKeyType"),

    /**
     * 数据库主键字段名称
     */
    PRIMARY_KEY("primaryKey", "primaryKey"),

    /**
     * 表主键扩展,与Java属性对应,去掉下划线"_"变成驼峰
     */
    NORMAL_PRIMARY_KEY("NORMAL_PRIMARY_KEY", "primaryKey"),

    /**
     * 表主键扩展, 全部大写
     */
    COL_ALL_UPPERCASE_PRIMARY_KEY("COL_ALL_UPPERCASE_PRIMARY_KEY", "colPrimaryKey"),

    /**
     * oracle schema
     */
    ORACLE_SCHEMA("oracle.schema", "oracle_schema"),

    /**
     * 数据库表名称
     */
    TABLE_NAME("tableName", "tableName"),

    /**
     * 没有前缀表格名称
     */
    NO_PREFIX_TABLE_NAME("NO_PREFIX_TABLE_NAME", "noPrefixTableName"),

    /**
     * Java class名称,首字母大写
     */
    UP_CLASS_NAME("upClassName", "upClassName"),

    /**
     * JAVA class实例, 首字母小写
     */
    LOW_CLASS_NAME("lowClassName", "lowClassName"),

    /**
     * JAVA Package路径
     */
    PACKAGE_NAME("packageName", "packageName"),

    /**
     * 文件描述
     */
    CLASS_TITLE("classTitle", "classTitle"),

    /**
     * UUID
     */
    SERIAL_VERSION_UID("SerialVersionUID", "SerialVersionUID"),

    /**
     * 动态字段
     */
    DYNAMIC_FILEDS("DYNAMIC_FILEDS", "dynamicFileds_"),

    /**
     * result Mapper
     */
    RESULT_MAP_COLUMNS("RESULT_MAP_COLUMNS", "resultMapColumns"),

    /**
     * where查询条件
     */
    WHERE_CONDITIONS("WHERE_CONDITIONS", "whereConditions"),

    /**
     * 增加条件值
     */
    INSERT_VALUE_CONDITIONS("INSERT_VALUE_CONDITIONS", "insertValueConditions"),

    /**
     * 增加条件col
     */
    INSERT_COLS_CONDITIONS("INSERT_COLS_CONDITIONS", "insertColsConditions"),

    /**
     * 修改条件
     */
    UPDATE_CONDITIONS("UPDATE_CONDITIONS", "updateConditions"),

    /**
     * 表属性列
     */
    COLUMNS("columns", "columns"),

    /**
     * 所有列大写
     */
    ALL_UP_CASE_COLS("ALL_UP_CASE_COLS", "allUpCaseCols"),

    /**
     * 字段配置集合
     */
    REMARK_MAP("REMARK_MAP", "remarkMap"),

    /**
     * 方法集合
     */
    METHODS("methods", "methods"),

    /**
     * 属性集合
     */
    FIELDS("FIELDS", "fields"),

    /**
     * 导入包集合
     */
    IMPORT_SETS("IMPORT_SETS", "importSets"),

    /**
     * 转换domain
     */
    CONVERT_DOMAINS("CONVERT_DOMAINS", "convertDomains"),

    /**
     * 转换entity
     */
    CONVERTS("CONVERTS", "converts"),

    /**
     * 工具类
     */
    ADD_UTILS("ADD_UTILS", "addUtils"),

    /**
     * 修改工具类
     */
    UPT_UTILS("UPT_UTILS", "uptUtils"),


    ;

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
