package com.jesson.sexybelle.dao;

import com.jesson.sexybelle.model.Series;

import java.util.List;

/**
 * Created by zhangdi on 14-3-11.
 */
public interface ISeriesDAO {

    public List<Series> getAll(int mode) throws Exception;

}
