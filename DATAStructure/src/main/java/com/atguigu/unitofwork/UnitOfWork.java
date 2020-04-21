package com.atguigu.unitofwork;

import com.atguigu.unitofwork.repository.UnitOfWorkRepository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public interface UnitOfWork {
    public void registerAdd(AggrateRoot root, UnitOfWorkRepository repository);

    public void registerModify(AggrateRoot root, UnitOfWorkRepository repository);

    public void registerDelete(AggrateRoot root, UnitOfWorkRepository repository);

    public void commit();
}

class UnitOfWorkImpl implements UnitOfWork {
    private Map<AggrateRoot, UnitOfWorkRepository> addMaps = new HashMap<AggrateRoot, UnitOfWorkRepository>();
    private Map<AggrateRoot, UnitOfWorkRepository> deleteMaps = new HashMap<AggrateRoot, UnitOfWorkRepository>();
    private Map<AggrateRoot, UnitOfWorkRepository> modifyMaps = new HashMap<AggrateRoot, UnitOfWorkRepository>();

    @Override
    public void registerAdd(AggrateRoot root, UnitOfWorkRepository repository) {
        if (!addMaps.containsKey(root)) {
            this.addMaps.put(root, repository);
        }
    }

    @Override
    public void registerModify(AggrateRoot root, UnitOfWorkRepository repository) {
        if (!modifyMaps.containsKey(root)) {
            this.modifyMaps.put(root, repository);
        }
    }

    @Override
    public void registerDelete(AggrateRoot root, UnitOfWorkRepository repository) {
        if (!deleteMaps.containsKey(root)) {
            this.deleteMaps.put(root, repository);
        }
    }

    @Override
    public void commit() {
        // 在这里稍微卡顿了，c#中可以方便的使用TransactionScope事务域，但是java中没有这个概念
        // 而事务性是建立在数据库链接的基础上，如果这里的添加，删除，修改使用的不是同一个数据库链接
        // 那么就无法保证其事务性

        Connection conn = DataSource.getConnection();
        try {
            conn.setAutoCommit(false);
            doCommit(conn);
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            // 这里不太舒服，更好的方式是实现java.sql.Connection接口,对原始Connection做一个封装（间接层）
            DataSource.close();
        }
    }

    private void doCommit(Connection conn) {
        for (AggrateRoot root : addMaps.keySet()) {
            addMaps.get(root).doPut(conn, root);
        }

        for (AggrateRoot root : modifyMaps.keySet()) {
            modifyMaps.get(root).doModify(conn, root);
        }

        for (AggrateRoot root : deleteMaps.keySet()) {
            deleteMaps.get(root).doRemove(conn, root);
        }
    }
}






class Account implements AggrateRoot {
    public Account(int id, int money) {
        this.id = id;
        this.money = money;
    }

    private int id;
    // 仅作示例
    private int money;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void add(int money) {
        if (money > 0) {
            this.money += money;
        }
    }

    public void minus(int money) {
        if (this.money >= money) {
            this.money -= money;
        }
    }

}


class DataSource {
    private static Connection conn = null;

    private DataSource() {
    }

    // 不考虑多线程的情况
    public static Connection getConnection() {
        if (conn == null) {
            conn=CreateConnection();
            // 创建链接---或者从连接池拿
        }

        return conn;
    }

    private static Connection CreateConnection() {
        return new Connection() {
            @Override
            public <T> T unwrap(Class<T> iface) throws SQLException {
                return null;
            }

            @Override
            public boolean isWrapperFor(Class<?> iface) throws SQLException {
                return false;
            }

            @Override
            public Statement createStatement() throws SQLException {
                return null;
            }

            @Override
            public PreparedStatement prepareStatement(String sql) throws SQLException {
                return null;
            }

            @Override
            public CallableStatement prepareCall(String sql) throws SQLException {
                return null;
            }

            @Override
            public String nativeSQL(String sql) throws SQLException {
                return null;
            }

            @Override
            public void setAutoCommit(boolean autoCommit) throws SQLException {

            }

            @Override
            public boolean getAutoCommit() throws SQLException {
                return false;
            }

            @Override
            public void commit() throws SQLException {

            }

            @Override
            public void rollback() throws SQLException {

            }

            @Override
            public void close() throws SQLException {

            }

            @Override
            public boolean isClosed() throws SQLException {
                return false;
            }

            @Override
            public DatabaseMetaData getMetaData() throws SQLException {
                return null;
            }

            @Override
            public void setReadOnly(boolean readOnly) throws SQLException {

            }

            @Override
            public boolean isReadOnly() throws SQLException {
                return false;
            }

            @Override
            public void setCatalog(String catalog) throws SQLException {

            }

            @Override
            public String getCatalog() throws SQLException {
                return null;
            }

            @Override
            public void setTransactionIsolation(int level) throws SQLException {

            }

            @Override
            public int getTransactionIsolation() throws SQLException {
                return 0;
            }

            @Override
            public SQLWarning getWarnings() throws SQLException {
                return null;
            }

            @Override
            public void clearWarnings() throws SQLException {

            }

            @Override
            public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
                return null;
            }

            @Override
            public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
                return null;
            }

            @Override
            public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
                return null;
            }

            @Override
            public Map<String, Class<?>> getTypeMap() throws SQLException {
                return null;
            }

            @Override
            public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

            }

            @Override
            public void setHoldability(int holdability) throws SQLException {

            }

            @Override
            public int getHoldability() throws SQLException {
                return 0;
            }

            @Override
            public Savepoint setSavepoint() throws SQLException {
                return null;
            }

            @Override
            public Savepoint setSavepoint(String name) throws SQLException {
                return null;
            }

            @Override
            public void rollback(Savepoint savepoint) throws SQLException {

            }

            @Override
            public void releaseSavepoint(Savepoint savepoint) throws SQLException {

            }

            @Override
            public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
                return null;
            }

            @Override
            public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
                return null;
            }

            @Override
            public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
                return null;
            }

            @Override
            public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
                return null;
            }

            @Override
            public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
                return null;
            }

            @Override
            public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
                return null;
            }

            @Override
            public Clob createClob() throws SQLException {
                return null;
            }

            @Override
            public Blob createBlob() throws SQLException {
                return null;
            }

            @Override
            public NClob createNClob() throws SQLException {
                return null;
            }

            @Override
            public SQLXML createSQLXML() throws SQLException {
                return null;
            }

            @Override
            public boolean isValid(int timeout) throws SQLException {
                return false;
            }

            @Override
            public void setClientInfo(String name, String value) throws SQLClientInfoException {

            }

            @Override
            public void setClientInfo(Properties properties) throws SQLClientInfoException {

            }

            @Override
            public String getClientInfo(String name) throws SQLException {
                return null;
            }

            @Override
            public Properties getClientInfo() throws SQLException {
                return null;
            }

            @Override
            public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
                return null;
            }

            @Override
            public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
                return null;
            }

            @Override
            public void setSchema(String schema) throws SQLException {

            }

            @Override
            public String getSchema() throws SQLException {
                return null;
            }

            @Override
            public void abort(Executor executor) throws SQLException {

            }

            @Override
            public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

            }

            @Override
            public int getNetworkTimeout() throws SQLException {
                return 0;
            }
        };
    }

    public static void close() {
        try {
            if (conn != null) {
                conn.close();// 关闭或放回连接池中
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn = null;
        }
    }
}