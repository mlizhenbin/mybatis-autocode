package com.oneplus.mybatis.generat.connect;

import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/13 Time：00:11
 */
public interface Connector {

    public List<String> getAllTables();

    public Map<String, String> getPrimaryKey(String tableName);
}
