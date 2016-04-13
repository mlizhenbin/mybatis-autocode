package com.oneplus.mybatis.generat.utils;

import com.google.common.collect.Lists;
import com.oneplus.mybatis.generat.core.context.AutoCodeGeneratorType;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Properties;

/**
 * 功能描述：属性工具类
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/12 Time：23:42
 */
public class PropertiesUtils {

    /**
     * Don't let anyone instantiate this class.
     */
    private PropertiesUtils() {

    }

    /**
     * 获取Package路径
     *
     * @param properties
     * @return
     */
    public static String getPackage(Properties properties) {
        String propertiesProperty = properties.getProperty("generator.package");
        if (StringUtils.isNotBlank(propertiesProperty)) {
            propertiesProperty = propertiesProperty.trim();
        }
        return propertiesProperty;
    }

    /**
     * 获取文件location
     *
     * @param properties
     * @return
     */
    public static String getLocation(Properties properties) {
        String location = properties.getProperty("generator.location");
        if (location != null) {
            location = location.trim();
        }
        String project = getProject(properties);
        if (project != null && !"".equals(project)) {
            location = location + "/" + project;
        }
        return location;
    }

    /**
     * 获取工程名称
     *
     * @param properties
     * @return
     */
    public static String getProject(Properties properties) {
        String project = properties.getProperty("generator.project.name");
        if (project != null) {
            project = project.trim();
        }
        return project;
    }

    /**
     * 获取table集合
     *
     * @param properties
     * @return
     */
    public static List<String> getTableList(Properties properties) {
        List<String> list = Lists.newArrayList();
        String tables = properties.getProperty("generator.tables");
        String[] tableArr = tables.split(",");
        for (String str : tableArr) {
            str = str.trim();
            if (!"".equals(str)) {
                list.add(str);
            }
        }
        return list;
    }

    /**
     * 获取table前缀
     *
     * @param properties
     * @return
     */
    public static String getTablePrefix(Properties properties) {
        String tablePrefix = properties.getProperty("generator.tablePrefix");
        if (StringUtils.isBlank(tablePrefix)) {
            tablePrefix = "";
        }
        return tablePrefix.toLowerCase().trim();
    }

    /**
     * 获取是否类型转换
     *
     * @param properties
     * @return
     */
    public static String getPrecision(Properties properties) {
        String precision = properties.getProperty("generator.precision");
        if (StringUtils.isBlank(precision)) {
            precision = "";
        }
        return precision.toLowerCase().trim();
    }

    /**
     * 获取生成代码层结构
     *
     * @param properties
     * @return
     */
    public static String getLayers(Properties properties) {
        String layers = properties.getProperty("generator.layers");
        if (StringUtils.isBlank(layers)) {
            layers = AutoCodeGeneratorType.getDefaultConfigLayer();
        }
        return layers.toLowerCase().trim();
    }
}
