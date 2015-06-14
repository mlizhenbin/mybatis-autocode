package com.oneplus.mybatis.generat.connect;

import java.util.List;
import java.util.Map;

/**
 * 功能描述：连接数据库接口
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:11
 */
public interface Connector {

    /**
     * 获取表的键值类型
     *
     * @param tableName
     * @return
     */
    public Map<String, String> getPrimaryKey(String tableName);

    public List<String> getColumnNameList(String tableName);
}
