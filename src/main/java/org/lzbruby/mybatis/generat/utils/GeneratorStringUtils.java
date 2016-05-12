package org.lzbruby.mybatis.generat.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Properties;

/**
 * 功能描述：字符串格式化操作工具类
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
 * Date: 15/6/12 Time：23:42
 */
public class GeneratorStringUtils {

    /**
     * Don't let anyone instantiate this class.
     */
    private GeneratorStringUtils() {

    }

    /**
     * format database type into java type, eg format "card_type" into "cardType"
     * 全部大写的大写字母,不包含"_"也变成小写
     *
     * @param str
     * @return
     */
    public static String format(String str) {
        if (!StringUtils.contains(str, "_")) {
            if (StringUtils.equals(str, StringUtils.upperCase(str))) {
                return StringUtils.lowerCase(str);
            } else {
                return str;
            }
        }

        StringBuilder sb = new StringBuilder();
        char[] cArr = str.trim().toLowerCase().toCharArray();
        for (int i = 0; i < cArr.length; i++) {
            char c = cArr[i];
            if (c == '_') {
                i++;
                sb.append(Character.toUpperCase(cArr[i]));
            } else {
                sb.append(c);
            }
        }


        return sb.toString();
    }

    /**
     * 过滤掉表格的前缀
     *
     * @param tableName
     * @param properties
     * @return
     */
    public static String formatAndNoPrefix(String tableName, Properties properties) {
        String noPrefixTableName = tableName.toLowerCase()
                .replaceFirst(PropertiesUtils.getTablePrefix(properties), "");
        noPrefixTableName = format(noPrefixTableName);
        return noPrefixTableName;
    }

    /**
     * 首字母大写
     *
     * @param string
     * @return
     */
    public static String firstUpper(String string) {
        String str = format(string);
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }

    /**
     * 首字母大写,并且去掉前缀
     *
     * @param tableName
     * @param properties
     * @return
     */
    public static String firstUpperAndNoPrefix(String tableName, Properties properties) {
        String noPrefixTableName = tableName.toLowerCase()
                .replaceFirst(PropertiesUtils.getTablePrefix(properties), "");
        noPrefixTableName = firstUpper(noPrefixTableName);
        return noPrefixTableName;
    }

    /**
     * 首字母大写不格式化
     *
     * @param string
     * @return
     */
    public static String firstUpperNoFormat(String string) {
        String str = string.substring(0, 1).toUpperCase() + string.substring(1);
        return str;
    }
}
