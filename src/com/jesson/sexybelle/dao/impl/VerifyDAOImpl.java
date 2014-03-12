package com.jesson.sexybelle.dao.impl;

import com.jesson.sexybelle.dao.IVerifyDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by zhangdi on 14-3-10.
 */
public class VerifyDAOImpl implements IVerifyDAO {

    private Connection conn; //数据库连接对象
    private PreparedStatement pStmt = null; //数据库操作对象

    public VerifyDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean verifyAppid(String appid) throws Exception {
        if (appid == null) {
            return false;
        }
        boolean flag = false;
        String sql = "select * from verify where appid=?";
        this.pStmt = this.conn.prepareStatement(sql);
        this.pStmt.setString(1, appid);
        ResultSet rs = this.pStmt.executeQuery();//执行查询操作
        if (rs.next()) {
            flag = true;
        }
        this.pStmt.close();
        return flag;
    }
}
