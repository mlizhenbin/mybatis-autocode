package com.oneplus.mybatis.generat.connect;

import com.oneplus.mybatis.generat.utils.GeneratorStringUtils;
import com.oneplus.mybatis.generat.utils.PropertiesUtils;

import java.sql.*;
import java.util.*;

public class MysqlConnector implements Connector {

    private Properties properties;

    public MysqlConnector(Properties properties) {
        this.properties = properties;
    }

    private Connection getConnection() {
        Connection connection;
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
        return connection;
    }

    private DatabaseMetaData getDatabaseMetaData() {
        Connection connection = getConnection();
        DatabaseMetaData meta;
        try {
            meta = connection.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return meta;
    }

    public Map<String, String> getColumnNameTypeMap(String tableName) {
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        DatabaseMetaData meta = getDatabaseMetaData();
        try {
            ResultSet colRet = meta.getColumns(null, "%", tableName, "%");
            while (colRet.next()) {
                String columnName = colRet.getString("COLUMN_NAME");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int dataType = colRet.getInt("DATA_TYPE");
                String columnType = getDataType(dataType, digits);
                colMap.put(columnName, columnType);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return colMap;
    }

    public Map<String, String> getColumnRemarkMap(String tableName) {
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

    public Map<String, String> getFormatedColumnNameTypeMap(String tableName) {
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        DatabaseMetaData meta = getDatabaseMetaData();
        try {
            ResultSet colRet = meta.getColumns(null, "%", tableName, "%");
            while (colRet.next()) {
                String columnName = colRet.getString("COLUMN_NAME");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int dataType = colRet.getInt("DATA_TYPE");
                String columnType = columnType = getDataType(dataType, digits);
                colMap.put(GeneratorStringUtils.format(columnName), columnType);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return colMap;
    }

    public List<String> getColumnNameList(String tableName) {
        List<String> list = new ArrayList<String>();
        DatabaseMetaData meta = getDatabaseMetaData();
        try {
            ResultSet colRet = meta.getColumns(null, "%", tableName, "%");
            while (colRet.next()) {
                String columnName = colRet.getString("COLUMN_NAME");
                list.add(columnName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<String> getFormatedColumnNameList(String tableName) {
        List<String> list = new ArrayList<String>();
        DatabaseMetaData meta = getDatabaseMetaData();
        try {
            ResultSet colRet = meta.getColumns(null, "%", tableName, "%");
            while (colRet.next()) {
                String columnName = colRet.getString("COLUMN_NAME");
                list.add(GeneratorStringUtils.format(columnName));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * translate database type into java type
     *
     * @param type
     * @return
     */
    private String getDataType(int type, int digits) {
        String dataType = "";
        switch (type) {
            case Types.VARCHAR:  //12
                dataType = "String";
                break;
            case Types.INTEGER:    //4
                dataType = "Integer";
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

    public Map<String, String> getPrimaryKey(String tableName) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            ResultSet pkRSet = getDatabaseMetaData().getPrimaryKeys(null, null, tableName);
            while (pkRSet.next()) {
                String primaryKey = pkRSet.getString("COLUMN_NAME");
                String primaryKeyType = getColumnNameTypeMap(pkRSet.getString("TABLE_NAME")).get(primaryKey);
                map.put("primaryKey", primaryKey);
                map.put("primaryKeyType", primaryKeyType);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
