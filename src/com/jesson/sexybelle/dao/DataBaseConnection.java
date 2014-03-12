package com.jesson.sexybelle.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by zhangdi on 14-3-6.
 */
public class DataBaseConnection {

    private static final String Driver = "com.mysql.jdbc.Driver";
//    private static final String URL = "jdbc:mysql://localhost:3306/sexy_belle";
//    private static final String USER = "root";
//    private static final String PWD = "123456";

    private static final String URL = "jdbc:mysql://sqld.duapp.com:4050/RtERYTTrAtTpPpbvujuR";
    private static final String USER = "QKFTu78j2MMt1CGjhD7uRxd2";
    private static final String PWD = "ESrmqdpARxzOSxBQZy2fEdxHmhZCaAja";

    private Connection conn = null;

    public DataBaseConnection() throws Exception {  //在构造方法中进行数据库连接
        try {
            Class.forName(Driver);   //加载驱动类
            this.conn = DriverManager.getConnection(URL, USER, PWD);
        } catch (Exception e) {
            throw e;          //直接抛出异常
        }
    }

    public Connection getConnection() {
        return this.conn;  //取得数据库的连接
    }

    public void close() throws Exception {  //数据库关闭
        if (this.conn != null) {
            try {
                this.conn.close();  //数据库关闭
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
