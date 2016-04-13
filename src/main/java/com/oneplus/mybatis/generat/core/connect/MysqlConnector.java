package com.oneplus.mybatis.generat.core.connect;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oneplus.mybatis.generat.config.AutoCodeConstantsType;
import com.oneplus.mybatis.generat.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * 功能描述：Mysql Connector实现
 *
 * @author: Zhenbin.Li
 * email： lizhenbin@oneplus.cn
 * company：一加科技
 * Date: 15/6/12 Time：23:42
 */
public class MysqlConnector implements Connector {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlConnector.class);

    /**
     * Connector session
     */
    public Map<SessionType, Object> session = Maps.newHashMap();

    /**
     * 配置properties
     */
    protected Properties properties;

    /**
     * 构造带配置
     *
     * @param properties
     */
    public MysqlConnector(Properties properties) {
        this.properties = properties;
    }

    /**
     * 获取所有属性和类型的map
     * key:属性 value:类型
     *
     * @param tableName
     * @return
     */
    public Map<String, String> mapColumnNameType(String tableName) {
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        DatabaseMetaData meta = getDatabaseMetaData();
        try {
            ResultSet colRet = meta.getColumns(null, "%", tableName, "%");
            while (colRet.next()) {
                String columnName = colRet.getString("COLUMN_NAME");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int dataType = colRet.getInt("DATA_TYPE");
                int columnSize = colRet.getInt("COLUMN_SIZE");
                String columnType = getDataType(dataType, digits, columnSize);
                colMap.put(columnName, columnType);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return colMap;
    }

    /**
     * 获取表结构字段描述备注
     *
     * @param tableName
     * @return
     */
    public Map<String, String> mapColumnRemark(String tableName) {
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        DatabaseMetaData meta = getDatabaseMetaData();
        try {
            ResultSet colRet = meta.getColumns(null, "%", tableName, "%");
            while (colRet.next()) {
                String columnName = colRet.getString("COLUMN_NAME");
                String columnRemark = colRet.getString("REMARKS");
                colMap.put(columnName, columnRemark);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return colMap;
    }

    /**
     * 获取所有的表索引
     *
     * @param tableName
     * @return
     */
    public List<String> listAllIndex(String tableName) {
        try {
            List<String> indexs = Lists.newArrayList();
            ResultSet resultSet = getDatabaseMetaData().getIndexInfo(null, null, tableName, false, true);
            while (resultSet.next()) {
                String indexName = resultSet.getString("INDEX_NAME");
                indexs.add(indexName);
            }
            return indexs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 尝试关闭Connection
     */
    public void closeConnection() {
        try {
            Object connection = session.get(SessionType.connection);
            if (connection != null) {
                Connection conn = (Connection) connection;
                try {
                    conn.close();
                } catch (Exception ex) {
                    LOGGER.error("关闭Connection异常", ex);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("关闭Connection失败", ex);
        }
    }

    /**
     * 获取主键
     *
     * @param tableName
     * @return
     */
    public Map<String, String> getPrimaryKey(String tableName) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            ResultSet pkRSet = getDatabaseMetaData().getPrimaryKeys(null, null, tableName);
            while (pkRSet.next()) {
                String primaryKey = pkRSet.getString("COLUMN_NAME");
                String primaryKeyType = mapColumnNameType(pkRSet.getString("TABLE_NAME")).get(primaryKey);
                map.put(AutoCodeConstantsType.PRIMARY_KEY.getDesc(), primaryKey);
                map.put(AutoCodeConstantsType.PRIMARY_KEY_TYPE.getDesc(), primaryKeyType);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * translate database type into java type
     *
     * @param type
     * @return
     */
    private String getDataType(int type, int digits, int columnSize) {
        String dataType = "";
        switch (type) {
            case Types.VARCHAR:  //12
                dataType = "String";
                break;
            case Types.INTEGER:    //4
                dataType = columnSize >= 8 ? "Long" : "Integer";
                break;
            case Types.BIT:    //-7
                dataType = "Integer";
                break;
            case Types.LONGVARCHAR: //-1
                dataType = "Long";
                break;
            case Types.BIGINT: //-5
                dataType = "Long";
                break;
            case Types.DOUBLE: //8
                dataType = getPrecisionType();
                break;
            case Types.REAL: //7
                dataType = getPrecisionType();
                break;
            case Types.FLOAT: //6
                dataType = getPrecisionType();
                break;
            case Types.DECIMAL:    //3
                dataType = "BigDecimal";
                break;
            case Types.NUMERIC: //2
                switch (digits) {
                    case 0:
                        dataType = "Integer";
                        break;
                    default:
                        dataType = getPrecisionType();
                }
                break;
            case Types.DATE:  //91
                dataType = "Date";
                break;
            case Types.TIMESTAMP: //93
                dataType = "Date";
                break;
            default:
                dataType = "String";
        }
        return dataType;
    }

    /**
     * 是否类型转换
     *
     * @return
     */
    private String getPrecisionType() {
        String dataType;
        if ("high".equals(PropertiesUtils.getPrecision(properties))) {
            dataType = "BigDecimal";
        } else {
            dataType = "Double";
        }
        return dataType;
    }

    /**
     * 获取JDBC连接信息
     *
     * @return
     */
    protected Connection getConnection() {
        Connection connection = (Connection) session.get(SessionType.connection);
        if (connection != null) {
            return connection;
        }

        try {
            String driverClassName = properties.getProperty("jdbc.driverClassName");
            String url = properties.getProperty("jdbc.url");
            String userName = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        session.put(SessionType.connection, connection);
        return connection;
    }

    /**
     * 获取DatabaseMetaData
     *
     * @return
     */
    protected DatabaseMetaData getDatabaseMetaData() {
        Connection connection = getConnection();
        DatabaseMetaData meta = (DatabaseMetaData) session.get(SessionType.DatabaseMetaData);
        if (meta != null) {
            return meta;
        }

        try {
            meta = connection.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        session.put(SessionType.DatabaseMetaData, meta);
        return meta;
    }

    /**
     * session配置类型
     */
    protected enum SessionType {
        connection, DatabaseMetaData

    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
