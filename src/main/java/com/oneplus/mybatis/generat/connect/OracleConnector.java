package com.oneplus.mybatis.generat.connect;

import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 功能描述：获取Mysql的连接池信息
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 16/4/12 Time: 10:57
 */
public class OracleConnector extends MysqlConnector {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OracleConnector.class);

    public OracleConnector(Properties properties) {
        super(properties);
    }

    @Override
    public Map<String, String> getPrimaryKey(String tableName) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            ResultSet pkRSet = getDatabaseMetaData().getPrimaryKeys(null, null, tableName);
            while (pkRSet.next()) {
                String primaryKey = pkRSet.getString("COLUMN_NAME");
                String primaryKeyType = mapColumnNameType(pkRSet.getString("TABLE_NAME")).get(primaryKey);
                map.put("primaryKey", primaryKey);
                map.put("primaryKeyType", primaryKeyType);
            }

            if (MapUtils.isEmpty(map)) {
                Map<String, String> columnNameType = mapColumnNameType(tableName);
                String primaryKey = (String) getProperties().get("oracle.primaryKey.name");
                map.put("primaryKey", primaryKey);
                map.put("primaryKeyType", columnNameType.get(primaryKey));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    @Override
    public List<String> listAllIndex(String tableName) {
        return Lists.newArrayList();
    }

    @Override
    protected Connection getConnection() {
        Connection connection;
        try {
            String driver = getProperties().getProperty("oracle.jdbc.driverClassName");
            Class.forName(driver);

            String url = getProperties().getProperty("oracle.jdbc.url");
            String username = getProperties().getProperty("oracle.jdbc.username");
            String pwd = getProperties().getProperty("oracle.jdbc.password");
            connection = DriverManager.getConnection(url, username, pwd);
            session.put(SessionType.connection, connection);
            return connection;
        } catch (Exception e) {
            LOGGER.error("获取Oracle JDBC信息失败!", e);
            throw new RuntimeException("连接Oracle失败");
        }
    }

}
