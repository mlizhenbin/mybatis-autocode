package com.oneplus.mybatis.generat.connect;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oneplus.mybatis.generat.utils.ConstantsType;
import com.oneplus.mybatis.generat.utils.PropertiesUtils;

import java.sql.*;
import java.util.*;

public class MysqlConnector implements Connector {

    public Map<SessionType, Object> session = Maps.newHashMap();

    protected Properties properties;

    public MysqlConnector(Properties properties) {
        this.properties = properties;
    }

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

    public Map<String, String> getPrimaryKey(String tableName) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            ResultSet pkRSet = getDatabaseMetaData().getPrimaryKeys(null, null, tableName);
            while (pkRSet.next()) {
                String primaryKey = pkRSet.getString("COLUMN_NAME");
                String primaryKeyType = mapColumnNameType(pkRSet.getString("TABLE_NAME")).get(primaryKey);
                map.put(ConstantsType.PRIMARY_KEY.getDesc(), primaryKey);
                map.put(ConstantsType.PRIMARY_KEY_TYPE.getDesc(), primaryKeyType);
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

    private String getPrecisionType() {
        String dataType;
        if ("high".equals(PropertiesUtils.getPrecision(properties))) {
            dataType = "BigDecimal";
        } else {
            dataType = "Double";
        }
        return dataType;
    }

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
