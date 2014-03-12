package com.jesson.sexybelle.dao.impl;

import com.jesson.sexybelle.dao.IBelleDAO;
import com.jesson.sexybelle.model.Belle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdi on 14-3-6.
 */
public class BelleDAOImpl implements IBelleDAO {

    private Connection conn; //数据库连接对象
    private PreparedStatement pStmt = null; //数据库操作对象

    public BelleDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean insert(Belle belle) throws Exception {
        boolean flag = false;
        String sql = "replace into belles(time,type,url) values(?,?,?)";
        this.pStmt = this.conn.prepareStatement(sql);
        this.pStmt.setLong(1, belle.getTime());
        this.pStmt.setInt(2, belle.getType());
        this.pStmt.setString(3, belle.getUrl());
        if (this.pStmt.executeUpdate() > 0) {//更新记录的行数大于0
            flag = true;
        }
        this.pStmt.close();
        return flag;
    }

    @Override
    public boolean insertAll(List<Belle> list) throws Exception {
        if (list == null || list.size() == 0) {
            return true;
        }
        String sql = "replace into belles(time,type,url) values(?,?,?)";
        try {
            this.conn.setAutoCommit(false);
            this.pStmt = this.conn.prepareStatement(sql);
            for (Belle belle : list) {
                this.pStmt.setLong(1, belle.getTime());
                this.pStmt.setInt(2, belle.getType());
                this.pStmt.setString(3, belle.getUrl());
                this.pStmt.addBatch();
            }
            this.pStmt.executeBatch();
            this.conn.commit();
        } catch (SQLException e) {
            if (this.conn != null) {
                try {
                    this.conn.rollback();
                    this.conn.commit();
                } catch (SQLException e1) {

                }
            }
            throw e;
        } finally {
            this.pStmt.close();
        }
        return true;
    }

    @Override
    public List<Belle> findAllByType(int type, long id, int count) throws Exception {
        List<Belle> list = new ArrayList<Belle>();
        if (id <= 0) {
            String sql = "select _id,time,type,url from belles where type= ? order by _id desc limit ?";
            this.pStmt = this.conn.prepareStatement(sql);
            this.pStmt.setInt(1, type);
            this.pStmt.setInt(2, count);
        } else {
            String sql = "select _id,time,type,url from belles where type= ? and _id < ? order by _id desc limit ?";
            this.pStmt = this.conn.prepareStatement(sql);
            this.pStmt.setInt(1, type);
            this.pStmt.setLong(2, id);
            this.pStmt.setInt(3, count);
        }

        ResultSet rs = this.pStmt.executeQuery();//执行查询操作
        Belle belle;
        while (rs.next()) {
            belle = new Belle();
            belle.setId(rs.getInt(1));
            belle.setTime(rs.getLong(2));
            belle.setType(rs.getInt(3));
            belle.setUrl(rs.getString(4));
            list.add(belle);
        }
        this.pStmt.close();
        return list;
    }

    @Override
    public List<Belle> randomFindByType(int type, int count) throws Exception {
        List<Belle> list = new ArrayList<Belle>();

        String sql = "select _id,time,type,url from belles where type=? order by rand() limit ?";
        this.pStmt = this.conn.prepareStatement(sql);
        this.pStmt.setInt(1, type);
        this.pStmt.setInt(2, count);

        ResultSet rs = this.pStmt.executeQuery();
        while (rs.next()) {
            Belle belle = new Belle();
            belle.setId(rs.getInt(1));
            belle.setTime(rs.getLong(2));
            belle.setType(rs.getInt(3));
            belle.setUrl(rs.getString(4));
            list.add(belle);
        }

        this.pStmt.close();
        return list;
    }
}
