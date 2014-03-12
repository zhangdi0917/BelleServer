package com.jesson.sexybelle.dao.impl;

import com.jesson.sexybelle.dao.ISeriesDAO;
import com.jesson.sexybelle.model.Series;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdi on 14-3-11.
 */
public class SeriesDAOImpl implements ISeriesDAO {

    private Connection conn; //数据库连接对象
    private PreparedStatement pStmt = null; //数据库操作对象

    public SeriesDAOImpl(Connection conn) throws Exception {
        this.conn = conn;
    }

    @Override
    public List<Series> getAll(int mode) throws Exception {
        List<Series> list = new ArrayList<Series>();
        String sql = "select type,title from series where mode=? order by id asc";
        this.pStmt = this.conn.prepareStatement(sql);
        this.pStmt.setInt(1, mode);
        ResultSet rs = this.pStmt.executeQuery();
        while (rs.next()) {
            Series series = new Series();
            series.setType(rs.getInt(1));
            series.setTitle(rs.getString(2));
            list.add(series);
        }
        this.pStmt.close();
        return list;
    }
}
