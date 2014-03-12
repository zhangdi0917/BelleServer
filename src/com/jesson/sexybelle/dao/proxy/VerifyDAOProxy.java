package com.jesson.sexybelle.dao.proxy;

import com.jesson.sexybelle.dao.DataBaseConnection;
import com.jesson.sexybelle.dao.IVerifyDAO;
import com.jesson.sexybelle.dao.impl.VerifyDAOImpl;

/**
 * Created by zhangdi on 14-3-10.
 */
public class VerifyDAOProxy implements IVerifyDAO {

    private DataBaseConnection dbc = null; //定义数据库连接类
    private IVerifyDAO dao = null;  //声明DAO对象

    public VerifyDAOProxy() throws Exception {
        this.dbc = new DataBaseConnection();
        this.dao = new VerifyDAOImpl(this.dbc.getConnection());
    }

    @Override
    public boolean verifyAppid(String appid) throws Exception {
        boolean flag = false;
        try {
            flag = this.dao.verifyAppid(appid);
        } catch (Exception e) {
            throw e;
        } finally {
            this.dbc.close();
        }
        return flag;
    }

}
