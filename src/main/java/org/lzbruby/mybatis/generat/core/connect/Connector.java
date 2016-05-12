package org.lzbruby.mybatis.generat.core.connect;

import java.util.List;
import java.util.Map;

/**
 * 功能描述：连接数据库接口
 *
 * @author: Zhenbin.Li
 * email： lizhenbin08@sina.com
 * company：org.lzbruby
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

    /**
     * 获取类型
     *
     * @param tableName
     * @return
     */
    public Map<String, String> mapColumnNameType(String tableName);

    /**
     * 获取备注
     *
     * @param tableName
     * @return
     */
    public Map<String, String> mapColumnRemark(String tableName);

    /**
     * 获取所有的索引信息
     *
     * @param tableName
     * @return
     */
    public List<String> listAllIndex(String tableName);

    /**
     * 关闭JDBC连接
     */
    public void closeConnection();
}
