package com.jesson.sexybelle.dao.proxy;

import com.jesson.sexybelle.dao.DataBaseConnection;
import com.jesson.sexybelle.dao.ISeriesDAO;
import com.jesson.sexybelle.dao.impl.SeriesDAOImpl;
import com.jesson.sexybelle.model.Series;

import java.util.List;

/**
 * Created by zhangdi on 14-3-11.
 */
public class SeriesDAOProxy implements ISeriesDAO {

    private DataBaseConnection dbc = null; //定义数据库连接类
    private ISeriesDAO dao = null;  //声明DAO对象

    public SeriesDAOProxy() throws Exception {
        this.dbc = new DataBaseConnection();
        this.dao = new SeriesDAOImpl(this.dbc.getConnection());
    }

    @Override
    public List<Series> getAll(int mode) throws Exception {
        List<Series> list = null;
        try {
            list = this.dao.getAll(mode);
        } catch (Exception e) {
            throw e;
        } finally {
            this.dbc.close();
        }
        return list;
    }

}
