package com.jesson.sexybelle.dao.proxy;

import com.jesson.sexybelle.dao.DataBaseConnection;
import com.jesson.sexybelle.dao.IBelleDAO;
import com.jesson.sexybelle.dao.impl.BelleDAOImpl;
import com.jesson.sexybelle.model.Belle;

import java.util.List;

/**
 * Created by zhangdi on 14-3-6.
 */
public class BelleDAOProxy implements IBelleDAO {

    private DataBaseConnection dbc = null; //定义数据库连接类
    private IBelleDAO dao = null;  //声明DAO对象

    public BelleDAOProxy() throws Exception {
        this.dbc = new DataBaseConnection();
        this.dao = new BelleDAOImpl(this.dbc.getConnection());
    }

    @Override
    public boolean insert(Belle belle) throws Exception {
        boolean flag = false;
        try {
            flag = this.dao.insert(belle);
        } catch (Exception e) {
            throw e;
        } finally {
            this.dbc.close();
        }
        return flag;
    }

    @Override
    public boolean insertAll(List<Belle> list) throws Exception {
        boolean flag = false;
        try {
            flag = this.dao.insertAll(list);
        } catch (Exception e) {
            throw e;
        } finally {
            this.dbc.close();
        }
        return flag;
    }

    @Override
    public List<Belle> findAllByType(int type, long id, int count) throws Exception {
        List<Belle> list = null;
        try {
            list = this.dao.findAllByType(type, id, count);
        } catch (Exception e) {
            throw e;
        } finally {
            this.dbc.close();
        }
        return list;
    }

    @Override
    public List<Belle> randomFindByType(int type, int count) throws Exception {
        List<Belle> list = null;
        try {
            list = this.dao.randomFindByType(type, count);
        } catch (Exception e) {
            throw e;
        } finally {
            this.dbc.close();
        }
        return list;
    }
}
