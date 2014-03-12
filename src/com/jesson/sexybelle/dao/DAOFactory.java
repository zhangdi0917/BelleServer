package com.jesson.sexybelle.dao;

import com.jesson.sexybelle.dao.proxy.BelleDAOProxy;
import com.jesson.sexybelle.dao.proxy.SeriesDAOProxy;
import com.jesson.sexybelle.dao.proxy.VerifyDAOProxy;

/**
 * 工厂类方法
 */

public class DAOFactory {

    public static IBelleDAO getIBelleDAOInstance() throws Exception {
        return new BelleDAOProxy();
    }

    public static IVerifyDAO getIVerifyDAOInstance() throws Exception {
        return new VerifyDAOProxy();
    }

    public static ISeriesDAO getISeriesDAOInstance() throws Exception {
        return new SeriesDAOProxy();
    }
}