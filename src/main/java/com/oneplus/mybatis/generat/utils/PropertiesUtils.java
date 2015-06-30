package com.oneplus.mybatis.generat.utils;

import com.google.common.collect.Lists;
import com.oneplus.mybatis.generat.generator.context.PackageConfigType;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Properties;


public class PropertiesUtils {

    public static String getPackage(Properties properties) {
        String propertiesProperty = properties.getProperty("generator.package");
        if (StringUtils.isNotBlank(propertiesProperty)) {
            propertiesProperty = propertiesProperty.trim();
        }
        return propertiesProperty;
    }

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

    public static String getProject(Properties properties) {
        String project = properties.getProperty("generator.project.name");
        if (project != null) {
            project = project.trim();
        }
        return project;
    }

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

    public static String getTablePrefix(Properties properties) {
        String tablePrefix = properties.getProperty("generator.tablePrefix");
        if (StringUtils.isBlank(tablePrefix)) {
            tablePrefix = "";
        }
        return tablePrefix.toLowerCase().trim();
    }

    public static String getPrecision(Properties properties) {
        String precision = properties.getProperty("generator.precision");
        if (StringUtils.isBlank(precision)) {
            precision = "";
        }
        return precision.toLowerCase().trim();
    }

    public static String getLayers(Properties properties) {
        String layers = properties.getProperty("generator.layers");
        if (StringUtils.isBlank(layers)) {
            layers = PackageConfigType.getDefaultConfigLayer();
        }
        return layers.toLowerCase().trim();
    }
}
